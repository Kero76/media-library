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
package fr.nicolasgille.medialibrary.daos.video;


import fr.nicolasgille.medialibrary.models.video.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the series table.
 *
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with the table "movies", in particular getXXXX methods.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.1
 */
@Transactional
public interface SeriesRepository extends JpaRepository<Series, Long> {

    /**
     * Find a series by his name.
     *
     * @param title
     *  Title of the series at search on Database.
     * @return
     *  An instance of series search by his name.
     * @since 1.0
     * @version 1.0
     */
    Series findByTitleIgnoreCase(String title);

    /**
     * Find a series by his name and his current season.
     *
     * @param title
     *  The title of the series search on database.
     * @param currentSeason
     *  The current season of the series.
     * @return
     *  An instance of series if found on database or null if series not found.
     * @since 1.1
     * @version 1.0
     */
    Series findByTitleAndCurrentSeason(String title, int currentSeason);
}
