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
 * Class Producer present on class Movie to representing the producer of the movie.
 *
 * @see Movie
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Entity
@Table(name = "common_producers")
public class Producer {

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
     * Producer who produce movie.
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
     * @since 1.0
     * @version 1.0
     */
    public Producer(long id, String firstName, String lastName, Set<Movie> movies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }

    /**
     * Return id of the Producer.
     *
     * @return Id of the Producer.
     * @since 1.0
     * @version 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set id of the Producer.
     *
     * @param id New id.
     * @since 1.0
     * @version 1.0
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return first name of the Producer.
     *
     * @return First name of the Producer.
     * @since 1.0
     * @version 1.0
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name of the Producer.
     *
     * @param firstName New first name.
     * @since 1.0
     * @version 1.0
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return last name of the Producer.
     *
     * @return Last name of the Producer.
     * @since 1.0
     * @version 1.0
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the Producer.
     *
     * @param lastName New last name.
     * @since 1.0
     * @version 1.0
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * List of movies who the Producer produce the movie.
     *
     * @return List of all movies who the Producer produce the movie.
     * @since 1.0
     * @version 1.0
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Set list of movies where actor played as main actor.
     *
     * @param movies New list of movies.
     * @since 1.0
     * @version 1.0
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
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
        StringBuilder movies = new StringBuilder();
        if (this.movies != null) {
            for (Movie m : this.movies) {
                movies.append(m.toString());
                movies.append(";");
            }
        } else {
            movies.append("");
        }

        return "Producer{" +
                ", id=" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", movies='" + movies.toString() + '\'' +
                '}';
    }
}
