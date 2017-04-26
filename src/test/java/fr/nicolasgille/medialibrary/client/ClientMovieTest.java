package fr.nicolasgille.medialibrary.client;

import fr.nicolasgille.medialibrary.models.common.Actor;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import fr.nicolasgille.medialibrary.models.movie.Movie;
import fr.nicolasgille.medialibrary.models.movie.MovieCategory;
import fr.nicolasgille.medialibrary.models.movie.MovieSupport;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

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
        // Given - Instantiate category, support and actor.
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.VIDEO_TAPE);
        supports.add(MovieSupport.DVD);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Bruce", "Wayne"));
        actors.add(new Actor("Clark", "Kent"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Jacky", "LaFrite"));
        producers.add(new Producer("Michel", "LaPoutre"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Director", "Sama"));

        Calendar releasedDate = new GregorianCalendar();
        releasedDate.set(1999, Calendar.MAY, Calendar.JANUARY);

        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie("Batman return", categories, releasedDate, 95, "I'm Batman !!!", actors, producers, directors, supports);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());

        supports.remove(MovieSupport.VIDEO_TAPE);
        actors.add(new Actor("Jocker", ""));

        releasedDate.set(2012, Calendar.MAY, Calendar.JANUARY);

        movie = new Movie("Batman Dark Knight", categories, releasedDate, 137, "I'm a Darkness, i'm the Bat, I'm Batman !!!", actors, producers, directors, supports);
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
    public void testGetMovie() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Movie> responseEntity = restTemplate.getForEntity(REST_SERVICE_URI + "/movies/search/title/Batman return", Movie.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.out.println(responseEntity.getBody().toString());
            } else {
                System.out.println("An error occurred and movie not found on Database.");
            }
        } catch (HttpMessageNotReadableException exception) {
            System.out.println(exception.toString());
        }
    }

    /**
     * METHOD  |        URL      | BODY
     *  GET    | /movies/update  | movie.
     */
    @Test
    public void testUpdate() {
        // Given - Instantiate category, support, actors
        long id = 1;
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);
        categories.add(MovieCategory.COMEDY);
        categories.add(MovieCategory.ADVENTURE);

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Bruce", "Wayne"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Jacky", "LaFrite"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Director", "Sama"));

        Calendar releasedDate = new GregorianCalendar();
        releasedDate.set(1999, Calendar.MAY, Calendar.JANUARY);

        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie(id,"Batman Forever", categories, releasedDate, 195, "I'm Batman !!!", actors, producers, directors, supports);
        restTemplate.put(REST_SERVICE_URI + "/movies/update/" + movie.getId(), movie);
        System.out.println(movie.toString());
    }

    /**
     * METHOD  |        URL      | BODY
     * DELETE  | /movies/delete  |  /
     */
    @Test
    public void testDelete() {
        int id = 2;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/movies/delete/" + id);
    }

    @Test
    public void testInsertMovieAlreadyPresent() {
        // Given - Instantiate category, support, actors
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Optimus", "Prime"));
        actors.add(new Actor("Bumblebee", ""));
        actors.add(new Actor("Megatron", ""));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Michael", "Bay"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Director", "Sama"));

        Calendar releasedDate = new GregorianCalendar();
        releasedDate.set(1999, Calendar.MAY, Calendar.JANUARY);

        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie("Transformers", categories, releasedDate, 123, "BOUM PAF PAN PAN BOUM ", actors, producers, directors, supports);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());

        categories.add(MovieCategory.ACTION);
        categories.add(MovieCategory.ADVENTURE);
        movie = new Movie("Transformers", categories, releasedDate, 123, "BOUM PAF PAN PAN BOUM ", actors, producers, directors, supports);
        uri = restTemplate.postForLocation(REST_SERVICE_URI + "/movies/create", movie, Movie.class);
        System.out.println(uri.toASCIIString());
    }

    @Test
    public void testUpdateMovieNotPresent() {
        // Given - Instantiate category, support, actors
        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        categories.add(MovieCategory.ACTION);

        List<MovieSupport> supports = new ArrayList<MovieSupport>();
        supports.add(MovieSupport.DVD);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Bruce", "Wayne"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Michael", "Bay"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Director", "Sama"));

        Calendar releasedDate = new GregorianCalendar();
        releasedDate.set(1999, Calendar.MAY, Calendar.JANUARY);

        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie(1,"Batman Fornever", categories, releasedDate, 95, "I'm Batman !!!", actors, producers, directors, supports);
        restTemplate.put(REST_SERVICE_URI + "/movies/update/" + movie.getId(), movie);
        System.out.println(movie.toString());
    }

    @Test
    public void testDeleteMovieNotPresent() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/movies/delete/666");
    }
}
