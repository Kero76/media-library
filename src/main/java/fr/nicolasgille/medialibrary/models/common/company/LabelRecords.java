package fr.nicolasgille.medialibrary.models.common.company;

import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.models.music.Album;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;


/**
 * Class Publisher represent a company who published book media type.
 *
 * @see Company
 * @see Book
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "label_records")
public class LabelRecords extends Company {

    /**
     * Set who contains all albums record by the label.
     *
     * @since 1.0
     */
    @Transient
    private Set<Album> albumsRecords;

    /**
     * Empty constructor
     *
     * @since 1.0
     * @version 1.0
     */
    public LabelRecords() {}

    /**
     * Constructor with <code>name</code> attribute.
     *
     * @param name
     *  Name of the label.
     * @since 1.0
     * @version 1.0
     */
    public LabelRecords(String name) {
        super.name = name;
    }

    /**
     * Constructor with all attributes.
     *
     * @param id
     *  Identifier of the label.
     * @param name
     *  Name of the label.
     * @param albumsRecords
     *  Set of all albums records by the label.
     * @since 1.0
     * @version 1.0
     */
    public LabelRecords(long id, String name, Set<Album> albumsRecords) {
        super.id = id;
        super.name = name;
        this.albumsRecords = albumsRecords;
    }

    /**
     * Set the list of albums records by the Label.
     *
     * @param albumsRecords
     *  New set of albums.
     * @since 1.0
     * @version 1.0
     */
    public void setAlbumsRecords(Set<Album> albumsRecords) {
        this.albumsRecords = albumsRecords;
    }

    /**
     * Get the list of all albums records by the Label.
     *
     * @return
     *  A set with all albums records by the Label.
     * @since 1.0
     * @version 1.0
     */
    public Set<Album> getAlbumsRecords() {
        return albumsRecords;
    }

    /**
     * Display all information about the label.
     *
     * @return
     *  Information about label.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Labels{" +
                "id=" + super.id +
                ", name=" + super.name +
                ", albumsRecords=" + CollectionAsString.setToString(this.albumsRecords) +
                '}';
    }
}
