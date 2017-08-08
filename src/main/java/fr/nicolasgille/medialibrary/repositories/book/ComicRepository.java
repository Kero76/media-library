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
package fr.nicolasgille.medialibrary.repositories.book;

import fr.nicolasgille.medialibrary.models.book.Comic;
import fr.nicolasgille.medialibrary.models.video.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository used to interact with all comics available on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.2
 */
@Transactional
public interface ComicRepository extends JpaRepository<Comic, Long> {

    /**
     * Find a cartoon by his name.
     *
     * @param title
     *  Title of the cartoon at search on Database.
     * @return
     *  An instance of cartoon search by the name.
     * @since 1.0
     * @version 1.1
     */
    List<Comic> findByTitleIgnoreCase(String title);

    /**
     * Find a cartoon by his name and his current volume.
     *
     * @param title
     *  Title of the cartoon at search on Database.
     * @param currentVolume
     *  Current volume of the cartoon at search on Database.
     * @return
     *  An instance of cartoon search by the name.
     * @since 1.1
     * @version 1.0
     */
    Comic findByTitleIgnoreCaseAndCurrentVolume(String title, int currentVolume);

    /**
     * Find all comic present on Media Library.
     *
     * @return
     *  List of all Comic.
     * @since 1.2
     * @version 1.0
     */
    @Query(value = "SELECT * FROM media WHERE media_type = \"comic\"", nativeQuery = true)
    List<Comic> findAll();
}
