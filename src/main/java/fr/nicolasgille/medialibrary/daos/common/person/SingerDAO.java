package fr.nicolasgille.medialibrary.daos.common.person;

import fr.nicolasgille.medialibrary.models.common.person.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_singer</code> table.
 *
 * This interface can extends with many methods to request singers on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface SingerDAO extends JpaRepository<Singer, Long> {

    /**
     * Search an Singer by his first name and last name.
     *
     * @param fname
     *  First name of the singer at search.
     * @param lname
     *  Last name of the singer at search.
     * @return
     *  The singer is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Singer findByFirstNameAndLastName(String fname, String lname);
}
