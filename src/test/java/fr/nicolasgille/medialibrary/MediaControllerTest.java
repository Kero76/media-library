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
package fr.nicolasgille.medialibrary;

import fr.nicolasgille.medialibrary.models.video.Movie;
import fr.nicolasgille.medialibrary.models.video.Series;
import fr.nicolasgille.medialibrary.utils.DataUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit class test used to test All Controller in same test class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2.1
 * @version 1.0
 */
public class MediaControllerTest {

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

    @Test
    public void testInsertion() {
        // Given - Instantiate a List of all keys used for the movie and the series stored on DataUtils object.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;

        // Stored all responseEntity for each movie.
        List<ResponseEntity<Movie>> responseEntitiesMovies = new ArrayList<ResponseEntity<Movie>>();

        // Stored all responseEntity for each series.
        List<ResponseEntity<Series>> responseEntitiesSeries = new ArrayList<ResponseEntity<Series>>();

        // When - Send all medias to save them on persistent system.
        for (String key : DataUtils.getInstance().getKeys().get("movies")) {
            responseEntitiesMovies.add(this.restTemplate.postForEntity(REST_SERVICE_URI + "/movies/", DataUtils.getInstance().getMovies().get(key), Movie.class));
        }

        for (String key : DataUtils.getInstance().getKeys().get("series")) {
            responseEntitiesSeries.add(this.restTemplate.postForEntity(REST_SERVICE_URI + "/series/", DataUtils.getInstance().getSeries().get(key), Series.class));
        }

        // Then - Compare HTTP status and uri.
        for (ResponseEntity responseEntity : responseEntitiesMovies) {
            assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        }

        for (ResponseEntity responseEntity : responseEntitiesSeries) {
            assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }


    @Test
    public void getMovies() {
        // Given - Instantiate http code return and number of movies expected.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int numberOfMoviesExpected = DataUtils.getInstance().getMovies().size();

        // When - Get all movies.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/movies/", List.class);

        // Then - Check http code and size of list.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(numberOfMoviesExpected);
    }

    @Test
    public void getSeries() {
        // Given - Instantiate http code return and number of series expected.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int numberOfSeriesExpected = DataUtils.getInstance().getSeries().size();

        // When - Get all movies.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/series/", List.class);

        // Then - Check http code and size of list.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(numberOfSeriesExpected);
    }
}
