package fr.nicolasgille.medialibrary.controllers;

import fr.nicolasgille.medialibrary.dao.MovieDAO;
import fr.nicolasgille.medialibrary.models.Movie;
import fr.nicolasgille.medialibrary.models.MovieCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller of Movies models object.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Controller
@RequestMapping("/movies")
public class MovieController {

    /**
     * DAO used to interact with the table movies present on Database.
     */
    @Autowired
    private MovieDAO movieDao;

    /**
     * Return all movies found on Database.
     *
     * @return
     *  All movies from Database.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getAll() {
        return (List<Movie>) movieDao.findAll();
    }

    /**
     * Return a movie by is title.
     *
     * @param title
     *  Title of the movie at search on Database.
     * @return
     *  An instance of movie who corresponding at the movie on Database.
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Movie getMovieByTitle(@RequestParam(value = "title") String title) {
        return movieDao.findByTitleIgnoreCase(title);
    }

    /**
     * Add a movie on the Database.
     *
     * @param title
     *  Title of the movie.
     * @param cat
     *  Category of the movie.
     * @param releaseDate
     *  Date of release.
     * @param duration
     *  Duration of the movie.
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  MainActor of the movie.
     * @return
     *  Return a message who confirm the successful or failure process.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String create(String title, String cat, int releaseDate, int duration, String synopsis, String mainActors) {
        try {
            MovieCategory category = MovieCategory.valueOf(cat.toUpperCase());
            Movie movie = new Movie(title, category, releaseDate, duration, synopsis, mainActors);
            movieDao.save(movie);
        } catch (Exception e) {
            return "An error occurred during creation process : " + e.toString();
        }
        return "Movie successfully created.";
    }

    /**
     * Removed a movie from the Database.
     *
     * @param title
     *  Title of the movie at removed.
     * @return
     *  Return a message who confirm the successful or failure process.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(String title) {
        try {
            Movie movie = new Movie(title);
            movieDao.delete(movie);
        } catch (Exception e) {
            return "An error occurred during deletion process : " + e.toString();
        }
        return "Movie successfully deleted.";
    }

    /**
     * Update a movie present on the Database.
     *
     * @param id
     *  Id of the movie on Database.
     * @param title
     *  New title of the movie.
     * @param cat
     *  New category of the movie.
     * @param releaseDate
     *  New date of release.
     * @param duration
     *  New duration of the movie.
     * @param synopsis
     *  New synopsis of the movie.
     * @param mainActors
     *  New mainActor of the movie.
     * @return
     *  Return a message who confirm the successful or failure process.
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public String update(long id, String title, String cat, int releaseDate, int duration, String synopsis, String mainActors) {
        try {
            MovieCategory category = MovieCategory.valueOf(cat.toUpperCase());
            Movie movie = movieDao.findOne(id);
            movie.setTitle(title);
            movie.setCategory(category);
            movie.setReleaseDate(releaseDate);
            movie.setDuration(duration);
            movie.setSynopsis(synopsis);
            movie.setMainActors(mainActors);
            movieDao.save(movie);
        } catch (Exception e) {
            return "An error occurred during update process : " + e.toString();
        }
        return "Movie successfully updated.";
    }
}
