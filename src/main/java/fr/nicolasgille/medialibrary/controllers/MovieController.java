package fr.nicolasgille.medialibrary.controllers;

import fr.nicolasgille.medialibrary.Exception.MovieException;
import fr.nicolasgille.medialibrary.daos.MovieDAO;
import fr.nicolasgille.medialibrary.models.Movie;
import fr.nicolasgille.medialibrary.models.MovieCategory;
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
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@RestController
@RequestMapping("/movies")
public class MovieController {
    /**
     * DAO used to interact with the table movies present on Database.
     */
    @Autowired
    private MovieDAO movieDao;

    /**
     * Logger for debugging app.
     */
    public static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    /**
     * Return all movies found on Database.
     *
     * @return
     *  A ResponseEntity with all movies found on Database, or a message to indicate absence of content on the request.
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
     * @param title
     *  Title of the movie at search on Database.
     * @return
     *  A ResponseEntity with the movie found on Database, or a message to indicate absence of content on the request.
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
     * @param movie
     *  Movie at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on movie page.
     * @return
     *  A ResponseEntity with all movies found on Database, or a message to indicate the failure of the request.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Movie movie, UriComponentsBuilder uriBuilder) {
        logger.info("Created movie : {}", movie);

        if (movieDao.isMovieExist(movie)) {
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
     * @param id
     *  Id of the movie on Database.
     * @param movie
     *  Movie at update.
     * @return
     *  A ResponseEntity with all movies found on Database, or a message to indicate the failure of the request
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
     * @param id
     *  Id of the movie at delete, if present.
     * @return
     *  A ResponseEntity with all movies found on Database, or a message to indicate the failure of the request
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Updating Movie with id {}", id);

        Movie movie = movieDao.findOne(id);
        if (movie == null) {
            logger.error("Unable to delete. Movie with id {} not found", id);
            return new ResponseEntity<Object>(new MovieException("Unable to delete. Movie with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        movieDao.delete(movie);
        return new ResponseEntity<Object>(movie, HttpStatus.OK);
    }
}
