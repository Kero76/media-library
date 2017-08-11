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

import fr.nicolasgille.medialibrary.exceptions.book.BookException;
import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.repositories.book.BookRepository;
import fr.nicolasgille.medialibrary.repositories.common.company.PublisherRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.AuthorRepository;
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
 * You can use CRUD method to insert, delete, update or select book from Database.
 * So, many methods about research are available on the controller to search book with different way of search.
 * You can add you own method of research if you would have a new research type of book.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.1.1
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
     * Repository used to interact with book present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * Repository used to interact with authors present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Repository used to interact with publishers present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private PublisherRepository publisherRepository;

    /**
     * Logger to get information during some process.
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
     * Return a list of books by his title.
     *
     * This method return a ResponseEntity with the book retrieve from the Database.
     * If the database doesn't get the book, this method return an HTTP error : 204.
     * In other case, this method return the book found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the book at research.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used on search process.
     *
     * @param titleEncoded
     *  Title of the book encoded to search on Database.
     * @return
     *  A ResponseEntity with the all books found on Database, or an error HTTP 204 : No Content.
     * @throws UnsupportedEncodingException
     *  The method throw an <code>UnsupportedEncodingException</code> when a problem occurred during title decoding.
     * @since 1.0
     * @version 1.0.1
     */
    @RequestMapping(value = "/books/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getBooksByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, BookController.ENCODING);
        logger.info("Fetching Book with title {}", title);
        List<Book> books = bookRepository.findByTitleIgnoreCaseContaining(title);
        if (books == null) {
            logger.error("Book(s) with title {} not found.", title);
            return new ResponseEntity<Object>(new BookException("Book(s) with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    /**
     * Return a book by his identifier.
     *
     * This method return a ResponseEntity with the book retrieve from the Database.
     * If the database doesn't get the book, this method return an HTTP error : 204.
     * In other case, this method return the book found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the identifier of the book at research.
     *
     * @param id
     *  Identifier of the Book on Database.
     * @return
     *  A ResponseEntity with the book found on Database, or an error HTTP 204 : No Content.
     * @since 1.1
     * @version 1.0
     */
    @RequestMapping(value = "/books/search/id/{id}")
    public ResponseEntity<?> getBookById(@PathVariable(value = "id") long id) {
        logger.info("Fetching Book with id {}", id);
        Book book = bookRepository.findOne(id);
        if (book == null) {
            logger.error("Book with id {} not found.", id);
            return new ResponseEntity<Object>(new BookException("Book with id " + id + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    /**
     * Add a book on the Database.
     *
     * Before added the book on database, it check if the book is already present on the database.
     * So, if the book is present, the method return an error HTTP 409 : CONFLICT.
     * In other case, it return the code HTTP 200 and an uri to get information about the new book insert.
     * So, this method is call by POST method and take the book at insert on the BODY request.
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
        Book bookExist = bookRepository.findByTitleAndReleaseDate(book.getTitle(), book.getReleaseDate());
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
        Set<Publisher> publishersOnBook = book.getPublishers();
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
        book.setPublishers(publishers);
        bookRepository.save(book);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/books/search/id/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a book present on the Database.
     *
     * It update a book only if found on database.
     * It the book is not found, the method return an error with the HTTP code 404.
     * In other case, it update the information about the book and return in the body the book update
     * can use to check if the modification are succeeded and the HTTP code 200.
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
        Set<Publisher> publishersOnBook = book.getPublishers();
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
        book.setPublishers(publishers);

        // Copy content of the book receive on request body on the book retrieve from the database.
        bookAtUpdate = new Book(book);
        bookRepository.save(bookAtUpdate);
        return new ResponseEntity<Object>(bookAtUpdate, HttpStatus.OK);
    }

    /**
     * Remove a book from the Database.
     *
     * It remove a book if it found on database.
     * If the book is not found on database, this method return an error and the HTTP code 404.
     * Otherwise, the method delete the book thanks to the identifier and return in the body the book deleted
     * and the code HTTP 200 to confirm the success of the deletion.
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
