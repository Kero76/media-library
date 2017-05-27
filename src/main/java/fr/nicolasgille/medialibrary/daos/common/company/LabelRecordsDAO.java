package fr.nicolasgille.medialibrary.daos.common.company;

import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * An interface who specified method to interact with the <code>common_labelrecords</code> table.
 *
 * This interface can extends with many methods to request labelrecords on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@Transactional
public interface LabelRecordsDAO extends JpaRepository<LabelRecords, Long> {

    /**
     * Search an Label Records by his name.
     *
     * @param name
     *  Name of the label records at search.
     * @return
     *  The label records is an occurrence was found on Database, or null.
     * @since 1.0
     * @version 1.0
     */
    LabelRecords findByName(String name);
}
