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

package fr.nicolasgille.medialibrary.models.common.person;

import fr.nicolasgille.medialibrary.models.music.Album;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Singer who represent a singer or a music group for an Album.
 * To represent a band, you can add the name of the band directly as first name, last name or twice if you like.
 * So, in following of your choice, you just get the information thanks to the right getXXX methods.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @see Person
 * @see Album
 * @since Media-Library 0.4
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
     * @version 1.0
     * @since 1.0
     */
    public Singer() {}

    /**
     * Constructor used to create Singer on Database.
     *
     * @param firstName First name of the Singer.
     * @param lastName Last name of the Singer.
     *
     * @version 1.0
     * @since 1.0
     */
    public Singer(String firstName, String lastName) {
        super.firstName = firstName;
        super.lastName = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id Identifier stored on database.
     * @param firstName First name.
     * @param lastName Last name.
     * @param albums All album where the singer appear as Singer.
     *
     * @version 1.0
     * @since 1.0
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
     * @return List of all albums where the singer appear.
     *
     * @version 1.0
     * @since 1.0
     */
    public Set<Album> getAlbums() {
        return albums;
    }

    /**
     * Set the list of all albums where the singer appear.
     *
     * @param albums New list of albums.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    /**
     * Display content of the Singer.
     *
     * @return A simple representation of the Singer.
     *
     * @version 1.1
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Singer{" +
               ", id=" + super.id + '\'' +
               ", firstName='" + super.firstName + '\'' +
               ", lastName='" + super.lastName + '\'' +
               "', albums='" + CollectionAsString.collectionToString(this.albums) + '\'' +
               '}';
    }
}
