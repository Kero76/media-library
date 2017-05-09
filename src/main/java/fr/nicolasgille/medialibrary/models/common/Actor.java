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

import fr.nicolasgille.medialibrary.models.video.Movie;

import javax.persistence.*;
import java.util.Set;

/**
 * Class Actor present on class Movie to representing main actor in a movie.
 *
 * V1.1 :
 * <ul>
 *     <li>Added attribute <code>movies</code> who contains a list of all movies where the actor as considers as a main actor.</li>
 *     <li>Updated constructor with new attribute on parameter.</li>
 *     <li>Added corresponding getter and setter for attribute <code>movies</code></li>
 *     <li>Added <code>@Transient</code> annotations on new attribute <code>movies</code>.</li>
 * </ul>
 *
 * @see Movie
 * @author Nicolas GILLE
 * @since IMedia-Library 0.1
 * @version 1.1
 */
@Entity
@Table(name = "common_actors")
public class Actor {

    /**
     * Identifier of the movie.
     * It auto-increment to avoid same identifier for 2 movies.
     *
     * @since 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * First name of the Actor.
     *
     * @since 1.0
     */
    @Column(name = "fname", unique = true)
    private String firstName;

    /**
     * Last name of the actor.
     *
     * @since 1.0
     */
    @Column(name = "lname", unique = true)
    private String lastName;

    /**
     * Main actors present on the movie.
     *
     * @since 1.0
     */
    @Transient
    private Set<Movie> movies;

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
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id
     *  Identifier stored on DB.
     * @param firstName
     *  First name.
     * @param lastName
     *  Last name.
     * @since 1.0
     * @version 1.1
     */
    public Actor(long id, String firstName, String lastName, Set<Movie> movies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }

    /**
     * Return id of the Actor.
     *
     * @return
     *  Id of the Actor.
     * @since 1.0
     * @version 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set id of the Actor.
     *
     * @param id
     *  New id.
     * @since 1.0
     * @version 1.0
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return first name of the Actor.
     *
     * @return
     *  First name of the Actor.
     * @since 1.0
     * @version 1.0
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name of the Actor.
     *
     * @param firstName
     *  New first name.
     * @since 1.0
     * @version 1.0
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return last name of the Actor.
     *
     * @return
     *  Last name of the Actor.
     * @since 1.0
     * @version 1.0
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the Actor.
     *
     * @param lastName
     *  New last name.
     * @since 1.0
     * @version 1.0
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * List of movies where the actor played as main actor.
     *
     * @return
     *  List of all movies where the actor played as main actor.
     * @since 1.1
     * @version 1.0
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Set list of movies where actor played as main actor.
     *
     * @param movies
     *  New list of movies.
     * @since 1.1
     * @version 1.0
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
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
        StringBuilder movies = new StringBuilder();
        if (this.movies != null) {
            for (Movie m : this.movies) {
                movies.append(m.toString());
                movies.append(";");
            }
        } else {
            movies.append("");
        }

        return "Actor{" +
                ", id=" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "', movies='" + movies.toString() + '\'' +
                '}';
    }
}

