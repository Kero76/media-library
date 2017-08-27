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

package fr.nicolasgille.medialibrary.repositories.video;

import fr.nicolasgille.medialibrary.models.video.Cartoon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.List;

/**
 * An interface who specified method to interact with the cartoon.
 * <p>
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with "cartoon", in particular find methods.
 *
 * @author Nicolas GILLE
 * @version 1.1
 * @since Media-Library 0.3
 */
public interface CartoonRepository extends JpaRepository<Cartoon, Long> {

    /**
     * Find all cartoons by his name.
     *
     * @param title Title of the cartoons at search on Database.
     *
     * @return An instance of cartoons search by the name.
     *
     * @version 1.0.1
     * @since 1.0
     */
    List<Cartoon> findByTitleIgnoreCase(String title);

    /**
     * Find all cartoons by his name.
     *
     * @param title Title of the cartoons at search on Database.
     *
     * @return An instance of cartoons search by the name.
     *
     * @version 1.0
     * @since 1.1
     */
    List<Cartoon> findByTitleIgnoreCaseContaining(String title);

    /**
     * Find a cartoon by his name, his duration and date of release.
     *
     * @param title Title of the cartoon.
     * @param runtime Runtime of the cartoon.
     * @param releaseDate Date of release of the cartoon search.
     *
     * @return An instance of cartoon search by title, duration and releaseDate.
     *
     * @version 1.0
     * @since 1.0
     */
    Cartoon findByTitleAndRuntimeAndReleaseDate(String title, int runtime, Calendar releaseDate);
}
