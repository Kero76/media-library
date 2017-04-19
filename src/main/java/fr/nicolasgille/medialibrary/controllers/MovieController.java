package fr.nicolasgille.medialibrary.controllers;

import fr.nicolasgille.medialibrary.Exception.MovieException;
import fr.nicolasgille.medialibrary.daos.MovieDAO;
import fr.nicolasgille.medialibrary.models.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Controller of Movies models object.
 *
 * This class control the access of the movie on the project.
 * In fact, it define CRUD method to interact with the model and the persistence model.
 * It can update in the future to add new methods like getXXX requests.
 *
 * V2.0:
 *  -> Completely rewrite content of all methods to modernize methods.
 *  -> Added Logger object to see step of each method and help debugging.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 2.0
 */
@RestController
@RequestMapping("/media-library/movies")
public class MovieController {

    /**
     * DAO used to interact with the table movies present on Database.
     *
     * @since 1.0
     */
    @Autowired
    private MovieDAO movieDao;

    /**
     * Logger for debugging app.
     *
     * @since 2.0
     */
    public static final Logger logger = LoggerFactory.getLogger(MovieController.class);

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
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Movie> movies = (List<Movie>) movieDao.findAll();
        if (movies.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
    }

    /**
     * Return a movie by is title.
     *
     * This method return a ResponseEntity with the movie retrieve from the Database.
     * If the database research don't retrieve the movie, this method return an HTTP error.
     * This method can call by GET request and take an path variable the title of the movie at research.
     *
     * @param title
     *  Title of the movie ti search on Database.
     * @return
     *  A ResponseEntity with the movie found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovieByTitle(@PathVariable(value = "title") String title) {
        logger.info("Fetching Movie with title {}", title);
        Movie movie = movieDao.findByTitleIgnoreCase(title);
        if (movie == null) {
            logger.error("Movie with title {} not found.", title);
            return new ResponseEntity<Object>(new MovieException("Movie with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    /**
     * Add a movie on the Database.
     *
     * Before added the movie on database, it check if the movie is already present on the database.
     * And if the movie is present, the method return an error HTTP 409 : CONFLICT.
     * This method can call only by a POST request and take on BODY the movie at insert on Database.
     *
     * @param movie
     *  Movie at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on movie page.
     * @return
     *  A ResponseEntity with the movie added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Movie movie, UriComponentsBuilder uriBuilder) {
        logger.info("Created movie : {}", movie);

        // Check if the movie already exist on database.
        Movie movieExist = movieDao.findOne(movie.getId());
        if (movieExist != null) {
            logger.error("Unable to create. The movie {} already exist", movie.getTitle());
            return new ResponseEntity<Object>(new MovieException("Unable to create. The movie " + movie.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }
        movieDao.save(movie);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/movies/search/title/{id}").buildAndExpand(movie.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a movie present on the Database.
     *
     * This method update a movie present on database only if this movie is present on it.
     * In other case, this method return a HTTP error 404 : Not Found.
     * This method can call only by PUT method and take the id of the movie at update on path variable
     * and the object movie with the new content on BODY.
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
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Movie movie) {
        logger.info("Updating Movie with id {}", id);

        Movie movieAtUpdate = movieDao.findOne(id);
        if (movieAtUpdate == null) {
            logger.error("Unable to update. Movie with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to update. Movie with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Copy content of the movie receive on request body on the movie retrieve from the database.
        movieAtUpdate = new Movie(movie);
        movieDao.save(movieAtUpdate);
        return new ResponseEntity<Object>(movieAtUpdate, HttpStatus.OK);
    }

    /**
     * Removed a movie from the Database.
     *
     * This method remove a movie from the database only if the movie is present on the Database.
     * It return an error HTTP 404 : NOT FOUND if the movie at deleted isn't present on the database.
     * To call this method, you can pass on the url the id of the movie at remove
     * and this method can call only with DELETE request.
     *
     * @param id
     *  Id of the movie at delete.
     * @return
     *  A ResponseEntity with all movies found on Database, or an error HTTP 409 : CONFLICT..
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Movie with id {}", id);

        Movie movie = movieDao.findOne(id);
        if (movie == null) {
            logger.error("Unable to delete. Movie with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to delete. Movie with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        movieDao.delete(movie);
        return new ResponseEntity<Object>(movie, HttpStatus.OK);
    }
}
