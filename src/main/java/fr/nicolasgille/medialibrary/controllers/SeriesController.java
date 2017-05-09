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
package fr.nicolasgille.medialibrary.controllers;

import fr.nicolasgille.medialibrary.daos.SeriesDAO;
import fr.nicolasgille.medialibrary.daos.common.ActorDAO;
import fr.nicolasgille.medialibrary.daos.common.DirectorDAO;
import fr.nicolasgille.medialibrary.daos.common.ProducerDAO;
import fr.nicolasgille.medialibrary.exception.MovieException;
import fr.nicolasgille.medialibrary.exception.SeriesException;
import fr.nicolasgille.medialibrary.models.common.Actor;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import fr.nicolasgille.medialibrary.models.video.Movie;
import fr.nicolasgille.medialibrary.models.video.Series;
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
 * Controller of Series model object.
 *
 * This class control the access of the series on the project.
 * In fact, it define CRUD method to interact with the model and the persistence system.
 * It can update in the future to add new methods like getXXX requests.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class SeriesController {

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
    private SeriesDAO seriesDAO;

    /**
     * DAO used to interact with the table <code>common_actors</code>.
     *
     * @since 1.0
     */
    @Autowired
    private ActorDAO actorDAO;

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
    static final Logger logger = LoggerFactory.getLogger(SeriesController.class);


    /**
     * Return all series found on Database.
     *
     * This method return a ResponseEntity object who contains a list of series found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all series found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/series/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Series> series = seriesDAO.findAll();
        if (series.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Series>>(series, HttpStatus.OK);
    }

    /**
     * Return a series by is title.
     *
     * This method return a ResponseEntity with the series retrieve from the Database.
     * If the database research don't retrieve the series, this method return an HTTP error.
     * This method can call by GET request and take an path variable the title of the series at research.
     * So, the title retrieve from the URL is encoded and it necessary to decoded it before search series on Database.
     *
     * @param titleEncoded
     *  Title of the series encoded to search on Database.
     * @return
     *  A ResponseEntity with the series found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getSeriesByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, SeriesController.ENCODING);
        logger.info("Fetching Series with title {}", title);
        Series series = seriesDAO.findByTitleIgnoreCase(title);
        if (series == null) {
            logger.error("Series with title {} not found.", title);
            return new ResponseEntity<Object>(new MovieException("Series with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Series>(series, HttpStatus.OK);
    }

    /**
     * Add a series on the Database.
     *
     * Before added the series on database, it check if the series is already present on the database.
     * And if the series is present, the method return an error HTTP 409 : CONFLICT.
     * This method can call only by a POST request and take on BODY the series at insert on Database.
     *
     * @param series
     *  Series at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on series page.
     * @return
     *  A ResponseEntity with the series added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/series/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Series series, UriComponentsBuilder uriBuilder) {
        logger.info("Created series : {}", series);

        // Check if the series already exist on database.
        Series seriesExist = seriesDAO.findByTitleIgnoreCase(series.getTitle());
        if (series != null) {
            logger.error("Unable to create. The series {} already exist", series.getTitle());
            return new ResponseEntity<SeriesException>(new SeriesException("Unable to create. The series " + series.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the actors are present on Database or not.
        Set<Actor> actorsOnMovie = series.getMainActors();
        Set<Actor> mainActors = new HashSet<Actor>();
        for (Actor a : actorsOnMovie) {
            Actor actorExist = actorDAO.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the actor is not present on Database, he add on it.
            if (actorExist == null) {
                logger.info("Created actor : {}", a);
                actorDAO.save(a);
                mainActors.add(a);
            } else {
                logger.info("Added actor {} already present on persistent system", actorExist);
                mainActors.add(actorExist);
            }
        }
        series.setMainActors(mainActors);

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = series.getProducers();
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
        series.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnMovie = series.getDirectors();
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
        series.setDirectors(directors);
        seriesDAO.save(series);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/series/search/title/{id}").buildAndExpand(series.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a series present on the Database.
     *
     * This method update a series present on database only if this series is present on it.
     * In other case, this method return a HTTP error 404 : Not Found.
     * This method can call only by PUT method and take the id of the series at update on path variable
     * and the object series with the new content on BODY.
     *
     * @param id
     *  Id of the series on Database.
     * @param series
     *  Series with new content at update.
     * @return
     *  A ResponseEntity with all series found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/series/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Series series) {
        logger.info("Updating Series with id {}", id);

        Series seriesAtUpdate = seriesDAO.findOne(id);
        if (seriesAtUpdate == null) {
            logger.error("Unable to update. Series with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to update. Series with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the actor are present on Database or not.
        Set<Actor> actorsOnMovie = series.getMainActors();
        Set<Actor> mainActors = new HashSet<Actor>();
        for (Actor a : actorsOnMovie) {
            Actor actorExist = actorDAO.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the actor is not present on Database, it add on it.
            if (actorExist == null) {
                logger.info("Created actor : {}", a);
                actorDAO.save(a);
                mainActors.add(a);
            } else {
                logger.info("Added actor {} already present on persistent system", actorExist);
                mainActors.add(actorExist);
            }
        }
        series.setMainActors(mainActors);

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = series.getProducers();
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
        series.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnMovie = series.getDirectors();
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
        series.setDirectors(directors);

        // Copy content of the series receive on request body on the series retrieve from the database.
        seriesAtUpdate = new Series(series);
        seriesDAO.save(seriesAtUpdate);
        return new ResponseEntity<Object>(seriesAtUpdate, HttpStatus.OK);
    }

    /**
     * Removed a series from the Database.
     *
     * This method remove a series from the database only if the series is present on the Database.
     * It return an error HTTP 404 : NOT FOUND if the series at deleted isn't present on the database.
     * To call this method, you can pass on the url the id of the series at remove
     * and this method can call only with DELETE request.
     *
     * @param id
     *  Id of the series at delete.
     * @return
     *  A ResponseEntity with all series found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/series/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Series with id {}", id);

        Series series = seriesDAO.findOne(id);
        if (series == null) {
            logger.error("Unable to delete. Series with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to delete. Series with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        seriesDAO.delete(series);
        return new ResponseEntity<Object>(series, HttpStatus.OK);
    }
}
