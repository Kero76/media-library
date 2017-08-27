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

package fr.nicolasgille.medialibrary.models.common.company;

import fr.nicolasgille.medialibrary.models.game.VideoGame;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Developer represent a company who develop a game.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @see Company
 * @see VideoGame
 * @since Media-Library 0.4
 */
@Entity
@DiscriminatorValue(value = "developer")
public class Developer extends Company {

    /**
     * List of all VideoGame developed by the team.
     *
     * @since 1.0
     */
    @Transient
    private Set<VideoGame> videoGames;

    /**
     * Empty constructor
     *
     * @version 1.0
     * @since 1.0
     */
    public Developer() {}

    /**
     * Constructor with <code>name</code> attribute.
     *
     * @param name Name of the developer.
     *
     * @version 1.0
     * @since 1.0
     */
    public Developer(String name) {
        super.name = name;
    }

    /**
     * Constructor with all attributes.
     *
     * @param id Identifier of the developer.
     * @param name Name of the developer.
     * @param videoGames Set of all videoGames developed by the developer.
     *
     * @version 1.0
     * @since 1.0
     */
    public Developer(long id, String name, Set<VideoGame> videoGames) {
        super.id = id;
        super.name = name;
        this.videoGames = videoGames;
    }

    /**
     * Get the list of all videoGames published by the developer.
     *
     * @return A set with all book published by the developer.
     *
     * @version 1.0
     * @since 1.0
     */
    public Set<VideoGame> getVideoGames() {
        return videoGames;
    }

    /**
     * Set the list of videoGames developed by the developer.
     *
     * @param videoGames New set of videoGames.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setVideoGames(Set<VideoGame> videoGames) {
        this.videoGames = videoGames;
    }

    /**
     * Display all information about the developer.
     *
     * @return Information about developer.
     *
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Developer{" +
               "id=" + super.id +
               ", name=" + super.name +
               ", videogame=" + CollectionAsString.collectionToString(this.videoGames) +
               '}';
    }
}
