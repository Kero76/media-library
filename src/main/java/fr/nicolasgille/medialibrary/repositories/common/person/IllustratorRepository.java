package fr.nicolasgille.medialibrary.repositories.common.person;

import fr.nicolasgille.medialibrary.models.common.person.Illustrator;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_illustrator</code> table.
 *
 * This interface can extends with many methods to request illustrator on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface IllustratorRepository extends JpaRepository<Illustrator, Long> {

    /**
     * Search an illustrator by his first name and last name.
     *
     * @param fname
     *  First name of the illustrator at search.
     * @param lname
     *  Last name of the illustrator at search.
     * @return
     *  The illustrator is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Illustrator findByFirstNameAndLastName(String fname, String lname);
}
