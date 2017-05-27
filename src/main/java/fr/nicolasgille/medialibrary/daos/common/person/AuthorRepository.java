package fr.nicolasgille.medialibrary.daos.common.person;

import fr.nicolasgille.medialibrary.models.common.person.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_author</code> table.
 *
 * This interface can extends with many methods to request author on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Search an Author by his first name and last name.
     *
     * @param fname
     *  First name of the author at search.
     * @param lname
     *  Last name of the author at search.
     * @return
     *  The author is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Author findByFirstNameAndLastName(String fname, String lname);
}
