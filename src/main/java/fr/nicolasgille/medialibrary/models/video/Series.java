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
 * Model class for Series instance.
 *
 * It extends the class Video to get all main attributes for series type.
 * The attributes added on the media are :
 * <ul>
 *     <li>The List of main actors of the movie</li>
 * </ul>
 *
 * @see Anime
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 2.1
 */
@Entity
@DiscriminatorValue(value = "series")
public class Series extends Anime {

    /**
     * Main actors present on the series.
     *
     * @see Actor
     * @since 1.0
     */
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
    public Series() {}

    /**
     * Constructor of the series object.
     *
     * @param title
     *  Title of the series.
     * @param originalTitle
     *  Original title of the series.
     * @param synopsis
     *  Synopsis of the series.
     * @param mainActors
     *  Main actors of the series.
     * @param producers
     *  List of all producers of the series.
     * @param directors
     *  List of all directors of the series.
     * @param genres
     *  List of all genres for the series.
     * @param supports
     *  Supports present for the series.
     * @param languagesSpoken
     *  List of languages spoken available on series.
     * @param subtitles
     *  List of subtitle languages available on series.
     * @param numberOfSeasons
     *  Number of seasons who composed the series.
     * @param currentSeason
     *  Current season of the series.
     * @param startDate
     *  Release date of the series.
     * @param endDate
     *  End date of the release.
     * @param maxEpisodes
     *  Number of episode during the series.
     * @param numberOfEpisode
     *  Average time of episode in minute.
     * @param averageEpisodeRuntime
     *  Number of episode available on the season.
     * @since 1.0
     * @version 1.2
     */
    public Series(String title, String originalTitle, String synopsis,
                  Set<Actor> mainActors, Set<Director> directors, Set<Producer> producers,
                  List<MediaGenre> genres, List<MediaSupport> supports,
                  List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                  Calendar startDate, Calendar endDate,
                  int numberOfSeasons, int currentSeason,
                  int maxEpisodes, int numberOfEpisode, int averageEpisodeRuntime) {
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
        super.releaseDate = startDate;
        super.endDate = endDate;
        super.numberOfSeasons = numberOfSeasons;
        super.currentSeason = currentSeason;
        super.maxEpisodes = maxEpisodes;
        super.numberOfEpisode = numberOfEpisode;
        super.averageEpisodeRuntime = averageEpisodeRuntime;
    }

    /**
     * Constructor of the series object.
     *
     * @param id
     *  Identifier of the series.
     * @param title
     *  Title of the series.
     * @param originalTitle
     *  Original title of the series.
     * @param synopsis
     *  Synopsis of the series.
     * @param mainActors
     *  Main actors of the series.
     * @param producers
     *  List of all producers of the series.
     * @param directors
     *  List of all directors of the series.
     * @param genres
     *  List of all genres for the series.
     * @param supports
     *  Supports present for the series.
     * @param languagesSpoken
     *  List of languages spoken available on series.
     * @param subtitles
     *  List of subtitle languages available on series.
     * @param numberOfSeasons
     *  Number of seasons who composed the series.
     * @param currentSeason
     *  Current season of the series.
     * @param startDate
     *  Release date of the series.
     * @param endDate
     *  End date of the release.
     * @param maxEpisodes
     *  Number of episode during the series.
     * @param numberOfEpisode
     *  Average time of episode in minute.
     * @param averageEpisodeRuntime
     *  Number of episode available on the season.
     * @since 1.0
     * @version 1.2
     */
    public Series(long id, String title, String originalTitle, String synopsis,
                  Set<Actor> mainActors, Set<Director> directors, Set<Producer> producers,
                  List<MediaGenre> genres, List<MediaSupport> supports,
                  List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                  Calendar startDate, Calendar endDate,
                  int numberOfSeasons, int currentSeason,
                  int maxEpisodes, int numberOfEpisode, int averageEpisodeRuntime) {
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
        super.releaseDate = startDate;
        super.endDate = endDate;
        super.numberOfSeasons = numberOfSeasons;
        super.currentSeason = currentSeason;
        super.maxEpisodes = maxEpisodes;
        super.numberOfEpisode = numberOfEpisode;
        super.averageEpisodeRuntime = averageEpisodeRuntime;
    }

    /**
     * Constructor use to update attribute of the current series by the series passed on parameter.
     *
     * @param series
     *  New content of each attribute of super.
     * @since 1.0
     * @version 1.1
     */
    public Series(Series series) {
        super.id = series.getId();
        super.title = series.getTitle();
        super.originalTitle = series.getOriginalTitle();
        super.genres = series.getGenres();
        super.synopsis = series.getSynopsis();
        this.mainActors = series.getMainActors();
        super.supports = series.getSupports();
        super.directors = series.getDirectors();
        super.producers = series.getProducers();
        super.subtitles = series.getSubtitles();
        super.languagesSpoken = series.getLanguagesSpoken();
        super.numberOfSeasons = series.getNumberOfSeasons();
        super.currentSeason = series.getCurrentSeason();
        super.releaseDate = series.getReleaseDate();
        super.endDate = series.getEndDate();
        super.numberOfEpisode = series.getNumberOfEpisode();
        super.averageEpisodeRuntime = series.getAverageEpisodeRuntime();
        super.maxEpisodes = series.getMaxEpisodes();
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
     * Display Series information.
     *
     * @return
     *  A short description of the content of the series's attribute.
     * @since 1.0
     * @version 2.0.1
     */
    @Override
    public String toString() {
        return "Series{" +
                "id=" + super.id +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + super.originalTitle + '\'' +
                ", genres=" + CollectionAsString.listToString(super.genres) +
                ", synopsis='" + super.synopsis + '\'' +
                ", mainActors='" + CollectionAsString.setToString(this.mainActors) + '\'' +
                ", producers='" + CollectionAsString.setToString(super.producers) + '\'' +
                ", directors='" + CollectionAsString.setToString(super.directors) + '\'' +
                ", supports='" + CollectionAsString.listToString(super.supports) +
                ", languageSpoken='" + CollectionAsString.listToString(super.languagesSpoken) +
                ", subtitles='" + CollectionAsString.listToString(super.subtitles) +
                ", numberOfSeasons=" + super.numberOfSeasons +
                ", currentSeason=" + super.currentSeason +
                ", startDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", endDate=" + DateFormatter.frenchDate(super.endDate) +
                ", averageEpisodeRuntime=" + super.averageEpisodeRuntime +
                ", numberOfEpisode=" + super.numberOfEpisode +
                ", maxepisodes=" + super.maxEpisodes +
                '}';
    }
}
