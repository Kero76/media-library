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
 * An enumeration who representing all genres available in Media Library.
 * This enumeration is composed by the genre from the Book, Video, Music and Game genre.
 * You can add new genre on it, if you would add specific genre not found initially on Media-Library.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.2
 */
public enum MediaGenre {
    // Book / Video
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
    WESTERN("Western"),

    // Music
    BLUES("Blues"),
    CLASSIC("Classic"),
    CELTIC("Celtic"),
    COUNTRY("Country"),
    DISCO("Disco"),
    ELECTRO("Electro"),
    FUNK("Funk"),
    JAZZ("Jazz"),
    J_POP("J-Pop"),
    J_ROCK("J-Rock"),
    HARD_ROCK("Hard Rock"),
    HIP_HOP("Hip Hop"),
    METAL("Metal"),
    NEW_WAVE("New Wave"),
    OPERA("Opera"),
    ORCHESTRA("Orchestra"),
    POP("Pop"),
    PUNK("Punk"),
    REGGAE("Reggae"),
    ROCK_N_ROLL("Rock'n'Roll"),
    RNB("R'N'B"),
    SOUL("Soul"),
    TECHNO("Techno"),
    ZOUK("Zouk"),

    // VideoGame
    ACTION_RPG("Action RPG"),
    BEAT_EM_ALL("Beat'em All"),
    BEAT_EM_UP("Beat'em Up"),
    COURSES("Courses"),
    FPS("First Person Shooter"),
    IDLE("Idle"),
    MANAGEMENT("Management"),
    PLATFORMS("Platform"),
    PUZZLE_GAME("Puzzle Game"),
    ROGUE_LIKE("Rogue Like"),
    RPG("Role Playing Game"),
    RTS("Real-Time Strategy"),
    SANDBOX("Sandbox"),
    SHOOTER("Shooter"),
    SURVIVAL_HORROR("Survival Horror"),
    TACTICAL_RPG("Tactical RPG"),
    TPS("Third Person Shooter"),
    VERSUS_FIGHTING("Versus Fighting");

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
    MediaGenre(String name) {
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
