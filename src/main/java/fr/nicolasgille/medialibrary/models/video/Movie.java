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
package fr.nicolasgille.medialibrary.models.video;

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.models.common.person.Actor;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;
import fr.nicolasgille.medialibrary.utils.DateFormatter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Model class for Movie instance.
 *
 * It extends the class Video to get all main attributes for video type.
 * The attributes added on the media are :
 * <ul>
 *     <li>The <code>runtime</code> of the movie.</li>
 *     <li>The List of main actors of the movie</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 2.0
 */
@Entity
@DiscriminatorValue(value = "movie")
public class Movie extends Video {

    /**
     * Runtime of the movies (in minutes).
     *
     * @since 1.0
     */
    @NotNull
    private Integer runtime;

    /**
     * Main actors present on the movie.
     *
     * @see Actor
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "video_main_actors",
            joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "main_actors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Actor.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Actor> mainActors;

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
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  Main actors of the movie.
     * @param directors
     *  List of all directors of the movie.
     * @param producers
     *  List of all producers of the movie.
     * @param genres
     *  List of all genres for the movie.
     * @param supports
     *  Supports present for the movie.
     * @param languagesSpoken
     *  List of languages spoken available on movie.
     * @param subtitles
     *  List of subtitle languages available on movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param runtime
     *  Duration of the movie in minute.
     * @since 1.0
     * @version 2.0
     */
    public Movie(String title, String originalTitle, String synopsis,
                 Set<Actor> mainActors, Set<Director> directors, Set<Producer> producers,
                 List<MediaGenre> genres, List<MediaSupport> supports,
                 List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                 Calendar releaseDate, int runtime) {
        super.title = title;
        super.originalTitle = originalTitle;
        super.synopsis = synopsis;
        this.mainActors = mainActors;
        super.directors = directors;
        super.producers = producers;
        super.genres = genres;
        super.supports = supports;
        super.languagesSpoken = languagesSpoken;
        super.subtitles = subtitles;
        super.releaseDate = releaseDate;
        this.runtime = runtime;
    }

    /**
     * Constructor of the movie object.
     * This constructor is used to stored information retrieve from the persistent system.
     *
     * @param title
     *  Title of the movie.
     * @param originalTitle
     *  Original title of the movie.
     * @param synopsis
     *  Synopsis of the movie.
     * @param mainActors
     *  Main actors of the movie.
     * @param directors
     *  List of all directors of the movie.
     * @param producers
     *  List of all producers of the movie.
     * @param genres
     *  List of all genres for the movie.
     * @param supports
     *  Supports present for the movie.
     * @param languagesSpoken
     *  List of languages spoken available on movie.
     * @param subtitles
     *  List of subtitle languages available on movie.
     * @param releaseDate
     *  Date of release of the movie.
     * @param runtime
     *  Duration of the movie in minute.
     * @since 1.0
     * @version 2.0
     */
    public Movie(long id, String title, String originalTitle, String synopsis,
                 Set<Actor> mainActors, Set<Director> directors, Set<Producer> producers,
                 List<MediaGenre> genres, List<MediaSupport> supports,
                 List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                 Calendar releaseDate, int runtime) {
        super.id = id;
        super.title = title;
        super.originalTitle = originalTitle;
        super.synopsis = synopsis;
        this.mainActors = mainActors;
        super.directors = directors;
        super.producers = producers;
        super.genres = genres;
        super.supports = supports;
        super.languagesSpoken = languagesSpoken;
        super.subtitles = subtitles;
        super.releaseDate = releaseDate;
        this.runtime = runtime;
    }

    /**
     * Constructor use to update attribute of the current movie by the movie passed on parameter.
     *
     * @param movie
     *  New content of each attribute of this.
     * @since 1.0
     * @version 2.0
     */
    public Movie(Movie movie) {
        super.id = movie.getId();
        super.title = movie.getTitle();
        super.originalTitle = movie.getOriginalTitle();
        super.synopsis = movie.getSynopsis();
        this.mainActors = movie.getMainActors();
        super.genres = movie.getGenres();
        super.supports = movie.getSupports();
        this.directors = movie.getDirectors();
        this.producers = movie.getProducers();
        super.languagesSpoken = movie.getLanguagesSpoken();
        super.subtitles = movie.getSubtitles();
        this.releaseDate = movie.getReleaseDate();
        this.runtime = movie.getRuntime();
    }

    /**
     * Return the runtime of the movie in minute.
     *
     * @return
     *  The runtime in minute.
     * @since 1.0
     * @version 1.0
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
     * @version 1.0
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Return the set composed by main actors.
     *
     * @return
     *  Set of the main actors.
     * @since 1.0
     * @version 1.0
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
     * @version 1.0
     */
    public void setMainActors(Set<Actor> mainActors) {
        this.mainActors = mainActors;
    }

    /**
     * Display Movie information.
     *
     * @return
     *  A short description of the content of the movie's attribute.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + super.id +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + super.originalTitle + '\'' +
                ", genres=" + CollectionAsString.listToString(super.genres) +
                ", releaseDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", runtime=" + this.runtime +
                ", synopsis='" + super.synopsis + '\'' +
                ", mainActors='" + CollectionAsString.setToString(this.mainActors) + '\'' +
                ", producers='" + CollectionAsString.setToString(super.producers) + '\'' +
                ", directors='" + CollectionAsString.setToString(super.directors) + '\'' +
                ", supports='" + CollectionAsString.listToString(supports) +
                ", languageSpoken='" + CollectionAsString.listToString(languagesSpoken) +
                ", subtitles='" + CollectionAsString.listToString(subtitles) +
                '}';
    }
}
