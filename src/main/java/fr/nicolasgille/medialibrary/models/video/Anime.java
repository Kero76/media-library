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
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGenre;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;
import fr.nicolasgille.medialibrary.utils.DateFormatter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Model class for Anime instance.
 *
 * It extends the class Video to get all main attributes for video type.
 * The attributes added on the media are :
 * <ul>
 *     <li>The number of seasons for the anime.</li>
 *     <li>The current season of the anime.</li>
 *     <li>The average time for the episode of the anime.</li>
 *     <li>The number of episode for the current season.</li>
 *     <li>The number of episode for all seasons of the seasons.</li>
 * </ul>
 *
 * @see Video
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "anime")
public class Anime extends Video {

    /**
     * Number of seasons for the anime.
     *
     * @since 1.0
     */
    protected int numberOfSeasons;

    /**
     * Current season of the anime.
     *
     * @since 1.0
     */
    protected int currentSeason;

    /**
     * Date of the end of the anime.
     *
     * @since 1.0
     */
    @Temporal(TemporalType.DATE)
    protected Calendar endDate;

    /**
     * Average runtime episode in minutes.
     *
     * @since 1.0
     */
    protected int averageEpisodeRuntime;

    /**
     * Number of episode available for the season.
     *
     * @since 1.0
     */
    protected int numberOfEpisode;

    /**
     * Number of episodes for the anime.
     *
     * @since 1.1
     */
    protected int maxEpisodes;

    /**
     * Empty constructor.
     *
     * @since 1.0
     * @version 1.0
     */
    public Anime() {}

