package fr.nicolasgille.medialibrary.daos.common.company;

import fr.nicolasgille.medialibrary.models.common.company.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_developer</code> table.
 *
 * This interface can extends with many methods to request developer on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    /**
     * Search an Actor by his name.
     *
     * @param name
     *  Name of the developer at search.
     * @return
     *  The developer is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Developer findByName(String name);
}
