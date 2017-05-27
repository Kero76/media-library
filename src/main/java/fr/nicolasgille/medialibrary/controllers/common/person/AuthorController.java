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
package fr.nicolasgille.medialibrary.controllers.common.person;

import fr.nicolasgille.medialibrary.daos.common.person.AuthorRepository;
import fr.nicolasgille.medialibrary.exception.common.person.AuthorException;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Controller of Author model object.
 *
 * This class control the access of the author on the project.
 * In fact, it define CRUD method to interact with the model and the persistence model.
 * It can update in the future to add new methods like getXXX requests.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * DAO object used to interact with Actor on Database.
     *
     * @since 1.0
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Logger for debugging app.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Get all Actors on persistent system.
     *
     * @return
     *  A list of all actors present on persistent system or an error HTTP : NO_CONTENT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/authors/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
    }

    /**
     * Return a author by his first name and last name.
     *
     * This method return a ResponseEntity with the movie retrieve from the Database.
     * If the database research don't retrieve the author, this method return an HTTP error.
     * This method can call by GET request and take two arguments on url which represent the first and the last name of the Author.
     * So, these arguments get from the URL are encoded and it necessary to decoded them before search author on Database.
     *
     * @param fNameEncoded
     *  First name of the author encoding in UTF8.
     * @param lNameEncoded
     *  Last name of the author encoding in UTF8.
     * @return
     *  A ResponseEntity with the author found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/search/author", method = RequestMethod.GET)
    public ResponseEntity<?> getAuthorByFirstNameAndLastName(@RequestParam(name = "fname") String fNameEncoded, @RequestParam(name = "lname") String lNameEncoded) throws UnsupportedEncodingException {
        String fName = URLDecoder.decode(fNameEncoded, AuthorController.ENCODING);
        String lName = URLDecoder.decode(lNameEncoded, AuthorController.ENCODING);
        logger.info("Fetching Author named {} {}", fName, lName);
        Author author = authorRepository.findByFirstNameAndLastName(fName, lName);
        if (author == null) {
            logger.error("Author named {} {} not found on Database", fName, lName);
            return new ResponseEntity<Object>(new AuthorException("Author named " + fName + " " + lName + " not found on Database"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Author>(author, HttpStatus.OK);
    }
}
