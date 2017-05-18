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

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.exception.video.SeriesException;
import fr.nicolasgille.medialibrary.models.common.Actor;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import fr.nicolasgille.medialibrary.models.video.Series;
import fr.nicolasgille.medialibrary.models.video.utils.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.utils.VideoSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit class test used to test SeriesController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.0
 */
public class ClientSeriesTest {
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
    public void testDeleteWithEmptyPersistentSystem() {
        // Given - Instantiate id at delete on persistent system.
        int id = 666;
        String messageExcepted = "404 null";

        // When - Try to delete it.
        try {
            this.restTemplate.delete(REST_SERVICE_URI + "/series/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void testUpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding series.
        String messageExcepted = "404 null";
        int id = 666;

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                id, "Star Gate SG1", "Star Gate SG1", "My Synopsis",
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, 10, 2, 226, 22, 42
        );

        // When - Try to update series.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/series/" + series.getId(), series);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void testGetAllSeriesWithEmptyPersistentSystem() {
        // Given / When - Get all series from persistent system.
        ResponseEntity<List> series = this.restTemplate.getForEntity(REST_SERVICE_URI + "/series/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(series.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(series.getBody()).isNull();
    }

    @Test
    public void testGetSeriesWithEmptyPersistentSystem() {
        // Given - Instantiate title of series.
        String title = "Persistent System 2 : Return of the Empty Row";

        // When - Get one series from persistent system.
        ResponseEntity<Series> series = this.restTemplate.getForEntity(REST_SERVICE_URI + "/series/search/title/" + title, Series.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(series.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(series.getBody()).isNull();
    }

    @Test
    public void testAddSeriesOnPersistentSystem() {
        // Given - Instantiate Series at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 3;
        String uriExpected = "http://localhost:8080/media-library/series/search/title/" + id;

        String title = "Star Gate SG1";
        String synopsis = "Star Gate SG1 is awesome !";

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        int averageEpisodeRuntime = 44;
        int numberOfSeasons = 10;
        int currentSeason   = 2;
        int numberOfEpisode = 22;
        int maxEpisodes = 226;

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                title, title, synopsis,
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        // When - Send series to save it on persistent system.
        ResponseEntity<Series> responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/series/", series, Series.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders().getLocation().toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void testAddSeriesAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate Series at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Star Gate SG1";
        String synopsis = "Star Gate SG1 is awesome !";

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        int numberOfSeasons = 10;
        int currentSeason = 2;
        int averageEpisodeRuntime = 44;
        int numberOfEpisode = 22;
        int maxEpisodes = 226;

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                title, title, synopsis,
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        ResponseEntity<SeriesException> responseEntity = null;
        // When - Send series to save it on persistent system.
        try {
            responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/series/", series, SeriesException.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void testGetOneSeries() throws UnsupportedEncodingException {
        // Given - Instantiate Series at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Star Gate SG1";
        String synopsis = "Star Gate SG1 is awesome !";

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        int averageEpisodeRuntime = 44;
        int numberOfSeasons = 10;
        int currentSeason   = 2;
        int numberOfEpisode = 22;
        int maxEpisodes = 226;

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                title, title, synopsis,
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        // When - Get series from persistent system.
        ResponseEntity<Series> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/series/search/title/" + URLEncoder.encode(series.getTitle(), URL_ENCODER), Series.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and series retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(series.getTitle());
        assertThat(responseEntity.getBody().getStartDate().get(Calendar.YEAR)).isEqualTo(series.getStartDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getEndDate().get(Calendar.YEAR)).isEqualTo(series.getEndDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getGenres()).isEqualTo(series.getGenres());
        assertThat(responseEntity.getBody().getSupports()).isEqualTo(series.getSupports());
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(series.getSynopsis());
        assertThat(responseEntity.getBody().getAverageEpisodeRuntime()).isEqualTo(series.getAverageEpisodeRuntime());
        assertThat(responseEntity.getBody().getCurrentSeason()).isEqualTo(series.getCurrentSeason());
        assertThat(responseEntity.getBody().getNumberOfSeasons()).isEqualTo(series.getNumberOfSeasons());
        assertThat(responseEntity.getBody().getNumberOfEpisode()).isEqualTo(series.getNumberOfEpisode());
        assertThat(responseEntity.getBody().getDirectors().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getMainActors().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getProducers().size()).isEqualTo(sizeExpected);

        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void testGetSeriesNotFoundOnPersistentSystem() {
        // Given - Instantiate Series at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Star Gate SG2";
        String synopsis = "Star Gate SG2 is awesome !";

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        int averageEpisodeRuntime = 44;
        int numberOfSeasons = 10;
        int currentSeason   = 2;
        int numberOfEpisode = 22;
        int maxEpisodes = 226;

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                title, title, synopsis,
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        // When - Get series from persistent system.
        ResponseEntity<Series> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/search/title/" + URLEncoder.encode(series.getTitle(), URL_ENCODER), Series.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void testGetAllSeries() {
        // Given - Instantiate a series to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Star Gate SG2";
        String synopsis = "Star Gate SG2 is awesome !";

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        int averageEpisodeRuntime = 44;
        int numberOfSeasons = 10;
        int currentSeason   = 2;
        int numberOfEpisode = 22;
        int maxEpisodes = 226;

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                title, title, synopsis,
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        this.restTemplate.postForEntity(REST_SERVICE_URI + "/series/", series, Series.class);

        // When - Get all seriess from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/series/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void updateSeries() throws UnsupportedEncodingException {
        // Given - Instantiate a series to update on persistent system.
        int id = 4;
        String title = "Star Gate SG2";
        String synopsis = "Star Gate SG2 is amazing !";

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        int averageEpisodeRuntime = 44;
        int numberOfSeasons = 10;
        int currentSeason   = 2;
        int numberOfEpisode = 22;
        int maxEpisodes = 226;

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                title, title, synopsis,
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );
        this.restTemplate.put(REST_SERVICE_URI + "/series/" + series.getId(), series, Series.class);

        // When - Get series update and check if the difference appear.
        ResponseEntity<Series> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/series/search/title/" + URLEncoder.encode(title, URL_ENCODER), Series.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void updateSeriesNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate a series to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Star Gate SG2";
        String synopsis = "Star Gate SG2 is awesome !";

        List<VideoGenre> categories = new ArrayList<VideoGenre>();
        categories.add(VideoGenre.FANTASY);

        Set<Actor> actors = new HashSet<Actor>();
        actors.add(new Actor("Nicolas", "Cage"));

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Steven", "Spielberg"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Ridley", "Scott"));

        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.en);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.de);
        subtitles.add(LanguageCode.it);

        int averageEpisodeRuntime = 44;
        int numberOfSeasons = 10;
        int currentSeason   = 2;
        int numberOfEpisode = 22;
        int maxEpisodes = 226;

        Calendar startDate = new GregorianCalendar(1997, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);
        Calendar endDate   = new GregorianCalendar(2007, GregorianCalendar.SEPTEMBER, GregorianCalendar.THURSDAY);

        Series series = new Series(
                title, title, synopsis,
                actors, directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        try {
            // When - Try to update series not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/series/" + series.getId(), series, Series.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void deleteSeries() {
        // Given - id of series at delete and all elements expected.
        int id = 4;
        String title = "Star Gate SG2";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete series
        this.restTemplate.delete(REST_SERVICE_URI + "/series/" + id);

        try {
            ResponseEntity<Series> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/series/search/title/" + URLEncoder.encode(title, URL_ENCODER), Series.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void deleteSeriesNotFoundOnPersistentSystem() {
        // Given - id of series at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete series
            this.restTemplate.delete(REST_SERVICE_URI + "/series/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }
}
