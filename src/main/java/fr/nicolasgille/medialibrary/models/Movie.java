package fr.nicolasgille.medialibrary.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Movie class.
 *
 * This class represent the information about a specific movies.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Entity
@Table(name = "movies")
public class Movie {

    /**
     * Identifier of the movie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * Title of the movie.
     */
    @NotNull
    private String title;

    /**
     * Category of the movie.
     *
     * @see MovieCategory
     */
    @NotNull
    private MovieCategory category;

    /**
     * Date of release.
     */
    @NotNull
    private int releaseDate;

    /**
     * Duration of the movies (in minutes).
     */
    @NotNull
    private int duration;

    /**
     * Synopsis of the movie.
     */
    private String synopsis;

    /**
     * Main actors present on the movie.
     */
    private String mainActors;

    /**
     * Empty constructor.
     */
    public Movie() {}

    /**
     * Constructor used to delete movie on Database.
     *
     * @param title
     *  Title of the movie at remove.
     */
    public Movie(String title) {
        this.title = title;
    }

    /**
     * Constructor of the movie object.
     *
     * @param title
     *  Title of the movie.
     * @param category
     *  Category of the movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param duration
     *  Duration of the movie in minute.
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  Main actors of the movie.
     */
    public Movie(String title, MovieCategory category, int releaseDate, int duration, String synopsis, String mainActors) {
        this.title       = title;
        this.category    = category;
        this.releaseDate = releaseDate;
        this.duration    = duration;
        this.synopsis    = synopsis;
        this.mainActors  = mainActors;
    }

    /**
     * Constructor of the movie object.
     *
     * @param id
     *  identifier of the movie.
     * @param title
     *  Title of the movie.
     * @param category
     *  Category of the movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param duration
     *  Duration of the movie in minute.
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  Main actors of the movie.
     */
    public Movie(long id, String title, MovieCategory category, int releaseDate, int duration, String synopsis, String mainActors) {
        this.id          = id;
        this.title       = title;
        this.category    = category;
        this.releaseDate = releaseDate;
        this.duration    = duration;
        this.synopsis    = synopsis;
        this.mainActors  = mainActors;
    }

    /**
     * Constructor use to update attribute of the current movie by the movie passed on parameter.
     * @param movie
     *  New content of each attribute of this.
     */
    public Movie(Movie movie) {
        this.id          = movie.getId();
        this.title       = movie.getTitle();
        this.category    = movie.getCategory();
        this.releaseDate = movie.getReleaseDate();
        this.duration    = movie.getDuration();
        this.synopsis    = movie.getSynopsis();
        this.mainActors  = movie.getMainActors();
    }

    /**
     * Return the id.
     *
     * @return
     *  The id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id
     *  New id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return the title.
     *
     * @return
     *  The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     *
     * @param title
     *  New title.
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
     */
    public MovieCategory getCategory() {
        return category;
    }

    /**
     * Set movieCategory.
     *
     * @param category
     *  New category.
     */
    public void setCategory(MovieCategory category) {
        this.category = category;
    }

    /**
     * Return the release date.
     *
     * @return
     *  The release date.
     */
    public int getReleaseDate() {
        return releaseDate;
    }

    /**
     * Set releaseDate.
     *
     * @param releaseDate
     *  New date of release.
     */
    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Return the duration of the movie in minute.
     *
     * @return
     *  The duration in minute.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set duration in minute.
     *
     * @param duration
     *  New duration in minute.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Return the synopsis.
     *
     * @return
     *  The synopsis.
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Set synopsis.
     *
     * @param synopsis
     *  New synopsis.
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Return the main actors separate by comma.
     *
     * @return
     *  A string representation of the actor separated by comma.
     */
    public String getMainActors() {
        return mainActors;
    }

    /**
     * Set main actors.
     *
     * @param mainActors
     *  New mainActors.
     */
    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }
}
