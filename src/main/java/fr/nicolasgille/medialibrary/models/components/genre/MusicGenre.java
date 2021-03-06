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
 * An enumeration representing all music genre present on app.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 1.0
 */
public enum MusicGenre {
    ALTERNATIVE_METAL("Alternative Metal"),
    ALTERNATIVE_ROCK("Alternative Rock"),
    BALLAD("Ballad"),
    BLUE_EYES_SOUL("Blue eyes Soul"),
    BLUES("Blues"),
    BLUES_ROCK("Blues Rock"),
    CLASSIC("Classic"),
    CELTIC("Celtic"),
    COUNTRY("Country"),
    DANCE("Dance"),
    DANCE_POP("Dance Pop"),
    DISCO("Disco"),
    ELECTRO("Electro"),
    EURODANCE("Eurodance"),
    EUROPOP("Europop"),
    FRENCH_VARIETY("French Variety"),
    FUNK("Funk"),
    JAZZ("Jazz"),
    J_POP("J Pop"),
    J_ROCK("J Rock"),
    HARD_ROCK("Hard Rock"),
    HEAVY_METAL("Heavy Metal"),
    HIP_HOP("Hip Hop"),
    HOUSE("House"),
    METAL("Metal"),
    NEW_WAVE("New Wave"),
    OPERA("Opera"),
    ORCHESTRA("Orchestra"),
    OST("Ost"),
    POP("Pop"),
    POP_FUNK("Pop Funk"),
    POP_ROCK("Pop Rock"),
    POST_GRUNGE("Post Grunge"),
    POWER_BALLAD("Power Ballad"),
    PROGRESSIVE_ROCK("Progressive Rock"),
    PUNK("Punk"),
    REGGAE("Reggae"),
    REGGAE_FUSION("Reggae Fusion"),
    RAP("Rap"),
    RAP_CELTIC("Rap Celtic"),
    ROCK("Rock"),
    ROCK_N_ROLL("Rock n Roll"),
    RNB("RnB"),
    SKA("Ska"),
    SOUL("Soul"),
    SOUTHERN_ROCK("Southern Rock"),
    SYNTHPOP("Synthpop"),
    TECHNO("Techno"),
    ZOUK("Zouk");

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
    MusicGenre(String name) {
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
