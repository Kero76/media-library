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
    ANIMATION("Animation"),
    BIOPIC("Biopic"),
    BUDDY_COP("Buddy Cop"),
    COMEDY("Comedy"),
    COP("Cop"),
    CRIME("Crime"),
    CYBERPUNK("Cyberpunk"),
    DISASTER("Disaster"),
    DRAMA("Drama"),
    DYSTOPIAN("Dystopian"),
    EPIC("Epic"),
    FAMILY("Family"),
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
    TEEN("Teen"),
    TOKUSATSU("Tokusatsu"),
    THEATER("Theater"),
    THRILLER("Thriller"),
    WAR("War"),
    WESTERN("Western"),

    // Music
    ALTERNATIVE_METAL("Alternative Metal"),
    ALTERNATIVE_ROCK("Alternative Rock"),
    BALLAD("Ballad"),
    BLUE_EYES_SOUL("Blue-eyes Soul"),
    BLUES("Blues"),
    BLUES_ROCK("Blues Rock"),
    CLASSIC("Classic"),
    CELTIC("Celtic"),
    COUNTRY("Country"),
    DANCE("Dance"),
    DANCE_POP("Dance-Pop"),
    DISCO("Disco"),
    ELECTRO("Electro"),
    EURODANCE("Eurodance"),
    EUROPOP("Europop"),
    FRENCH_VARIETY("French Variety"),
    FUNK("Funk"),
    JAZZ("Jazz"),
    J_POP("J-Pop"),
    J_ROCK("J-Rock"),
    HARD_ROCK("Hard Rock"),
    HEAVY_METAL("Heavy Metal"),
    HIP_HOP("Hip Hop"),
    HOUSE("House"),
    METAL("Metal"),
    NEW_WAVE("New Wave"),
    OPERA("Opera"),
    ORCHESTRA("Orchestra"),
    OST("Original Soundtrack"),
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
    ROCK_N_ROLL("Rock'n'Roll"),
    RNB("R'N'B"),
    SKA("Ska"),
    SOUL("Soul"),
    SOUTHERN_ROCK("Southern Rock"),
    SYNTHPOP("Synthpop"),
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
