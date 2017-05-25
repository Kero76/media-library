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

import java.util.Calendar;

/**
 * Main interface must implement by all media available on Media-Library.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 2.0
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
     * Get the synopsis of the book.
     *
     * @return
     *  The synopsis of the book.
     * @since 1.0
     * @version 1.0
     */
    String getSynopsis();

    /**
     * Set the synopsis of the book.
     *
     * @param synopsis
     *  New synopsis.
     * @since 1.0
     * @version 1.0
     */
    void setSynopsis(String synopsis);

    /**
     * Return the release date.
     *
     * @return
     *  The release date.
     * @since 2.0
     * @version 1.0
     */
    Calendar getReleaseDate();

    /**
     * Set releaseDate.
     *
     * @param releaseDate
     *  New date of release.
     * @since 2.0
     * @version 1.0
     */
    void setReleaseDate(Calendar releaseDate);
}
