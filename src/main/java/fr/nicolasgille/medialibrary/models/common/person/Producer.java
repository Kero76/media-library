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

package fr.nicolasgille.medialibrary.models.common.person;

import fr.nicolasgille.medialibrary.models.video.Video;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Producer present on class Movie or Series to representing producer of a movie or series.
 *
 * @author Nicolas GILLE
 * @version 2.0
 * @see Person
 * @see Video
 * @since Media-Library 0.1
 */
@Entity
@DiscriminatorValue(value = "producer")
public class Producer extends Person {

    /**
     * Producer who produce videos.
     *
     * @since 1.0
     */
    @Transient
    private Set<Video> videos;

    /**
     * Empty constructor.
     *
     * @version 1.0
     * @since 1.0
     */
    public Producer() {
    }

    /**
     * Constructor used to create Producer on Database.
     *
     * @param firstName First name of the Actor.
     * @param lastName Last name of the Actor.
     *
     * @version 1.0
     * @since 1.0
     */
    public Producer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id Identifier stored on DB.
     * @param firstName First name.
     * @param lastName Last name.
     * @param videos Video where the guy participate as Producer.
     *
     * @version 1.1
     * @since 1.0
     */
    public Producer(long id, String firstName, String lastName, Set<Video> videos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.videos = videos;
    }

    /**
     * List of videos who the Producer produce the video.
     *
     * @return List of all videos who the Producer produce the video.
     *
     * @version 1.1
     * @since 1.0
     */
    public Set<Video> getVideos() {
        return videos;
    }

    /**
     * Set list of videos who guy produce the video.
     *
     * @param videos New list of videos.
     *
     * @version 1.1
     * @since 1.0
     */
    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    /**
     * Display content of the Producer.
     *
     * @return A simple representation of the Producer.
     *
     * @version 1.2
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Producer{" +
               ", id=" + id + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", videos='" + CollectionAsString.collectionToString(this.videos) + '\'' +
               '}';
    }
}
