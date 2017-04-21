package fr.nicolasgille.medialibrary.models.movie;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

/**
 * An enumeration who representing all movies support available on Media Library.
 * This enumeration are composed by :
 *  - VIDEO_TAPE : For old movies.
 *  - DVD : Current support available.
 *  - BLU_RAY : New support available.
 *
 * V1.1 :
 *  -> Added constructor with <code>name</code> parameter.
 *  -> Added <<code>name</code> attributes and corresponding getter and setter.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.1
 */
public enum MovieSupport {
    VIDEO_TAPE("Video Tape"),
    DVD("DVD"),
    BLU_RAY("Blu Ray");

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
    private MovieSupport(String name) {
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
