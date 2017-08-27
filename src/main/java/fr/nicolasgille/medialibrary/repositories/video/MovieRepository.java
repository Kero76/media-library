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

package fr.nicolasgille.medialibrary.repositories.video;


import fr.nicolasgille.medialibrary.models.video.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * An interface who specified method to interact with the movies.
 * <p>
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with "movies", in particular find methods.
 * *
 *
 * @author Nicolas GILLE
 * @version 1.2
 * @since Media-Library 0.1
 */
@Transactional
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Find all movies by his name.
     *
     * @param title Title of the movies search on Database.
     *
     * @return An instance of movies search by the name.
     *
     * @version 1.0.1
     * @since 1.0
     */
    List<Movie> findByTitleIgnoreCase(String title);

    /**
     * Find all movies by title like.
     *
     * @param title Title of the movies search on Database.
     *
     * @return An instance of movies search by the name.
     *
     * @version 1.0
     * @since 1.2
     */
    List<Movie> findByTitleIgnoreCaseContaining(String title);

    /**
     * Find a movie by his name, his duration and date of release.
     *
     * @param title Title of the movie.
     * @param runtime Runtime of the movie.
     * @param releaseDate Date of release of the movie search.
     *
     * @return An instance of movie search by title, duration and releaseDate.
     *
     * @version 1.1
     * @since 1.1
     */
    Movie findByTitleAndRuntimeAndReleaseDate(String title, int runtime, Calendar releaseDate);
}
