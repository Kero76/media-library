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
package fr.nicolasgille.medialibrary.daos.video;

import fr.nicolasgille.medialibrary.models.video.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Calendar;

/**
 * An interface who specified method to interact with the movies.
 *
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with "movies", in particular find methods.
 *
 * V1.1 :
 * <ul>
 *     <li>Added method findByTitleAndDurationAndReleaseDate()</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.1
 */
@Transactional
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Find a movie by his name.
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
