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

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.exceptions.video.MovieException;
import fr.nicolasgille.medialibrary.models.common.person.Actor;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.video.Movie;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
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
 * Unit class test used to test MovieController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientMovieTest {

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
            this.restTemplate.delete(REST_SERVICE_URI + "/movies/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test02UpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding movie.
        String messageExcepted = "404 null";
        int id = 666;
        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Movie movie = new Movie(
                id,  "My title", "My original title", "My Synopsis",
                new HashSet<Actor>(), new HashSet<Director>(), new HashSet<Producer>(),
                new ArrayList<MediaGenre>(), new ArrayList<MediaSupport>(), new ArrayList<LanguageCode>(), new ArrayList<LanguageCode>(),
                releaseDate, 120
        );

        // When - Try to update movie.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/movies/" + movie.getId(), movie);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test03GetAllMoviesWithEmptyPersistentSystem() {
        // Given / When - Get all movies from persistent system.
        ResponseEntity<List> movies = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(movies.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(movies.getBody()).isNull();
    }

    @Test
    public void test04GetMovieWithEmptyPersistentSystem() {
        // Given - Instantiate title of movie.
        String title = "Persistent System 2 : Return of the Empty Row";

        // When - Get one movie from persistent system.
        ResponseEntity<Movie> movie = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + title, Movie.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(movie.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(movie.getBody()).isNull();
    }

    @Test
    public void test05AddMovieOnPersistentSystem() {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/movies/search/id/" + id;

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

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
        Movie movie = new Movie(
                title, title, synopsis,
                actors, directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 120
        );

        // When - Send movie to save it on persistent system.
        ResponseEntity<Movie> responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/movies/", movie, Movie.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders().getLocation().toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void test06AddMovieAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

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
        Movie movie = new Movie(
                title, title, synopsis,
                actors, directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 120
        );

        ResponseEntity<MovieException> responseEntity = null;
        // When - Send movie to save it on persistent system.
        try {
            responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/movies/", movie, MovieException.class);
         } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test07GetOneMovie() throws UnsupportedEncodingException {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

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
        Movie movie = new Movie(
                title, title, synopsis,
                actors, directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 120
        );

        // When - Get movie from persistent system.
        ResponseEntity<Movie> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + URLEncoder.encode(movie.getTitle(), URL_ENCODER), Movie.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and movie retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(movie.getTitle());
        assertThat(responseEntity.getBody().getReleaseDate().get(Calendar.YEAR)).isEqualTo(movie.getReleaseDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getGenres()).isEqualTo(movie.getGenres());
        assertThat(responseEntity.getBody().getSupports()).isEqualTo(movie.getSupports());
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(movie.getSynopsis());
        assertThat(responseEntity.getBody().getRuntime()).isEqualTo(movie.getRuntime());
        assertThat(responseEntity.getBody().getDirectors().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getMainActors().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getProducers().size()).isEqualTo(sizeExpected);
        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void test08GetMovieNotFoundOnPersistentSystem() {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer failed during empty row fix, and a new developer appear has a new hope !";

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

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
        Movie movie = new Movie(
                title, title, synopsis,
                actors, directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );

        // When - Get movie from persistent system.
        ResponseEntity<Movie> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + URLEncoder.encode(movie.getTitle(), URL_ENCODER), Movie.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test09GetAllMovies() {
        // Given - Instantiate a movie to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer failed during empty row fix, and a new developer appear has a new hope !";

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

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
        Movie movie = new Movie(
                title, title, synopsis,
                actors, directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );
        this.restTemplate.postForEntity(REST_SERVICE_URI + "/movies/", movie, Movie.class);

        // When - Get all movies from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void test10UpdateMovie() throws UnsupportedEncodingException {
        // Given - Instantiate a movie to update on persistent system.
        int id = 2;
        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer defeated the empty row fix, but a new developer appear has a new hope ?";

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

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
        Movie movie = new Movie(
                id, title, title, synopsis,
                actors, directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );
        this.restTemplate.put(REST_SERVICE_URI + "/movies/" + movie.getId(), movie, Movie.class);

        // When - Get movie update and check if the difference appear.
        ResponseEntity<Movie> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + URLEncoder.encode(title, URL_ENCODER), Movie.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void test11UpdateMovieNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate a movie to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Persistent System 3 : A new Despair";
        String synopsis = "The developer defeated the empty row fix, but a new developer appear has a new hope or despair ?";

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

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
        Movie movie = new Movie(
                title, title, synopsis,
                actors, directors, producers, genres, supports, languageSpoken, subtitles,
                releaseDate, 126
        );

        try {
            // When - Try to update movie not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/movies/" + movie.getId(), movie, Movie.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test12DeleteMovie() {
        // Given - id of movie at delete and all elements expected.
        int id = 2;
        String title = "Persistent System 3 : A new Hope";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete movie
        this.restTemplate.delete(REST_SERVICE_URI + "/movies/" + id);

        try {
            ResponseEntity<Movie> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + URLEncoder.encode(title, URL_ENCODER), Movie.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test13DeleteMovieNotFoundOnPersistentSystem() {
        // Given - id of movie at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete movie
            this.restTemplate.delete(REST_SERVICE_URI + "/movies/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test14GetAllKingKongMovies() throws UnsupportedEncodingException {
        // Given - id of movie at delete and all elements expected.
        int sizeExpected = 3;
        String title = "King Kong";

        // When - Get all King Kong movie.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + URLEncoder.encode(title, URL_ENCODER), List.class);

        // Then - Compare size of king kong retrieve.
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }
}
