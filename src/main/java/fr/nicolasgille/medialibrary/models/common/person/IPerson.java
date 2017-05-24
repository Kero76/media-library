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

/**
 * Interface <code>IPerson</code> implements by abstract class <code>Person</code>.
 * It must be implement by all human representing in the project.
 * It force all Human represented on the Media-Library to have a first name, last name and an id.
 *
 * @author Nicolas GILLE
 * @see Person
 * @since Media-Library 0.2.1
 * @version 1.0
 */
public interface IPerson {

    /**
     * Return id of the Person.
     *
     * @return
     *  Id of the Person.
     * @since 1.0
     * @version 1.0
     */
    public long getId();

    /**
     * Set id of the Person.
     *
     * @param id
     *  New id.
     * @since 1.0
     * @version 1.0
     */
    public void setId(long id);

    /**
     * Return first name of the Person.
     *
     * @return
     *  First name of the Person.
     * @since 1.0
     * @version 1.0
     */
    public String getFirstName();

    /**
     * Set first name of the Person.
     *
     * @param firstName
     *  New first name.
     * @since 1.0
     * @version 1.0
     */
    public void setFirstName(String firstName);

    /**
     * Return last name of the Person.
     *
     * @return
     *  Last name of the Person.
     * @since 1.0
     * @version 1.0
     */
    public String getLastName();

    /**
     * Set the last name of the Person.
     *
     * @param lastName
     *  New last name.
     * @since 1.0
     * @version 1.0
     */
    public void setLastName(String lastName);
}
