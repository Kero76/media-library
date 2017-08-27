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

package fr.nicolasgille.medialibrary.models;

import fr.nicolasgille.medialibrary.models.components.MediaSupport;

import java.util.Calendar;
import java.util.List;

/**
 * Main interface must implement by all media available on Media-Library.
 *
 * @author Nicolas GILLE
 * @version 2.0
 * @since Media-Library 0.2
 */
public interface IMedia {

    /**
     * Return the id.
     *
     * @return The id.
     *
     * @version 1.0
     * @since 1.0
     */
    long getId();

    /**
     * Set the id.
     *
     * @param id New id.
     *
     * @version 1.0
     * @since 1.0
     */
    void setId(long id);

    /**
     * Return the title.
     *
     * @return The title of the movie.
     *
     * @version 1.0
     * @since 1.0
     */
    String getTitle();

    /**
     * Set title.
     *
     * @param title New title.
     *
     * @version 1.0
     * @since 1.0
     */
    void setTitle(String title);

    /**
     * Get the synopsis of the book.
     *
     * @return The synopsis of the book.
     *
     * @version 1.0
     * @since 1.0
     */
    String getSynopsis();

    /**
     * Set the synopsis of the book.
     *
     * @param synopsis New synopsis.
     *
     * @version 1.0
     * @since 1.0
     */
    void setSynopsis(String synopsis);

    /**
     * Return the release date.
     *
     * @return The release date.
     *
     * @version 1.0
     * @since 2.0
     */
    Calendar getReleaseDate();

    /**
     * Set releaseDate.
     *
     * @param releaseDate New date of release.
     *
     * @version 1.0
     * @since 2.0
     */
    void setReleaseDate(Calendar releaseDate);

    /**
     * Return all supports for the video.
     *
     * @return List of all supports.
     *
     * @version 1.0
     * @since 2.0
     */
    List<MediaSupport> getSupports();

    /**
     * Set the supports for the video.
     *
     * @param supports New Supports.
     *
     * @version 1.0
     * @since 2.0
     */
    void setSupports(List<MediaSupport> supports);
}
