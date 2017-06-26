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
package fr.nicolasgille.medialibrary.controllers.video;

import fr.nicolasgille.medialibrary.exceptions.video.AnimeException;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.video.Anime;
import fr.nicolasgille.medialibrary.repositories.common.person.ActorRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.DirectorRepository;
import fr.nicolasgille.medialibrary.repositories.common.person.ProducerRepository;
import fr.nicolasgille.medialibrary.repositories.video.AnimeRepository;
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
 * Controller of the app to interact with animes present on Media-Library.
 * You can use CRUD method to insert, delete, update or select animes from Database.
 * So, many methods about research are available on the controller to search animes with different way of search.
 * You can add you own method of research if you would have a new research type of animes.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.1
 */
@RestController
@RequestMapping(value = "/media-library", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimeController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Repository used to interact with animes present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private AnimeRepository animesRepository;

    /**
     * Repository used to interact with actors present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private ActorRepository actorRepository;

    /**
     * Repository used to interact with producers present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private ProducerRepository producerRepository;

    /**
     * Repository used to interact with directors present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private DirectorRepository directorRepository;

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(AnimeController.class);


    /**
     * Return all animes found on Database.
     *
     * This method return a ResponseEntity object who contains a list of animes found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return
     *  A ResponseEntity with all animes found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/animes/", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<Anime> animes = animesRepository.findAll();
        if (animes.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Anime>>(animes, HttpStatus.OK);
    }

    /**
     * Return a animes by his title and his current season.
     *
     * This method return a ResponseEntity with the animes retrieve from the Database.
     * If the database doesn't get the animes, this method return an HTTP error : 204.
     * In other case, this method return the animes found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the animes at research and the current season of the animes.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used on search process.
     *
     * @param titleEncoded
     *  Title of the animes encoded to search on Database.
     * @param currentSeason
     *  Current season of the animes.
     * @return
     *  A ResponseEntity with the animes found on Database, or an error HTTP 204 : No Content.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/animes/search/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getAnimeByTitleAndCurrentSeason (
            @PathVariable(value = "title") String titleEncoded,
            @PathVariable(value = "currentSeason") int currentSeason) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, AnimeController.ENCODING);
        logger.info("Fetching Anime with title {}", title);
        Anime animes = animesRepository.findByTitleAndCurrentSeason(title, currentSeason);
        if (animes == null) {
            logger.error("Anime with title {} not found.", title);
            return new ResponseEntity<Object>(new AnimeException("Anime with title " + title + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Anime>(animes, HttpStatus.OK);
    }

    /**
     * Return a animes by his identifier.
     *
     * This method return a ResponseEntity with the animes retrieve from the Database.
     * If the database doesn't get the animes, this method return an HTTP error : 204.
     * In other case, this method return the animes found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the identifier of the animes at research.
     *
     * @param id
     *  Identifier of the Anime on Database.
     * @return
     *  A ResponseEntity with the animes found on Database, or an error HTTP 204 : No Content.
     * @since 1.1
     * @version 1.0
     */
    @RequestMapping(value = "/animes/search/id/{id}")
    public ResponseEntity<?> getAnimeById(@PathVariable(value = "id") long id) {
        logger.info("Fetching Anime with id {}", id);
        Anime animes = animesRepository.findOne(id);
        if (animes == null) {
            logger.error("Anime with id {} not found.", id);
            return new ResponseEntity<Object>(new AnimeException("Anime with id " + id + " not found."), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Anime>(animes, HttpStatus.OK);
    }

    /**
     * Add a animes on the Database.
     *
     * Before added the animes on database, it check if the animes is already present on the database.
     * So, if the animes is present, the method return an error HTTP 409 : CONFLICT.
     * In other case, it return the code HTTP 200 and an uri to get information about the new animes insert.
     * So, this method is call by POST method and take the animes at insert on the BODY request.
     *
     * @param animes
     *  Anime at insert on Database.
     * @param uriBuilder
     *  UrlComponentsBuilder use to redirect user on animes page.
     * @return
     *  A ResponseEntity with the animes added, or an error HTTP 409 : CONFLICT.
     * @since 1.0
     * @version 1.1
     */
    @RequestMapping(value = "/animes/", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Anime animes, UriComponentsBuilder uriBuilder) {
        logger.info("Created animes : {}", animes);

        // Check if the animes already exist on database.
        Anime animesExist = animesRepository.findByTitleAndCurrentSeason(animes.getTitle(), animes.getCurrentSeason());
        if (animesExist != null) {
            logger.error("Unable to create. The animes {} already exist", animes.getTitle());
            return new ResponseEntity<AnimeException>(new AnimeException("Unable to create. The animes " + animes.getTitle() + " already exist"), HttpStatus.CONFLICT);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnAnime = animes.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnAnime) {
            Producer producerExist = producerRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
            // If the producer is not present on Database, he add on it.
            if (producerExist == null) {
                logger.info("Created producer : {}", p);
                producerRepository.save(p);
                producers.add(p);
            } else {
                logger.info("Added producer {} already present on persistent system.", producerExist);
                producers.add(producerExist);
            }
        }
        animes.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnAnime = animes.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnAnime) {
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist  == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist );
                directors.add(directorExist);
            }
        }
        animes.setDirectors(directors);
        animesRepository.save(animes);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/animes/search/id/{id}").buildAndExpand(animes.getId()).toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update a animes present on the Database.
     *
     * It update a animes only if found on database.
     * It the animes is not found, the method return an error with the HTTP code 404.
     * In other case, it update the information about the animes and return in the body the animes update
     * can use to check if the modification are succeeded and the HTTP code 200.
     *
     * @param id
     *  Id of the animes on Database.
     * @param animes
     *  Anime with new content at update.
     * @return
     *  A ResponseEntity with all animes found on Database, or an error HTTP 404 : NOT FOUND.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/animes/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Anime animes) {
        logger.info("Updating Anime with id {}", id);

        Anime animesAtUpdate = animesRepository.findOne(id);
        if (animesAtUpdate == null) {
            logger.error("Unable to update. Anime with id {} not found", id);
            return new ResponseEntity<Object>(new AnimeException("Unable to update. Anime with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnAnime = animes.getProducers();
        Set<Producer> producers = new HashSet<Producer>();
        for (Producer p : producersOnAnime) {
            Producer producerExist = producerRepository.findByFirstNameAndLastName(p.getFirstName(), p.getLastName());
            // If the producer is not present on Database, he add on it.
            if (producerExist == null) {
                logger.info("Created producer : {}", p);
                producerRepository.save(p);
                producers.add(p);
            } else {
                logger.info("Added producer {} already present on persistent system.", producerExist);
                producers.add(producerExist);
            }
        }
        animes.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnAnime = animes.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnAnime) {
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist  == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist );
                directors.add(directorExist);
            }
        }
        animes.setDirectors(directors);

        // Copy content of the animes receive on request body on the animes retrieve from the database.
        animesAtUpdate = new Anime(animes);
        animesRepository.save(animesAtUpdate);
        return new ResponseEntity<Object>(animesAtUpdate, HttpStatus.OK);
    }

    /**
     * Remove a animes from the Database.
     *
     * It remove a animes if it found on database.
     * If the animes is not found on database, this method return an error and the HTTP code 404.
     * Otherwise, the method delete the animes thanks to the identifier and return in the body the animes deleted
     * and the code HTTP 200 to confirm the success of the deletion.
     *
     * @param id
     *  Id of the animes at delete.
     * @return
     *  A ResponseEntity with all animes found on Database, or an error HTTP 404 : NOT_FOUND.
     * @since 1.0
     * @version 2.0
     */
    @RequestMapping(value = "/animes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Anime with id {}", id);

        Anime animes = animesRepository.findOne(id);
        if (animes == null) {
            logger.error("Unable to delete. Anime with id {} not found", id);
            return new ResponseEntity<Object>(new AnimeException("Unable to delete. Anime with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        animesRepository.delete(animes);
        return new ResponseEntity<Object>(animes, HttpStatus.OK);
    }
}
