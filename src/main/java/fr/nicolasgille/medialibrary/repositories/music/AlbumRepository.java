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
package fr.nicolasgille.medialibrary.repositories.music;

import fr.nicolasgille.medialibrary.models.music.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Repository used to interact with all albums available on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Transactional
public interface AlbumRepository extends JpaRepository<Album, Long> {

    /**
     * Find a album by his name.
     *
     * @param title
     *  Title of the album at search on Database.
     * @return
     *  An instance of album search by the name.
     * @since 1.0
     * @version 1.0
     */
    Album findByTitleIgnoreCase(String title);
}
