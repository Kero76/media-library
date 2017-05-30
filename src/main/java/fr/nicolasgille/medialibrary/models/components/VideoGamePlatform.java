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
package fr.nicolasgille.medialibrary.models.components;

/**
 * An enumeration to represent all video-game platforms available for the Media-Library.
 * It composed by :
 * <ul>
 *     <li><code>NES</code></li>
 *     <li><code>SNES</code></li>
 *     <li><code>N64</code></li>
 *     <li><code>GAMECUBE</code></li>
 *     <li><code>WII</code></li>
 *     <li><code>WIIU</code></li>
 *     <li><code>GAMEBOY</code></li>
 *     <li><code>GAMEBOY_ADVANCE</code></li>
 *     <li><code>NINTENDO_DS</code></li>
 *     <li><code>NINTENDO_3DS</code></li>
 *     <li><code>MEGA_DRIVE</code></li>
 *     <li><code>SEGA_SATURN</code></li>
 *     <li><code>DREAMCAST</code></li>
 *     <li><code>PSX</code></li>
 *     <li><code>PS2</code></li>
 *     <li><code>PS3</code></li>
 *     <li><code>PS4</code></li>
 *     <li><code>PSP</code></li>
 *     <li><code>XBOX</code></li>
 *     <li><code>XBOX_360</code></li>
 *     <li><code>XBOX_ONE</code></li>
 *     <li><code>PC</code></li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
public enum VideoGamePlatform {
    // Nintendo
    NES("Nintendo Entertainment System"),
    SNES("Super Nintendo"),
    N64("Nintendo 64"),
    GAMECUBE("Gamecube"),
    WII("Wii"),
    WIIU("Wii U"),
    GAMEBOY("Gameboy"),
    GAMEBOY_ADVANCE("Gameboy Advance"),
    NINTENDO_DS("Nintendo DS"),
    NINTENDO_3DS("Nintendo 3DS"),

    // Sega
    MEGA_DRIVE("Sega Mega Drive"),
    SEGA_SATURN("Sega Saturn"),
    DREAMCAST("Sega Dreamcast"),

    // Sony
    PSX("Playstation"),
    PS2("Playstation 2"),
    PS3("Playstation 3"),
    PS4("Playstation 4"),
    PSP("Playstation Portable"),

    // Microsoft
    XBOX("Xbox"),
    XBOX_360("Xbox 360"),
    XBOX_ONE("Xbox One"),

    // Computer
    PC("Personnal Computer");

    /**
     * Name stored in database.
     *
     * @since 1.0
     */
    private String name;

    /**
     * Constructor of the Enum for instantiate value of attribute <code>name</code>.
     *
     * @param name
     *  Name stored in database.
     * @since 1.0
     * @version 1.0
     */
    VideoGamePlatform(String name) {
        this.name = name;
    }

    /**
     * Return the name.
     *
     * @return
     *  Return the name of the element.
     * @since 1.0
     * @version 1.0
     */
    public String getName() {
        return name;
    }
}
