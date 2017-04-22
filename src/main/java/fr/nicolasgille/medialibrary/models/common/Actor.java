package fr.nicolasgille.medialibrary.models.common;

import fr.nicolasgille.medialibrary.models.movie.Movie;

import javax.persistence.*;
import java.util.List;

/**
 * Class Actor present on class Movie to representing main actor in a movie.
 *
 * V1.1 :
 *  -> Added attribute <code>movies</code> who contains a list of all movies where the actor as considers as a main actor.
 *  -> Updated constructor with new attribute on parameter.
 *  -> Added corresponding getter and setter for attribute <code>movies</code>
 *  -> Added <code>@ManyToMany</code> annotations new attribute <code>movies</code>.
 *
 * @see fr.nicolasgille.medialibrary.models.movie.Movie
 * @author Nicolas GILLE
 * @since Media-Library 1.0
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
    @ManyToMany(targetEntity = Movie.class, fetch = FetchType.LAZY, mappedBy = "mainActors", cascade = CascadeType.ALL)
    private List<Movie> movies;

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
    public Actor(long id, String firstName, String lastName, List<Movie> movies) {
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
    public List<Movie> getMovies() {
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
    public void setMovies(List<Movie> movies) {
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
        return "Actor{" +
                ", id=" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "', movies='" + movies + '\'' +
                '}';
    }
}

