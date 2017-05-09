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
package fr.nicolasgille.medialibrary.models;

import fr.nicolasgille.medialibrary.models.common.Genre;

import java.util.List;

/**
 * Main interface must implement by all media available on Media-Library.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.0
 */
public interface IMedia {

    /**
     * Return the id.
     *
     * @return
     *  The id.
     * @since 1.0
     * @version 1.0
     */
    long getId();

    /**
     * Set the id.
     *
     * @param id
     *  New id.
     * @since 1.0
     * @version 1.0
     */
    void setId(long id);

    /**
     * Return the title.
     *
     * @return
     *  The title of the movie.
     * @since 1.0
     * @version 1.0
     */
    String getTitle();

    /**
     * Set title.
     *
     * @param title
     *  New title.
     * @since 1.0
     * @version 1.0
     */
    void setTitle(String title);

    /**
     * Return the original title.
     *
     * @return
     *  The original title of the movie.
     * @since 1.0
     * @version 1.0
     */
    String getOriginalTitle();

    /**
     * Set original title.
     *
     * @param originalTitle
     *  New title.
     * @since 1.0
     * @version 1.0
     */
    void setOriginalTitle(String originalTitle);

    /**
     * Return the synopsis.
     *
     * @return
     *  The synopsis.
     * @since 1.0
     * @version 1.0
     */
    String getSynopsis();

    /**
     * Set synopsis.
     *
     * @param synopsis
     *  New synopsis.
     * @since 1.0
     * @version 1.0
     */
    void setSynopsis(String synopsis);

    /**
     * Return the genres.
     *
     * @return
     *  The genres of the movie.
     * @see Genre
     * @since 1.0
     * @version 1.0
     */
    List<Genre> getGenres();

    /**
     * Set genres of Movie.
     *
     * @param genres
     *  New genres.
     * @since 1.0
     * @version 1.0
     */
    void setGenres(List<Genre> genres);
}
