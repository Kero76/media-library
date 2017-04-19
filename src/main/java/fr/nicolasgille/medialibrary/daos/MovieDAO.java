package fr.nicolasgille.medialibrary.daos;

import fr.nicolasgille.medialibrary.models.Movie;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the movies table.
 *
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with the table "movies", in particular getXXXX methods.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
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
     * @since 1.0
     * @version 1.0
     */
    Movie findByTitleIgnoreCase(String title);
}
