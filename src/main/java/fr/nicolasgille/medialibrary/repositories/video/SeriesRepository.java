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

import fr.nicolasgille.medialibrary.models.video.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * An interface who specified method to interact with the series table.
 * <p>
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with the table "movies", in particular getXXXX methods.
 *
 * @author Nicolas GILLE
 * @version 1.2
 * @since Media-Library 0.2
 */
@Transactional
public interface SeriesRepository extends JpaRepository<Series, Long> {

    /**
     * Find a series by his name.
     *
     * @param title Title of the series at search on Database.
     *
     * @return An instance of series search by his name.
     *
     * @version 1.1
     * @since 1.0
     */
    List<Series> findByTitleIgnoreCase(String title);

    /**
     * Find a series by his name.
     *
     * @param title Title of the series at search on Database.
     *
     * @return An instance of series search by his name.
     *
     * @version 1.0
     * @since 1.2
     */
    List<Series> findByTitleIgnoreCaseContaining(String title);

    /**
     * Find a series by his name and his current season.
     *
     * @param title The title of the series search on database.
     * @param currentSeason The current season of the series.
     *
     * @return An instance of series if found on database or null if series not found.
     *
     * @version 1.0
     * @since 1.1
     */
    Series findByTitleAndCurrentSeason(String title, int currentSeason);
}
