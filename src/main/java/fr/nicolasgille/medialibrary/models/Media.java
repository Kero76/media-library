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

package fr.nicolasgille.medialibrary.models;

import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * Abstract class at inherit by all subclasses of media type.
 *
 * @author Nicolas GILLE
 * @version 2.0
 * @since Media-Library 0.2
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
     * Synopsis of the media.
     *
     * @since 2.0
     */
    @Column(columnDefinition = "TEXT")
    protected String synopsis;

    /**
     * Date of release for the media.
     *
     * @since 2.0
     */
    @Temporal(TemporalType.DATE)
    protected Calendar releaseDate;

    /**
     * List of Support for the media.
     *
     * @see MediaSupport
     * @since 2.0
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = MediaSupport.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<MediaSupport> supports;

    /**
     * Return the id.
     *
     * @return The id.
     *
     * @version 1.0
     * @since 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id New id.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return the title.
     *
     * @return The title of the movie.
     *
     * @version 1.0
     * @since 1.0
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set title.
     *
     * @param title New title.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the synopsis of the media.
     *
     * @return The synopsis of the media.
     *
     * @version 1.0
     * @since 1.0
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Set the synopsis of the media.
     *
     * @param synopsis New synopsis.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Return the release date.
     *
     * @return The release date.
     *
     * @version 1.0
     * @since 2.0
     */
    public Calendar getReleaseDate() {
        return releaseDate;
    }

    /**
     * Set releaseDate.
     *
     * @param releaseDate New date of release.
     *
     * @version 1.0
     * @since 2.0
     */
    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Return all supports for the video.
     *
     * @return List of all supports.
     *
     * @version 1.0
     * @since 2.0
     */
    public List<MediaSupport> getSupports() {
        return supports;
    }

    /**
     * Set the supports for the video.
     *
     * @param supports New Supports.
     *
     * @version 1.0
     * @since 2.0
     */
    public void setSupports(List<MediaSupport> supports) {
        this.supports = supports;
    }
}
