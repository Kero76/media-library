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
 * It extends the class Video to get all main attributes for video type.
 * The attributes added on the media are :
 * <ul>
 *     <li>The List of main actors of the movie</li>
 *     <li>The number of seasons for the series.</li>
 *     <li>The current season of the series.</li>
 *     <li>The average time for the episode of the series.</li>
 *     <li>The number of episode for the current season.</li>
 *     <li>The number of episode for all seasons of the seasons.</li>
 *     <li></li>
 * </ul>
 *
 * @see Video
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 2.0
 */
@Entity
@DiscriminatorValue(value = "series")
public class Series extends Video {

    /**
     * Main actors present on the series.
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
     * Number of seasons for the series.
     *
     * @since 1.0
     */
    @NotNull
    private int numberOfSeasons;

    /**
     * Current season of the series.
     *
     * @since 1.0
     */
    @NotNull
    private int currentSeason;

    /**
     * Date of the end of the series.
     *
     * @since 1.0
     */
    @Temporal(TemporalType.DATE)
    private Calendar endDate;

    /**
     * Average runtime episode in minutes.
     *
     * @since 1.0
     */
    @NotNull
    private int averageEpisodeRuntime;

    /**
     * Number of episode available for the season.
     *
     * @since 1.0
     */
    @NotNull
    private int numberOfEpisode;

    /**
     * Number of episodes for the series.
     *
     * @since 1.1
     */
    @NotNull
    private int maxEpisodes;

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
        this.directors = directors;
        this.producers = producers;
        super.genres = genres;
        super.supports = supports;
        super.languagesSpoken = languagesSpoken;
        super.subtitles = subtitles;
        super.releaseDate = startDate;
        this.endDate = endDate;
        this.numberOfSeasons = numberOfSeasons;
        this.currentSeason = currentSeason;
        this.maxEpisodes = maxEpisodes;
        this.numberOfEpisode = numberOfEpisode;
        this.averageEpisodeRuntime = averageEpisodeRuntime;
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
        this.directors = directors;
        this.producers = producers;
        super.genres = genres;
        super.supports = supports;
        super.languagesSpoken = languagesSpoken;
        super.subtitles = subtitles;
        super.releaseDate = startDate;
        this.endDate = endDate;
        this.numberOfSeasons = numberOfSeasons;
        this.currentSeason = currentSeason;
        this.maxEpisodes = maxEpisodes;
        this.numberOfEpisode = numberOfEpisode;
        this.averageEpisodeRuntime = averageEpisodeRuntime;
    }

    /**
     * Constructor use to update attribute of the current series by the series passed on parameter.
     *
     * @param series
     *  New content of each attribute of this.
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
        this.numberOfSeasons = series.getNumberOfSeasons();
        this.currentSeason = series.getCurrentSeason();
        super.releaseDate = series.getReleaseDate();
        this.endDate = series.getEndDate();
        this.numberOfEpisode = series.getNumberOfEpisode();
        this.averageEpisodeRuntime = series.getAverageEpisodeRuntime();
        this.maxEpisodes = series.getMaxEpisodes();
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
     * Get the number of season for the series.
     *
     * @return
     *  The number of seasons for the series.
     * @since 1.0
     * @version 1.0
     */
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     * Set the number of seasons who composed the series.
     *
     * @param numberOfSeasons
     *  New number of seasons.
     * @since 1.0
     * @version 1.0
     */
    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    /**
     * Get the current season number.
     *
     * @return
     *  The current number.
     * @since 1.0
     * @version 1.0
     */
    public int getCurrentSeason() {
        return currentSeason;
    }

    /**
     * Set the current number of seasons.
     *
     * @param currentSeason
     *  Current season number.
     * @since 1.0
     * @version 1.0
     */
    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    /**
     * Get the date of the end of the series.
     *
     * @return
     *  The date of the end of the series.
     * @since 1.0
     * @version 1.0
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Set the date of the end of the series.
     *
     * @param endDate
     *  The end of the series.
     * @since 1.0
     * @version 1.0
     */
    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the average time for each episode in minute.
     *
     * @return
     *  The average time of each episode in minute.
     * @since 1.0
     * @version 1.0
     */
    public int getAverageEpisodeRuntime() {
        return averageEpisodeRuntime;
    }

    /**
     * Set the average time for each episode.
     *
     * @param averageEpisodeRuntime
     *  New average time of episode.
     * @since 1.0
     * @version 1.0
     */
    public void setAverageEpisodeRuntime(int averageEpisodeRuntime) {
        this.averageEpisodeRuntime = averageEpisodeRuntime;
    }

    /**
     * Get the number of episode available during the current season.
     *
     * @return
     *  The number of episode available during the current season.
     * @since 1.0
     * @version 1.0
     */
    public int getNumberOfEpisode() {
        return numberOfEpisode;
    }

    /**
     * Set the number of episode available during the current season.
     *
     * @param numberOfEpisode
     *  The new number of episode available during the current season.
     * @since 1.0
     * @version 1.0
     */
    public void setNumberOfEpisode(int numberOfEpisode) {
        this.numberOfEpisode = numberOfEpisode;
    }

    /**
     * Get the number of episodes available on the series.
     *
     * @return
     *  The number of episodes for the series.
     * @since 1.1
     * @version 1.0
     */
    public int getMaxEpisodes() {
        return maxEpisodes;
    }

    /**
     * Set the number of episodes available on the series.
     *
     * @param maxEpisodes
     *  The new number of episodes for the series.
     * @since 1.1
     * @version 1.0
     */
    public void setMaxEpisodes(int maxEpisodes) {
        this.maxEpisodes = maxEpisodes;
    }

    /**
     * Display Series information.
     *
     * @return
     *  A short description of the content of the series's attribute.
     * @since 1.0
     * @version 2.0
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
                ", supports='" + CollectionAsString.listToString(supports) +
                ", languageSpoken='" + CollectionAsString.listToString(languagesSpoken) +
                ", subtitles='" + CollectionAsString.listToString(subtitles) +
                ", numberOfSeasons=" + numberOfSeasons +
                ", currentSeason=" + currentSeason +
                ", startDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", endDate=" + endDate.toString() +
                ", averageEpisodeRuntime=" + averageEpisodeRuntime +
                ", numberOfEpisode=" + numberOfEpisode +
                ", maxepisodes=" + maxEpisodes +
                '}';
    }
}
