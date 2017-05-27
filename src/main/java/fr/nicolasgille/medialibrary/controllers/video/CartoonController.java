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
package fr.nicolasgille.medialibrary.controllers.video;

import fr.nicolasgille.medialibrary.daos.video.CartoonRepository;
import fr.nicolasgille.medialibrary.daos.common.person.DirectorDAO;
import fr.nicolasgille.medialibrary.daos.common.person.ProducerDAO;
import fr.nicolasgille.medialibrary.exception.video.CartoonException;
import fr.nicolasgille.medialibrary.exception.video.MovieException;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.video.Cartoon;
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
 * Controller of Cartoon model object.
 *
 * This class control the access of the series on the project.
 * In fact, it define CRUD method to interact with the model and the persistence system.
 * It can update in the future to add new methods like getXXX requests.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.3
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartoonController {
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
    private CartoonRepository cartoonRepository;

    /**
     * DAO used to interact with the table <code>common_producers</code>.
     *
     * @since 1.0
     */
    @Autowired
    private ProducerDAO producerDAO;

    /**
     * DAO used to interact with the table <code>common_director</code>.
     *
     * @since 1.0
     */
    @Autowired
    private DirectorDAO directorDAO;

    /**
     * Logger for debugging app.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(CartoonController.class);


    /**
     * Return all cartoons found on Database.
     *
     * This method return a ResponseEntity object who contains a list of cartoons found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all cartoons found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/cartoons/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Cartoon> cartoons = cartoonRepository.findAll();
        if (cartoons.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Cartoon>>(cartoons, HttpStatus.OK);
    }

    /**
     * Return a cartoon by his title.
     *
     * This method return a ResponseEntity with the cartoon retrieve from the Database.
     * If the database research don't retrieve the cartoon, this method return an HTTP error.
     * This method can call by GET request and take an path variable the title of the cartoon at research.
     * So, the title retrieve from the URL is encoded and it necessary to decoded it before search cartoon on Database.
     *
     * @param titleEncoded
     *  Title of the cartoon encoded to search on Database.
     * @return
     *  A ResponseEntity with the cartoon found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/cartoons/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getCartoonByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, CartoonController.ENCODING);
        logger.info("Fetching Cartoon with title {}", title);
        Cartoon cartoon = cartoonRepository.findByTitleIgnoreCase(title);
        if (cartoon == null) {
            logger.error("Cartoon with title {} not found.", title);
            return new ResponseEntity<Object>(new MovieException("Cartoon with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Cartoon>(cartoon, HttpStatus.OK);
    }

    /**
     * Add a cartoon on the Database.
     *
     * Before added the cartoon on database, it check if the cartoon is already present on the database.
     * And if the cartoon is present, the method return an error HTTP 409 : CONFLICT.
     * This method can call only by a POST request and take on BODY the cartoon at insert on Database.
     *
     * @param cartoon
     *  Series at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on cartoon page.
     * @return
     *  A ResponseEntity with the cartoon added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.1
     */
    @RequestMapping(value = "/cartoons/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Cartoon cartoon, UriComponentsBuilder uriBuilder) {
        logger.info("Created series : {}", cartoon);

        // Check if the series already exist on database.
        Cartoon cartoonExist = cartoonRepository.findByTitleIgnoreCase(cartoon.getTitle());
        if (cartoonExist!= null) {
            logger.error("Unable to create. The cartoon {} already exist", cartoon.getTitle());
            return new ResponseEntity<CartoonException>(new CartoonException("Unable to create. The cartoon " + cartoon.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = cartoon.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnMovie) {
            Producer producerExist = producerDAO.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
            // If the producer is not present on Database, he add on it.
            if (producerExist == null) {
                logger.info("Created producer : {}", p);
                producerDAO.save(p);
                producers.add(p);
            } else {
                logger.info("Added producer {} already present on persistent system.", producerExist);
                producers.add(producerExist);
            }
        }
        cartoon.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnMovie = cartoon.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnMovie) {
            Director directorExist = directorDAO.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist  == null) {
                logger.info("Created director : {}", d);
                directorDAO.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist );
                directors.add(directorExist);
            }
        }
        cartoon.setDirectors(directors);
        cartoonRepository.save(cartoon);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/cartoons/search/title/{id}").buildAndExpand(cartoon.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a cartoon present on the Database.
     *
     * This method update a cartoon present on database only if this cartoon is present on it.
     * In other case, this method return a HTTP error 404 : Not Found.
     * This method can call only by PUT method and take the id of the cartoon at update on path variable
     * and the object series with the new content on BODY.
     *
     * @param id
     *  Id of the cartoon on Database.
     * @param cartoon
     *  Cartoon with new content at update.
     * @return
     *  A ResponseEntity with all cartoon found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/cartoons/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Cartoon cartoon) {
        logger.info("Updating Cartoon with id {}", id);

        Cartoon cartoonAtUpdate = cartoonRepository.findOne(id);
        if (cartoonAtUpdate == null) {
            logger.error("Unable to update. Cartoon with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to update. Cartoon with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = cartoon.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnMovie) {
            Producer producerExist = producerDAO.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
            // If the producer is not present on Database, he add on it.
            if (producerExist == null) {
                logger.info("Created producer : {}", p);
                producerDAO.save(p);
                producers.add(p);
            } else {
                logger.info("Added producer {} already present on persistent system.", producerExist);
                producers.add(producerExist);
            }
        }
        cartoon.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnMovie = cartoon.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnMovie) {
            Director directorExist = directorDAO.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist  == null) {
                logger.info("Created director : {}", d);
                directorDAO.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist );
                directors.add(directorExist);
            }
        }
        cartoon.setDirectors(directors);

        // Copy content of the cartoon receive on request body on the cartoon retrieve from the database.
        cartoonAtUpdate = new Cartoon(cartoon);
        cartoonRepository.save(cartoonAtUpdate);
        return new ResponseEntity<Object>(cartoonAtUpdate, HttpStatus.OK);
    }

    /**
     * Removed a cartoon from the Database.
     *
     * This method remove a cartoon from the database only if the cartoon is present on the Database.
     * It return an error HTTP 404 : NOT FOUND if the cartoon at deleted isn't present on the database.
     * To call this method, you can pass on the url the id of the cartoon at remove
     * and this method can call only with DELETE request.
     *
     * @param id
     *  Id of the cartoon at delete.
     * @return
     *  A ResponseEntity with all cartoon found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/cartoons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Cartoon with id {}", id);

        Cartoon cartoon = cartoonRepository.findOne(id);
        if (cartoon == null) {
            logger.error("Unable to delete. Cartoon with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to delete. Cartoon with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        cartoonRepository.delete(cartoon);
        return new ResponseEntity<Object>(cartoon, HttpStatus.OK);
    }
}
