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

package fr.nicolasgille.medialibrary.repositories.game;

import fr.nicolasgille.medialibrary.models.game.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * Repository used to interact with all video games available on Database.
 *
 * @author Nicolas GILLE
 * @version 1.2
 * @since Media-Library 0.4
 */
@Transactional
public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {

    /**
     * Find all video games by his name.
     *
     * @param title Title of the video game at search on Database.
     *
     * @return An instance of all video games search by the name.
     *
     * @version 1.0
     * @since 1.0
     */
    List<VideoGame> findByTitleIgnoreCase(String title);

    /**
     * Find all video games by his name.
     *
     * @param title Title of the video game at search on Database.
     *
     * @return An instance of all video games search by the name.
     *
     * @version 1.0
     * @since 1.2
     */
    List<VideoGame> findByTitleIgnoreCaseContaining(String title);

    /**
     * Find video game by his name and his release date.
     *
     * @param title Title of the video game at search on Database.
     * @param releaseDate Date of release of the book search.
     *
     * @return An instance of video game search by the name.
     *
     * @version 1.0
     * @since 1.1
     */
    VideoGame findByTitleIgnoreCaseAndReleaseDate(String title, Calendar releaseDate);
}
