package fr.nicolasgille.medialibrary.daos;

import fr.nicolasgille.medialibrary.models.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Calendar;

/**
 * An interface who specified method to interact with the movies table.
 *
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with the table "movies", in particular getXXXX methods.
 *
 * V1.1 :
 *  -> Added method findByTitleAndDurationAndReleaseDate()
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.1
 */
@Transactional
public interface MovieDAO extends JpaRepository<Movie, Long> {

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

    /**
     * Find a movie by his name, his duration and date of release.
     *
     * @param title
     *  Title of the movie.
     * @param runtime
     *  Runtime of the movie.
     * @param releaseDate
     *  Date of release of the movie search.
     * @return
     *  An instance of movie search by title, duration and releaseDate.
     * @since 1.1
     * @version 1.1
     */
    Movie findByTitleAndRuntimeAndReleaseDate(String title, int runtime, Calendar releaseDate);
}
