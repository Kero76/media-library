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
package fr.nicolasgille.medialibrary.models.common.person;

import fr.nicolasgille.medialibrary.models.video.Video;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Actor present on class Movie or Series to representing main actor in a movie or series.
 *
 * V2.0 :
 * <ul>
 *     <li>Inherit abstract class <code>Person</code></li>
 *     <li>Removed all attributes excepted <code>movies</code> who he's renamed videos and became a <code>Set<Video></code></li>
 *     <li>Modified getter and setter for attribute Video.</li>
 * </ul>
 *
 * V1.1 :
 * <ul>
 *     <li>Added attribute <code>movies</code> who contains a list of all movies where the actor as considers as a main actor.</li>
 *     <li>Updated constructor with new attribute on parameter.</li>
 *     <li>Added corresponding getter and setter for attribute <code>movies</code></li>
 *     <li>Added <code>@Transient</code> annotations on new attribute <code>movies</code>.</li>
 * </ul>
 *
 * @see Person
 * @see Video
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 2.0
 */
@Entity
@DiscriminatorValue(value = "actor")
public class Actor extends Person {

    /**
     * Videos where the actor played.
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
    public Actor() {}

    /**
     * Constructor used to create Actor on Database.
     *
     * @param firstName
     *  First name of the Actor.
     * @param lastName
     *  Last name of the Actor.
     * @since 1.0
     * @version 1.0
     */
    public Actor(String firstName, String lastName) {
        super.firstName = firstName;
        super.lastName  = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id
     *  Identifier stored on database.
     * @param firstName
     *  First name.
     * @param lastName
     *  Last name.
     * @param videos
     *  Videos where the actor played as main actor.
     * @since 1.0
     * @version 1.2
     */
    public Actor(long id, String firstName, String lastName, Set<Video> videos) {
        super.id = id;
        super.firstName = firstName;
        super.lastName = lastName;
        this.videos = videos;
    }

    /**
     * List of videos where the actor played as main actor.
     *
     * @return
     *  List of all videos where the actor played as main actor.
     * @since 1.1
     * @version 1.1
     */
    public Set<Video> getVideos() {
        return videos;
    }

    /**
     * Set list of videos where actor played as main actor.
     *
     * @param videos
     *  New list of videos.
     * @since 1.1
     * @version 1.0
     */
    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    /**
     * Display content of the Actor.
     *
     * @return
     *  A simple representation of the Actor.
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

        return "Actor{" +
                ", id=" + super.id + '\'' +
                ", firstName='" + super.firstName + '\'' +
                ", lastName='" + super.lastName + '\'' +
                "', videos='" + videos.toString() + '\'' +
                '}';
    }
}

