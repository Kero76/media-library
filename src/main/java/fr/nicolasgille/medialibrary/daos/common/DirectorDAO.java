package fr.nicolasgille.medialibrary.daos.common;

import fr.nicolasgille.medialibrary.models.common.Director;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_director</code> table.
 *
 * This interface can extends with many methods to request actors on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.0
 */
@Transactional
public interface DirectorDAO extends CrudRepository<Director, Long> {

    /**
     * Search an Director by his first name and last name.
     *
     * @param fname
     *  First name of the director at search.
     * @param lname
     *  Last name of the director at search.
     * @return
     *  The director is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Director findByFirstNameAndLastName(String fname, String lname);
}
