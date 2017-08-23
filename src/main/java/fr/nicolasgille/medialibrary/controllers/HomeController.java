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
package fr.nicolasgille.medialibrary.controllers;

import fr.nicolasgille.medialibrary.controllers.video.AnimeController;
import fr.nicolasgille.medialibrary.models.Media;
import fr.nicolasgille.medialibrary.models.components.BookFormat;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.VideoGamePlatform;
import fr.nicolasgille.medialibrary.repositories.book.BookRepository;
import fr.nicolasgille.medialibrary.repositories.book.ComicRepository;
import fr.nicolasgille.medialibrary.repositories.game.VideoGameRepository;
import fr.nicolasgille.medialibrary.repositories.music.AlbumRepository;
import fr.nicolasgille.medialibrary.repositories.video.AnimeRepository;
import fr.nicolasgille.medialibrary.repositories.video.CartoonRepository;
import fr.nicolasgille.medialibrary.repositories.video.MovieRepository;
import fr.nicolasgille.medialibrary.repositories.video.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HomeController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.1
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeController {

    /**
     * Constant used to specified URL encoding.
     *
     * @since 1.0
     */
    private final static String ENCODING = "UTF-8";

    /**
     * @since 1.0
     */
    @Autowired
    private AnimeRepository animesRepository;

    /**
     * @since 1.0
     */
    @Autowired
    private CartoonRepository cartoonsRepository;

    /**
     * @since 1.0
     */
    @Autowired
    private MovieRepository moviesRepository;

    /**
     * @since 1.0
     */
    @Autowired
    private SeriesRepository seriesRepository;

    /**
     * @since 1.0
     */
    @Autowired
    private BookRepository booksRepository;

    /**
     * @since 1.0
     */
    @Autowired
    private ComicRepository comicsRepository;

    /**
     * @since 1.0
     */
    @Autowired
    private VideoGameRepository videoGamesRepository;

    /**
     * @since 1.0
     */
    @Autowired
    private AlbumRepository albumsRepository;

    /**
     * Logger to get information during some process.
     *
     * @since 1.0
     */
    static final Logger logger = LoggerFactory.getLogger(AnimeController.class);

    /**
     * Return home page with all media present on Library.
     *
     * @return
     *  Return an instance of ResponseEntity who contains
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/home/", method = RequestMethod.GET)
    public ResponseEntity<?> getHomePage() {
        // Instantiate a Map with key as name of the media type and value as List of specific media.
        Map<String, List> homeContent = new HashMap<>();
        String[] mediaName = {
            "animes",
            "cartoons",
            "movies",
            "series",
            "books",
            "comics",
            "musics",
            "video-games",
        };

        // Loop on each media type and get all media.
        List<?> media = null;
        for (int i = 0; i < mediaName.length; ++i) {
            media = new ArrayList<>();
            switch (i) {
                case 0 :
                    media = animesRepository.findAll();
                    break;

                case 1 :
                    media = cartoonsRepository.findAll();
                    break;

                case 2 :
                    media = moviesRepository.findAll();
                    break;

                case 3 :
                    media = seriesRepository.findAll();
                    break;

                case 4 :
                    media = booksRepository.findAll();
                    break;

                case 5 :
                    media = comicsRepository.findAll();
                    break;

                case 6 :
                    media = albumsRepository.findAll();
                    break;

                case 7 :
                    media = videoGamesRepository.findAll();
                    break;
            }

            if (media.size() > 10) {
                homeContent.put(mediaName[i], media.subList(media.size() - 10, media.size()));
            } else {
                homeContent.put(mediaName[i], media.subList(0, media.size()));
            }

        }

        return new ResponseEntity<Map>(homeContent, HttpStatus.OK);
    }

    /**
     * Get all book format present on Media Library.
     *
     * @return
     *  An array with all book format.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/books/formats/", method = RequestMethod.GET)
    public ResponseEntity<?> getBookFormat() {
        return new ResponseEntity<>(BookFormat.values(), HttpStatus.OK);
    }

    /**
     * Get all media genres present on Media Library.
     *
     * @return
     *  An array with all media genres.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/media/genres/", method = RequestMethod.GET)
    public ResponseEntity<?> getMediaGenre() {
        return new ResponseEntity<>(MediaGenre.values(), HttpStatus.OK);
    }

    /**
     * Get all media support present on Media-Library.
     *
     * @return
     *  An array with all media supports.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/media/supports/", method = RequestMethod.GET)
    public ResponseEntity<?> getMediaSupport() {
        return new ResponseEntity<>(MediaSupport.values(), HttpStatus.OK);
    }

    /**
     * Get all video games platform.
     *
     * @return
     *  An array with all video game platform.
     * @since 1.0
     * @version 1.0
     */
    @RequestMapping(value = "/video-games/platforms/", method = RequestMethod.GET)
    public ResponseEntity<?> getVideoGamePlatform() {
        return new ResponseEntity<>(VideoGamePlatform.values(), HttpStatus.OK);
    }
}
