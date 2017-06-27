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
package fr.nicolasgille.medialibrary.models.book;

import fr.nicolasgille.medialibrary.models.Media;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.models.common.person.Illustrator;
import fr.nicolasgille.medialibrary.models.components.BookFormat;
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
 * Representation of the comic in Media-Library.
 * It can be represent a <code>Manga</code>, a <code>American Comic</code> or an <code>European Comic</code>.
 *
 * @see Media
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.1
 */
@Entity
@DiscriminatorValue(value = "comic")
public class Comic extends Book {

    /**
     * Number of volumes available for the comic.
     *
     * @since 1.0
     */
    private int volumes;

    /**
     * Current volume of the series of comic.
     *
     * @since 1.0
     */
    private int currentVolume;

    /**
     * Illustrators of the Comic.
     *
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "books_illustrator",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "illustrator_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Illustrator.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Illustrator> illustrators;

    /**
     * Empty constructor.
     *
     * @version 1.0
     * @since 1.0
     */
    public Comic() {}

    /**
     * Constructor of the Comic.
     *
     * @param title         Title of the comic.
     * @param originalTitle Original title of the comic.
     * @param synopsis      Synopsis of the comic.
     * @param releaseDate   Release date of the comic.
     * @param nbPages       Number of page of the comic.
     * @param isbn          ISBN of the comic.
     * @param authors       Authors of the comic.
     * @param publishers    Publishers of the comic.
     * @param genres        Genres of the comic.
     * @param supports      Supports of the comic.
     * @param format        Format of the comic.
     * @param volumes       Number of volume for the comic.
     * @param currentVolume current volume for the comic.
     * @param illustrators  Illustrators of the comic.
     * @version 1.0
     * @since 1.0
     */
    public Comic(String title, String originalTitle, String synopsis,
                 Calendar releaseDate, int nbPages, String isbn,
                 Set<Author> authors, Set<Publisher> publishers, List<MediaGenre> genres, List<MediaSupport> supports, BookFormat format,
                 int volumes, int currentVolume, Set<Illustrator> illustrators) {
        super(title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports, format);
        this.volumes = volumes;
        this.currentVolume = currentVolume;
        this.illustrators = illustrators;
    }

    /**
     * Constructor of the Comic.
     *
     * @param id            Identifier of the comic.
     * @param title         Title of the comic.
     * @param originalTitle Original title of the comic.
     * @param synopsis      Synopsis of the comic.
     * @param releaseDate   Release date of the comic.
     * @param nbPages       Number of page of the comic.
     * @param isbn          ISBN of the comic.
     * @param authors       Authors of the comic.
     * @param publishers    Publishers of the comic.
     * @param genres        Genres of the comic.
     * @param supports      Supports of the comic.
     * @param format        Format of the comic.
     * @param volumes       Number of volume for the comic.
     * @param currentVolume current volume for the comic.
     * @param illustrators  Illustrators of the comic.
     * @version 1.0
     * @since 1.0
     */
    public Comic(long id, String title, String originalTitle, String synopsis,
                 Calendar releaseDate, int nbPages, String isbn,
                 Set<Author> authors, Set<Publisher> publishers, List<MediaGenre> genres, List<MediaSupport> supports, BookFormat format,
                 int volumes, int currentVolume, Set<Illustrator> illustrators) {
        super(id, title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports, format);
        this.volumes = volumes;
        this.currentVolume = currentVolume;
        this.illustrators = illustrators;
    }

    /**
     * Constructor to copy another instance of Comic.
     *
     * @param comic
     *  Comic at copy.
     * @since 1.0
     * @version 1.0
     */
    public Comic(Comic comic) {
        super.id = comic.getId();
        super.title = comic.getTitle();
        super.setOriginalTitle(comic.getOriginalTitle());
        super.setSynopsis(comic.getSynopsis());
        super.releaseDate = comic.getReleaseDate();
        super.setNbPages(comic.getNbPages());
        super.setIsbn(comic.getIsbn());
        super.setAuthors(comic.getAuthors());
        super.setPublisher(comic.getPublisher());
        super.genres = comic.getGenres();
        super.supports = comic.getSupports();
        super.setFormat(comic.getFormat());
        this.volumes = comic.getVolumes();
        this.currentVolume = comic.getCurrentVolume();
        this.illustrators = comic.getIllustrators();
    }

    /**
     * Get the number of volumes for the comic.
     *
     * @return
     *  The number of volume who composed the comic.
     * @since 1.0
     * @version 1.0
     */
    public int getVolumes() {
        return volumes;
    }

    /**
     * Set the number of volumes available for the comic.
     *
     * @param volumes
     *  New number of volumes.
     * @since 1.0
     * @version 1.0
     */
    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    /**
     * Get the number of the current volume of the comic.
     *
     * @return
     *  The current number of the volume for the comic.
     * @since 1.0
     * @version 1.0
     */
    public int getCurrentVolume() {
        return currentVolume;
    }

    /**
     * Set the current volume of the comic.
     *
     * @param currentVolume
     *  New current volume for the comic.
     * @since 1.0
     * @version 1.0
     */
    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    /**
     * Return the illustrators who draw the comic.
     *
     * @return
     *  The illustrators of the comic.
     * @since 1.0
     * @version 1.0
     */
    public Set<Illustrator> getIllustrators() {
        return illustrators;
    }

    /**
     * Set the illustrator of the comic.
     *
     * @param illustrators
     *  New set of illustrators of the comic.
     * @since 1.0
     * @version 1.0
     */
    public void setIllustrators(Set<Illustrator> illustrators) {
        this.illustrators = illustrators;
    }

    /**
     * Some information about the Comic.
     *
     * @return
     *  The information about the comic.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Comic{" +
                "id=" + super.id +
                ", isbn='" + super.getIsbn() + '\'' +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + super.getOriginalTitle() + '\'' +
                ", synopsis='" + super.getSynopsis() + '\'' +
                ", genres=" + CollectionAsString.listToString(super.getGenres()) +
                ", releaseDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", supports='" + CollectionAsString.listToString(super.getSupports()) +
                ", format=" + super.getFormat().getName() +
                ", nbPages=" + super.getNbPages() +
                ", authors=" + CollectionAsString.setToString(super.getAuthors()) +
                ", publisher=" + CollectionAsString.setToString(super.getPublisher()) +
                ", volumes=" + volumes +
                ", currentVolume=" + currentVolume +
                ", illustrator=" + CollectionAsString.setToString(this.illustrators) +
                '}';
    }
}
