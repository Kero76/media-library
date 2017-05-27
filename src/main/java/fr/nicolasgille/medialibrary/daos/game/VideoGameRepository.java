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
package fr.nicolasgille.medialibrary.daos.game;

import fr.nicolasgille.medialibrary.models.game.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Repository used to interact with all video games available on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Transactional
public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {

    /**
     * Find a cartoon by his name.
     *
     * @param title
     *  Title of the cartoon at search on Database.
     * @return
     *  An instance of cartoon search by the name.
     * @since 1.0
     * @version 1.0
     */
    VideoGame findByTitleIgnoreCase(String title);
}
