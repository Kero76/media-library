package fr.nicolasgille.medialibrary.client;

import fr.nicolasgille.medialibrary.models.Movie;
import fr.nicolasgille.medialibrary.models.MovieCategory;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Test persistence of the movie in Database MySQL / MariaDB.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
public class ClientMovieTest {
    private static final String REST_SERVICE_URI = "http://localhost:8080/media-library";

    /**
     * METHOD  |        URL      | BODY
     * POST    | /movies/create  | movie.
     */
    @Test
    public void testSave() {
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie("Batman return", MovieCategory.ACTION, 1995, 95, "I'm Batman !!!", "Batman et Robin");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());
    }

    /**
     * METHOD  |        URL      | BODY
     *  GET    | /movies/update  | movie.
     */
    @Test
    public void testUpdate() {
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie(1,"Batman Forever", MovieCategory.ACTION, 1995, 95, "I'm Batman !!!", "Batman et Robin");
        restTemplate.put(REST_SERVICE_URI + "/movies/update/" + movie.getId(), movie);
        System.out.println(movie.toString());
    }

    /**
     * METHOD  |        URL      | BODY
     * DELETE  | /movies/delete  |  /
     */
    @Test
    public void testDelete() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/movies/delete/1");
    }
}
