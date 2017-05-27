/*
 * This file is part of Media-Library.
 *
 * Media-Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Media-Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Media-Library. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.nicolasgille.medialibrary.controllers.book;

import fr.nicolasgille.medialibrary.daos.book.BookRepository;
import fr.nicolasgille.medialibrary.daos.common.company.PublisherRepository;
import fr.nicolasgille.medialibrary.daos.common.person.AuthorRepository;
import fr.nicolasgille.medialibrary.exception.book.BookException;
import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller of the app to interact with books present on Media-Library.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * DAO used to interact with the table <code>media</code> present on Database.
     *
     * @since 1.0
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * DAO used to interact with the authors present on media-library.
     *
     * @since 1.0
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * DAO used to interact with the publisher present on media-library.
     *
     * @since 1.0
     */
    @Autowired
    private PublisherRepository publisherRepository;

    /**
     * Logger for debugging app.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(BookController.class);

    /**
     * Return all books found on Database.
     *
     * This method return a ResponseEntity object who contains a list of books found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all books found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/books/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    /**
     * Return a book by his title.
     *
     * This method return a ResponseEntity with the book retrieve from the Database.
     * If the database research don't retrieve the book, this method return an HTTP error.
     * This method can call by GET request and take an path variable the title of the book at research.
     * So, the title retrieve from the URL is encoded and it necessary to decoded it before search book on Database.
     *
     * @param titleEncoded
     *  Title of the book encoded to search on Database.
     * @return
     *  A ResponseEntity with the book found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/books/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getBookByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, BookController.ENCODING);
        logger.info("Fetching Book with title {}", title);
        Book book = bookRepository.findByTitleIgnoreCase(title);
        if (book == null) {
            logger.error("Book with title {} not found.", title);
            return new ResponseEntity<Object>(new BookException("Book with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    /**
     * Add a book on the Database.
     *
     * Before added the book on database, it check if the book is already present on the database.
     * And if the book is present, the method return an error HTTP 409 : CONFLICT.
     * This method can call only by a POST request and take on BODY the book at insert on Database.
     *
     * @param book
     *  Book at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on book page.
     * @return
     *  A ResponseEntity with the book added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/books/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Book book, UriComponentsBuilder uriBuilder) {
        logger.info("Created book : {}", book);

        // Check if the book already exist on database.
        Book bookExist = bookRepository.findByTitleIgnoreCase(book.getTitle());
        if (bookExist != null) {
            logger.error("Unable to create. The book {} already exist", book.getTitle());
            return new ResponseEntity<BookException>(new BookException("Unable to create. The book " + book.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the author are present on Database or not.
        Set<Author> authorsOnBook = book.getAuthors();
        Set<Author> authors = new HashSet<Author>();
        for (Author a : authorsOnBook) {
            Author authorExist = authorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the author is not present on Database, it add on it.
            if (authorExist == null) {
                logger.info("Created author : {}", a);
                authorRepository.save(a);
                authors.add(a);
            } else {
                logger.info("Added author {} already present on persistent system", authorExist);
                authors.add(authorExist);
            }
        }
        book.setAuthors(authors);

        // Check if the publisher are present on Database or not.
        Set<Publisher> publishersOnBook = book.getPublisher();
        Set<Publisher> publishers = new HashSet<Publisher>();
        for (Publisher p : publishersOnBook) {
            Publisher publisherExist = publisherRepository.findByName(p.getName());
            // If the publisher is not present on Database, it add on it.
            if (publisherExist == null) {
                logger.info("Created publisher : {}", p);
                publisherRepository.save(p);
                publishers.add(p);
            } else {
                logger.info("Added publisher {} already present on persistent system", publisherExist);
                publishers.add(publisherExist);
            }
        }
        book.setPublisher(publishers);
        bookRepository.save(book);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/books/search/title/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a book present on the Database.
     *
     * This method update a book present on database only if this book is present on it.
     * In other case, this method return a HTTP error 404 : Not Found.
     * This method can call only by PUT method and take the id of the book at update on path variable
     * and the object book with the new content on BODY.
     *
     * @param id
     *  Id of the book on Database.
     * @param book
     *  Book with new content at update.
     * @return
     *  A ResponseEntity with all books found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Book book) {
        logger.info("Updating Book with id {}", id);

        Book bookAtUpdate = bookRepository.findOne(id);
        if (bookAtUpdate == null) {
            logger.error("Unable to update. Book with id {} not found", id);
            return new ResponseEntity<Object>(new BookException("Unable to update. Book with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the author are present on Database or not.
        Set<Author> authorsOnBook = book.getAuthors();
        Set<Author> authors = new HashSet<Author>();
        for (Author a : authorsOnBook) {
            Author authorExist = authorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the author is not present on Database, it add on it.
            if (authorExist == null) {
                logger.info("Created author : {}", a);
                authorRepository.save(a);
                authors.add(a);
            } else {
                logger.info("Added author {} already present on persistent system", authorExist);
                authors.add(authorExist);
            }
        }
        book.setAuthors(authors);

        // Check if the publisher are present on Database or not.
        Set<Publisher> publishersOnBook = book.getPublisher();
        Set<Publisher> publishers = new HashSet<Publisher>();
        for (Publisher p : publishersOnBook) {
            Publisher publisherExist = publisherRepository.findByName(p.getName());
            // If the publisher is not present on Database, it add on it.
            if (publisherExist == null) {
                logger.info("Created publisher : {}", p);
                publisherRepository.save(p);
                publishers.add(p);
            } else {
                logger.info("Added publisher {} already present on persistent system", publisherExist);
                publishers.add(publisherExist);
            }
        }
        book.setPublisher(publishers);

        // Copy content of the book receive on request body on the book retrieve from the database.
        bookAtUpdate = new Book(book);
        bookRepository.save(bookAtUpdate);
        return new ResponseEntity<Object>(bookAtUpdate, HttpStatus.OK);
    }

    /**
     * Removed a book from the Database.
     *
     * This method remove a book from the database only if the book is present on the Database.
     * It return an error HTTP 404 : NOT FOUND if the book at deleted isn't present on the database.
     * To call this method, you can pass on the url the id of the book at remove
     * and this method can call only with DELETE request.
     *
     * @param id
     *  Id of the book at delete.
     * @return
     *  A ResponseEntity with all books found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Book with id {}", id);

        Book book = bookRepository.findOne(id);
        if (book == null) {
            logger.error("Unable to delete. Book with id {} not found", id);
            return new ResponseEntity<Object>(new BookException("Unable to delete. Book with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        bookRepository.delete(book);
        return new ResponseEntity<Object>(book, HttpStatus.OK);
    }
}
