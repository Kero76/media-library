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
package fr.nicolasgille.medialibrary.models.music;

import fr.nicolasgille.medialibrary.models.Media;
import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import fr.nicolasgille.medialibrary.models.common.person.Singer;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.genre.BookGenre;
import fr.nicolasgille.medialibrary.models.components.genre.MusicGenre;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;
import fr.nicolasgille.medialibrary.utils.DateFormatter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Representation of music album.
 *
 * @see Media
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.1
 */
@Entity
@DiscriminatorValue(value = "album")
public class Album extends Media {

    /**
     * Number of track available on album
     *
     * @since 1.0
     */
    private int nbTracks;

    /**
     * Length of the music album.
     *
     * @since 1.0
     */
    private double length;

    /**
     * Genre of the media.
     *
     * @see MusicGenre
     * @since 1.1
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = MusicGenre.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MusicGenre> genres;

    /**
     * Labels who records the album.
     *
     * @since 1.0
     */
    @JoinTable(
            name = "album_labels_records",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "labels_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = LabelRecords.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<LabelRecords> labelRecords;

    /**
     * Singers present on the Album.
     *
     * @since 1.0
     */
    @JoinTable(
            name = "album_singers",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "singers_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Singer.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Singer> singers;

    /**
     * Empty constructor
     *
     * @since 1.0
     * @version 1.0
     */
    public Album() {}

    /**
     * Constructor with all parameters expected id.
     *
     * @param title
     *  Title of the album.
     * @param tracks
     *  Tracks present on the album.
     * @param releaseDate
     *  Release date of the album.
     * @param genres
     *  Genre of the album.
     * @param supports
     *  Support of the album.
     * @param nbTracks
     *  Number of track of the album.
     * @param length
     *  Length of the album.
     * @param labelRecords
     *  Label records of the album.
     * @param singers
     *  Singers of the album.
     * @since 1.0
     * @version 1.0
     */
    public Album(String title, String tracks, Calendar releaseDate, List<MusicGenre> genres, List<MediaSupport> supports,
                 int nbTracks, double length, Set<LabelRecords> labelRecords, Set<Singer> singers) {
        super.title = title;
        super.synopsis = tracks;
        super.releaseDate = releaseDate;
        this.genres = genres;
        super.supports = supports;
        this.nbTracks = nbTracks;
        this.length = length;
        this.labelRecords = labelRecords;
        this.singers = singers;
    }

    /**
     * Constructor with all parameters expected id.
     *
     * @param id
     *  Identifier of the album.
     * @param title
     *  Title of the album.
     * @param tracks
     *  Tracks present on the album.
     * @param releaseDate
     *  Release date of the album.
     * @param genres
     *  Genre of the album.
     * @param supports
     *  Support of the album.
     * @param nbTracks
     *  Number of track of the album.
     * @param length
     *  Length of the album.
     * @param labelRecords
     *  Label records of the album.
     * @param singers
     *  Singers of the album.
     * @since 1.0
     * @version 1.0
     */
    public Album(long id, String title, String tracks, Calendar releaseDate, List<MusicGenre> genres, List<MediaSupport> supports,
                 int nbTracks, double length, Set<LabelRecords> labelRecords, Set<Singer> singers) {
        super.id = id;
        super.title = title;
        super.synopsis = tracks;
        super.releaseDate = releaseDate;
        this.genres = genres;
        super.supports = supports;
        this.nbTracks = nbTracks;
        this.length = length;
        this.labelRecords = labelRecords;
        this.singers = singers;
    }

    /**
     * Constructor to copy another instance of Album.
     *
     * @param album
     *  Album at copying.
     * @since 1.0
     * @version 1.0
     */
    public Album(Album album) {
        super.id = album.getId();
        super.title = album.getTitle();
        super.synopsis = album.getSynopsis();
        super.releaseDate = album.getReleaseDate();
        this.genres = album.getGenres();
        super.supports = album.getSupports();
        this.nbTracks = album.getNbTracks();
        this.length = album.getLength();
        this.labelRecords = album.getLabelRecords();
        this.singers = album.getSingers();
    }

    /**
     * Return the number of tracks present on the album.
     *
     * @return
     *  The number of tracks available on album.
     * @since 1.0
     * @version 1.0
     */
    public int getNbTracks() {
        return nbTracks;
    }

    /**
     * Set the number of tracks on the album.
     *
     * @param nbTracks
     *  New number of tracks of the album.
     * @since 1.0
     * @version 1.0
     */
    public void setNbTracks(int nbTracks) {
        this.nbTracks = nbTracks;
    }

    /**
     * Get the length of the album.
     *
     * @return
     *  The length of the album.
     * @since 1.0
     * @version 1.0
     */
    public double getLength() {
        return length;
    }

    /**
     * Set the length of the album.
     *
     * @param length
     *  New length of the album.
     * @since 1.0
     * @version 1.0
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Get the label records for the album.
     *
     * @return
     *  Get all label records.
     * @since 1.0
     * @version 1.0
     */
    public Set<LabelRecords> getLabelRecords() {
        return labelRecords;
    }

    /**
     * Set the label records.
     *
     * @param labelRecords
     *  New labels records.
     * @since 1.0
     * @version 1.0
     */
    public void setLabelRecords(Set<LabelRecords> labelRecords) {
        this.labelRecords = labelRecords;
    }

    /**
     * Get all singers or groups for the album.
     *
     * @return
     *  Get all singers or groups present on the album.
     * @since 1.0
     * @version 1.0
     */
    public Set<Singer> getSingers() {
        return singers;
    }

    /**
     * Set the singers or groups for the albums.
     *
     * @param singers
     *  New singers or groups.
     * @since 1.0
     * @version 1.0
     */
    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    /**
     * Return the genres.
     *
     * @return The genres of the book.
     * @see BookGenre
     * @since 1.1
     * @version 1.0
     */
    public List<MusicGenre> getGenres() {
        return this.genres;
    }

    /**
     * Set genres of Media.
     *
     * @param genres New book.
     * @since 1.1
     * @version 1.0
     */
    public void setGenres(List<MusicGenre> genres) {
        this.genres = genres;
    }

    /**
     * Some information about the Album.
     *
     * @return
     *  The information about the album.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Album{" +
                "id=" + super.id +
                ", title='" + super.title + '\'' +
                ", tracks='" + super.getSynopsis() + '\'' +
                ", genres=" + CollectionAsString.listToString(this.getGenres()) +
                ", releaseDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", supports='" + CollectionAsString.listToString(super.getSupports()) +
                ", nbTracks=" + nbTracks +
                ", length=" + length +
                ", singers=" + CollectionAsString.setToString(this.singers) +
                ", labels=" + CollectionAsString.setToString(this.labelRecords) +
                '}';
    }
}
