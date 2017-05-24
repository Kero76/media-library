package fr.nicolasgille.medialibrary.models.book;

import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.models.common.person.Illustrator;
import fr.nicolasgille.medialibrary.utils.BookFormat;
import fr.nicolasgille.medialibrary.utils.MediaGenre;
import fr.nicolasgille.medialibrary.utils.MediaSupport;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Representation of the comic in Media-Library.
 * It can be represent a <code>Manga</code>, a <code>American Comic</code> or an <code>European Comic</code>.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "book")
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
     * Date of the end of the series.
     *
     * @since 1.0
     */
    private Calendar endDate;

    /**
     * Illustrator of the Comic.
     *
     * @since 1.0
     */
    private Illustrator illustrator;

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
     * @param publisher     Publisher of the comic.
     * @param genres        Genres of the comic.
     * @param supports      Supports of the comic.
     * @param format        Format of the comic.
     * @param volumes       Number of volume for the comic.
     * @param currentVolume current volume for the comic.
     * @param endDate       Date of the end of the last volume.
     * @param illustrator   Illustrator of the comic.
     * @version 1.0
     * @since 1.0
     */
    public Comic(String title, String originalTitle, String synopsis,
                 Calendar releaseDate, int nbPages, String isbn,
                 Set<Author> authors, Publisher publisher, List<MediaGenre> genres, List<MediaSupport> supports, BookFormat format,
                 int volumes, int currentVolume, Calendar endDate, Illustrator illustrator) {
        super(title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publisher, genres, supports, format);
        this.volumes = volumes;
        this.currentVolume = currentVolume;
        this.endDate = endDate;
        this.illustrator = illustrator;
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
     * @param publisher     Publisher of the comic.
     * @param genres        Genres of the comic.
     * @param supports      Supports of the comic.
     * @param format        Format of the comic.
     * @param volumes       Number of volume for the comic.
     * @param currentVolume current volume for the comic.
     * @param endDate       Date of the end of the last volume.
     * @param illustrator   Illustrator of the comic.
     * @version 1.0
     * @since 1.0
     */
    public Comic(long id, String title, String originalTitle, String synopsis,
                 Calendar releaseDate, int nbPages, String isbn,
                 Set<Author> authors, Publisher publisher, List<MediaGenre> genres, List<MediaSupport> supports, BookFormat format,
                 int volumes, int currentVolume, Calendar endDate, Illustrator illustrator) {
        super(id, title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publisher, genres, supports, format);
        this.volumes = volumes;
        this.currentVolume = currentVolume;
        this.endDate = endDate;
        this.illustrator = illustrator;
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
        this.endDate = comic.getEndDate();
        this.illustrator = comic.getIllustrator();
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
     * The date of the end of the comic series.
     *
     * @return
     *  The last date of release for a volume of the comic.
     * @since 1.0
     * @version 1.0
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Set the date of the final release of a volume for the comic.
     *
     * @param endDate
     *  New end date.
     * @since 1.0
     * @version 1.0
     */
    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    /**
     * Return the illustrator who draw the comic.
     *
     * @return
     *  The illustrator of the comic.
     * @since 1.0
     * @version 1.0
     */
    public Illustrator getIllustrator() {
        return illustrator;
    }

    /**
     * Set the illustrator of the comic.
     *
     * @param illustrator
     *  New illustrator of the comic.
     * @since 1.0
     * @version 1.0
     */
    public void setIllustrator(Illustrator illustrator) {
        this.illustrator = illustrator;
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

        return "Comic{" +
                "id=" + super.id +
                "isbn='" + super.getIsbn() + '\'' +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + super.getOriginalTitle() + '\'' +
                ", synopsis='" + super.getSynopsis() + '\'' +
                ", categories=" + genres.toString() +
                ", releaseDate=" + super.releaseDate.toString() +
                ", supports='" + supports.toString() +
                ", format=" + super.getFormat().getName() +
                ", nbPages=" + super.getNbPages() +
                ", authors=" + this.setStringBuilder(super.getAuthors()) +
                ", publisher=" + super.getPublisher() +
                ", volumes=" + volumes +
                ", currentVolume=" + currentVolume +
                ", endDate=" + endDate +
                ", illustrator=" + illustrator +
                '}';
    }
}
