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

import fr.nicolasgille.medialibrary.models.common.Genre;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Abstract class at inherit by all subclasses of media type.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.0
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
     * Original title of the media.
     *
     * @since 1.0
     */
    protected String originalTitle;

    /**
     * Synopsis of the media.
     *
     * @since 1.0
     */
    protected String synopsis;

    @NotNull
    @ElementCollection(targetClass = Genre.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<Genre> genres;

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
     * Return the original title.
     *
     * @return The original title of the media.
     * @since 1.0
     * @version 1.0
     */
    public String getOriginalTitle() {
        return this.originalTitle;
    }

    /**
     * Set original title.
     *
     * @param originalTitle New title.
     * @since 1.0
     * @version 1.0
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Return the synopsis.
     *
     * @return The synopsis.
     * @since 1.0
     * @version 1.0
     */
    public String getSynopsis() {
        return this.synopsis;
    }

    /**
     * Set synopsis.
     *
     * @param synopsis New synopsis.
     * @since 1.0
     * @version 1.0
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Return the genres.
     *
     * @return The genres of the media.
     * @see Genre
     * @since 1.0
     * @version 1.0
     */
    public List<Genre> getGenres() {
        return this.genres;
    }

    /**
     * Set genres of Media.
     *
     * @param genres New genres.
     * @since 1.0
     * @version 1.0
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
