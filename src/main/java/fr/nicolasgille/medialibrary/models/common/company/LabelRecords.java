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

package fr.nicolasgille.medialibrary.models.common.company;

import fr.nicolasgille.medialibrary.models.music.Album;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class LabelRecords represent a company who records music album.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @see Company
 * @see Album
 * @since Media-Library 0.4
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
     * @version 1.0
     * @since 1.0
     */
    public LabelRecords() {}

    /**
     * Constructor with <code>name</code> attribute.
     *
     * @param name Name of the label.
     *
     * @version 1.0
     * @since 1.0
     */
    public LabelRecords(String name) {
        super.name = name;
    }

    /**
     * Constructor with all attributes.
     *
     * @param id Identifier of the label.
     * @param name Name of the label.
     * @param albumsRecords Set of all albums records by the label.
     *
     * @version 1.0
     * @since 1.0
     */
    public LabelRecords(long id, String name, Set<Album> albumsRecords) {
        super.id = id;
        super.name = name;
        this.albumsRecords = albumsRecords;
    }

    /**
     * Get the list of all albums records by the Label.
     *
     * @return A set with all albums records by the Label.
     *
     * @version 1.0
     * @since 1.0
     */
    public Set<Album> getAlbumsRecords() {
        return albumsRecords;
    }

    /**
     * Set the list of albums records by the Label.
     *
     * @param albumsRecords New set of albums.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setAlbumsRecords(Set<Album> albumsRecords) {
        this.albumsRecords = albumsRecords;
    }

    /**
     * Display all information about the label.
     *
     * @return Information about label.
     *
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Labels{" +
               "id=" + super.id +
               ", name=" + super.name +
               ", albumsRecords=" + CollectionAsString.collectionToString(this.albumsRecords) +
               '}';
    }
}
