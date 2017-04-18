package fr.nicolasgille.medialibrary.model;

/**
 * Movie class.
 *
 * This class represent the information about a specific movies.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
public class Movie {

    /**
     * Identifier of the movie.
     */
    private long id;

    /**
     * Title of the movie.
     */
    private String title;

    /**
     * Category of the movie.
     *
     * @see MovieCategory
     */
    private MovieCategory category;

    /**
     * Date of release.
     */
    private int releaseDate;

    /**
     * Duration of the movies (in minutes).
     */
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
     * Constructor of the movie object.
     *
     * @param id
     *  identifier of the movie.
     * @param title
     *  Title of the movie.
     * @param category
     *  Category of the movie.
     * @param releaseDate
     *  Date of the movie was released.
     * @param duration
     *  Duration of the movie in minute.
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  Main actor present on the movie.
     */
    public Movie(long id, String title, MovieCategory category, int releaseDate, int duration, String synopsis, String mainActors) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.synopsis = synopsis;
        this.mainActors = mainActors;
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
     * Return the title.
     *
     * @return
     *  The title of the movie.
     */
    public String getTitle() {
        return title;
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
     * Return the release date.
     *
     * @return
     *  The release date.
     */
    public int getReleaseDate() {
        return releaseDate;
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
     * Return the synopsis.
     *
     * @return
     *  The synopsis.
     */
    public String getSynopsis() {
        return synopsis;
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
}
