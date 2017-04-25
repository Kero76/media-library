package fr.nicolasgille.medialibrary.models.movie;

import fr.nicolasgille.medialibrary.models.common.Actor;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Movie class.
 *
 * This class is a model of the movie object stored in persistent system.
 * If you would add new attribute on the Database, you just add new attribute on this class
 * to regenerate new Database with new row.
 *
 * V 1.1 :
 *  -> Added constructor Movie(Movie movie) use to copy a movie into another movie. (see MovieController for more information).
 *  -> Added toString() method to see result on logger.
 *  -> Removed unused constructors.
 *  -> Update field category to add multiple category for movie.
 *  -> Update constructors with new attributes.
 *  -> Added following fields : supports,
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.1
 */
@Entity
@Table(name = "movies")
public class Movie {

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
     * Title of the movie.
     *
     * @since 1.0
     */
    @NotNull
    private String title;

    /**
     * Date of release.
     *
     * @since 1.0
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    private Calendar releaseDate;

    /**
     * Duration of the movies (in minutes).
     *
     * @since 1.0
     */
    @NotNull
    private int duration;

    /**
     * Synopsis of the movie.
     *
     * @since 1.0
     */
    private String synopsis;

    /**
     * Main actors present on the movie.
     *
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "movies_main_actors",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "main_actors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Actor.class, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Actor> mainActors;

    /**
     * List of Producer for the movie.
     *
     * @since 1.1
     */
    @NotNull
    @JoinTable(
            name = "movies_producers",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "producers_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Producer.class, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Producer> producers;

    /**
     * List of Director for the movie.
     *
     * @since 1.1
     */
    @NotNull
    @JoinTable(
            name = "movies_directors",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "directors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Director.class, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Director> directors;

    /**
     * List of all categories of the movie.
     *
     * @see MovieCategory
     * @since 1.0
     */
    @NotNull
    @ElementCollection(targetClass = MovieCategory.class)
    private List<MovieCategory> categories;

    /**
     * List of Support for the movie
     *
     * @see MovieSupport
     * @since 1.1
     */
    @NotNull
    @ElementCollection(targetClass = MovieSupport.class)
    private List<MovieSupport> supports;

    /**
     * Empty constructor.
     *
     * @since 1.0
     * @version 1.0
     */
    public Movie() {}

    /**
     * Constructor of the movie object.
     *
     * @param title
     *  Title of the movie.
     * @param categories
     *  List of all category for the movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param duration
     *  Duration of the movie in minute.
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  Main actors of the movie.
     * @param producers
     *  List of all producers of the movie.
     * @param directors
     *  List of all directors of the movie.
     * @param supports
     *  Supports present for the movie.
     * @since 1.0
     * @version 1.1
     */
    public Movie(String title, List<MovieCategory> categories, Calendar releaseDate, int duration, String synopsis, List<Actor> mainActors, List<Producer> producers, List<Director> directors, List<MovieSupport> supports) {
        this.title       = title;
        this.categories  = categories;
        this.releaseDate = releaseDate;
        this.duration    = duration;
        this.synopsis    = synopsis;
        this.mainActors  = mainActors;
        this.supports    = supports;
        this.directors   = directors;
        this.producers   = producers;
    }

    /**
     * Constructor of the movie object.
     * This constructor is used to stored information retrieve from the persistent system.
     *
     * @param id
     *  identifier of the movie.
     * @param title
     *  Title of the movie.
     * @param categories
     *  List of all category of the movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param duration
     *  Duration of the movie in minute.
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  Main actors of the movie.
     * @param producers
     *  List of all producers of the movie.
     * @param directors
     *  List of all directors of the movie.
     * @param supports
     *  Supports present for the movie.
     * @since 1.0
     * @version 1.0
     */
    public Movie(long id, String title, List<MovieCategory> categories, Calendar releaseDate, int duration, String synopsis, List<Actor> mainActors, List<Producer> producers, List<Director> directors, List<MovieSupport> supports) {
        this.id          = id;
        this.title       = title;
        this.categories  = categories;
        this.releaseDate = releaseDate;
        this.duration    = duration;
        this.synopsis    = synopsis;
        this.mainActors  = mainActors;
        this.producers   = producers;
        this.directors   = directors;
        this.supports    = supports;
    }

    /**
     * Constructor use to update attribute of the current movie by the movie passed on parameter.
     *
     * @param movie
     *  New content of each attribute of this.
     * @since 1.1
     * @version 1.0
     */
    public Movie(Movie movie) {
        this.id          = movie.getId();
        this.title       = movie.getTitle();
        this.categories  = movie.getCategories();
        this.releaseDate = movie.getReleaseDate();
        this.duration    = movie.getDuration();
        this.synopsis    = movie.getSynopsis();
        this.mainActors  = movie.getMainActors();
        this.producers   = movie.getProducers();
        this.directors   = movie.getDirectors();
        this.supports    = movie.getSupports();
    }

    /**
     * Return the id.
     *
     * @return
     *  The id.
     * @since 1.0
     * @version 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id.
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
     * Return the title.
     *
     * @return
     *  The title of the movie.
     * @since 1.0
     * @version 1.0
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     *
     * @param title
     *  New title.
     * @since 1.0
     * @version 1.0
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the category.
     *
     * @return
     *  The category of the movie.
     * @see MovieCategory
     * @since 1.0
     * @version 1.1
     */
    public List<MovieCategory> getCategories() {
        return categories;
    }

    /**
     * Set movieCategory.
     *
     * @param category
     *  New category.
     * @since 1.0
     * @version 1.1
     */
    public void setCategories(List<MovieCategory> category) {
        this.categories = category;
    }

    /**
     * Return the release date.
     *
     * @return
     *  The release date.
     * @since 1.0
     * @version 1.0
     */
    public Calendar getReleaseDate() {
        return releaseDate;
    }

    /**
     * Set releaseDate.
     *
     * @param releaseDate
     *  New date of release.
     * @since 1.0
     * @version 1.0
     */
    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Return the duration of the movie in minute.
     *
     * @return
     *  The duration in minute.
     * @since 1.0
     * @version 1.0
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set duration in minute.
     *
     * @param duration
     *  New duration in minute.
     * @since 1.0
     * @version 1.0
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Return the synopsis.
     *
     * @return
     *  The synopsis.
     * @since 1.0
     * @version 1.0
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Set synopsis.
     *
     * @param synopsis
     *  New synopsis.
     * @since 1.0
     * @version 1.0
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Return the main actors separate by comma.
     *
     * @return
     *  A string representation of the actor separated by comma.
     * @since 1.0
     * @version 1.0
     */
    public List<Actor> getMainActors() {
        return mainActors;
    }

    /**
     * Set main actors.
     *
     * @param mainActors
     *  New mainActors.
     * @since 1.0
     * @version 1.0
     */
    public void setMainActors(List<Actor> mainActors) {
        this.mainActors = mainActors;
    }

    /**
     * Return all supports for the movie.
     *
     * @return
     *  List of all supports.
     * @since 1.0
     * @version 1.0
     */
    public List<MovieSupport> getSupports() {
        return supports;
    }

    /**
     * Set the supports for the movie.
     *
     * @param supports
     *  New Supports.
     * @since 1.0
     * @version 1.0
     */
    public void setSupports(List<MovieSupport> supports) {
        this.supports = supports;
    }

    /**
     * Return all producers for the movie.
     *
     * @return
     *  List of all producer of the movie.
     * @since 1.0
     * @version 1.1
     */
    public List<Producer> getProducers() {
        return producers;
    }

    /**
     * Set the list of producers.
     *
     * @return
     *  New List of producer.
     * @since 1.0
     * @version 1.1
     */
    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    /**
     * Return all directors for the movie.
     *
     * @return
     *  List of all directors of the movie.
     * @since 1.0
     * @version 1.1
     */
    public List<Director> getDirectors() {
        return directors;
    }

    /**
     * Set the list of directors.
     *
     * @return
     *  New List of directors.
     * @since 1.0
     * @version 1.1
     */
    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    /**
     * Display Movie information.
     *
     * @return
     *  A short description of the content of the movie's attribute.
     * @since 1.1
     * @version 1.1
     */
    @Override
    public String toString() {
        // Build categories string.
        StringBuilder categories = new StringBuilder();
        for (int i = 0; i < this.categories.size(); ++i) {
            categories.append(this.categories.get(i).name().toLowerCase());
            if (i != this.categories.size() - 1) {
                categories.append(", ");
            }
        }

        // Build supports string.
        StringBuilder supports = new StringBuilder();
        for (int i = 0; i < this.supports.size(); ++i) {
            supports.append(this.supports.get(i).name().toLowerCase());
            if (i != this.supports.size() - 1) {
                supports.append(", ");
            }
        }

        // Build mainActor string.
        StringBuilder mainActors = new StringBuilder();
        for (int i = 0; i < this.mainActors.size(); ++i) {
            mainActors.append(this.mainActors.get(i).toString());
            if (i != this.mainActors.size() - 1) {
                mainActors.append(", ");
            }
        }

        // Build producers string.
        StringBuilder producers = new StringBuilder();
        for (int i = 0; i < this.producers.size(); ++i) {
            producers.append(this.producers.get(i).toString());
            if (i != this.producers.size() - 1) {
                producers.append(", ");
            }
        }

        // Build directors string.
        StringBuilder directors = new StringBuilder();
        for (int i = 0; i < this.directors.size(); ++i) {
            directors.append(this.directors.get(i).toString());
            if (i != this.directors.size() - 1) {
                directors.append(", ");
            }
        }

        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + categories.toString() +
                ", releaseDate=" + releaseDate.toString() +
                ", duration=" + duration +
                ", synopsis='" + synopsis + '\'' +
                ", mainActors='" + mainActors.toString() + '\'' +
                ", producers='" + producers.toString() + '\'' +
                ", producers='" + directors.toString() + '\'' +
                ", supports='" + supports.toString() +
                '}';
    }
}
