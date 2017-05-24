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
package fr.nicolasgille.medialibrary.models;

import fr.nicolasgille.medialibrary.utils.MediaGenre;
import fr.nicolasgille.medialibrary.utils.MediaSupport;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Abstract class at inherit by all subclasses of media type.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 2.0
 */
@Entity
@Table(name = "media")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "media_type")
public abstract class Media implements IMedia {

    /**
     * Identifier of the media.
     *
     * @since 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected long id;

    /**
     * title of the media.
     *
     * @since 1.0
     */
    protected String title;

    /**
     * Date of release for the media.
     *
     * @since 2.0
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    protected Calendar releaseDate;

    /**
     * Genre of the media.
     *
     * @see MediaGenre
     * @since 2.0
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = MediaGenre.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<MediaGenre> genres;

    /**
     * List of Support for the media.
     *
     * @see MediaSupport
     * @since 2.0
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = MediaSupport.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<MediaSupport> supports;

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
     * @return The title of the movie.
     * @since 1.0
     * @version 1.0
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set title.
     *
     * @param title New title.
     * @since 1.0
     * @version 1.0
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Return the release date.
     *
     * @return
     *  The release date.
     * @since 2.0
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
     * @since 2.0
     * @version 1.0
     */
    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Return the genres.
     *
     * @return The genres of the media.
     * @see MediaGenre
     * @since 2.0
     * @version 1.0
     */
    public List<MediaGenre> getGenres() {
        return this.genres;
    }

    /**
     * Set genres of Media.
     *
     * @param genres New genres.
     * @since 2.0
     * @version 1.0
     */
    public void setGenres(List<MediaGenre> genres) {
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
    public List<MediaSupport> getSupports() {
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
    public void setSupports(List<MediaSupport> supports) {
        this.supports = supports;
    }

    /**
     * Generate a String with content of Set.
     *
     * @param set
     *  Set used to displayed element.
     * @return
     *  A string representation of the Set.
     * @since 1.0
     * @version 1.0
     */
    protected String setStringBuilder(Set<?> set) {
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
