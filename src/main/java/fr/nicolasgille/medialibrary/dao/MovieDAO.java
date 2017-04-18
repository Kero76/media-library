package fr.nicolasgille.medialibrary.dao;

import fr.nicolasgille.medialibrary.models.Movie;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the movies table.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface MovieDAO extends CrudRepository<Movie, Long> {

    /**
     * Find a movie by is name.
     *
     * @param title
     *  Title of the movie at search on Database.
     * @return
     *  An instance of movie search by the name.
     */
    public Movie findByTitleIgnoreCase(String title);
}
