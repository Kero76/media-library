package fr.nicolasgille.medialibrary.daos.common.company;

import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_publisher</code> table.
 *
 * This interface can extends with many methods to request Publisher on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * Search an Publisher by his first name and last name.
     *
     * @param name
     *  Name of the publisher at search.
     * @return
     *  The publisher is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    Publisher findByName(String name);
}
