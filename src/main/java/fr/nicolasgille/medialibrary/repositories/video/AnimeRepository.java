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
package fr.nicolasgille.medialibrary.repositories.video;

import fr.nicolasgille.medialibrary.models.video.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * An interface who specified method to interact with the anime table.
 *
 * In fact, hsqldb can generate corresponding source code in function of the nomenclature of the method.
 * So, this interface don't implements to use method present on it.
 * Then, we add in the future much method to interact with the table "movies", in particular getXXXX methods.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.2.1
 */
@Transactional
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    /**
     * Find a anime by his name.
     *
     * @param title
     *  Title of the anime at search on Database.
     * @return
     *  An instance of anime search by his name.
     * @since 1.0
     * @version 1.1
     */
    List<Anime> findByTitleIgnoreCase(String title);

    /**
     * Find a anime by his name and his current season.
     *
     * @param title
     *  The title of the anime search on database.
     * @param currentSeason
     *  The current season of the anime.
     * @return
     *  An instance of anime if found on database or null if anime not found.
     * @since 1.1
     * @version 1.0
     */
    Anime findByTitleAndCurrentSeason(String title, int currentSeason);

    /**
     * Find all animes present on Media Library.
     *
     * @return
     *  List of all Anime.
     * @since 1.2
     * @version 1.0
     */
    @Query(value = "SELECT * FROM media WHERE media_type = \"anime\"", nativeQuery = true)
    List<Anime> findAll();
}
