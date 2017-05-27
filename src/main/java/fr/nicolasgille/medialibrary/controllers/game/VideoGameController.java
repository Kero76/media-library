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
package fr.nicolasgille.medialibrary.controllers.game;

import fr.nicolasgille.medialibrary.daos.common.company.DeveloperRepository;
import fr.nicolasgille.medialibrary.daos.common.company.PublisherRepository;
import fr.nicolasgille.medialibrary.daos.game.VideoGameRepository;
import fr.nicolasgille.medialibrary.exception.game.VideoGameException;
import fr.nicolasgille.medialibrary.models.common.company.Developer;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.game.VideoGame;
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
 * Controller of the app to interact with video game present on Media-Library.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoGameController {

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
    private VideoGameRepository videoGameRepository;

    /**
     * DAO used to interact with the Label Records present on media-library.
     *
     * @since 1.0
     */
    @Autowired
    private DeveloperRepository developerRepository;

    /**
     * DAO used to interact with the Singer present on media-library.
     *
     * @since 1.0
     */
    @Autowired
    private PublisherRepository publisherRepository;

    /**
     * Logger for debugging app.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(VideoGameController.class);

    /**
     * Return all videoGames found on Database.
     *
     * This method return a ResponseEntity object who contains a list of videoGames found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all videoGames found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/video-games/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<VideoGame> videoGames = videoGameRepository.findAll();
        if (videoGames.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<VideoGame>>(videoGames, HttpStatus.OK);
    }

    /**
     * Return a videoGame by his title.
     *
     * This method return a ResponseEntity with the videoGame retrieve from the Database.
     * If the database research don't retrieve the videoGame, this method return an HTTP error.
     * This method can call by GET request and take an path variable the title of the videoGame at research.
     * So, the title retrieve from the URL is encoded and it necessary to decoded it before search videoGame on Database.
     *
     * @param titleEncoded
     *  Title of the videoGame encoded to search on Database.
     * @return
     *  A ResponseEntity with the videoGame found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/video-games/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getVideoGameByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, VideoGameController.ENCODING);
        logger.info("Fetching VideoGame with title {}", title);
        VideoGame videoGame = videoGameRepository.findByTitleIgnoreCase(title);
        if (videoGame == null) {
            logger.error("VideoGame with title {} not found.", title);
            return new ResponseEntity<Object>(new VideoGameException("VideoGame with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<VideoGame>(videoGame, HttpStatus.OK);
    }

    /**
     * Add a videoGame on the Database.
     *
     * Before added the videoGame on database, it check if the videoGame is already present on the database.
     * And if the videoGame is present, the method return an error HTTP 409 : CONFLICT.
     * This method can call only by a POST request and take on BODY the videoGame at insert on Database.
     *
     * @param videoGame
     *  VideoGame at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on videoGame page.
     * @return
     *  A ResponseEntity with the videoGame added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/video-games/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody VideoGame videoGame, UriComponentsBuilder uriBuilder) {
        logger.info("Created videoGame : {}", videoGame);

        // Check if the videoGame already exist on database.
        VideoGame videoGameExist = videoGameRepository.findByTitleIgnoreCase(videoGame.getTitle());
        if (videoGameExist != null) {
            logger.error("Unable to create. The videoGame {} already exist", videoGame.getTitle());
            return new ResponseEntity<VideoGameException>(new VideoGameException("Unable to create. The videoGame " + videoGame.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the developer are present on Database or not.
        Set<Developer> developersOnVideoGame = videoGame.getDevelopers();
        Set<Developer> developers = new HashSet<Developer>();
        for (Developer d : developersOnVideoGame) {
            Developer developerExist = developerRepository.findByName(d.getName());
            // If the developer is not present on Database, it add on it.
            if (developerExist == null) {
                logger.info("Created developer : {}", d);
                developerRepository.save(d);
                developers.add(d);
            } else {
                logger.info("Added developer {} already present on persistent system", developerExist);
                developers.add(developerExist);
            }
        }
        videoGame.setDevelopers(developers);

        // Check if the illustrator are present on Database or not.
        Set<Publisher> publishersOnVideoGame = videoGame.getPublishers();
        Set<Publisher> publishers = new HashSet<Publisher>();
        for (Publisher d : publishersOnVideoGame) {
            Publisher publisherExist = publisherRepository.findByName(d.getName());
            // If the publisher is not present on Database, it add on it.
            if (publisherExist == null) {
                logger.info("Created publisher : {}", d);
                publisherRepository.save(d);
                publishers.add(d);
            } else {
                logger.info("Added publisher {} already present on persistent system", publisherExist);
                publishers.add(publisherExist);
            }
        }
        videoGame.setPublishers(publishers);
        videoGameRepository.save(videoGame);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/videoGames/search/title/{id}").buildAndExpand(videoGame.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a videoGame present on the Database.
     *
     * This method update a videoGame present on database only if this videoGame is present on it.
     * In other case, this method return a HTTP error 404 : Not Found.
     * This method can call only by PUT method and take the id of the videoGame at update on path variable
     * and the object videoGame with the new content on BODY.
     *
     * @param id
     *  Id of the videoGame on Database.
     * @param videoGame
     *  VideoGame with new content at update.
     * @return
     *  A ResponseEntity with all videoGames found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/video-games/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody VideoGame videoGame) {
        logger.info("Updating VideoGame with id {}", id);

        VideoGame videoGameAtUpdate = videoGameRepository.findOne(id);
        if (videoGameAtUpdate == null) {
            logger.error("Unable to update. VideoGame with id {} not found", id);
            return new ResponseEntity<Object>(new VideoGameException("Unable to update. VideoGame with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the developer are present on Database or not.
        Set<Developer> developersOnVideoGame = videoGame.getDevelopers();
        Set<Developer> developers = new HashSet<Developer>();
        for (Developer d : developersOnVideoGame) {
            Developer developerExist = developerRepository.findByName(d.getName());
            // If the developer is not present on Database, it add on it.
            if (developerExist == null) {
                logger.info("Created developer : {}", d);
                developerRepository.save(d);
                developers.add(d);
            } else {
                logger.info("Added developer {} already present on persistent system", developerExist);
                developers.add(developerExist);
            }
        }
        videoGame.setDevelopers(developers);

        // Check if the illustrator are present on Database or not.
        Set<Publisher> publishersOnVideoGame = videoGame.getPublishers();
        Set<Publisher> publishers = new HashSet<Publisher>();
        for (Publisher d : publishersOnVideoGame) {
            Publisher publisherExist = publisherRepository.findByName(d.getName());
            // If the publisher is not present on Database, it add on it.
            if (publisherExist == null) {
                logger.info("Created publisher : {}", d);
                publisherRepository.save(d);
                publishers.add(d);
            } else {
                logger.info("Added publisher {} already present on persistent system", publisherExist);
                publishers.add(publisherExist);
            }
        }
        videoGame.setPublishers(publishers);

        // Copy content of the videoGame receive on request body on the videoGame retrieve from the database.
        videoGameAtUpdate = new VideoGame(videoGame);
        videoGameRepository.save(videoGameAtUpdate);
        return new ResponseEntity<Object>(videoGameAtUpdate, HttpStatus.OK);
    }

    /**
     * Removed a videoGame from the Database.
     *
     * This method remove a videoGame from the database only if the videoGame is present on the Database.
     * It return an error HTTP 404 : NOT FOUND if the videoGame at deleted isn't present on the database.
     * To call this method, you can pass on the url the id of the videoGame at remove
     * and this method can call only with DELETE request.
     *
     * @param id
     *  Id of the videoGame at delete.
     * @return
     *  A ResponseEntity with all videoGames found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/video-games/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting VideoGame with id {}", id);

        VideoGame videoGame = videoGameRepository.findOne(id);
        if (videoGame == null) {
            logger.error("Unable to delete. VideoGame with id {} not found", id);
            return new ResponseEntity<Object>(new VideoGameException("Unable to delete. VideoGame with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        videoGameRepository.delete(videoGame);
        return new ResponseEntity<Object>(videoGame, HttpStatus.OK);
    }
}
