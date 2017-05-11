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
package fr.nicolasgille.medialibrary.models.common;

import javax.persistence.*;

/**
 * Main class to represent Human in Media-Library.
 * It implements the interface <code>IPerson</code> to impose many attributes :
 * <ul>
 *     <li>id : Identifier for the Database.</li>
 *     <li>firstName : First name of the Person.</li>
 *     <li>lastName : Last name of the Person.</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @see IPerson
 * @since Media-Library 0.2.1
 * @version 1.0
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
abstract class Person implements IPerson {

    /**
     * Identifier of the Person.
     *
     * @since 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected long id;

    /**
     * First name of the Person.
     *
     * @since 1.0
     */
    @Column(name = "fname")
    protected String firstName;

    /**
     * Last name of the Person.
     *
     * @since 1.0
     */
    @Column(name = "lname")
    protected String lastName;

    /**
     * Return id of the Person.
     *
     * @return
     *  Id of the Person.
     * @since 1.0
     * @version 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set id of the Person.
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
     * Return first name of the Person.
     *
     * @return
     *  First name of the Person.
     * @since 1.0
     * @version 1.0
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name of the Person.
     *
     * @param firstName
     *  New first name.
     * @since 1.0
     * @version 1.0
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return last name of the Person.
     *
     * @return
     *  Last name of the Person.
     * @since 1.0
     * @version 1.0
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the Person.
     *
     * @param lastName
     *  New last name.
     * @since 1.0
     * @version 1.0
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
