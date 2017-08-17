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

import fr.nicolasgille.medialibrary.repositories.common.person.SingerRepository;
import fr.nicolasgille.medialibrary.exceptions.common.person.SingerException;
import fr.nicolasgille.medialibrary.models.common.person.Singer;
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
 * Controller of singer model object.
 *
 * This class control the access of the singer on the project.
 * It define some method to search singer present on the database.
 * You can add your own method to search or interact with singer if you like.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class SingerController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Repository used to interact with singers present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private SingerRepository singerRepository;

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(SingerController.class);

    /**
     * Get all singers from the database.
     *
     * If the database is empty, it return a response with the following code HTTP : 204.
     * In other case, it return all singers present on database.
     *
     * @return
     *  A list of all singers present on persistent system or an error HTTP : NO_CONTENT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/singers/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Singer> singers = singerRepository.findAll();
        if (singers.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Singer>>(singers, HttpStatus.OK);
    }

    /**
     * Return a singer by his first name and his last name.
     *
     * This method return an singer by his nam and his last name.
     * If the singer is not found on database, it return a HTTP response with the error code 204.
     * In other case, this method return the singer on the body of the HTTP response.
     * The first name and the last name must encoded in UTF-8 to avoid problems with special characters,
     * and must present as parameter of the url like the following format :
     * <code>//search/singers?fname=XXX&lname=YYY</code>
     *
     * @param fNameEncoded
     *  First name of the singer encoding in UTF8.
     * @param lNameEncoded
     *  Last name of the singer encoding in UTF8.
     * @return
     *  A ResponseEntity with the singer found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/search/singers", method = RequestMethod.GET)
    public ResponseEntity<?> getSingerByFirstNameAndLastName(@RequestParam(name = "fname") String fNameEncoded, @RequestParam(name = "lname") String lNameEncoded) throws UnsupportedEncodingException {
        String fName = URLDecoder.decode(fNameEncoded, SingerController.ENCODING);
        String lName = URLDecoder.decode(lNameEncoded, SingerController.ENCODING);
        logger.info("Fetching Singer named {} {}", fName, lName);
        Singer singer = singerRepository.findByFirstNameAndLastName(fName, lName);
        if (singer == null) {
            logger.error("Singer named {} {} not found on Database", fName, lName);
            return new ResponseEntity<Object>(new SingerException("Singer named " + fName + " " + lName + " not found on Database"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Singer>(singer, HttpStatus.OK);
    }
}
