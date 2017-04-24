package fr.nicolasgille.medialibrary.daos.common;

import fr.nicolasgille.medialibrary.models.common.Producer;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_actor</code> table.
 *
 * This interface can extends with many methods to request actors on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.0
 */
@Transactional
public interface ProducerDAO extends CrudRepository<Producer, Long> {

    /**
     * Search an Actor by his first name and last name.
     *
     * @param fname
     *  First name of the producer at search.
     * @param lname
     *  Last name of the producer at search.
     * @return
     *  The actor is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Producer findByFirstNameAndLastName(String fname, String lname);
}
