package fr.nicolasgille.medialibrary.client;

import fr.nicolasgille.medialibrary.models.movie.Movie;
import fr.nicolasgille.medialibrary.models.movie.MovieCategory;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie("Batman return", categories, 1995, 95, "I'm Batman !!!", "Batman et Robin");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());
        movie = new Movie("Batman Dark Knight", categories, 2012, 137, "I'm a Darkness, i'm the Bat, I'm Batman !!!", "Batman, Robin, Joker");
        uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());
    }

    /**
     * METHOD  |        URL      | BODY
     *  GET    |     /movies/    |  /
     */
    @Test
    public void testGetAllMovies() {
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> movies = restTemplate.getForObject(REST_SERVICE_URI + "/movies/", List.class);

        if (movies != null) {
            for(LinkedHashMap<String, Object> map : movies){
                for (String key : map.keySet()) {
                    System.out.print(key + " : " + map.get(key) + " | ");
                }
                System.out.println();
            }
        }
    }

    /**
     * METHOD  |                URL           | BODY
     *  GET    | /movies/search/title/{title} |  /
     */
    @Test
    public void testMovie() {
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject(REST_SERVICE_URI + "/movies/search/title/Batman return", Movie.class);
        System.out.println(movie.toString());
    }

    /**
     * METHOD  |        URL      | BODY
     *  GET    | /movies/update  | movie.
     */
    @Test
    public void testUpdate() {
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);
        categories.add(MovieCategory.COMEDY);
        categories.add(MovieCategory.ADVENTURE);
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie(1,"Batman Forever", categories, 1995, 95, "I'm Batman !!!", "Batman et Robin");
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

    @Test
    public void testInsertMovieAlreadyPresent() {
        RestTemplate restTemplate = new RestTemplate();
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);
        Movie movie = new Movie("Transformers", categories, 2010, 123, "BOUM PAF PAN PAN BOUM ", "Optimus Prime, Bumblebee, Megatron");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());

        categories.add(MovieCategory.ACTION);
        categories.add(MovieCategory.ADVENTURE);
        movie = new Movie("Transformers", categories, 2010, 123, "BOUM PAF PAN PAN BOUM ", "Optimus Prime, Bumblebee, Megatron");
        uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());
    }

    @Test
    public void testUpdateMovieNotPresent() {
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie(1,"Batman Fornever", categories, 1995, 95, "I'm Batman !!!", "Batman et Robin");
        restTemplate.put(REST_SERVICE_URI + "/movies/update/" + movie.getId(), movie);
        System.out.println(movie.toString());
    }

    @Test
    public void testDeleteMovieNotPresent() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/movies/delete/666");
    }
}
