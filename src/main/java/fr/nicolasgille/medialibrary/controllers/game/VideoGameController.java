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

import fr.nicolasgille.medialibrary.exceptions.game.VideoGameException;
import fr.nicolasgille.medialibrary.models.common.company.Developer;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.game.VideoGame;
import fr.nicolasgille.medialibrary.repositories.common.company.DeveloperRepository;
import fr.nicolasgille.medialibrary.repositories.common.company.PublisherRepository;
import fr.nicolasgille.medialibrary.repositories.game.VideoGameRepository;
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
 * Controller of the app to interact with video games present on Media-Library.
 * You can use CRUD method to insert, delete, update or select video game from Database.
 * So, many methods about research are available on the controller to search video game with different way of search.
 * You can add you own method of research if you would have a new research type of video game.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.1
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
     * Repository used to interact with video game present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private VideoGameRepository videoGameRepository;

    /**
     * Repository used to interact with developers present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private DeveloperRepository developerRepository;

    /**
     * Repository used to interact with publishers present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private PublisherRepository publisherRepository;

    /**
     * Logger to get information during some process.
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
     * Return a video game by his title.
     *
     * This method return a ResponseEntity with the video game retrieve from the Database.
     * If the database doesn't get the video game, this method return an HTTP error : 204.
     * In other case, this method return the video game found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the video game at research.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used on search process.
     *
     * @param titleEncoded
     *  Title of the videoGame encoded to search on Database.
     * @return
     *  A ResponseEntity with the videoGame found on Database, or an error HTTP 204 : No Content.
     * @throws UnsupportedEncodingException
     *  The method throw an <code>UnsupportedEncodingException</code> when a problem occurred during title decoding.
     * @since 1.0
     * @version 1.0.1
     */
    @RequestMapping(value = "/video-games/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getVideoGameByTitle(@PathVariable(value = "title") String titleEncoded) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, VideoGameController.ENCODING);
        logger.info("Fetching VideoGame with title {}", title);
        List<VideoGame> videoGames = videoGameRepository.findByTitleIgnoreCase(title);
        if (videoGames == null) {
            logger.error("VideoGame with title {} not found.", title);
            return new ResponseEntity<Object>(new VideoGameException("VideoGame with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<VideoGame>>(videoGames, HttpStatus.OK);
    }

    /**
     * Return a video game by his identifier.
     *
     * This method return a ResponseEntity with the video game retrieve from the Database.
     * If the database doesn't get the video game, this method return an HTTP error : 204.
     * In other case, this method return the video game found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the identifier of the video game at research.
     *
     * @param id
     *  Identifier of the Series on Database.
     * @return
     *  A ResponseEntity with the video game found on Database, or an error HTTP 204 : No Content.
     * @since 1.1
     * @version 1.0
     */
    @RequestMapping(value = "/video-games/search/id/{id}")
    public ResponseEntity<?> getVideoGameById(@PathVariable(value = "id") long id) {
        logger.info("Fetching Video Game with id {}", id);
        VideoGame videoGame = videoGameRepository.findOne(id);
        if (videoGame == null) {
            logger.error("Video Game with id {} not found.", id);
            return new ResponseEntity<Object>(new VideoGameException("Video Game with id " + id + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<VideoGame>(videoGame, HttpStatus.OK);
    }

    /**
     * Add a videoGame on the Database.
     *
     * Before added the video game on database, it check if the video game is already present on the database.
     * So, if the video game is present, the method return an error HTTP 409 : CONFLICT.
     * In other case, it return the code HTTP 200 and an uri to get information about the new video game insert.
     * So, this method is call by POST method and take the video game at insert on the BODY request.
     *
     * @param videoGame
     *  VideoGame at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on videoGame page.
     * @return
     *  A ResponseEntity with the videoGame added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.0.1
     */
    @RequestMapping(value = "/video-games/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody VideoGame videoGame, UriComponentsBuilder uriBuilder) {
        logger.info("Created videoGame : {}", videoGame);

        // Check if the videoGame already exist on database.
        VideoGame videoGameExist = videoGameRepository.findByTitleIgnoreCaseAndReleaseDate(videoGame.getTitle(), videoGame.getReleaseDate());
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

        // Check if the publisher are present on Database or not.
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
        header.setLocation(uriBuilder.path("/media-library/video-games/search/id/{id}").buildAndExpand(videoGame.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a video game present on the Database.
     *
     * It update a video game only if found on database.
     * It the video game is not found, the method return an error with the HTTP code 404.
     * In other case, it update the information about the video game and return in the body the video game update
     * can use to check if the modification are succeeded and the HTTP code 200.
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

        // Check if the publisher are present on Database or not.
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
     * Remove a video game from the Database.
     *
     * It remove a video game if it found on database.
     * If the video game is not found on database, this method return an error and the HTTP code 404.
     * Otherwise, the method delete the video game thanks to the identifier and return in the body the video game deleted
     * and the code HTTP 200 to confirm the success of the deletion.
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
