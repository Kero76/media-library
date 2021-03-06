/*
 * MediaLibrary.
 * Copyright (C) 2017 Nicolas GILLE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.nicolasgille.medialibrary.controllers.video;

import fr.nicolasgille.medialibrary.exceptions.video.AnimeException;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.Anime;
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
 * Controller of the app to interact with anime present on Media-Library.
 * You can use CRUD method to insert, delete, update or select anime from Database.
 * So, many methods about research are available on the controller to search anime with different way of search.
 * You can add you own method of research if you would have a new research type of anime.
 *
 * @author Nicolas GILLE
 * @version 1.2
 * @since Media-Library 0.2
 */
@RestController
@RequestMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class AnimeController {

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(AnimeController.class);

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Repository used to interact with anime present on the service.
     *
     * @since 1.0
     */
    @Autowired
    private AnimeRepository animesRepository;

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
     * Return all animes found on Database.
     * <p>
     * This method return a ResponseEntity object who contains a list of animes found on the Database.
     * If the database is empty, this method return an error HTTP 204 : No Content.
     * This method can call only by GET request and take nothing parameter to work.
     *
     * @return A ResponseEntity with all animes found on Database, or an error HTTP 204 : No Content.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/animes/",
                    method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<Anime> animes = animesRepository.findAll();
        if (animes.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Anime>>(animes, HttpStatus.OK);
    }

    /**
     * Return all animes with his title.
     * <p>
     * This method return a ResponseEntity with the anime retrieve from the Database.
     * If the database doesn't get the anime, this method return an HTTP error : 204.
     * In other case, this method return the anime found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the anime at research.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used
     * on search process.
     *
     * @param titleEncoded Title of the animes encoded to search on Database.
     *
     * @return A ResponseEntity with the anime found on Database, or an error HTTP 204 : No Content.
     *
     * @throws UnsupportedEncodingException The method throw an <code>UnsupportedEncodingException</code> when a
     *         problem occurred during title decoding.
     * @version 1.0
     * @since 1.2
     */
    @RequestMapping(value = "/animes/search/title/{title}",
                    method = RequestMethod.GET)
    public ResponseEntity<?> getAnimesByTitle(@PathVariable(value = "title") String titleEncoded)
            throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, AnimeController.ENCODING);
        logger.info("Fetching Anime with title {}", title);
        List<Anime> animes = animesRepository.findByTitleIgnoreCaseContaining(title);
        if (animes == null) {
            logger.error("Anime with title {} not found.", title);
            return new ResponseEntity<Object>(new AnimeException("Anime with title " + title + " not found."),
                                              HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Anime>>(animes, HttpStatus.OK);
    }

    /**
     * Return an anime by his title and his current season.
     * <p>
     * This method return a ResponseEntity with the anime retrieve from the Database.
     * If the database doesn't get the anime, this method return an HTTP error : 204.
     * In other case, this method return the anime found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the title of the anime at research and the current season of the anime.
     * The title is encoded in <code>UTF8</code> to avoid problems with specials characters and it decoded before used
     * on search process.
     *
     * @param titleEncoded Title of the animes encoded to search on Database.
     * @param currentSeason Current season of the anime.
     *
     * @return A ResponseEntity with the anime found on Database, or an error HTTP 204 : No Content.
     *
     * @throws UnsupportedEncodingException The method throw an <code>UnsupportedEncodingException</code> when a
     *         problem occurred during title decoding.
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/animes/search/title/{title}/{currentSeason}",
                    method = RequestMethod.GET)
    public ResponseEntity<?> getAnimeByTitleAndCurrentSeason(
            @PathVariable(value = "title") String titleEncoded,
            @PathVariable(value = "currentSeason") int currentSeason) throws UnsupportedEncodingException {
        String title = URLDecoder.decode(titleEncoded, AnimeController.ENCODING);
        logger.info("Fetching Anime with title {} and current season {}", title, currentSeason);
        Anime anime = animesRepository.findByTitleAndCurrentSeason(title, currentSeason);
        if (anime == null) {
            logger.error("Anime with title {} not found.", title);
            return new ResponseEntity<Object>(new AnimeException("Anime with title " + title + " not found."),
                                              HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Anime>(anime, HttpStatus.OK);
    }

    /**
     * Return an anime by his identifier.
     * <p>
     * This method return a ResponseEntity with the anime retrieve from the Database.
     * If the database doesn't get the anime, this method return an HTTP error : 204.
     * In other case, this method return the anime found in body response and the success code HTTP 200.
     * This method is call only by the method HTTP <em>GET</em>, and it's necessary to passed on
     * parameter the identifier of the anime at research.
     *
     * @param id Identifier of the Anime on Database.
     *
     * @return A ResponseEntity with the anime found on Database, or an error HTTP 204 : No Content.
     *
     * @version 1.0
     * @since 1.1
     */
    @RequestMapping(value = "/animes/search/id/{id}")
    public ResponseEntity<?> getAnimeById(@PathVariable(value = "id") long id) {
        logger.info("Fetching Anime with id {}", id);
        Anime anime = animesRepository.findOne(id);
        if (anime == null) {
            logger.error("Anime with id {} not found.", id);
            return new ResponseEntity<Object>(new AnimeException("Anime with id " + id + " not found."),
                                              HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Anime>(anime, HttpStatus.OK);
    }

    /**
     * Add an anime on the Database.
     * <p>
     * Before added the anime on database, it check if the anime is already present on the database.
     * So, if the anime is present, the method return an error HTTP 409 : CONFLICT.
     * In other case, it return the code HTTP 200 and an uri to get information about the new anime insert.
     * So, this method is call by POST method and take the anime at insert on the BODY request.
     *
     * @param anime Anime at insert on Database.
     * @param uriBuilder UrlComponentsBuilder use to redirect user on anime page.
     *
     * @return A ResponseEntity with the anime added, or an error HTTP 409 : CONFLICT.
     *
     * @version 1.1
     * @since 1.0
     */
    @RequestMapping(value = "/animes/",
                    method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Anime anime, UriComponentsBuilder uriBuilder) {
        logger.info("Created anime : {}", anime);

        // Check if the anime already exist on database.
        Anime animeExist = animesRepository.findByTitleAndCurrentSeason(anime.getTitle(), anime.getCurrentSeason());
        if (animeExist != null) {
            logger.error("Unable to create. The anime {} already exist", anime.getTitle());
            return new ResponseEntity<AnimeException>(
                    new AnimeException("Unable to create. The anime " + anime.getTitle() + " already exist"),
                    HttpStatus.CONFLICT);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnAnime = anime.getProducers();
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
        anime.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnAnime = anime.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnAnime) {
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist);
                directors.add(directorExist);
            }
        }
        anime.setDirectors(directors);
        animesRepository.save(anime);

        HttpHeaders header = new HttpHeaders();
        header.setLocation(uriBuilder.path("/media-library/animes/search/id/{id}")
                                     .buildAndExpand(anime.getId())
                                     .toUri());
        return new ResponseEntity<String>(header, HttpStatus.CREATED);
    }

    /**
     * Update an anime present on the Database.
     * <p>
     * It update an anime only if found on database.
     * It the anime is not found, the method return an error with the HTTP code 404.
     * In other case, it update the information about the anime and return in the body the anime update
     * can use to check if the modification are succeeded and the HTTP code 200.
     *
     * @param id Id of the anime on Database.
     * @param anime Anime with new content at update.
     *
     * @return A ResponseEntity with all anime found on Database, or an error HTTP 404 : NOT FOUND.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/animes/{id}",
                    method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Anime anime) {
        logger.info("Updating Anime with id {}", id);

        Anime animeAtUpdate = animesRepository.findOne(id);
        if (animeAtUpdate == null) {
            logger.error("Unable to update. Anime with id {} not found", id);
            return new ResponseEntity<Object>(
                    new AnimeException("Unable to update. Anime with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        // Check if the producers are present on Database or not.
        Set<Producer> producersOnAnime = anime.getProducers();
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
        anime.setProducers(producers);

        // Check if the directors are present on Database or not.
        Set<Director> directorOnAnime = anime.getDirectors();
        Set<Director> directors = new HashSet<Director>();
        for (Director d : directorOnAnime) {
            Director directorExist = directorRepository.findByFirstNameAndLastName(d.getFirstName(), d.getLastName());
            // If the director is not present on Database, he add on it.
            if (directorExist == null) {
                logger.info("Created director : {}", d);
                directorRepository.save(d);
                directors.add(d);
            } else {
                logger.info("Added director {} already present on persistent system.", directorExist);
                directors.add(directorExist);
            }
        }
        anime.setDirectors(directors);

        // Copy content of the anime receive on request body on the anime retrieve from the database.
        animeAtUpdate = new Anime(anime);
        animesRepository.save(animeAtUpdate);
        return new ResponseEntity<Object>(animeAtUpdate, HttpStatus.OK);
    }

    /**
     * Remove an anime from the Database.
     * <p>
     * It remove a anime if it found on database.
     * If the anime is not found on database, this method return an error and the HTTP code 404.
     * Otherwise, the method delete the anime thanks to the identifier and return in the body the anime deleted
     * and the code HTTP 200 to confirm the success of the deletion.
     *
     * @param id Id of the anime at delete.
     *
     * @return A ResponseEntity with all anime found on Database, or an error HTTP 404 : NOT_FOUND.
     *
     * @version 2.0
     * @since 1.0
     */
    @RequestMapping(value = "/animes/{id}",
                    method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        logger.info("Deleting Anime with id {}", id);

        Anime anime = animesRepository.findOne(id);
        if (anime == null) {
            logger.error("Unable to delete. Anime with id {} not found", id);
            return new ResponseEntity<Object>(
                    new AnimeException("Unable to delete. Anime with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        animesRepository.delete(anime);
        return new ResponseEntity<Object>(anime, HttpStatus.OK);
    }

    /**
     * Get all video genres present on Media Library.
     *
     * @return An array with all video genres.
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(value = "/animes/genres/",
                    method = RequestMethod.GET)
    public ResponseEntity<?> getMediaGenre() {
        return new ResponseEntity<>(VideoGenre.values(), HttpStatus.OK);
    }
}
