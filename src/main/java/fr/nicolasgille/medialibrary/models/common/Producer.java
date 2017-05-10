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
package fr.nicolasgille.medialibrary.models.common;

import fr.nicolasgille.medialibrary.models.video.Video;

import javax.persistence.*;
import java.util.Set;

/**
 * Class Producer present on class Movie or Series to representing producer of a movie or series.
 *
 * V2.0 :
 * <ul>
 *     <li>Inherit abstract class <code>Person</code></li>
 *     <li>Removed all attributes excepted <code>movies</code> who he's renamed videos and became a <code>Set<Video></code></li>
 *     <li>Modified getter and setter for attribute Video.</li>
 * </ul>
 *
 * @see Person
 * @see Video
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 2.0
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
     * @since 1.0
     * @version 1.0
     */
    public Producer() {
    }

    /**
     * Constructor used to create Producer on Database.
     *
     * @param firstName First name of the Actor.
     * @param lastName  Last name of the Actor.
     * @since 1.0
     * @version 1.0
     */
    public Producer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id        Identifier stored on DB.
     * @param firstName First name.
     * @param lastName  Last name.
     * @param videos    Video where the guy participate as Producer.
     * @since 1.0
     * @version 1.1
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
     * @since 1.0
     * @version 1.1
     */
    public Set<Video> getVideos() {
        return videos;
    }

    /**
     * Set list of videos who guy produce the video.
     *
     * @param videos New list of videos.
     * @since 1.0
     * @version 1.1
     */
    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    /**
     * Display content of the Producer.
     *
     * @return A simple representation of the Producer.
     * @since 1.0
     * @version 1.1
     */
    @Override
    public String toString() {
        StringBuilder videos = new StringBuilder();
        if (this.videos != null) {
            for (Video v : this.videos) {
                videos.append(v.toString());
                videos.append(";");
            }
        } else {
            videos.append("");
        }

        return "Producer{" +
                ", id=" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", videos='" + videos.toString() + '\'' +
                '}';
    }
}
