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
 * An enumeration who representing all media's support available on Media-Library.
 * This enumeration are composed by :
 * <ul>
 *     <li>VIDEO_TAPE : For old medias.</li>
 *     <li>DVD : DVD support.</li>
 *     <li>BLU_RAY : Blu Ray support.</li>
 *     <li>PAPER : Paper support for book.</li>
 *     <li>DIGITAL : Digital version (on computer).</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.2
 */
public enum MediaSupport {
    // Video support
    VIDEO_TAPE("Video Tape"),
    DVD("DVD"),
    BLU_RAY("Blu Ray"),

    // Book support
    PAPER("Paper"),

    // Music support
    AUDIO_TAPE("Audio Tape"),
    VYNIL("Vynil"),
    CD("CD"),

    // For all media
    DIGITAL("Digital");

    /**
     * Name of the element.
     *
     * @since 1.1
     */
    private String name;

    /**
     * Constructor of the Enum for instantiate value of attribute <code>name</code>.
     *
     * @param name
     *  Name stored in database.
     * @since 1.1
     * @version 1.0
     */
    MediaSupport(String name) {
        this.name = name;
    }

    /**
     * Return the name.
     *
     * @return
     *  Return the name of the element.
     * @since 1.1
     * @version 1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name
     *  New name.
     * @since 1.1
     * @version 1.0
     */
    public void setName(String name) {
        this.name = name;
    }
}
