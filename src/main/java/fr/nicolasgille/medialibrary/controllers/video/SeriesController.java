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

package fr.nicolasgille.medialibrary.controllers.video;

import fr.nicolasgille.medialibrary.exceptions.video.SeriesException;
import fr.nicolasgille.medialibrary.models.common.person.Actor;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.Series;
import fr.nicolasgille.medialibrary.repositories.common.person.ActorRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.DirectorRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.ProducerRepository;
import fr.nicolasgille.medialibrary.repositories.video.SeriesRepository;
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
 * Controller of the app to interact with series present on Media-Library.
 * You can use CRUD method to insert, delete, update or select series from Database.
 * So, many methods about research are available on the controller to search series with different way of search.
 * You can add you own method of research if you would have a new research type of series.
 *
 * @author Nicolas GILLE
 * @version 1.2
 * @since Media-Library 0.2
 */
@RestController
@RequestMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class SeriesController {

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(SeriesController.class);

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Repository used to interact with series present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private SeriesRepository seriesRepository;

    /**
     * Repository used to interact with actors present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private ActorRepository actorRepository;

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
     * Return all series found on Database.
     * <p>
     * This method return a ResponseEntity object who contains a list of series found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return A ResponseEntity with all series found on Database, or an error HTTP 204 : No Content.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/series/",
                    method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Series> series = seriesRepository.findAll();
        if (series.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Series>>(series, HttpStatus.OK);
    }

    /**
     * Return all series found on Database.
     * <p>
     * This method return a ResponseEntity object who contains a list of series found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @param titleEncoded Title of the series encoded to search on Database.
     *
     * @return A ResponseEntity with the series found on Database, or an error HTTP 204 : No Content.
     *
     * @throws UnsupportedEncodingException The method throw an <code>UnsupportedEncodingException</code> when a
     *         problem occurred during title decoding.
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/series/search/title/{title}",
                    method = RequestMethod.GET)
    public ResponseEntity getSeriesByTitle(@PathVariable(value = "title") String titleEncoded)
            throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, SeriesController.ENCODING);
        logger.info("Fetching Series with title {}", title);
        List<Series> series = seriesRepository.findByTitleIgnoreCaseContaining(title);
        if (series == null) {
            logger.error("Series with title {} not found.", title);
            return new ResponseEntity<Object>(new SeriesException("Series with title " + title + " not found."),
                                              HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Series>>(series, HttpStatus.OK);
    }

    /**
     * Return a series by his title and his current season.
     * <p>
     * This method return a ResponseEntity with the series retrieve from the Database.
     * If the database doesn't get the series, this method return an HTTP error : 204.
     * In other case, this method return the series found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the series at research and the current season of the series.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used
     * on search process.
     *
     * @param titleEncoded Title of the series encoded to search on Database.
     * @param currentSeason Current season of the series.
     *
     * @return A ResponseEntity with the series found on Database, or an error HTTP 204 : No Content.
     *
     * @throws UnsupportedEncodingException The method throw an <code>UnsupportedEncodingException</code> when a
     *         problem occurred during title decoding.
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/series/search/title/{title}/{currentSeason}",
                    method = RequestMethod.GET)
    public ResponseEntity<?> getSeriesByTitleAndCurrentSeason(
            @PathVariable(value = "title") String titleEncoded,
            @PathVariable(value = "currentSeason") int currentSeason) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, SeriesController.ENCODING);
        logger.info("Fetching Series with title {}", title);
        Series series = seriesRepository.findByTitleAndCurrentSeason(title, currentSeason);
        if (series == null) {
            logger.error("Series with title {} not found.", title);
            return new ResponseEntity<Object>(new SeriesException("Series with title " + title + " not found."),
                                              HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Series>(series, HttpStatus.OK);
    }

    /**
     * Return a series by his identifier.
     * <p>
     * This method return a ResponseEntity with the series retrieve from the Database.
     * If the database doesn't get the series, this method return an HTTP error : 204.
     * In other case, this method return the series found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the identifier of the series at research.
     *
     * @param id Identifier of the Series on Database.
     *
     * @return A ResponseEntity with the series found on Database, or an error HTTP 204 : No Content.
     *
     * @version 1.0
     * @since 1.1
     */
    @RequestMapping(value = "/series/search/id/{id}")
    public ResponseEntity<?> getSeriesById(@PathVariable(value = "id") long id) {
        logger.info("Fetching Series with id {}", id);
        Series series = seriesRepository.findOne(id);
        if (series == null) {
            logger.error("Series with id {} not found.", id);
            return new ResponseEntity<Object>(new SeriesException("Series with id " + id + " not found."),
                                              HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Series>(series, HttpStatus.OK);
    }

    /**
     * Add a series on the Database.
     * <p>
     * Before added the series on database, it check if the series is already present on the database.
     * So, if the series is present, the method return an error HTTP 409 : CONFLICT.
     * In other case, it return the code HTTP 200 and an uri to get information about the new series insert.
     * So, this method is call by POST method and take the series at insert on the BODY request.
     *
     * @param series Series at insert on Database.
     * @param uriBuilder UrlComponentsBuilder use to redirect user on series page.
     *
     * @return A ResponseEntity with the series added, or an error HTTP 409 : CONFLICT.
     *
     * @version 1.1
     * @since 1.0
     */
    @RequestMapping(value = "/series/",
                    method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Series series, UriComponentsBuilder uriBuilder) {
        logger.info("Created series : {}", series);

        // Check if the series already exist on database.
        Series seriesExist = seriesRepository.findByTitleAndCurrentSeason(series.getTitle(), series.getCurrentSeason());
        if (seriesExist != null) {
            logger.error("Unable to create. The series {} already exist", series.getTitle());
            return new ResponseEntity<SeriesException>(
                    new SeriesException("Unable to create. The series " + series.getTitle() + " already exist"),
                    HttpStatus.CONFLICT);
        }

        // Check if the actors are present on Database or not.
        Set<Actor> actorsOnSeries = series.getMainActors();
        Set<Actor> mainActors = new HashSet<Actor>();
        for (Actor a : actorsOnSeries) {
            Actor actorExist = actorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the actor is not present on Database, he add on it.
            if (actorExist == null) {
                logger.info("Created actor : {}", a);
                actorRepository.save(a);
                mainActors.add(a);
            } else {
                logger.info("Added actor {} already present on persistent system", actorExist);
                mainActors.add(actorExist);
            }
        }
        series.setMainActors(mainActors);

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnSeries = series.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnSeries) {
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
        series.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnSeries = series.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnSeries) {
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist);
                directors.add(directorExist);
            }
        }
        series.setDirectors(directors);
        seriesRepository.save(series);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/series/search/id/{id}")
                                     .buildAndExpand(series.getId())
                                     .toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a series present on the Database.
     * <p>
     * It update a series only if found on database.
     * It the series is not found, the method return an error with the HTTP code 404.
     * In other case, it update the information about the series and return in the body the series update
     * can use to check if the modification are succeeded and the HTTP code 200.
     *
     * @param id Id of the series on Database.
     * @param series Series with new content at update.
     *
     * @return A ResponseEntity with all series found on Database, or an error HTTP 404 : NOT FOUND.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/series/{id}",
                    method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Series series) {
        logger.info("Updating Series with id {}", id);

        Series seriesAtUpdate = seriesRepository.findOne(id);
        if (seriesAtUpdate == null) {
            logger.error("Unable to update. Series with id {} not found", id);
            return new ResponseEntity<Object>(
                    new SeriesException("Unable to update. Series with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the actor are present on Database or not.
        Set<Actor> actorsOnSeries = series.getMainActors();
        Set<Actor> mainActors = new HashSet<Actor>();
        for (Actor a : actorsOnSeries) {
            Actor actorExist = actorRepository.findByFirstNameAndLastName(a.getFirstName(), a.getLastName());
            // If the actor is not present on Database, it add on it.
            if (actorExist == null) {
                logger.info("Created actor : {}", a);
                actorRepository.save(a);
                mainActors.add(a);
            } else {
                logger.info("Added actor {} already present on persistent system", actorExist);
                mainActors.add(actorExist);
            }
        }
        series.setMainActors(mainActors);

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnSeries = series.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnSeries) {
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
        series.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnSeries = series.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnSeries) {
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist);
                directors.add(directorExist);
            }
        }
        series.setDirectors(directors);

        // Copy content of the series receive on request body on the series retrieve from the database.
        seriesAtUpdate = new Series(series);
        seriesRepository.save(seriesAtUpdate);
        return new ResponseEntity<Object>(seriesAtUpdate, HttpStatus.OK);
    }

    /**
     * Remove a series from the Database.
     * <p>
     * It remove a series if it found on database.
     * If the series is not found on database, this method return an error and the HTTP code 404.
     * Otherwise, the method delete the series thanks to the identifier and return in the body the series deleted
     * and the code HTTP 200 to confirm the success of the deletion.
     *
     * @param id Id of the series at delete.
     *
     * @return A ResponseEntity with all series found on Database, or an error HTTP 404 : NOT_FOUND.
     *
     * @version 2.0
     * @since 1.0
     */
    @RequestMapping(value = "/series/{id}",
                    method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Series with id {}", id);

        Series series = seriesRepository.findOne(id);
        if (series == null) {
            logger.error("Unable to delete. Series with id {} not found", id);
            return new ResponseEntity<Object>(
                    new SeriesException("Unable to delete. Series with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        seriesRepository.delete(series);
        return new ResponseEntity<Object>(series, HttpStatus.OK);
    }

    /**
     * Get all video genres present on Media Library.
     *
     * @return An array with all video genres.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/series/genres/",
                    method = RequestMethod.GET)
    public ResponseEntity<?> getMediaGenre() {
        return new ResponseEntity<>(VideoGenre.values(), HttpStatus.OK);
    }
}
