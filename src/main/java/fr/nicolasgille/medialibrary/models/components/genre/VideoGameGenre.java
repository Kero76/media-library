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

package fr.nicolasgille.medialibrary.models.components.genre;

/**
 * An enumeration representing all video-games genre present on app.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 1.0
 */
public enum VideoGameGenre {
    ACTION("Action"),
    ACTION_RPG("Action RPG"),
    BEAT_EM_ALL("Beat em All"),
    BEAT_EM_UP("Beat em Up"),
    COURSES("Courses"),
    FPS("FPS"),
    IDLE("Idle"),
    MANAGEMENT("Management"),
    PLATFORMS("Platform"),
    PUZZLE_GAME("Puzzle Game"),
    ROGUE_LIKE("Rogue Like"),
    RPG("RPG"),
    RTS("RTS"),
    SANDBOX("Sandbox"),
    SHOOTER("Shooter"),
    SPORT("Sport"),
    SURVIVAL_HORROR("Survival Horror"),
    TACTICAL_RPG("Tactical RPG"),
    TPS("TPS"),
    VERSUS_FIGHTING("Versus Fighting");

    /**
     * Name stored in database.
     *
     * @since 1.0
     */
    private String name;

    /**
     * Constructor of the Enum for instantiate value of attribute <code>name</code>.
     *
     * @param name Name stored in database.
     *
     * @version 1.0
     * @since 1.0
     */
    VideoGameGenre(String name) {
        this.name = name;
    }

    /**
     * Return the name.
     *
     * @return Return the name of the element.
     *
     * @version 1.0
     * @since 1.0
     */
    public String getName() {
        return name;
    }
}
