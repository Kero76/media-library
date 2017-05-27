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
package fr.nicolasgille.medialibrary.controllers.music;

import fr.nicolasgille.medialibrary.daos.common.company.LabelRecordsRepository;
import fr.nicolasgille.medialibrary.daos.common.person.SingerRepository;
import fr.nicolasgille.medialibrary.daos.music.AlbumRepository;
import fr.nicolasgille.medialibrary.exception.music.AlbumException;
import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import fr.nicolasgille.medialibrary.models.common.person.Singer;
import fr.nicolasgille.medialibrary.models.music.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller of the app to interact with music album present on Media-Library.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlbumController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * DAO used to interact with the table <code>media</code> present on Database.
     *
     * @since 1.0
     */
    @Autowired
    private AlbumRepository albumRepository;

    /**
     * DAO used to interact with the Label Records present on media-library.
     *
     * @since 1.0
     */
    @Autowired
    private LabelRecordsRepository labelRecordsRepository;

    /**
     * DAO used to interact with the Singer present on media-library.
     *
     * @since 1.0
     */
    @Autowired
    private SingerRepository singerRepository;

    /**
     * Logger for debugging app.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    /**
     * Return all albums found on Database.
     *
     * This method return a ResponseEntity object who contains a list of albums found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all albums found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/music/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Album> albums = albumRepository.findAll();
        if (albums.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Album>>(albums, HttpStatus.OK);
    }

    /**
     * Return a album by his title.
     *
     * This method return a ResponseEntity with the album retrieve from the Database.
     * If the database research don't retrieve the album, this method return an HTTP error.
     * This method can call by GET request and take an path variable the title of the album at research.
     * So, the title retrieve from the URL is encoded and it necessary to decoded it before search album on Database.
     *
     * @param titleEncoded
     *  Title of the album encoded to search on Database.
     * @return
     *  A ResponseEntity with the album found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/music/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getAlbumByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, AlbumController.ENCODING);
        logger.info("Fetching Album with title {}", title);
        Album album = albumRepository.findByTitleIgnoreCase(title);
        if (album == null) {
            logger.error("Album with title {} not found.", title);
            return new ResponseEntity<Object>(new AlbumException("Album with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Album>(album, HttpStatus.OK);
    }

    /**
     * Add a album on the Database.
     *
     * Before added the album on database, it check if the album is already present on the database.
     * And if the album is present, the method return an error HTTP 409 : CONFLICT.
     * This method can call only by a POST request and take on BODY the album at insert on Database.
     *
     * @param album
     *  Album at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on album page.
     * @return
     *  A ResponseEntity with the album added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/music/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Album album, UriComponentsBuilder uriBuilder) {
        logger.info("Created album : {}", album);

        // Check if the album already exist on database.
        Album albumExist = albumRepository.findByTitleIgnoreCase(album.getTitle());
        if (albumExist != null) {
            logger.error("Unable to create. The album {} already exist", album.getTitle());
            return new ResponseEntity<AlbumException>(new AlbumException("Unable to create. The album " + album.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the developer are present on Database or not.
        Set<LabelRecords> developersOnAlbum = album.getLabelRecords();
        Set<LabelRecords> developers = new HashSet<LabelRecords>();
        for (LabelRecords d : developersOnAlbum) {
            LabelRecords developerExist = labelRecordsRepository.findByName(d.getName());
            // If the developer is not present on Database, it add on it.
            if (developerExist == null) {
                logger.info("Created developer : {}", d);
                labelRecordsRepository.save(d);
                developers.add(d);
            } else {
                logger.info("Added developer {} already present on persistent system", developerExist);
                developers.add(developerExist);
            }
        }
        album.setLabelRecords(developers);

        // Check if the singer are present on Database or not.
        Set<Singer> singersOnComic = album.getSingers();
        Set<Singer> singers = new HashSet<Singer>();
        for (Singer s : singersOnComic) {
            Singer singerExist = singerRepository.findByFirstNameAndLastName(s.getFirstName(), s.getLastName());
            // If the singer is not present on Database, it add on it.
            if (singerExist == null) {
                logger.info("Created singer : {}", s);
                singerRepository.save(s);
                singers.add(s);
            } else {
                logger.info("Added singer {} already present on persistent system", singerExist);
                singers.add(singerExist);
            }
        }
        album.setSingers(singers);
        albumRepository.save(album);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/music/search/title/{id}").buildAndExpand(album.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a album present on the Database.
     *
     * This method update a album present on database only if this album is present on it.
     * In other case, this method return a HTTP error 404 : Not Found.
     * This method can call only by PUT method and take the id of the album at update on path variable
     * and the object album with the new content on BODY.
     *
     * @param id
     *  Id of the album on Database.
     * @param album
     *  Album with new content at update.
     * @return
     *  A ResponseEntity with all albums found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/music/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Album album) {
        logger.info("Updating Album with id {}", id);

        Album albumAtUpdate = albumRepository.findOne(id);
        if (albumAtUpdate == null) {
            logger.error("Unable to update. Album with id {} not found", id);
            return new ResponseEntity<Object>(new AlbumException("Unable to update. Album with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the developer are present on Database or not.
        Set<LabelRecords> developersOnAlbum = album.getLabelRecords();
        Set<LabelRecords> developers = new HashSet<LabelRecords>();
        for (LabelRecords d : developersOnAlbum) {
            LabelRecords developerExist = labelRecordsRepository.findByName(d.getName());
            // If the developer is not present on Database, it add on it.
            if (developerExist == null) {
                logger.info("Created developer : {}", d);
                labelRecordsRepository.save(d);
                developers.add(d);
            } else {
                logger.info("Added developer {} already present on persistent system", developerExist);
                developers.add(developerExist);
            }
        }
        album.setLabelRecords(developers);

        // Check if the singer are present on Database or not.
        Set<Singer> singersOnComic = album.getSingers();
        Set<Singer> singers = new HashSet<Singer>();
        for (Singer s : singersOnComic) {
            Singer singerExist = singerRepository.findByFirstNameAndLastName(s.getFirstName(), s.getLastName());
            // If the singer is not present on Database, it add on it.
            if (singerExist == null) {
                logger.info("Created singer : {}", s);
                singerRepository.save(s);
                singers.add(s);
            } else {
                logger.info("Added singer {} already present on persistent system", singerExist);
                singers.add(singerExist);
            }
        }
        album.setSingers(singers);

        // Copy content of the album receive on request body on the album retrieve from the database.
        albumAtUpdate = new Album(album);
        albumRepository.save(albumAtUpdate);
        return new ResponseEntity<Object>(albumAtUpdate, HttpStatus.OK);
    }

    /**
     * Removed a album from the Database.
     *
     * This method remove a album from the database only if the album is present on the Database.
     * It return an error HTTP 404 : NOT FOUND if the album at deleted isn't present on the database.
     * To call this method, you can pass on the url the id of the album at remove
     * and this method can call only with DELETE request.
     *
     * @param id
     *  Id of the album at delete.
     * @return
     *  A ResponseEntity with all albums found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/music/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Album with id {}", id);

        Album album = albumRepository.findOne(id);
        if (album == null) {
            logger.error("Unable to delete. Album with id {} not found", id);
            return new ResponseEntity<Object>(new AlbumException("Unable to delete. Album with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        albumRepository.delete(album);
        return new ResponseEntity<Object>(album, HttpStatus.OK);
    }
}
