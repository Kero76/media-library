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

package fr.nicolasgille.medialibrary.repositories.common.person;

import fr.nicolasgille.medialibrary.models.common.person.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_director</code> table.
 * <p>
 * This interface can extends with many methods to request directors on Database.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.1
 */
@Transactional
public interface DirectorRepository extends JpaRepository<Director, Long> {

    /**
     * Search an Director by his first name and last name.
     *
     * @param fname First name of the director at search.
     * @param lname Last name of the director at search.
     *
     * @return The director is an occurrence was found on Database, or null.
     *
     * @version 1.0
     * @since 1.0
     */
    Director findByFirstNameAndLastName(String fname, String lname);
}
