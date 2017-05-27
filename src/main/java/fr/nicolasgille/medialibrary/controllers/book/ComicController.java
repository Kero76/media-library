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

import fr.nicolasgille.medialibrary.daos.book.ComicRepository;
import fr.nicolasgille.medialibrary.daos.common.company.PublisherRepository;
import fr.nicolasgille.medialibrary.daos.common.person.AuthorRepository;
import fr.nicolasgille.medialibrary.daos.common.person.IllustratorRepository;
import fr.nicolasgille.medialibrary.exception.book.ComicException;
import fr.nicolasgille.medialibrary.models.book.Comic;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.models.common.person.Illustrator;
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
 * Controller of the app to interact with comics present on Media-Library.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComicController {

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
    private ComicRepository comicRepository;

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
     * DAO used to interact with the illustrators present on media-library.
     *
     * @since 1.0
     */
    @Autowired
    private IllustratorRepository illustratorRepository;

    /**
     * Logger for debugging app.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(ComicController.class);

    /**
     * Return all comics found on Database.
     *
     * This method return a ResponseEntity object who contains a list of comics found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all comics found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/comics/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Comic> comics = comicRepository.findAll();
        if (comics.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comic>>(comics, HttpStatus.OK);
    }

    /**
     * Return a comic by his title.
     *
     * This method return a ResponseEntity with the comic retrieve from the Database.
     * If the database research don't retrieve the comic, this method return an HTTP error.
     * This method can call by GET request and take an path variable the title of the comic at research.
     * So, the title retrieve from the URL is encoded and it necessary to decoded it before search comic on Database.
     *
     * @param titleEncoded
     *  Title of the comic encoded to search on Database.
     * @return
     *  A ResponseEntity with the comic found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/comics/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getComicByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, ComicController.ENCODING);
        logger.info("Fetching Comic with title {}", title);
        Comic comic = comicRepository.findByTitleIgnoreCase(title);
        if (comic == null) {
            logger.error("Comic with title {} not found.", title);
            return new ResponseEntity<Object>(new ComicException("Comic with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Comic>(comic, HttpStatus.OK);
    }

    /**
     * Add a comic on the Database.
     *
     * Before added the comic on database, it check if the comic is already present on the database.
     * And if the comic is present, the method return an error HTTP 409 : CONFLICT.
     * This method can call only by a POST request and take on BODY the comic at insert on Database.
     *
     * @param comic
     *  Comic at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on comic page.
     * @return
     *  A ResponseEntity with the comic added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/comics/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Comic comic, UriComponentsBuilder uriBuilder) {
        logger.info("Created comic : {}", comic);

        // Check if the comic already exist on database.
        Comic comicExist = comicRepository.findByTitleIgnoreCase(comic.getTitle());
        if (comicExist != null) {
            logger.error("Unable to create. The comic {} already exist", comic.getTitle());
            return new ResponseEntity<ComicException>(new ComicException("Unable to create. The comic " + comic.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the author are present on Database or not.
        Set<Author> authorsOnComic = comic.getAuthors();
        Set<Author> authors = new HashSet<Author>();
        for (Author a : authorsOnComic) {
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
        comic.setAuthors(authors);

        // Check if the publisher are present on Database or not.
        Set<Publisher> publishersOnComic = comic.getPublisher();
        Set<Publisher> publishers = new HashSet<Publisher>();
        for (Publisher p : publishersOnComic) {
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
        comic.setPublisher(publishers);

        // Check if the illustrator are present on Database or not.
        Set<Illustrator> illustratorsOnComic = comic.getIllustrators();
        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        for (Illustrator a : illustratorsOnComic) {
            Illustrator illustratorExist = illustratorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the illustrator is not present on Database, it add on it.
            if (illustratorExist == null) {
                logger.info("Created illustrator : {}", a);
                illustratorRepository.save(a);
                illustrators.add(a);
            } else {
                logger.info("Added illustrator {} already present on persistent system", illustratorExist);
                illustrators.add(illustratorExist);
            }
        }
        comic.setIllustrators(illustrators);
        comicRepository.save(comic);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/comics/search/title/{id}").buildAndExpand(comic.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a comic present on the Database.
     *
     * This method update a comic present on database only if this comic is present on it.
     * In other case, this method return a HTTP error 404 : Not Found.
     * This method can call only by PUT method and take the id of the comic at update on path variable
     * and the object comic with the new content on BODY.
     *
     * @param id
     *  Id of the comic on Database.
     * @param comic
     *  Comic with new content at update.
     * @return
     *  A ResponseEntity with all comics found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/comics/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Comic comic) {
        logger.info("Updating Comic with id {}", id);

        Comic comicAtUpdate = comicRepository.findOne(id);
        if (comicAtUpdate == null) {
            logger.error("Unable to update. Comic with id {} not found", id);
            return new ResponseEntity<Object>(new ComicException("Unable to update. Comic with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the author are present on Database or not.
        Set<Author> authorsOnComic = comic.getAuthors();
        Set<Author> authors = new HashSet<Author>();
        for (Author a : authorsOnComic) {
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
        comic.setAuthors(authors);

        // Check if the publisher are present on Database or not.
        Set<Publisher> publishersOnComic = comic.getPublisher();
        Set<Publisher> publishers = new HashSet<Publisher>();
        for (Publisher p : publishersOnComic) {
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
        comic.setPublisher(publishers);

        // Check if the illustrator are present on Database or not.
        Set<Illustrator> illustratorsOnComic = comic.getIllustrators();
        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        for (Illustrator a : illustratorsOnComic) {
            Illustrator illustratorExist = illustratorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the illustrator is not present on Database, it add on it.
            if (illustratorExist == null) {
                logger.info("Created illustrator : {}", a);
                illustratorRepository.save(a);
                illustrators.add(a);
            } else {
                logger.info("Added illustrator {} already present on persistent system", illustratorExist);
                illustrators.add(illustratorExist);
            }
        }
        comic.setIllustrators(illustrators);

        // Copy content of the comic receive on request body on the comic retrieve from the database.
        comicAtUpdate = new Comic(comic);
        comicRepository.save(comicAtUpdate);
        return new ResponseEntity<Object>(comicAtUpdate, HttpStatus.OK);
    }

    /**
     * Removed a comic from the Database.
     *
     * This method remove a comic from the database only if the comic is present on the Database.
     * It return an error HTTP 404 : NOT FOUND if the comic at deleted isn't present on the database.
     * To call this method, you can pass on the url the id of the comic at remove
     * and this method can call only with DELETE request.
     *
     * @param id
     *  Id of the comic at delete.
     * @return
     *  A ResponseEntity with all comics found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/comics/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Comic with id {}", id);

        Comic comic = comicRepository.findOne(id);
        if (comic == null) {
            logger.error("Unable to delete. Comic with id {} not found", id);
            return new ResponseEntity<Object>(new ComicException("Unable to delete. Comic with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        comicRepository.delete(comic);
        return new ResponseEntity<Object>(comic, HttpStatus.OK);
    }
}
