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
package fr.nicolasgille.medialibrary.models.movie;

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.models.common.Actor;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Movie class.
 *
 * This class is a model of the movie object stored in persistent system.
 * If you would add new attribute on the Database, you just add new attributes on this class
 * to regenerate new Database with new corresponding rows.
 *
 * V 1.1 :
 * <ul>
 *     <li>Added constructor Movie(Movie movie) use to copy a movie into another movie. (see MovieController for more information).</li>
 *     <li>Added toString() method to see result on logger.</li>
 *     <li>Removed unused constructors.</li>
 *     <li>Update field category to add multiple category for movie.</li>
 *     <li>Update constructors with new attributes.</li>
 *     <li>Added following fields :
 *          <ul>
 *              <li>supports</li>
 *              <li>directors</li>
 *              <li>producers</li>
 *              <li>languagesSpoken</li>
 *              <li>subtitleLanguage</li>
 *              <li>originalTitle</li>
 *          </ul>
 *     </li>
 *     <li>Update <code>releaseDate</code> type by Calendar object.</li>
 * </ul>
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
     * Original title of the movie.
     *
     * @since 1.1
     */
    private String originalTitle;

    /**
     * Date of release.
     *
     * @since 1.0
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    private Calendar releaseDate;

    /**
     * Runtime of the movies (in minutes).
     *
     * @since 1.0
     */
    @NotNull
    private Integer runtime;

    /**
     * Synopsis of the movie.
     *
     * @since 1.0
     */
    private String synopsis;

    /**
     * Main actors present on the movie.
     *
     * @see Actor
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "movies_main_actors",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "main_actors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Actor.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Actor> mainActors;

    /**
     * List of Producer for the movie.
     *
     * @see Producer
     * @since 1.1
     */
    @NotNull
    @JoinTable(
            name = "movies_producers",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "producers_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Producer.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Producer> producers;

    /**
     * List of Director for the movie.
     *
     * @see Director
     * @since 1.1
     */
    @NotNull
    @JoinTable(
            name = "movies_directors",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "directors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Director.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Director> directors;

    /**
     * List of all categories of the movie.
     *
     * @see MovieGenre
     * @since 1.0
     */
    @NotNull
    @ElementCollection(targetClass = MovieGenre.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MovieGenre> genres;

    /**
     * List of Support for the movie
     *
     * @see MovieSupport
     * @since 1.1
     */
    @NotNull
    @ElementCollection(targetClass = MovieSupport.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MovieSupport> supports;

    /**
     * List of language spoken available on the movie.
     *
     * @see LanguageCode
     * @since 1.1
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = LanguageCode.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LanguageCode> languagesSpoken;

    /**
     * List of language present as subtitle available on the movie.
     *
     * @see LanguageCode
     * @since 1.1
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = LanguageCode.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LanguageCode> subtitles;

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
     * @param originalTitle
     *  Original title of the movie.
     * @param genres
     *  List of all genres for the movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param runtime
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
     * @param languagesSpoken
     *  List of languages spoken available on movie.
     * @param subtitles
     *  List of subtitle languages available on movie.
     * @since 1.0
     * @version 1.1
     */
    public Movie(String title, String originalTitle, List<MovieGenre> genres, Calendar releaseDate, int runtime, String synopsis,
                 Set<Actor> mainActors, Set<Producer> producers, Set<Director> directors, List<MovieSupport> supports,
                 List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles) {
        this.title         = title;
        this.originalTitle = originalTitle;
        this.genres        = genres;
        this.releaseDate   = releaseDate;
        this.runtime       = runtime;
        this.synopsis      = synopsis;
        this.mainActors    = mainActors;
        this.supports      = supports;
        this.directors     = directors;
        this.producers     = producers;
        this.subtitles     = subtitles;
        this.languagesSpoken = languagesSpoken;
    }

    /**
     * Constructor of the movie object.
     * This constructor is used to stored information retrieve from the persistent system.
     *
     * @param id
     *  identifier of the movie.
     * @param title
     *  Title of the movie.
     * @param genres
     *  List of all genres of the movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param runtime
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
     * @param languagesSpoken
     *  List of languages spoken available on movie.
     * @param subtitles
     *  List of subtitle languages available on movie.
     * @since 1.0
     * @version 1.1
     */
    public Movie(long id, String title, String originalTitle, List<MovieGenre> genres, Calendar releaseDate, int runtime, String synopsis,
                 Set<Actor> mainActors, Set<Producer> producers, Set<Director> directors, List<MovieSupport> supports,
                 List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles) {
        this.id            = id;
        this.title         = title;
        this.originalTitle = originalTitle;
        this.genres        = genres;
        this.releaseDate   = releaseDate;
        this.runtime       = runtime;
        this.synopsis      = synopsis;
        this.mainActors    = mainActors;
        this.producers     = producers;
        this.directors     = directors;
        this.supports      = supports;
        this.subtitles     = subtitles;
        this.languagesSpoken = languagesSpoken;
    }

    /**
     * Constructor use to update attribute of the current movie by the movie passed on parameter.
     *
     * @param movie
     *  New content of each attribute of this.
     * @since 1.1
     * @version 1.1
     */
    public Movie(Movie movie) {
        this.id            = movie.getId();
        this.title         = movie.getTitle();
        this.originalTitle = movie.getOriginalTitle();
        this.genres        = movie.getGenres();
        this.releaseDate   = movie.getReleaseDate();
        this.runtime       = movie.getRuntime();
        this.synopsis      = movie.getSynopsis();
        this.mainActors    = movie.getMainActors();
        this.producers     = movie.getProducers();
        this.directors     = movie.getDirectors();
        this.supports      = movie.getSupports();
        this.subtitles     = movie.getSubtitles();
        this.languagesSpoken = movie.getLanguagesSpoken();
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
     * Return the original title.
     *
     * @return
     *  The origianl title of the movie.
     * @since 1.1
     * @version 1.0
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Set original title.
     *
     * @param originalTitle
     *  New title.
     * @since 1.1
     * @version 1.0
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Return the genres.
     *
     * @return
     *  The genres of the movie.
     * @see MovieGenre
     * @since 1.0
     * @version 1.2
     */
    public List<MovieGenre> getGenres() {
        return genres;
    }

    /**
     * Set genres of Movie.
     *
     * @param genres
     *  New genres.
     * @since 1.0
     * @version 1.2
     */
    public void setGenres(List<MovieGenre> genres) {
        this.genres = genres;
    }

    /**
     * Return the release date.
     *
     * @return
     *  The release date.
     * @since 1.0
     * @version 1.1
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
     * @version 1.1
     */
    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Return the runtime of the movie in minute.
     *
     * @return
     *  The runtime in minute.
     * @since 1.0
     * @version 1.1
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Set runtime in minute.
     *
     * @param runtime
     *  New runtime in minute.
     * @since 1.0
     * @version 1.1
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
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
     * Return the set composed by main actors.
     *
     * @return
     *  Set of the main actors.
     * @since 1.0
     * @version 1.1
     */
    public Set<Actor> getMainActors() {
        return mainActors;
    }

    /**
     * Set main actors.
     *
     * @param mainActors
     *  New mainActors.
     * @since 1.0
     * @version 1.1
     */
    public void setMainActors(Set<Actor> mainActors) {
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
     *  Set of all producer of the movie.
     * @since 1.1
     * @version 1.1
     */
    public Set<Producer> getProducers() {
        return producers;
    }

    /**
     * Set the list of producers.
     *
     * @param producers
     *  New Set of producer.
     * @since 1.1
     * @version 1.1
     */
    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    /**
     * Return all directors for the movie.
     *
     * @return
     *  Set of all directors of the movie.
     * @since 1.1
     * @version 1.1
     */
    public Set<Director> getDirectors() {
        return directors;
    }

    /**
     * Set the list of directors.
     *
     * @param directors
     *  New set of Director.
     * @since 1.1
     * @version 1.1
     */
    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    /**
     * Get the list of languages spoken.
     *
     * @return
     *  A list of languages spoken.
     * @since 1.1
     * @version 1.0
     */
    public List<LanguageCode> getLanguagesSpoken() {
        return languagesSpoken;
    }

    /**
     * Set the list of languages spoken available on the movie.
     *
     * @param languagesSpoken
     *  New list of languages spoken.
     * @since 1.1
     * @version 1.0
     */
    public void setLanguagesSpoken(List<LanguageCode> languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    /**
     * Get the list of subtitle  languages.
     *
     * @return
     *  A list of subtitle languages.
     * @since 1.1
     * @version 1.1
     */
    public List<LanguageCode> getSubtitles() {
        return subtitles;
    }

    /**
     * Set the list of subtitle languages available on the movie.
     *
     * @param subtitles
     *  New list of subtitle languages.
     * @since 1.1
     * @version 1.1
     */
    public void setSubtitles(List<LanguageCode> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Display Movie information.
     *
     * @return
     *  A short description of the content of the movie's attribute.
     * @since 1.0
     * @version 1.1
     */
    @Override
    public String toString() {
        // Build categories string.
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < this.genres.size(); ++i) {
            genres.append(this.genres.get(i).getName());
            if (i != this.genres.size() - 1) {
                genres.append(", ");
            }
        }

        // Build supports string.
        StringBuilder supports = new StringBuilder();
        for (int i = 0; i < this.supports.size(); ++i) {
            supports.append(this.supports.get(i).getName());
            if (i != this.supports.size() - 1) {
                supports.append(", ");
            }
        }

        // Build languages spoken string.
        StringBuilder languagesSpoken = new StringBuilder();
        for (int i = 0; i < this.languagesSpoken.size(); ++i) {
            languagesSpoken.append(this.languagesSpoken.get(i).getName());
            if (i != this.languagesSpoken.size() - 1) {
                languagesSpoken.append(", ");
            }
        }

        // Build subtitles languages string.
        StringBuilder subtitles = new StringBuilder();
        for (int i = 0; i < this.subtitles.size(); ++i) {
            subtitles.append(this.subtitles.get(i).getName());
            if (i != this.subtitles.size() - 1) {
                subtitles.append(", ");
            }
        }

        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", categories=" + genres.toString() +
                ", releaseDate=" + releaseDate.toString() +
                ", runtime=" + runtime +
                ", synopsis='" + synopsis + '\'' +
                ", mainActors='" + this.setStringBuilder(this.mainActors) + '\'' +
                ", producers='" + this.setStringBuilder(this.producers) + '\'' +
                ", directors='" + this.setStringBuilder(this.directors) + '\'' +
                ", supports='" + supports.toString() +
                ", languageSpoken='" + languagesSpoken.toString() +
                ", subtitles='" + subtitles.toString() +
                '}';
    }

    /**
     * Generate a String with content of Set.
     *
     * @param set
     *  Set used to displayed element.
     * @return
     *  A string representation of the Set.
     * @since 1.1
     * @version 1.0
     */
    private String setStringBuilder(Set<?> set) {
        StringBuilder str = new StringBuilder();
        if (set != null) {
            for (int i = 0; i < set.size(); ++i) {
                str.append(set.toArray()[i].toString());
                if (i != set.size() - 1) {
                    str.append(", ");
                }
            }
        } else {
            str.append("");
        }
        return str.toString();
    }
}
