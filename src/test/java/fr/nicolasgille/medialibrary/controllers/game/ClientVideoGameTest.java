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

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.exceptions.game.VideoGameException;
import fr.nicolasgille.medialibrary.models.common.company.Developer;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.VideoGamePlatform;
import fr.nicolasgille.medialibrary.models.game.VideoGame;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit class test used to test VideoGameController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientVideoGameTest {

    /**
     * URI of the Rest service.
     */
    private static final String REST_SERVICE_URI = "http://localhost:8080/media-library";

    /**
     * Encoding used to encoded URL.
     */
    private static final String URL_ENCODER = "UTF-8";

    /**
     * RestTemplate used to interact with Rest service.
     */
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.restTemplate = new RestTemplate();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(250);
    }

    @Test
    public void test01DeleteWithEmptyPersistentSystem() {
        // Given - Instantiate id at delete on persistent system.
        int id = 666;
        String messageExcepted = "404 null";

        // When - Try to delete it.
        try {
            this.restTemplate.delete(REST_SERVICE_URI + "/video-games/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test02UpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding video game.
        String messageExcepted = "404 null";
        int id = 666;
        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        VideoGame videoGame = new VideoGame(
                id,"My Title", "My Original Title", "", releaseDate, new ArrayList<MediaGenre>(), new ArrayList<MediaSupport>(), false, new ArrayList<LanguageCode>(),
                new HashSet<Developer>(), new HashSet<Publisher>(), new ArrayList<VideoGamePlatform>()
        );

        // When - Try to update album.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/video-games/" + videoGame.getId(), videoGame);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test03GetAllVideoGamesWithEmptyPersistentSystem() {
        // Given / When - Get all video games from persistent system.
        ResponseEntity<List> videoGames = this.restTemplate.getForEntity(REST_SERVICE_URI + "/video-games/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(videoGames.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(videoGames.getBody()).isNull();
    }

    @Test
    public void test04GetVideoGameWithEmptyPersistentSystem() {
        // Given - Instantiate title of cartoon.
        String title = "Final Fantasy";

        // When - Get one cartoon from persistent system.
        ResponseEntity<VideoGame> videoGame = this.restTemplate.getForEntity(REST_SERVICE_URI + "/video-games/search/title/" + title, VideoGame.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(videoGame.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(videoGame.getBody()).isNull();
    }

    @Test
    public void test05AddVideoGameOnPersistentSystem() {
        // Given - Instantiate VideoGame at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/video-games/search/id/" + id;

        String title = "Final Fantasy XII";
        String originalTitle = "Final Fantasy XII";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2006, GregorianCalendar.MARCH, 16);
        boolean multiplayers = false;

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.RPG);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        List<LanguageCode> languages = new ArrayList<LanguageCode>();
        languages.add(LanguageCode.fr);
        languages.add(LanguageCode.en);
        languages.add(LanguageCode.it);
        languages.add(LanguageCode.es);
        languages.add(LanguageCode.de);

        Set<Developer> developers = new HashSet<Developer>();
        developers.add(new Developer("Square Enix"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Square Enix"));

        ArrayList<VideoGamePlatform> platforms = new ArrayList<VideoGamePlatform>();
        platforms.add(VideoGamePlatform.PS2);

        VideoGame videoGame = new VideoGame(
                id,title, originalTitle, synopsis, releaseDate, genres, supports,
                multiplayers, languages, developers, publishers, platforms
        );

        // When - Send video game to save it on persistent system.
        ResponseEntity<VideoGame> responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/video-games/", videoGame, VideoGame.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders().getLocation().toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void test06AddVideoGameAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate VideoGame at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Final Fantasy XII";
        String originalTitle = "Final Fantasy XII";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2006, GregorianCalendar.MARCH, 16);
        boolean multiplayers = false;

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.RPG);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        List<LanguageCode> languages = new ArrayList<LanguageCode>();
        languages.add(LanguageCode.fr);
        languages.add(LanguageCode.en);
        languages.add(LanguageCode.it);
        languages.add(LanguageCode.es);
        languages.add(LanguageCode.de);

        Set<Developer> developers = new HashSet<Developer>();
        developers.add(new Developer("Square Enix"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Square Enix"));

        ArrayList<VideoGamePlatform> platforms = new ArrayList<VideoGamePlatform>();
        platforms.add(VideoGamePlatform.PS2);

        VideoGame videoGame = new VideoGame(
                title, originalTitle, synopsis, releaseDate, genres, supports,
                multiplayers, languages, developers, publishers, platforms
        );

        ResponseEntity<VideoGameException> responseEntity = null;
        // When - Send video game to save it on persistent system.
        try {
            responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/video-games/", videoGame, VideoGameException.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test07GetOneVideoGame() throws UnsupportedEncodingException {
        // Given - Instantiate VideoGame at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Final Fantasy XII";
        String originalTitle = "Final Fantasy XII";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2006, GregorianCalendar.MARCH, 16);
        boolean multiplayers = false;

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.RPG);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        List<LanguageCode> languages = new ArrayList<LanguageCode>();
        languages.add(LanguageCode.fr);
        languages.add(LanguageCode.en);
        languages.add(LanguageCode.it);
        languages.add(LanguageCode.es);
        languages.add(LanguageCode.de);

        Set<Developer> developers = new HashSet<Developer>();
        developers.add(new Developer("Square Enix"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Square Enix"));

        ArrayList<VideoGamePlatform> platforms = new ArrayList<VideoGamePlatform>();
        platforms.add(VideoGamePlatform.PS2);

        VideoGame videoGame = new VideoGame(
                title, originalTitle, synopsis, releaseDate, genres, supports,
                multiplayers, languages, developers, publishers, platforms
        );

        // When - Get video game from persistent system.
        ResponseEntity<VideoGame> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/video-games/search/title/" + URLEncoder.encode(videoGame.getTitle(), URL_ENCODER), VideoGame.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and video game retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(videoGame.getTitle());
        assertThat(responseEntity.getBody().getOriginalTitle()).isEqualTo(videoGame.getOriginalTitle());
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(videoGame.getSynopsis());
        assertThat(responseEntity.getBody().getReleaseDate().get(Calendar.YEAR)).isEqualTo(videoGame.getReleaseDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getGenres()).isEqualTo(videoGame.getGenres());
        assertThat(responseEntity.getBody().getSupports()).isEqualTo(videoGame.getSupports());
        assertThat(responseEntity.getBody().getDevelopers().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getPublishers().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getPlatforms().size()).isEqualTo(sizeExpected);
        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void test08GetVideoGameNotFoundOnPersistentSystem() {
        // Given - Instantiate VideoGame at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Final Fantasy X";
        String originalTitle = "Final Fantasy X";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2001, GregorianCalendar.DECEMBER, 17);
        boolean multiplayers = false;

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.RPG);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        List<LanguageCode> languages = new ArrayList<LanguageCode>();
        languages.add(LanguageCode.fr);
        languages.add(LanguageCode.en);
        languages.add(LanguageCode.it);
        languages.add(LanguageCode.es);
        languages.add(LanguageCode.de);

        Set<Developer> developers = new HashSet<Developer>();
        developers.add(new Developer("Square Enix"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Square Enix"));

        ArrayList<VideoGamePlatform> platforms = new ArrayList<VideoGamePlatform>();
        platforms.add(VideoGamePlatform.PS2);

        VideoGame videoGame = new VideoGame(
                title, originalTitle, synopsis, releaseDate, genres, supports,
                multiplayers, languages, developers, publishers, platforms
        );

        // When - Get video game from persistent system.
        ResponseEntity<VideoGame> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/video-games/search/title/" + URLEncoder.encode(videoGame.getTitle(), URL_ENCODER), VideoGame.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test09GetAllVideoGames() {
        // Given - Instantiate a video game to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Final Fantasy X";
        String originalTitle = "Final Fantasy X";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2001, GregorianCalendar.DECEMBER, 17);
        boolean multiplayers = false;

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.RPG);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        List<LanguageCode> languages = new ArrayList<LanguageCode>();
        languages.add(LanguageCode.fr);
        languages.add(LanguageCode.en);
        languages.add(LanguageCode.it);
        languages.add(LanguageCode.es);
        languages.add(LanguageCode.de);

        Set<Developer> developers = new HashSet<Developer>();
        developers.add(new Developer("Square Enix"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Square Enix"));

        ArrayList<VideoGamePlatform> platforms = new ArrayList<VideoGamePlatform>();
        platforms.add(VideoGamePlatform.PS2);

        VideoGame videoGame = new VideoGame(
                title, originalTitle, synopsis, releaseDate, genres, supports,
                multiplayers, languages, developers, publishers, platforms
        );
        this.restTemplate.postForEntity(REST_SERVICE_URI + "/video-games/", videoGame, VideoGame.class);

        // When - Get all video games from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/video-games/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void test10UpdateVideoGame() throws UnsupportedEncodingException {
        // Given - Instantiate a video game to update on persistent system.
        int id = 2;
        String title = "Final Fantasy XIII";
        String originalTitle = "Final Fantasy XIII";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2001, GregorianCalendar.DECEMBER, 17);
        boolean multiplayers = false;

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.RPG);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        List<LanguageCode> languages = new ArrayList<LanguageCode>();
        languages.add(LanguageCode.fr);
        languages.add(LanguageCode.en);
        languages.add(LanguageCode.it);
        languages.add(LanguageCode.es);
        languages.add(LanguageCode.de);

        Set<Developer> developers = new HashSet<Developer>();
        developers.add(new Developer("Square Enix"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Square Enix"));

        ArrayList<VideoGamePlatform> platforms = new ArrayList<VideoGamePlatform>();
        platforms.add(VideoGamePlatform.PS2);

        VideoGame videoGame = new VideoGame(
                id, title, originalTitle, synopsis, releaseDate, genres, supports,
                multiplayers, languages, developers, publishers, platforms
        );
        this.restTemplate.put(REST_SERVICE_URI + "/video-games/" + videoGame.getId(), videoGame, VideoGame.class);

        // When - Get video game update and check if the difference appear.
        ResponseEntity<VideoGame> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/video-games/search/title/" + URLEncoder.encode(title, URL_ENCODER), VideoGame.class);

        // Then - Compare originalTitle.
        assertThat(responseEntity.getBody().getOriginalTitle()).isEqualTo(originalTitle);
    }

    @Test
    public void test11UpdateVideoGameNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate a video game to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Dragon Quest";
        String originalTitle = "Dragon Quest";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2001, GregorianCalendar.DECEMBER, 17);
        boolean multiplayers = false;

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.RPG);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        List<LanguageCode> languages = new ArrayList<LanguageCode>();
        languages.add(LanguageCode.fr);
        languages.add(LanguageCode.en);
        languages.add(LanguageCode.it);
        languages.add(LanguageCode.es);
        languages.add(LanguageCode.de);

        Set<Developer> developers = new HashSet<Developer>();
        developers.add(new Developer("Square Enix"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Square Enix"));

        ArrayList<VideoGamePlatform> platforms = new ArrayList<VideoGamePlatform>();
        platforms.add(VideoGamePlatform.PS2);

        VideoGame videoGame = new VideoGame(
                title, originalTitle, synopsis, releaseDate, genres, supports,
                multiplayers, languages, developers, publishers, platforms
        );

        try {
            // When - Try to update video game not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/video-games/" + videoGame.getId(), videoGame, VideoGame.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test12DeleteVideoGame() {
        // Given - id of video game at delete and all elements expected.
        int id = 2;
        String title = "Final Fantasy XIII";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete video game
        this.restTemplate.delete(REST_SERVICE_URI + "/video-games/" + id);

        try {
            ResponseEntity<VideoGame> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/video-games/search/title/" + URLEncoder.encode(title, URL_ENCODER), VideoGame.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test13DeleteVideoGameNotFoundOnPersistentSystem() {
        // Given - id of video game at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete video game
            this.restTemplate.delete(REST_SERVICE_URI + "/video-games/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }
}
