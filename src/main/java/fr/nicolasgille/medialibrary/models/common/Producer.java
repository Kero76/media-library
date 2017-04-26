package fr.nicolasgille.medialibrary.models.common;

import fr.nicolasgille.medialibrary.models.movie.Movie;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Class Producer present on class Movie to representing the producer of the movie.
 *
 * @see fr.nicolasgille.medialibrary.models.movie.Movie
 * @author Nicolas GILLE
 * @since Media-Library 1.0
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
    @ManyToMany(targetEntity = Movie.class, mappedBy = "producers", cascade = CascadeType.ALL)
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
     * @param id        Identifier stored on DB.
     * @param firstName First name.
     * @param lastName  Last name.
     * @version 1.0
     * @since 1.0
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
     * @version 1.0
     * @since 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set id of the Producer.
     *
     * @param id New id.
     * @version 1.0
     * @since 1.0
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return first name of the Producer.
     *
     * @return First name of the Producer.
     * @version 1.0
     * @since 1.0
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name of the Producer.
     *
     * @param firstName New first name.
     * @version 1.0
     * @since 1.0
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return last name of the Producer.
     *
     * @return Last name of the Producer.
     * @version 1.0
     * @since 1.0
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the Producer.
     *
     * @param lastName New last name.
     * @version 1.0
     * @since 1.0
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * List of movies where the actor played as main actor.
     *
     * @return List of all movies where the actor played as main actor.
     * @version 1.0
     * @since 1.0
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Set list of movies where actor played as main actor.
     *
     * @param movies New list of movies.
     * @version 1.0
     * @since 1.0
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Display content of the Producer.
     *
     * @return A simple representation of the Producer.
     * @version 1.1
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Producer{" +
                ", id=" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", movies='" + movies + '\'' +
                '}';
    }
}
