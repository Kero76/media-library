package fr.nicolasgille.medialibrary.models.movie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
     * List of all categories of the movie.
     *
     * @see MovieCategory
     * @since 1.0
     */
    @NotNull
    private List<MovieCategory> categories;

    /**
     * Date of release.
     *
     * @since 1.0
     */
    @NotNull
    private int releaseDate;

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
     * Main actors present on the movie, separate only by one comma.
     *
     * @since 1.0
     */
    private String mainActors;

    /**
     * List of Support for the movie
     *
     * @see MovieSupport
     * @since 1.1
     */
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
     * @since 1.0
     * @version 1.0
     */
    public Movie(String title, List<MovieCategory> categories, int releaseDate, int duration, String synopsis, String mainActors, List<MovieSupport> supports) {
        this.title       = title;
        this.categories  = categories;
        this.releaseDate = releaseDate;
        this.duration    = duration;
        this.synopsis    = synopsis;
        this.mainActors  = mainActors;
        this.supports    = supports;
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
     * @since 1.0
     * @version 1.0
     */
    public Movie(long id, String title, List<MovieCategory> categories, int releaseDate, int duration, String synopsis, String mainActors, List<MovieSupport> supports) {
        this.id          = id;
        this.title       = title;
        this.categories  = categories;
        this.releaseDate = releaseDate;
        this.duration    = duration;
        this.synopsis    = synopsis;
        this.mainActors  = mainActors;
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
    public int getReleaseDate() {
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
    public void setReleaseDate(int releaseDate) {
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
    public String getMainActors() {
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
    public void setMainActors(String mainActors) {
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
            categories.append(this.supports.get(i).name().toLowerCase());
            if (i != this.supports.size() - 1) {
                supports.append(", ");
            }
        }

        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category=" + categories.toString() +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", synopsis='" + synopsis + '\'' +
                ", mainActors='" + mainActors + '\'' +
                ", supports='" + supports.toString() +
                '}';
    }
}
