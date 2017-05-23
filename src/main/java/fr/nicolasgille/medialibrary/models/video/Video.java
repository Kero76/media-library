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
import fr.nicolasgille.medialibrary.models.Media;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import fr.nicolasgille.medialibrary.models.video.utils.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.utils.VideoSupport;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Main class of the Video media type.
 *
 * If you would add new video subtype, you must extends <code>Video</code> class
 * to get all attributes available on Video type.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 2.0
 */
@MappedSuperclass
public abstract class Video extends Media {

    /**
     * Original title of the video.
     *
     * @since 2.0
     */
    protected String originalTitle;

    /**
     * Synopsis of the video.
     *
     * @since 2.0
     */
    @Column(columnDefinition = "TEXT")
    protected String synopsis;

    /**
     * Genre of the video.
     *
     * @see VideoGenre
     * @since 2.0
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = VideoGenre.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<VideoGenre> genres;

    /**
     * List of Support for the video.
     *
     * @see VideoSupport
     * @since 2.0
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = VideoSupport.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<VideoSupport> supports;

    /**
     * List of language spoken available on the video.
     *
     * @see LanguageCode
     * @since 2.0
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = LanguageCode.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<LanguageCode> languagesSpoken;

    /**
     * List of language present as subtitle available on the video.
     *
     * @see LanguageCode
     * @since 2.0
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = LanguageCode.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<LanguageCode> subtitles;

    /**
     * List of Producer for the video.
     *
     * @see Producer
     * @since 2.0
     */
    @NotNull
    @JoinTable(
            name = "video_producers",
            joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "producers_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Producer.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected Set<Producer> producers;

    /**
     * List of Director for the video.
     *
     * @see Director
     * @since 2.0
     */
    @NotNull
    @JoinTable(
            name = "video_directors",
            joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "directors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Director.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected Set<Director> directors;

    /**
     * Return the original title.
     *
     * @return The original title of the media.
     * @since 2.0
     * @version 1.0
     */
    public String getOriginalTitle() {
        return this.originalTitle;
    }

    /**
     * Set original title.
     *
     * @param originalTitle New title.
     * @since 2.0
     * @version 1.0
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Return the synopsis.
     *
     * @return The synopsis.
     * @since 2.0
     * @version 1.0
     */
    public String getSynopsis() {
        return this.synopsis;
    }

    /**
     * Set synopsis.
     *
     * @param synopsis New synopsis.
     * @since 2.0
     * @version 1.0
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Return the genres.
     *
     * @return The genres of the media.
     * @see VideoGenre
     * @since 2.0
     * @version 1.0
     */
    public List<VideoGenre> getGenres() {
        return this.genres;
    }

    /**
     * Set genres of Media.
     *
     * @param genres New genres.
     * @since 2.0
     * @version 1.0
     */
    public void setGenres(List<VideoGenre> genres) {
        this.genres = genres;
    }


    /**
     * Return all supports for the video.
     *
     * @return
     *  List of all supports.
     * @since 2.0
     * @version 1.0
     */
    public List<VideoSupport> getSupports() {
        return supports;
    }

    /**
     * Set the supports for the video.
     *
     * @param supports
     *  New Supports.
     * @since 2.0
     * @version 1.0
     */
    public void setSupports(List<VideoSupport> supports) {
        this.supports = supports;
    }

    /**
     * Get the list of languages spoken.
     *
     * @return
     *  A list of languages spoken.
     * @since 2.0
     * @version 1.0
     */
    public List<LanguageCode> getLanguagesSpoken() {
        return languagesSpoken;
    }

    /**
     * Set the list of languages spoken available on the video.
     *
     * @param languagesSpoken
     *  New list of languages spoken.
     * @since 2.0
     * @version 1.0
     */
    public void setLanguagesSpoken(List<LanguageCode> languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    /**
     * Get the list of subtitle languages.
     *
     * @return
     *  A list of subtitle languages.
     * @since 2.0
     * @version 1.0
     */
    public List<LanguageCode> getSubtitles() {
        return subtitles;
    }

    /**
     * Set the list of subtitle languages available on the video.
     *
     * @param subtitles
     *  New list of subtitle languages.
     * @since 2.0
     * @version 1.0
     */
    public void setSubtitles(List<LanguageCode> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Return all producers for the movie.
     *
     * @return
     *  Set of all producer of the movie.
     * @since 2.0
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
     * @since 2.0
     * @version 1.0
     */
    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    /**
     * Return all directors for the movie.
     *
     * @return
     *  Set of all directors of the movie.
     * @since 2.0
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
     * @since 2.0
     * @version 1.0
     */
    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }
}
