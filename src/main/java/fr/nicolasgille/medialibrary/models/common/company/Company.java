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
package fr.nicolasgille.medialibrary.models.common.company;

import javax.persistence.*;

/**
 * Main class to represent Company in Media-Library.
 * It implements the interface <code>ICompany</code> to impose many attributes :
 * <ul>
 *     <li>id : Identifier for the Database.</li>
 *     <li>name : Name of the company</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @see ICompany
 * @since Media-Library 0.4
 * @version 1.0
 */
@Entity
@Table(name = "company")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "company_type")
public class Company implements ICompany {

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
     * Name of the company.
     *
     * @since 1.0
     */
    protected String name;

    /**
     * Return id of the company.
     *
     * @return Id of the company.
     * @version 1.0
     * @since 1.0
     */
    public long getId() {
        return id;
    }

    /**
     * Set id of the company.
     *
     * @param id New id.
     * @version 1.0
     * @since 1.0
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Return the name of the company.
     *
     * @return The name of the company.
     * @version 1.0
     * @since 1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the company.
     *
     * @param name New name of the company.
     * @version 1.0
     * @since 1.0
     */
    public void setName(String name) {
        this.name = name;
    }
}