    /**
     * Constructor of the anime object.
     *
     * @param title
     *  Title of the anime.
     * @param originalTitle
     *  Original title of the anime.
     * @param synopsis
     *  Synopsis of the anime.
     * @param producers
     *  List of all producers of the anime.
     * @param directors
     *  List of all directors of the anime.
     * @param genres
     *  List of all genres for the anime.
     * @param supports
     *  Supports present for the anime.
     * @param languagesSpoken
     *  List of languages spoken available on anime.
     * @param subtitles
     *  List of subtitle languages available on anime.
     * @param numberOfSeasons
     *  Number of seasons who composed the anime.
     * @param currentSeason
     *  Current season of the anime.
     * @param startDate
     *  Release date of the anime.
     * @param endDate
     *  End date of the release.
     * @param maxEpisodes
     *  Number of episode during the anime.
     * @param numberOfEpisode
     *  Average time of episode in minute.
     * @param averageEpisodeRuntime
     *  Number of episode available on the season.
     * @since 1.0
     * @version 1.2
     */
    public Anime(String title, String originalTitle, String synopsis,
                  Set<Director> directors, Set<Producer> producers,
                  List<VideoGenre> genres, List<MediaSupport> supports,
                  List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                  Calendar startDate, Calendar endDate,
                  int numberOfSeasons, int currentSeason,
                  int maxEpisodes, int numberOfEpisode, int averageEpisodeRuntime) {
        super.title = title;
        super.originalTitle = originalTitle;
        super.synopsis = synopsis;
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
     * Constructor of the anime object.
     *
     * @param id
     *  Identifier of the anime.
     * @param title
     *  Title of the anime.
     * @param originalTitle
     *  Original title of the anime.
     * @param synopsis
     *  Synopsis of the anime.
     * @param producers
     *  List of all producers of the anime.
     * @param directors
     *  List of all directors of the anime.
     * @param genres
     *  List of all genres for the anime.
     * @param supports
     *  Supports present for the anime.
     * @param languagesSpoken
     *  List of languages spoken available on anime.
     * @param subtitles
     *  List of subtitle languages available on anime.
     * @param numberOfSeasons
     *  Number of seasons who composed the anime.
     * @param currentSeason
     *  Current season of the anime.
     * @param startDate
     *  Release date of the anime.
     * @param endDate
     *  End date of the release.
     * @param maxEpisodes
     *  Number of episode during the anime.
     * @param numberOfEpisode
     *  Average time of episode in minute.
     * @param averageEpisodeRuntime
     *  Number of episode available on the season.
     * @since 1.0
     * @version 1.2
     */
    public Anime(long id, String title, String originalTitle, String synopsis,
                 Set<Director> directors, Set<Producer> producers,
                 List<VideoGenre> genres, List<MediaSupport> supports,
                 List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                 Calendar startDate, Calendar endDate,
                 int numberOfSeasons, int currentSeason,
                 int maxEpisodes, int numberOfEpisode, int averageEpisodeRuntime) {
        super.id = id;
        super.title = title;
        super.originalTitle = originalTitle;
        super.synopsis = synopsis;
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
     * Constructor use to update attribute of the current anime by the anime passed on parameter.
     *
     * @param anime
     *  New content of each attribute of this.
     * @since 1.0
     * @version 1.1
     */
    public Anime(Anime anime) {
        super.id = anime.getId();
        super.title = anime.getTitle();
        super.originalTitle = anime.getOriginalTitle();
        super.genres = anime.getGenres();
        super.synopsis = anime.getSynopsis();
        super.supports = anime.getSupports();
        super.directors = anime.getDirectors();
        super.producers = anime.getProducers();
        super.subtitles = anime.getSubtitles();
        super.languagesSpoken = anime.getLanguagesSpoken();
        this.numberOfSeasons = anime.getNumberOfSeasons();
        this.currentSeason = anime.getCurrentSeason();
        super.releaseDate = anime.getReleaseDate();
        this.endDate = anime.getEndDate();
        this.numberOfEpisode = anime.getNumberOfEpisode();
        this.averageEpisodeRuntime = anime.getAverageEpisodeRuntime();
        this.maxEpisodes = anime.getMaxEpisodes();
    }

    /**
     * Get the number of season for the anime.
     *
     * @return
     *  The number of seasons for the anime.
     * @since 1.0
     * @version 1.0
     */
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    /**
     * Set the number of seasons who composed the anime.
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
     * Get the date of the end of the anime.
     *
     * @return
     *  The date of the end of the anime.
     * @since 1.0
     * @version 1.0
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Set the date of the end of the anime.
     *
     * @param endDate
     *  The end of the anime.
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
     * Get the number of episodes available on the anime.
     *
     * @return
     *  The number of episodes for the anime.
     * @since 1.1
     * @version 1.0
     */
    public int getMaxEpisodes() {
        return maxEpisodes;
    }

    /**
     * Set the number of episodes available on the anime.
     *
     * @param maxEpisodes
     *  The new number of episodes for the anime.
     * @since 1.1
     * @version 1.0
     */
    public void setMaxEpisodes(int maxEpisodes) {
        this.maxEpisodes = maxEpisodes;
    }

    /**
     * Display Anime information.
     *
     * @return
     *  A short description of the content of the anime's attribute.
     * @since 1.0
     * @version 2.0.1
     */
    @Override
    public String toString() {
        return "Anime{" +
                "id=" + super.id +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + super.originalTitle + '\'' +
                ", genres=" + CollectionAsString.listToString(super.genres) +
                ", synopsis='" + super.synopsis + '\'' +
                ", producers='" + CollectionAsString.setToString(super.producers) + '\'' +
                ", directors='" + CollectionAsString.setToString(super.directors) + '\'' +
                ", supports='" + CollectionAsString.listToString(supports) +
                ", languageSpoken='" + CollectionAsString.listToString(languagesSpoken) +
                ", subtitles='" + CollectionAsString.listToString(subtitles) +
                ", numberOfSeasons=" + numberOfSeasons +
                ", currentSeason=" + currentSeason +
                ", startDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", endDate=" + DateFormatter.frenchDate(endDate) +
                ", averageEpisodeRuntime=" + averageEpisodeRuntime +
                ", numberOfEpisode=" + numberOfEpisode +
                ", maxepisodes=" + maxEpisodes +
                '}';
    }
}
