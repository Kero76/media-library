/*
 * This file is part of Media-Library.
 *
 * Media-Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Media-Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Media-Library. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.nicolasgille.medialibrary.controllers.common.company;

import fr.nicolasgille.medialibrary.exceptions.common.company.LabelRecordsException;
import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import fr.nicolasgille.medialibrary.repositories.common.company.LabelRecordsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Controller of LabelRecords model object.
 *
 * This class control the access of the label records on the project.
 * It define some method to search label records present on the database.
 * You can add your own method to search or interact with label records if you like.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabelRecordsController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Repository used to interact with label records present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private LabelRecordsRepository labelRecordsRepository;

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(LabelRecordsController.class);

    /**
     * Get all label records from the database.
     *
     * If the database is empty, it return a response with the following code HTTP : 204.
     * In other case, it return all label records present on database.
     *
     * @return
     *  A list of all label records present on persistent system or an error HTTP : NO_CONTENT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/label-records/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<LabelRecords> labelRecordss = labelRecordsRepository.findAll();
        if (labelRecordss.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<LabelRecords>>(labelRecordss, HttpStatus.OK);
    }

    /**
     * Get a label records by his name.
     *
     * This method return an instance of label records on the response body and the code HTTP 200 only if the label records was found.
     * In the other case, the method return a response with the HTTP code 204.
     * The name of the label records must passed on the url after the last "/ and encoded in UTF-8 to decoded on the search method.
     *
     * @param nameEncoded
     *  Name of the label records encoding in UTF8.
     * @return
     *  A ResponseEntity with the label records found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/search/label-records/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getLabelRecordsByName(@PathVariable(value = "name") String nameEncoded) throws UnsupportedEncodingException {
        String name = URLDecoder.decode(nameEncoded, LabelRecordsController.ENCODING);
        logger.info("Fetching LabelRecords named {}", name);
        LabelRecords labelRecords = labelRecordsRepository.findByName(name);
        if (labelRecords == null) {
            logger.error("LabelRecords named {} not found on Database", name);
            return new ResponseEntity<Object>(new LabelRecordsException("LabelRecords named " + name + "  not found on Database"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<LabelRecords>(labelRecords, HttpStatus.OK);
    }
}
