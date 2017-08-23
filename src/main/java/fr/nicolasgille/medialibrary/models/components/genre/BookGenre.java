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
package fr.nicolasgille.medialibrary.models.components.genre;

/**
 * An enumeration representing all books genre present on app.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.0
 */
public enum BookGenre {
    // Book / Video
    ACTION("Action"),
    ADVENTURE("Adventure"),
    BIOPIC("Biopic"),
    COMEDY("Comedy"),
    COP("Cop"),
    CRIME("Crime"),
    CYBERPUNK("Cyberpunk"),
    DISASTER("Disaster"),
    DRAMA("Drama"),
    DYSTOPIAN("Dystopian"),
    EPIC("Epic"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    HEROIC_FANTASY("Heroic Fantasy"),
    HISTORICAL("Historical"),
    MAGICAL_GIRL("Magical Girl"),
    MARTIAL_ART("Martial Art"),
    MECHA("Mecha"),
    MONSTER("Monster"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    ROMANTIC("Romantic"),
    SCIENCE_FICTION("Science Fiction"),
    SPACE_OPERA("Space Opera"),
    SPAGUETTI_WESTERN("Spaghetti Western"),
    SPORT("Sport"),
    SPY("Spy"),
    SUPERHERO("Superhero"),
    SUPERNATURAL("Supernatural"),
    TECHNICAL("Technical"),
    TEEN("Teen"),
    THEATER("Theater"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western");

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
    BookGenre(String name) {
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
