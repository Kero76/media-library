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

import fr.nicolasgille.medialibrary.exceptions.video.MovieException;
import fr.nicolasgille.medialibrary.models.common.person.Actor;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.video.Movie;
import fr.nicolasgille.medialibrary.repositories.common.person.ActorRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.DirectorRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.ProducerRepository;
import fr.nicolasgille.medialibrary.repositories.video.MovieRepository;
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
 * Controller of the app to interact with movies present on Media-Library.
 * You can use CRUD method to insert, delete, update or select movie from Database.
 * So, many methods about research are available on the controller to search movie with different way of search.
 * You can add you own method of research if you would have a new research type of movie.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 2.2.1
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 2.1
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Repository used to interact with movies present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private MovieRepository movieRepository;

    /**
     * Repository used to interact with actors present on the service.
     *
     * @since 2.1
     */
    @Autowired
    private ActorRepository actorRepository;

    /**
     * Repository used to interact with producers present on the service.
     *
     * @since 2.1
     */
    @Autowired
    private ProducerRepository producerRepository;

    /**
     * Repository used to interact with directors present on the service.
     *
     * @since 2.1
     */
    @Autowired
    private DirectorRepository directorRepository;

    /**
     * Logger to get information during some process.
     *
     * @since 2.0
     */
    static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    /**
     * Return all movies found on Database.
     *
     * This method return a ResponseEntity object who contains a list of movies found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all movies found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/movies/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
    }

    /**
     * Return a movie by his title.
     *
     * This method return a ResponseEntity with the movie retrieve from the Database.
     * If the database doesn't get the movie, this method return an HTTP error : 204.
     * In other case, this method return the movie found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the movie at research.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used on search process.
     *
     * @param titleEncoded
     *  Title of the movie encoded to search on Database.
     * @return
     *  A ResponseEntity with the movie found on Database, or an error HTTP 204 : No Content.
     * @throws UnsupportedEncodingException
     *  The method throw an <code>UnsupportedEncodingException</code> when a problem occurred during title decoding.
     * @since 1.0
     * @version 2.1.1
     */
    @RequestMapping(value = "/movies/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovieByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, MovieController.ENCODING);
        logger.info("Fetching Movie with title {}", title);
        List<Movie> movies = movieRepository.findByTitleIgnoreCaseContaining(title);
        if (movies == null) {
            logger.error("Movie with title {} not found.", title);
            return new ResponseEntity<Object>(new MovieException("Movie with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
    }

    /**
     * Return a movie by his identifier.
     *
     * This method return a ResponseEntity with the movie retrieve from the Database.
     * If the database doesn't get the movie, this method return an HTTP error : 204.
     * In other case, this method return the movie found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the identifier of the movie at research.
     *
     * @param id
     *  Identifier of the movie on Database.
     * @return
     *  A ResponseEntity with the movie found on Database, or an error HTTP 204 : No Content.
     * @since 2.2
     * @version 1.0
     */
    @RequestMapping(value = "/movies/search/id/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable(value = "id") long id) {
        logger.info("Fetching Movies with id {}", id);
        Movie movie = movieRepository.findOne(id);
        if (movie == null) {
            logger.error("Movie with id {} not found.", id);
            return new ResponseEntity<Object>(new MovieException("Movie with id " + id + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    /**
     * Add a movie on the Database.
     *
     * Before added the movie on database, it check if the movie is already present on the database.
     * So, if the movie is present, the method return an error HTTP 409 : CONFLICT.
     * In other case, it return the code HTTP 200 and an uri to get information about the new movie insert.
     * So, this method is call by POST method and take the movie at insert on the BODY request.
     *
     * @param movie
     *  Movie at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on movie page.
     * @return
     *  A ResponseEntity with the movie added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 2.1
     */
    @RequestMapping(value = "/movies/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Movie movie, UriComponentsBuilder uriBuilder) {
        logger.info("Created movie : {}", movie);

        // Check if the movie already exist on database.
        Movie movieExist = movieRepository.findByTitleAndRuntimeAndReleaseDate(movie.getTitle(), movie.getRuntime(), movie.getReleaseDate());
        if (movieExist != null) {
            logger.error("Unable to create. The movie {} already exist", movie.getTitle());
            return new ResponseEntity<MovieException>(new MovieException("Unable to create. The movie " + movie.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the actors are present on Database or not.
        Set<Actor> actorsOnMovie = movie.getMainActors();
        Set<Actor> mainActors = new HashSet<Actor>();
        for (Actor a : actorsOnMovie) {
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
        movie.setMainActors(mainActors);

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = movie.getProducers();
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
        movie.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnMovie = movie.getDirectors();
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
        movie.setDirectors(directors);
        movieRepository.save(movie);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/movies/search/id/{id}").buildAndExpand(movie.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a movie present on the Database.
     *
     * It update a movie only if found on database.
     * It the movie is not found, the method return an error with the HTTP code 404.
     * In other case, it update the information about the movie and return in the body the movie update
     * can use to check if the modification are succeeded and the HTTP code 200.
     *
     * @param id
     *  Id of the movie on Database.
     * @param movie
     *  Movie with new content at update.
     * @return
     *  A ResponseEntity with all movies found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Movie movie) {
        logger.info("Updating Movie with id {}", id);

        Movie movieAtUpdate = movieRepository.findOne(id);
        if (movieAtUpdate == null) {
            logger.error("Unable to update. Movie with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to update. Movie with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the actor are present on Database or not.
        Set<Actor> actorsOnMovie = movie.getMainActors();
        Set<Actor> mainActors = new HashSet<Actor>();
        for (Actor a : actorsOnMovie) {
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
        movie.setMainActors(mainActors);

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnMovie = movie.getProducers();
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
        movie.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnMovie = movie.getDirectors();
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
        movie.setDirectors(directors);

        // Copy content of the movie receive on request body on the movie retrieve from the database.
        movieAtUpdate = new Movie(movie);
        movieRepository.save(movieAtUpdate);
        return new ResponseEntity<Object>(movieAtUpdate, HttpStatus.OK);
    }

    /**
     * Remove a movie from the Database.
     *
     * It remove a movie if it found on database.
     * If the movie is not found on database, this method return an error and the HTTP code 404.
     * Otherwise, the method delete the movie thanks to the identifier and return in the body the movie deleted
     * and the code HTTP 200 to confirm the success of the deletion.
     *
     * @param id
     *  Id of the movie at delete.
     * @return
     *  A ResponseEntity with all movies found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Movie with id {}", id);

        Movie movie = movieRepository.findOne(id);
        if (movie == null) {
            logger.error("Unable to delete. Movie with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to delete. Movie with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        movieRepository.delete(movie);
        return new ResponseEntity<Object>(movie, HttpStatus.OK);
    }
}
