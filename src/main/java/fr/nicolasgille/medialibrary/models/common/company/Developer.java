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
 * @see Company
 * @see VideoGame
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
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
     * @since 1.0
     * @version 1.0
     */
    public Developer() {}

    /**
     * Constructor with <code>name</code> attribute.
     *
     * @param name
     *  Name of the developer.
     * @since 1.0
     * @version 1.0
     */
    public Developer(String name) {
        super.name = name;
    }

    /**
     * Constructor with all attributes.
     *
     * @param id
     *  Identifier of the developer.
     * @param name
     *  Name of the developer.
     * @param videoGames
     *  Set of all videoGames developed by the developer.
     * @since 1.0
     * @version 1.0
     */
    public Developer(long id, String name, Set<VideoGame> videoGames) {
        super.id = id;
        super.name = name;
        this.videoGames = videoGames;
    }

    /**
     * Set the list of videoGames developed by the developer.
     *
     * @param videoGames
     *  New set of videoGames.
     * @since 1.0
     * @version 1.0
     */
    public void setVideoGames(Set<VideoGame> videoGames) {
        this.videoGames = videoGames;
    }

    /**
     * Get the list of all videoGames published by the developer.
     *
     * @return
     *  A set with all book published by the developer.
     * @since 1.0
     * @version 1.0
     */
    public Set<VideoGame> getVideoGames() {
        return videoGames;
    }

    /**
     * Display all information about the developer.
     *
     * @return
     *  Information about developer.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Developer{" +
                "id=" + super.id +
                ", name=" + super.name +
                ", videogame=" + CollectionAsString.setToString(this.videoGames) +
                '}';
    }
}
