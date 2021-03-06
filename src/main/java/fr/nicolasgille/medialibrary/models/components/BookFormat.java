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

package fr.nicolasgille.medialibrary.models.components;

/**
 * An enumeration to represent the format available for a book.
 * It composed by :
 * <ul>
 * <li><code>CLASSICAL</code> : Classical format when the book can be bought on the market.</li>
 * <li><code>POCKET</code> : A re-edition of the book in little format.</li>
 * <li><code>UNSPECIFIED</code> : Format not specified or unknown.</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.4
 */
public enum BookFormat {
    CLASSICAL("Classical"),
    POCKET("Pocket"),
    UNSPECIFIED("Unspecified");

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
    BookFormat(String name) {
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
