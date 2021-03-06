/*
 * MediaLibrary.
 * Copyright (C) 2017 Nicolas GILLE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.nicolasgille.medialibrary.models.game;

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.models.Media;
import fr.nicolasgille.medialibrary.models.common.company.Developer;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.VideoGamePlatform;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGameGenre;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;
import fr.nicolasgille.medialibrary.utils.DateFormatter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Representation of video game.
 *
 * @author Nicolas GILLE
 * @version 1.1
 * @see Media
 * @since Media-Library 0.4
 */
@Entity
@DiscriminatorValue(value = "video_game")
public class VideoGame extends Media {

    /**
     * The original title of the book
     *
     * @since 1.0
     */
    private String originalTitle;

    /**
     * Indicate if the video game can play on multiplayers or not.
     *
     * @since 1.0
     */
    private boolean multiplayers;

    /**
     * Genre of the media.
     *
     * @see VideoGameGenre
     * @since 1.1
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = VideoGameGenre.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VideoGameGenre> genres;

    /**
     * List of language spoken available on the video.
     *
     * @see LanguageCode
     * @since 1.0
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = LanguageCode.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<LanguageCode> languages;

    /**
     * List of all developers who develop the video game.
     *
     * @since 1.0
     */
    @JoinTable(
            name = "video_game_developer",
            joinColumns = @JoinColumn(name = "video_game_id",
                                      referencedColumnName = "id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "developer_id",
                                referencedColumnName = "id")
            }
    )
    @ManyToMany(targetEntity = Developer.class,
                cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Developer> developers;

    /**
     * List of all publishers who publish the video game.
     *
     * @since 1.0
     */
    @JoinTable(
            name = "video_game_publisher",
            joinColumns = @JoinColumn(name = "video_game_id",
                                      referencedColumnName = "id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "publisher_id",
                                referencedColumnName = "id")
            }
    )
    @ManyToMany(targetEntity = Publisher.class,
                cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE},
                fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Publisher> publishers;

    /**
     * List of all consoles for the video game.
     *
     * @see VideoGamePlatform
     * @since 1.0
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = VideoGamePlatform.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VideoGamePlatform> platforms;

    /**
     * Empty constructor.
     *
     * @versiob 1.0
     * @since 1.0
     */
    public VideoGame() {}

    /**
     * Constructor of a VideoGame without id.
     *
     * @param title Title of the Video Game.
     * @param originalTitle Original title of the video game.
     * @param synopsis Synopsis of the video game.
     * @param releaseDate Release date of the video game.
     * @param genres Genre of the video game.
     * @param supports Support of the video game.
     * @param multiplayers Indicate if the video game is multi playable.
     * @param languages Language of the video game.
     * @param developers Developers of the video game.
     * @param publishers Publishers of the video games.
     * @param platforms Platforms of the video game.
     *
     * @version 1.0
     * @since 1.0
     */
    public VideoGame(String title, String originalTitle, String synopsis, Calendar releaseDate,
                     List<VideoGameGenre> genres, List<MediaSupport> supports, boolean multiplayers,
                     List<LanguageCode> languages,
                     Set<Developer> developers, Set<Publisher> publishers, List<VideoGamePlatform> platforms) {
        super.title = title;
        this.originalTitle = originalTitle;
        super.synopsis = synopsis;
        super.releaseDate = releaseDate;
        this.genres = genres;
        super.supports = supports;
        this.multiplayers = multiplayers;
        this.languages = languages;
        this.developers = developers;
        this.publishers = publishers;
        this.platforms = platforms;
    }

    /**
     * Constructor of a VideoGame without id.
     *
     * @param id Identifier of the video game.
     * @param title Title of the Video Game.
     * @param originalTitle Original title of the video game.
     * @param synopsis Synopsis of the video game.
     * @param releaseDate Release date of the video game.
     * @param genres Genre of the video game.
     * @param supports Support of the video game.
     * @param multiplayers Indicate if the video game is multi playable.
     * @param languages Language of the video game.
     * @param developers Developers of the video game.
     * @param publishers Publishers of the video games.
     * @param platforms Platforms of the video game.
     *
     * @version 1.0
     * @since 1.0
     */
    public VideoGame(long id, String title, String originalTitle, String synopsis, Calendar releaseDate,
                     List<VideoGameGenre> genres, List<MediaSupport> supports, boolean multiplayers,
                     List<LanguageCode> languages,
                     Set<Developer> developers, Set<Publisher> publishers, List<VideoGamePlatform> platforms) {
        super.id = id;
        super.title = title;
        this.originalTitle = originalTitle;
        super.synopsis = synopsis;
        super.releaseDate = releaseDate;
        this.genres = genres;
        super.supports = supports;
        this.multiplayers = multiplayers;
        this.languages = languages;
        this.developers = developers;
        this.publishers = publishers;
        this.platforms = platforms;
    }

    /**
     * Construcor used to copy a VideoGame.
     *
     * @param videoGame VideoGame at copy.
     *
     * @version 1.0
     * @since 1.0
     */
    public VideoGame(VideoGame videoGame) {
        super.id = videoGame.getId();
        super.title = videoGame.getTitle();
        this.originalTitle = videoGame.getOriginalTitle();
        super.synopsis = videoGame.getSynopsis();
        super.releaseDate = videoGame.getReleaseDate();
        this.genres = videoGame.getGenres();
        super.supports = videoGame.getSupports();
        this.multiplayers = videoGame.isMultiplayers();
        this.languages = videoGame.getLanguages();
        this.developers = videoGame.getDevelopers();
        this.publishers = videoGame.getPublishers();
        this.platforms = videoGame.getPlatforms();
    }

    /**
     * Get the original title of the video game.
     *
     * @return The original title of the video game.
     *
     * @version 1.0
     * @since 1.0
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Set the original title of the video game.
     *
     * @param originalTitle New original title of the video game.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Return the genres.
     *
     * @return The genres of the book.
     *
     * @version 1.0
     * @see VideoGameGenre
     * @since 1.1
     */
    public List<VideoGameGenre> getGenres() {
        return this.genres;
    }

    /**
     * Get if the video game is multiplayer or not.
     *
     * @return True or false if the game can play in multi or not.
     *
     * @version 1.0
     * @since 1.0
     */
    public boolean isMultiplayers() {
        return multiplayers;
    }

    /**
     * Set the multiplayer value.
     *
     * @param multiplayers A boolean to indicate if the game is playable in multi.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setMultiplayers(boolean multiplayers) {
        this.multiplayers = multiplayers;
    }

    /**
     * Get all languages present on the game.
     *
     * @return A list of all languages for the video game.
     *
     * @version 1.0
     * @since 1.0
     */
    public List<LanguageCode> getLanguages() {
        return languages;
    }

    /**
     * Set the list of all languages available on the game.
     *
     * @param languages New list of languages.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setLanguages(List<LanguageCode> languages) {
        this.languages = languages;
    }

    /**
     * Get all developers for the video game.
     *
     * @return All developers who develop the game.
     *
     * @version 1.0
     * @since 1.0
     */
    public Set<Developer> getDevelopers() {
        return developers;
    }

    /**
     * Set the list of developer for the game.
     *
     * @param developers New list of developer.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    /**
     * Get all publishers of the video game.
     *
     * @return A set who contains all publishers.
     *
     * @version 1.0
     * @since 1.0
     */
    public Set<Publisher> getPublishers() {
        return publishers;
    }

    /**
     * Set the publishers of the game.
     *
     * @param publishers All publishers of the game.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }

    /**
     * Get the list of platform available for the game.
     *
     * @return A list of all platforms available for the video game.
     *
     * @version 1.0
     * @since 1.0
     */
    public List<VideoGamePlatform> getPlatforms() {
        return platforms;
    }

    /**
     * Set the platform for the game.
     *
     * @param platforms New list of platform.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setPlatforms(List<VideoGamePlatform> platforms) {
        this.platforms = platforms;
    }

    /**
     * Set genres of Media.
     *
     * @param genres New book.
     *
     * @version 1.0
     * @since 1.1
     */
    public void setGenres(List<VideoGameGenre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "VideoGame{" +
               "title⁼" + title + '\'' +
               ", originalTitle='" + originalTitle + '\'' +
               ", genres=" + CollectionAsString.collectionToString(this.getGenres()) +
               ", releaseDate=" + DateFormatter.frenchDate(super.releaseDate) +
               ", supports='" + CollectionAsString.collectionToString(super.getSupports()) +
               ", multiplayers=" + multiplayers +
               ", languages=" + CollectionAsString.collectionToString(languages) +
               ", developers=" + CollectionAsString.collectionToString(developers) +
               ", publishers=" + CollectionAsString.collectionToString(publishers) +
               ", platforms=" + CollectionAsString.collectionToString(platforms) +
               '}';
    }
}
