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

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.exceptions.video.CartoonException;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.Cartoon;
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
 * Unit class test used to test CartoonController class.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.3
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientCartoonTest {

    /**
     * URI of the Rest service.
     */
    private static final String REST_SERVICE_URI = "http://localhost:8080";

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
            this.restTemplate.delete(REST_SERVICE_URI + "/cartoons/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test02UpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding cartoon.
        String messageExcepted = "404 null";
        int id = 666;
        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Cartoon cartoon = new Cartoon(
                id, "My title", "My original title", "My Synopsis",
                new HashSet<Director>(), new HashSet<Producer>(), new ArrayList<VideoGenre>(),
                new ArrayList<MediaSupport>(),
                new ArrayList<LanguageCode>(), new ArrayList<LanguageCode>(), releaseDate, 120
        );

        // When - Try to update cartoon.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/cartoons/" + cartoon.getId(), cartoon);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test03GetAllCartoonsWithEmptyPersistentSystem() {
        // Given / When - Get all cartoons from persistent system.
        ResponseEntity<List> cartoons = this.restTemplate.getForEntity(REST_SERVICE_URI + "/cartoons/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(cartoons.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(cartoons.getBody()).isNull();
    }

    @Test
    public void test04GetCartoonWithEmptyPersistentSystem() {
        // Given - Instantiate title of cartoon.
        String title = "Persistent System 2 : Return of the Empty Row";

        // When - Get one cartoon from persistent system.
        ResponseEntity<Cartoon> cartoon =
                this.restTemplate.getForEntity(REST_SERVICE_URI + "/cartoons/search/title/" + title, Cartoon.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(cartoon.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(cartoon.getBody()).isNull();
    }

    @Test
    public void test05AddCartoonOnPersistentSystem() {
        // Given - Instantiate Cartoon at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/cartoons/search/id/" + id;

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);
        Cartoon cartoon = new Cartoon(
                title, title, synopsis,
                directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 120
        );

        // When - Send cartoon to save it on persistent system.
        ResponseEntity<Cartoon> responseEntity =
                this.restTemplate.postForEntity(REST_SERVICE_URI + "/cartoons/", cartoon, Cartoon.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders()
                                 .getLocation()
                                 .toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void test06AddCartoonAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate Cartoon at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);
        Cartoon cartoon = new Cartoon(
                title, title, synopsis,
                directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 88
        );

        ResponseEntity<CartoonException> responseEntity = null;
        // When - Send cartoon to save it on persistent system.
        try {
            responseEntity =
                    this.restTemplate.postForEntity(REST_SERVICE_URI + "/cartoons/", cartoon, CartoonException.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test07GetOneCartoon() throws UnsupportedEncodingException {
        // Given - Instantiate Cartoon at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);
        Cartoon cartoon = new Cartoon(
                title, title, synopsis,
                directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 120
        );

        // When - Get cartoon from persistent system.
        ResponseEntity<Cartoon> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(
                    REST_SERVICE_URI + "/cartoons/search/title/" + URLEncoder.encode(cartoon.getTitle(), URL_ENCODER),
                    Cartoon.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and cartoon retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody()
                                 .getTitle()).isEqualTo(cartoon.getTitle());
        assertThat(responseEntity.getBody()
                                 .getReleaseDate()
                                 .get(Calendar.YEAR)).isEqualTo(cartoon.getReleaseDate()
                                                                       .get(Calendar.YEAR));
        assertThat(responseEntity.getBody()
                                 .getGenres()).isEqualTo(cartoon.getGenres());
        assertThat(responseEntity.getBody()
                                 .getSupports()).isEqualTo(cartoon.getSupports());
        assertThat(responseEntity.getBody()
                                 .getSynopsis()).isEqualTo(cartoon.getSynopsis());
        assertThat(responseEntity.getBody()
                                 .getRuntime()).isEqualTo(cartoon.getRuntime());
        assertThat(responseEntity.getBody()
                                 .getDirectors()
                                 .size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody()
                                 .getProducers()
                                 .size()).isEqualTo(sizeExpected);
        System.out.println(responseEntity.getBody()
                                         .toString());
    }

    @Test
    public void test08GetCartoonNotFoundOnPersistentSystem() {
        // Given - Instantiate Cartoon at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer failed during empty row fix, and a new developer appear has a new hope !";

        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);
        Cartoon cartoon = new Cartoon(
                title, title, synopsis,
                directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );

        // When - Get cartoon from persistent system.
        ResponseEntity<Cartoon> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(
                    REST_SERVICE_URI + "/cartoons/search/title/" + URLEncoder.encode(cartoon.getTitle(), URL_ENCODER),
                    Cartoon.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test09GetAllCartoons() {
        // Given - Instantiate a cartoon to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer failed during empty row fix, and a new developer appear has a new hope !";

        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);
        Cartoon cartoon = new Cartoon(
                title, title, synopsis,
                directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );
        this.restTemplate.postForEntity(REST_SERVICE_URI + "/cartoons/", cartoon, Cartoon.class);

        // When - Get all cartoons from persistent system.
        ResponseEntity<List> responseEntity =
                this.restTemplate.getForEntity(REST_SERVICE_URI + "/cartoons/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody()
                                 .size()).isEqualTo(sizeExpected);
    }

    @Test
    public void test10UpdateCartoon() throws UnsupportedEncodingException {
        // Given - Instantiate a cartoon to update on persistent system.
        int id = 2;
        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer defeated the empty row fix, but a new developer appear has a new hope ?";

        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);
        Cartoon cartoon = new Cartoon(
                id, title, title, synopsis,
                directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );
        this.restTemplate.put(REST_SERVICE_URI + "/cartoons/" + cartoon.getId(), cartoon, Cartoon.class);

        // When - Get cartoon update and check if the difference appear.
        ResponseEntity<Cartoon> responseEntity = this.restTemplate.getForEntity(
                REST_SERVICE_URI + "/cartoons/search/title/" + URLEncoder.encode(title, URL_ENCODER), Cartoon.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody()
                                 .getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void test11UpdateCartoonNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate a cartoon to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Persistent System 3 : A new Despair";
        String synopsis =
                "The developer defeated the empty row fix, but a new developer appear has a new hope or despair ?";

        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);
        Cartoon cartoon = new Cartoon(
                title, title, synopsis,
                directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );

        try {
            // When - Try to update cartoon not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/cartoons/" + cartoon.getId(), cartoon, Cartoon.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test12DeleteCartoon() {
        // Given - id of cartoon at delete and all elements expected.
        int id = 2;
        String title = "Persistent System 3 : A new Hope";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete cartoon
        this.restTemplate.delete(REST_SERVICE_URI + "/cartoons/" + id);

        try {
            ResponseEntity<Cartoon> responseEntity = this.restTemplate.getForEntity(
                    REST_SERVICE_URI + "/cartoons/search/title/" + URLEncoder.encode(title, URL_ENCODER),
                    Cartoon.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test13DeleteCartoonNotFoundOnPersistentSystem() {
        // Given - id of cartoon at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete cartoon
            this.restTemplate.delete(REST_SERVICE_URI + "/cartoons/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }
}
