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
package fr.nicolasgille.medialibrary.models.common.person;

import fr.nicolasgille.medialibrary.models.music.Album;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Author present the author of the Book.
 *
 * @see Person
 * @see Album
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "singer")
public class Singer extends Person {

    /**
     * List of all albums sing by the Singer.
     *
     * @since 1.0
     */
    @Transient
    private Set<Album> albums;

    /**
     * Empty constructor.
     *
     * @since 1.0
     * @version 1.0
     */
    public Singer() {}

    /**
     * Constructor used to create Singer on Database.
     *
     * @param firstName
     *  First name of the Singer.
     * @param lastName
     *  Last name of the Singer.
     * @since 1.0
     * @version 1.0
     */
    public Singer(String firstName, String lastName) {
        super.firstName = firstName;
        super.lastName  = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id
     *  Identifier stored on database.
     * @param firstName
     *  First name.
     * @param lastName
     *  Last name.
     * @param albums
     *  All album where the singer appear as Singer.
     * @since 1.0
     * @version 1.0
     */
    public Singer(long id, String firstName, String lastName, Set<Album> albums) {
        super.id = id;
        super.firstName = firstName;
        super.lastName = lastName;
        this.albums = albums;
    }

    /**
     * List of all albums where the singer appear.
     *
     * @return
     *  List of all albums where the singer appear.
     * @since 1.0
     * @version 1.0
     */
    public Set<Album> getAlbums() {
        return albums;
    }

    /**
     * Set the list of all albums where the singer appear.
     *
     * @param albums
     *  New list of albums.
     * @since 1.0
     * @version 1.0
     */
    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    /**
     * Display content of the Singer.
     *
     * @return
     *  A simple representation of the Singer.
     * @since 1.0
     * @version 1.1
     */
    @Override
    public String toString() {
        return "Singer{" +
                ", id=" + super.id + '\'' +
                ", firstName='" + super.firstName + '\'' +
                ", lastName='" + super.lastName + '\'' +
                "', albums='" + CollectionAsString.setToString(this.albums) + '\'' +
                '}';
    }
}
