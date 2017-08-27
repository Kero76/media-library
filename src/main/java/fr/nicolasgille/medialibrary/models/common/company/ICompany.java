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

/**
 * Interface <code>IPerson</code> implements by abstract class <code>Company</code>.
 * It must be implement by all companies representing in the project.
 * It force all Company represented on the Media-Library to have a name and an id.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.4
 */
public interface ICompany {

    /**
     * Return id of the company.
     *
     * @return Id of the company.
     *
     * @version 1.0
     * @since 1.0
     */
    public long getId();

    /**
     * Set id of the company.
     *
     * @param id New id.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setId(long id);

    /**
     * Return the name of the company.
     *
     * @return The name of the company.
     *
     * @version 1.0
     * @since 1.0
     */
    public String getName();

    /**
     * Set the name of the company.
     *
     * @param name New name of the company.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setName(String name);
}
