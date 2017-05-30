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

import fr.nicolasgille.medialibrary.models.book.Comic;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Illustrator who represent the illustrator of the Comic.
 *
 * @see Person
 * @see Comic
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "illustrator")
public class Illustrator extends Person {

    /**
     * List of all books written by the illustrator.
     *
     * @since 1.0
     */
    @Transient
    private Set<Comic> comics;

    /**
     * Empty constructor.
     *
     * @since 1.0
     * @version 1.0
     */
    public Illustrator() {}

    /**
     * Constructor used to create Actor on Database.
     *
     * @param firstName
     *  First name of the Actor.
     * @param lastName
     *  Last name of the Actor.
     * @since 1.0
     * @version 1.0
     */
    public Illustrator(String firstName, String lastName) {
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
     * @param comics
     *  All comics drawing by the illustrator.
     * @since 1.0
     * @version 1.0
     */
    public Illustrator(long id, String firstName, String lastName, Set<Comic> comics) {
        super.id = id;
        super.firstName = firstName;
        super.lastName = lastName;
        this.comics = comics;
    }

    /**
     * List of all comics drawing by the illustrators.
     *
     * @return
     *  All comics drawing by the illustrator.
     * @since 1.0
     * @version 1.0
     */
    public Set<Comic> getVideos() {
        return comics;
    }

    /**
     * Set the list of all comics drawing by the illustrator.
     *
     * @param comics
     *  New list of comics.
     * @since 1.0
     * @version 1.0
     */
    public void setVideos(Set<Comic> comics) {
        this.comics = comics;
    }

    /**
     * Display content of the Illustrator.
     *
     * @return
     *  A simple representation of the Illustrator.
     * @since 1.0
     * @version 1.1
     */
    @Override
    public String toString() {
        return "Illustrator{" +
                ", id=" + super.id + '\'' +
                ", firstName='" + super.firstName + '\'' +
                ", lastName='" + super.lastName + '\'' +
                "', comics='" + CollectionAsString.setToString(this.comics) + '\'' +
                '}';
    }
}
