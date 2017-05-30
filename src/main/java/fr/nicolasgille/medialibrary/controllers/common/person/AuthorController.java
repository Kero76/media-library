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

import fr.nicolasgille.medialibrary.repositories.common.person.AuthorRepository;
import fr.nicolasgille.medialibrary.exceptions.common.person.AuthorException;
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
 * It define some method to search author present on the database.
 * You can add your own method to search or interact with author if you like.
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
     * Repository used to interact with authors present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Get all authors from the database.
     *
     * If the database is empty, it return a response with the following code HTTP : 204.
     * In other case, it return all authors present on database.
     *
     * @return
     *  A list of all authors present on persistent system or an error HTTP : NO_CONTENT.
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
     * Return an author by his first name and his last name.
     *
     * This method return an author by his nam and his last name.
     * If the author is not found on database, it return a HTTP response with the error code 204.
     * In other case, this method return the author on the body of the HTTP response.
     * The first name and the last name must encoded in UTF-8 to avoid problems with special characters,
     * and must present as parameter of the url like the following format :
     * <code>/media-library/search/authors?fname=XXX&lname=YYY</code>
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
    @RequestMapping(value = "/search/authors", method = RequestMethod.GET)
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
