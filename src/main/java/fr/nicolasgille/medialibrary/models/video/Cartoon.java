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
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import fr.nicolasgille.medialibrary.models.video.utils.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.utils.VideoSupport;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Model class for Cartoon instance.
 *
 * It extends the class Video to get all main attributes for video type.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.3
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "cartoon")
public class Cartoon extends Video {

    /**
     * Date of release.
     *
     * @since 1.0
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    private Calendar releaseDate;

    /**
     * Runtime of the cartoons (in minutes).
     *
     * @since 1.0
     */
    @NotNull
    private Integer runtime;

    /**
     * List of Producer for the cartoon.
     *
     * @see Producer
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "video_producers",
            joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "producers_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Producer.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Producer> producers;

    /**
     * List of Director for the cartoon.
     *
     * @see Director
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "video_directors",
            joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "directors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Director.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Director> directors;


    /**
     * Empty constructor.
     *
     * @since 1.0
     * @version 1.0
     */
    public Cartoon() {}

    /**
     * Constructor of the cartoon object.
     *
     * @param title
     *  Title of the cartoon.
     * @param originalTitle
     *  Original title of the cartoon.
     * @param synopsis
     *  Synopsis of the cartoon.
     * @param directors
     *  List of all directors of the cartoon.
     * @param producers
     *  List of all producers of the cartoon.
     * @param genres
     *  List of all genres for the cartoon.
     * @param supports
     *  Supports present for the cartoon.
     * @param languagesSpoken
     *  List of languages spoken available on cartoon.
     * @param subtitles
     *  List of subtitle languages available on cartoon.
     * @param releaseDate
     *  Date of release of the cartoon.
     * @param runtime
     *  Duration of the cartoon in minute.
     * @since 1.0
     * @version 1.0
     */
    public Cartoon(String title, String originalTitle, String synopsis,
                   Set<Director> directors, Set<Producer> producers,
                   List<VideoGenre> genres, List<VideoSupport> supports,
                   List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                   Calendar releaseDate, int runtime) {
        super.title = title;
        super.originalTitle = originalTitle;
        super.synopsis = synopsis;
        this.directors = directors;
        this.producers = producers;
        super.genres = genres;
        super.supports = supports;
        super.languagesSpoken = languagesSpoken;
        super.subtitles = subtitles;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
    }

    /**
     * Constructor of the cartoon object.
     * This constructor is used to stored information retrieve from the persistent system.
     *
     * @param title
     *  Title of the cartoon.
     * @param originalTitle
     *  Original title of the cartoon.
     * @param synopsis
     *  Synopsis of the cartoon.
     * @param directors
     *  List of all directors of the cartoon.
     * @param producers
     *  List of all producers of the cartoon.
     * @param genres
     *  List of all genres for the cartoon.
     * @param supports
     *  Supports present for the cartoon.
     * @param languagesSpoken
     *  List of languages spoken available on cartoon.
     * @param subtitles
     *  List of subtitle languages available on cartoon.
     * @param releaseDate
     *  Date of release of the cartoon.
     * @param runtime
     *  Duration of the cartoon in minute.
     * @since 1.0
     * @version 1.0
     */
    public Cartoon(long id, String title, String originalTitle, String synopsis,
                 Set<Director> directors, Set<Producer> producers,
                 List<VideoGenre> genres, List<VideoSupport> supports,
                 List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                 Calendar releaseDate, int runtime) {
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
        this.releaseDate = releaseDate;
        this.runtime = runtime;
    }

    /**
     * Constructor use to update attribute of the current cartoon by the cartoon passed on parameter.
     *
     * @param cartoon
     *  New content of each attribute of this.
     * @since 1.0
     * @version 1.0
     */
    public Cartoon(Cartoon cartoon) {
        super.id = cartoon.getId();
        super.title = cartoon.getTitle();
        super.originalTitle = cartoon.getOriginalTitle();
        super.synopsis = cartoon.getSynopsis();
        super.genres = cartoon.getGenres();
        super.supports = cartoon.getSupports();
        this.directors = cartoon.getDirectors();
        this.producers = cartoon.getProducers();
        super.languagesSpoken = cartoon.getLanguagesSpoken();
        super.subtitles = cartoon.getSubtitles();
        this.releaseDate = cartoon.getReleaseDate();
        this.runtime = cartoon.getRuntime();
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
     * Return the runtime of the cartoon in minute.
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
     * Return all producers for the cartoon.
     *
     * @return
     *  Set of all producer of the cartoon.
     * @since 1.0
     * @version 1.0
     */
    public Set<Producer> getProducers() {
        return producers;
    }

    /**
     * Set the list of producers.
     *
     * @param producers
     *  New Set of producer.
     * @since 1.0
     * @version 1.0
     */
    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    /**
     * Return all directors for the cartoon.
     *
     * @return
     *  Set of all directors of the cartoon.
     * @since 1.0
     * @version 1.0
     */
    public Set<Director> getDirectors() {
        return directors;
    }

    /**
     * Set the list of directors.
     *
     * @param directors
     *  New set of Director.
     * @since 1.0
     * @version 1.0
     */
    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    /**
     * Display Cartoon information.
     *
     * @return
     *  A short description of the content of the cartoon's attribute.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        // Build genres string.
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < super.genres.size(); ++i) {
            genres.append(super.genres.get(i).getName());
            if (i != super.genres.size() - 1) {
                genres.append(", ");
            }
        }

        // Build supports string.
        StringBuilder supports = new StringBuilder();
        for (int i = 0; i < super.supports.size(); ++i) {
            supports.append(super.supports.get(i).getName());
            if (i != super.supports.size() - 1) {
                supports.append(", ");
            }
        }

        // Build languages spoken string.
        StringBuilder languagesSpoken = new StringBuilder();
        for (int i = 0; i < super.languagesSpoken.size(); ++i) {
            languagesSpoken.append(super.languagesSpoken.get(i).getName());
            if (i != this.languagesSpoken.size() - 1) {
                languagesSpoken.append(", ");
            }
        }

        // Build subtitles languages string.
        StringBuilder subtitles = new StringBuilder();
        for (int i = 0; i < super.subtitles.size(); ++i) {
            subtitles.append(super.subtitles.get(i).getName());
            if (i != this.subtitles.size() - 1) {
                subtitles.append(", ");
            }
        }

        return "Cartoon{" +
                "id=" + super.id +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + super.originalTitle + '\'' +
                ", categories=" + genres.toString() +
                ", releaseDate=" + this.releaseDate.toString() +
                ", runtime=" + this.runtime +
                ", synopsis='" + super.synopsis + '\'' +
                ", producers='" + super.setStringBuilder(this.producers) + '\'' +
                ", directors='" + super.setStringBuilder(this.directors) + '\'' +
                ", supports='" + supports.toString() +
                ", languageSpoken='" + languagesSpoken.toString() +
                ", subtitles='" + subtitles.toString() +
                '}';
    }
}
