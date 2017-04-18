package fr.nicolasgille.medialibrary.controller;

import fr.nicolasgille.medialibrary.model.Movie;
import fr.nicolasgille.medialibrary.model.MovieCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Movie getMovieByTitle(@RequestParam(value = "title", required = false, defaultValue = "La Grande Vadrouille") String title) {
        return new Movie(1, title, MovieCategory.ACTION, 2016, 127, "I'm Batman !", "Batman, Robin");
    }
}
