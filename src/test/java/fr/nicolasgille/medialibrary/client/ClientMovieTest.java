package fr.nicolasgille.medialibrary.client;

import fr.nicolasgille.medialibrary.exception.MovieException;
import fr.nicolasgille.medialibrary.models.common.Actor;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import fr.nicolasgille.medialibrary.models.movie.Movie;
import fr.nicolasgille.medialibrary.models.movie.MovieCategory;
import fr.nicolasgille.medialibrary.models.movie.MovieSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Unit class test used to test MovieController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.0
 */
public class ClientMovieTest {
    /**
     * URI of the Rest service.
     */
    private static final String REST_SERVICE_URI = "http://localhost:8080/media-library";


    private static final String URL_ENCODER = "UTF-8";

    /**
     * RestTemplate used to interact with Rest service.
     */
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.restTemplate = new RestTemplate();
    }

    @Test
    public void testDeleteWithEmptyPersistentSystem() {
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
    public void testUpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding movie.
        String messageExcepted = "404 null";
        int id = 666;

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);

        Movie movie = new Movie(id, "My title", categories, releaseDate, 120, "My Synopsis", actors, producers, directors, supports);

        // When - Try to update movie.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/movies/" + movie.getId(), movie);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void testGetAllMoviesWithEmptyPersistentSystem() {
        // Given / When - Get all movies from persistent system.
        ResponseEntity<List> movies = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(movies.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(movies.getBody()).isNull();
    }

    @Test
    public void testGetMovieWithEmptyPersistentSystem() {
        // Given - Instantiate title of movie.
        String title = "Persistent System 2 : Return of the Empty Row";

        // When - Get one movie from persistent system.
        ResponseEntity<Movie> movie = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + title, Movie.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(movie.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(movie.getBody()).isNull();
    }

    @Test
    public void testAddMovieOnPersistentSystem() {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/movies/search/title/" + id;

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);
        Movie movie = new Movie(title, categories, releaseDate, 120, synopsis, actors, producers, directors, supports);

        // When - Send movie to save it on persistent system.
        ResponseEntity<Movie> responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/movies/", movie, Movie.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders().getLocation().toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void testAddMovieAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);
        Movie movie = new Movie(title, categories, releaseDate, 120, synopsis, actors, producers, directors, supports);

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
    public void testGetOneMovie() throws UnsupportedEncodingException {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Persistent System 2 : Return of the Empty Row";
        String synopsis = "A developer fight the empty row present on the persistent system";

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);
        Movie movie = new Movie(title, categories, releaseDate, 120, synopsis, actors, producers, directors, supports);

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
        assertThat(responseEntity.getBody().getCategories()).isEqualTo(movie.getCategories());
        assertThat(responseEntity.getBody().getSupports()).isEqualTo(movie.getSupports());
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(movie.getSynopsis());
        assertThat(responseEntity.getBody().getDuration()).isEqualTo(movie.getDuration());
        assertThat(responseEntity.getBody().getDirectors().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getMainActors().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getProducers().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void testGetMovieNotFoundOnPersistentSystem() {
        // Given - Instantiate Movie at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer failed during empty row fix, and a new developer appear has a new hope !";

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);
        Movie movie = new Movie(title, categories, releaseDate, 126, synopsis, actors, producers, directors, supports);

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
    public void testGetAllMovies() {
        // Given - Instantiate a movie to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer failed during empty row fix, and a new developer appear has a new hope !";

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);
        Movie movie = new Movie(title, categories, releaseDate, 126, synopsis, actors, producers, directors, supports);
        this.restTemplate.postForEntity(REST_SERVICE_URI + "/movies/", movie, Movie.class);

        // When - Get all movies from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void updateMovie() throws UnsupportedEncodingException {
        // Given - Instantiate a movie to update on persistent system.
        int id = 2;
        String title = "Persistent System 3 : A new Hope";
        String synopsis = "The developer defeated the empty row fix, but a new developer appear has a new hope ?";

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);
        Movie movie = new Movie(id, title, categories, releaseDate, 126, synopsis, actors, producers, directors, supports);
        this.restTemplate.put(REST_SERVICE_URI + "/movies/" + movie.getId(), movie, Movie.class);

        // When - Get movie update and check if the difference appear.
        ResponseEntity<Movie> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/" + URLEncoder.encode(title, URL_ENCODER), Movie.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void updateMovieNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate a movie to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Persistent System 3 : A new Despair";
        String synopsis = "The developer defeated the empty row fix, but a new developer appear has a new hope or despair ?";

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.FANTASY);

        Calendar releaseDate = new GregorianCalendar(2017, GregorianCalendar.MAY, GregorianCalendar.MONDAY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);
        Movie movie = new Movie(id, title, categories, releaseDate, 126, synopsis, actors, producers, directors, supports);

        try {
            // When - Try to update movie not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/movies/" + movie.getId(), movie, Movie.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void deleteMovie() {
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
    public void deleteMovieNotFoundOnPersistentSystem() {
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
}
