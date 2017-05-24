package fr.nicolasgille.medialibrary.daos.common;

import fr.nicolasgille.medialibrary.models.common.person.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_actor</code> table.
 *
 * This interface can extends with many methods to request actors on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface ActorDAO extends JpaRepository<Actor, Long> {

    /**
     * Search an Actor by his first name and last name.
     *
     * @param fname
     *  First name of the actor at search.
     * @param lname
     *  Last name of the actor at search.
     * @return
     *  The actor is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Actor findByFirstNameAndLastName(String fname, String lname);
}
