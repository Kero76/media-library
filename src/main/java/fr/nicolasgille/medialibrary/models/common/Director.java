package fr.nicolasgille.medialibrary.models.common;

import fr.nicolasgille.medialibrary.models.movie.Movie;

import javax.persistence.*;
import java.util.Set;

/**
 * Class Director present on class Movie to representing the director of the movie.
 *
 * @see Movie
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.0
 */
@Entity
@Table(name = "common_directors")
public class Director {

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
     * First name of the Director.
     *
     * @since 1.0
     */
    @Column(name = "fname", unique = true)
    private String firstName;

    /**
     * Last name of the Director.
     *
     * @since 1.0
     */
    @Column(name = "lname", unique = true)
    private String lastName;

    /**
     * Director who directed movie.
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
    public Director() {
    }

    /**
     * Constructor used to create Director on Database.
     *
     * @param firstName First name of the Director.
     * @param lastName  Last name of the Director.
     * @since 1.0
     * @version 1.0
     */
    public Director(String firstName, String lastName) {
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
    public Director(long id, String firstName, String lastName, Set<Movie> movies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.movies = movies;
    }

    /**
     * Return id of the Director.
     *
     * @return Id of the Director.
     * @since 1.0
     * @version 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set id of the Director.
     *
     * @param id New id.
     * @since 1.0
     * @version 1.0
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return first name of the Director.
     *
     * @return First name of the Director.
     * @since 1.0
     * @version 1.0
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name of the Director.
     *
     * @param firstName New first name.
     * @since 1.0
     * @version 1.0
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return last name of the Director.
     *
     * @return Last name of the Director.
     * @since 1.0
     * @version 1.0
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the Director.
     *
     * @param lastName New last name.
     * @since 1.0
     * @version 1.0
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * List of movies where director directed the movie.
     *
     * @return List of all movies where director directed the movie.
     * @since 1.0
     * @version 1.0
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Set list of movies where director directed the movie.
     *
     * @param movies New list of movies.
     * @since 1.0
     * @version 1.0
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Display content of the Director.
     *
     * @return A simple representation of the Director.
     * @since 1.0
     * @version 1.0
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

        return "Director{" +
                ", id=" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", movies='" + movies.toString() + '\'' +
                '}';
    }
}
