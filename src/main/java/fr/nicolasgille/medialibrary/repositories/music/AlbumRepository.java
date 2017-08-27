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

package fr.nicolasgille.medialibrary.repositories.music;

import fr.nicolasgille.medialibrary.models.music.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Repository used to interact with all albums available on Database.
 *
 * @author Nicolas GILLE
 * @version 1.2
 * @since Media-Library 0.4
 */
@Transactional
public interface AlbumRepository extends JpaRepository<Album, Long> {

    /**
     * Find all albums by their name.
     *
     * @param title Title of the album at search on Database.
     *
     * @return An instance of album search by the name.
     *
     * @version 1.0.1
     * @since 1.0
     */
    List<Album> findByTitleIgnoreCase(String title);

    /**
     * Find all albums by their name.
     *
     * @param title Title of the album at search on Database.
     *
     * @return An instance of album search by the name.
     *
     * @version 1.0
     * @since 1.2
     */
    List<Album> findByTitleIgnoreCaseContaining(String title);

    /**
     * Find an album by his name, his length and his number of tracks.
     *
     * @param title Title of the album.
     * @param nbTrack Number of track present on the album.
     * @param length Length of the album.
     *
     * @return An instance of album search by the name, the number of tracks and his length.
     *
     * @version 1.0
     * @since 1.1
     */
    Album findByTitleAndNbTracksAndLength(String title, int nbTrack, double length);
}
