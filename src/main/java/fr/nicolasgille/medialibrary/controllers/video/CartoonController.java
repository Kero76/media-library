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

import fr.nicolasgille.medialibrary.exceptions.video.CartoonException;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.video.Cartoon;
import fr.nicolasgille.medialibrary.repositories.common.person.DirectorRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.ProducerRepository;
import fr.nicolasgille.medialibrary.repositories.video.CartoonRepository;
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
 * Controller of the app to interact with cartoons present on Media-Library.
 * You can use CRUD method to insert, delete, update or select cartoon from Database.
 * So, many methods about research are available on the controller to search cartoon with different way of search.
 * You can add you own method of research if you would have a new research type of cartoon.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.3
 * @version 1.1.1
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
     * Repository used to interact with cartoons present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private CartoonRepository cartoonRepository;

    /**
     * Repository used to interact with producers present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private ProducerRepository producerRepository;

    /**
     * Repository used to interact with directors present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private DirectorRepository directorRepository;

    /**
     * Logger to get information during some process.
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
     * If the database doesn't get the cartoon, this method return an HTTP error : 204.
     * In other case, this method return the cartoon found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the cartoon at research.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used on search process.
     *
     * @param titleEncoded
     *  Title of the cartoon encoded to search on Database.
     * @return
     *  A ResponseEntity with the cartoon found on Database, or an error HTTP 204 : No Content.
     * @throws UnsupportedEncodingException
     *  The method throw an <code>UnsupportedEncodingException</code> when a problem occurred during title decoding.
     * @since 1.0
     * @version 1.0.1
     */
    @RequestMapping(value = "/cartoons/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getCartoonByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, CartoonController.ENCODING);
        logger.info("Fetching Cartoon with title {}", title);
        List<Cartoon> cartoons = cartoonRepository.findByTitleIgnoreCaseContaining(title);
        if (cartoons == null) {
            logger.error("Cartoon with title {} not found.", title);
            return new ResponseEntity<Object>(new CartoonException("Cartoon with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Cartoon>>(cartoons, HttpStatus.OK);
    }

    /**
     * Return a cartoon by his identifier.
     *
     * This method return a ResponseEntity with the cartoon retrieve from the Database.
     * If the database doesn't get the cartoon, this method return an HTTP error : 204.
     * In other case, this method return the cartoon found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the identifier of the cartoon at research.
     *
     * @param id
     *  Identifier of the cartoon on Database.
     * @return
     *  A ResponseEntity with the cartoon found on Database, or an error HTTP 204 : No Content.
     * @since 1.1
     * @version 1.0
     */
    @RequestMapping(value = "/cartoons/search/id/{id}")
    public ResponseEntity<?> getCartoonById(@PathVariable(value = "id") long id) {
        logger.info("Fetching Cartoon with id {}", id);
        Cartoon cartoon = cartoonRepository.findOne(id);
        if (cartoon == null) {
            logger.error("Cartoon with id {} not found.", id);
            return new ResponseEntity<Object>(new CartoonException("Cartoon with id " + id + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Cartoon>(cartoon, HttpStatus.OK);
    }

    /**
     * Add a cartoon on the Database.
     *
     * Before added the cartoon on database, it check if the cartoon is already present on the database.
     * So, if the cartoon is present, the method return an error HTTP 409 : CONFLICT.
     * In other case, it return the code HTTP 200 and an uri to get information about the new cartoon insert.
     * So, this method is call by POST method and take the cartoon at insert on the BODY request.
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
        logger.info("Created cartoon : {}", cartoon);

        // Check if the series already exist on database.
        Cartoon cartoonExist = cartoonRepository.findByTitleAndRuntimeAndReleaseDate(cartoon.getTitle(), cartoon.getRuntime(), cartoon.getReleaseDate());
        if (cartoonExist!= null) {
            logger.error("Unable to create. The cartoon {} already exist", cartoon.getTitle());
            return new ResponseEntity<CartoonException>(new CartoonException("Unable to create. The cartoon " + cartoon.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = cartoon.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnMovie) {
            Producer producerExist = producerRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
            // If the producer is not present on Database, he add on it.
            if (producerExist == null) {
                logger.info("Created producer : {}", p);
                producerRepository.save(p);
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
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist  == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist );
                directors.add(directorExist);
            }
        }
        cartoon.setDirectors(directors);
        cartoonRepository.save(cartoon);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/cartoons/search/id/{id}").buildAndExpand(cartoon.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a cartoon present on the Database.
     *
     * It update a cartoon only if found on database.
     * It the cartoon is not found, the method return an error with the HTTP code 404.
     * In other case, it update the information about the cartoon and return in the body the cartoon update
     * can use to check if the modification are succeeded and the HTTP code 200.
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
            return new ResponseEntity<Object>(new CartoonException("Unable to update. Cartoon with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = cartoon.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnMovie) {
            Producer producerExist = producerRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
            // If the producer is not present on Database, he add on it.
            if (producerExist == null) {
                logger.info("Created producer : {}", p);
                producerRepository.save(p);
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
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist  == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
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
     * Remove a cartoon from the Database.
     *
     * It remove a cartoon if it found on database.
     * If the cartoon is not found on database, this method return an error and the HTTP code 404.
     * Otherwise, the method delete the cartoon thanks to the identifier and return in the body the cartoon deleted
     * and the code HTTP 200 to confirm the success of the deletion.
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
            return new ResponseEntity<Object>(new CartoonException("Unable to delete. Cartoon with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        cartoonRepository.delete(cartoon);
        return new ResponseEntity<Object>(cartoon, HttpStatus.OK);
    }
}
