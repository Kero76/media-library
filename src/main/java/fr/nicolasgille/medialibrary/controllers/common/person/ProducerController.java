/*
 * MediaLibrary.
 * Copyright (C) 2017 Nicolas GILLE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.nicolasgille.medialibrary.controllers.common.person;

import fr.nicolasgille.medialibrary.exceptions.common.person.ProducerException;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.repositories.common.person.ProducerRepository;
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
 * Controller of Producer model object.
 * <p>
 * This class control the access of the producer on the project.
 * It define some method to search producer present on the database.
 * You can add your own method to search or interact with producer if you like.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.1
 */
@RestController
@RequestMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducerController {

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Repository used to interact with producers present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private ProducerRepository producerRepository;

    /**
     * Get all producers from the database.
     * <p>
     * If the database is empty, it return a response with the following code HTTP : 204.
     * In other case, it return all producers present on database.
     *
     * @return A list of all producers present on persistent system or an error HTTP : NO_CONTENT.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/producers/",
                    method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Producer> producers = producerRepository.findAll();
        if (producers.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Producer>>(producers, HttpStatus.OK);
    }

    /**
     * Return a producer by his first name and his last name.
     * <p>
     * This method return an producer by his nam and his last name.
     * If the producer is not found on database, it return a HTTP response with the error code 204.
     * In other case, this method return the producer on the body of the HTTP response.
     * The first name and the last name must encoded in UTF-8 to avoid problems with special characters,
     * and must present as parameter of the url like the following format :
     * <code>/media-library/search/producers?fname=XXX&lname=YYY</code>
     *
     * @param fNameEncoded First name of the producer encoding in UTF8.
     * @param lNameEncoded Last name of the producer encoding in UTF8.
     *
     * @return A ResponseEntity with the producer found on Database, or an error HTTP 204 : No Content.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/search/producers",
                    method = RequestMethod.GET)
    public ResponseEntity<?> getProducerByFirstNameAndLastName(@RequestParam(name = "fname") String fNameEncoded,
                                                               @RequestParam(name = "lname") String lNameEncoded)
            throws UnsupportedEncodingException {
        String fName = URLDecoder.decode(fNameEncoded, ProducerController.ENCODING);
        String lName = URLDecoder.decode(lNameEncoded, ProducerController.ENCODING);
        logger.info("Fetching Producer named {} {}", fName, lName);
        Producer producer = producerRepository.findByFirstNameAndLastName(fName, lName);
        if (producer == null) {
            logger.error("Producer named {} {} not found on Database", fName, lName);
            return new ResponseEntity<Object>(
                    new ProducerException("Producer named " + fName + " " + lName + " not found on Database"),
                    HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Producer>(producer, HttpStatus.OK);
    }
}
