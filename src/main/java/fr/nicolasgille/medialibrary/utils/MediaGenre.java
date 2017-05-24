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
package fr.nicolasgille.medialibrary.utils;

/**
 * An enumeration who representing all movies category available in Media Library.
 * This enumeration is present in order to force users at respect the syntax establish by Media Library.
 * It can be update in a future and a short description of each theme must write in a future.
 *
 * V1.2 :
 * <ul>
 *     <li>Added <code>HEROIC_FANTASY</code> attribute.</li>
 * </ul>
 *
 * V1.1 :
 * <ul>
 *     <li>Added constructor with <code>name</code> parameter.</li>
 *     <li>Added <code>name</code> attributes and corresponding getter and setter.</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.2
 */
public enum MediaGenre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    HEROIC_FANTASY("Heroic Fantasy"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    ROMANTIC("Romantic"),
    SCIENCE_FICTION("Science Fiction"),
    SPORT("Sport"),
    SPY("Spy"),
    TEEN("Teen"),
    THEATER("Theater"),
    THRILLER("Thriller"),
    WESTERN("Western");

    /**
     * Name stored in database.
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
    private MediaGenre(String name) {
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
}
