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
import fr.nicolasgille.medialibrary.exceptions.video.AnimeException;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.video.Anime;
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
 * Unit class test used to test AnimeController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientAnimeTest {

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
            this.restTemplate.delete(REST_SERVICE_URI + "/animes/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test02UpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding animes.
        String messageExcepted = "404 null";
        int id = 666;
        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime animes = new Anime(
                id, "Sakura, chasseuse de cartes", "Kādokyaputā Sakura", "My Synopsis",
                new HashSet<Director>(), new HashSet<Producer>(),
                new ArrayList<MediaGenre>(), new ArrayList<MediaSupport>(), new ArrayList<LanguageCode>(), new ArrayList<LanguageCode>(),
                startDate, endDate, 3, 1, 70, 36, 22
        );

        // When - Try to update animes.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/animes/" + animes.getId(), animes);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test03GetAllAnimeWithEmptyPersistentSystem() {
        // Given / When - Get all animes from persistent system.
        ResponseEntity<List> animes = this.restTemplate.getForEntity(REST_SERVICE_URI + "/animes/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(animes.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(animes.getBody()).isNull();
    }

    @Test
    public void test04GetAnimeWithEmptyPersistentSystem() {
        // Given - Instantiate title of anime.
        String title = "Persistent System 2 : Return of the Empty Row";

        // When - Get one anime from persistent system.
        ResponseEntity<Anime> anime = this.restTemplate.getForEntity(REST_SERVICE_URI + "/animes/search/title/" + title + "/" + 5, Anime.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(anime.getBody()).isNull();
    }

    @Test
    public void test05AddAnimeOnPersistentSystem() {
        // Given - Instantiate Anime at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/animes/search/id/" + id;

        String title = "Sakura, chasseuse de cartes";
        String originalTitle = "Kādokyaputā Sakura";
        String synopsis = "Cardcaptor Sakura 4ever <3";

        List<MediaGenre> categories = new ArrayList<MediaGenre>();
        categories.add(MediaGenre.FANTASY);
        categories.add(MediaGenre.ROMANTIC);
        categories.add(MediaGenre.COMEDY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Eizo", "Kondo"));
        producers.add(new Producer("Tatsuya", "Ono"));
        producers.add(new Producer("Yasuko", "Uchisawa"));
        producers.add(new Producer("Akira", "Watanabe"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Morio", "Asaka"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.ja);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);

        int averageEpisodeRuntime = 22;
        int numberOfSeasons = 3;
        int currentSeason   = 1;
        int numberOfEpisode = 36;
        int maxEpisodes = 70;

        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime anime = new Anime(
                title, originalTitle, synopsis,
                directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        // When - Send anime to save it on persistent system.
        ResponseEntity<Anime> responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/animes/", anime, Anime.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders().getLocation().toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void test06AddAnimeAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate Anime at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Sakura, chasseuse de cartes";
        String originalTitle = "Kādokyaputā Sakura";
        String synopsis = "Cardcaptor Sakura 4ever <3";

        List<MediaGenre> categories = new ArrayList<MediaGenre>();
        categories.add(MediaGenre.FANTASY);
        categories.add(MediaGenre.ROMANTIC);
        categories.add(MediaGenre.COMEDY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Eizo", "Kondo"));
        producers.add(new Producer("Tatsuya", "Ono"));
        producers.add(new Producer("Yasuko", "Uchisawa"));
        producers.add(new Producer("Akira", "Watanabe"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Morio", "Asaka"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.ja);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);

        int averageEpisodeRuntime = 22;
        int numberOfSeasons = 3;
        int currentSeason   = 1;
        int numberOfEpisode = 36;
        int maxEpisodes = 70;

        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime animes = new Anime(
                title, originalTitle, synopsis,
                directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        ResponseEntity<AnimeException> responseEntity = null;
        // When - Send animes to save it on persistent system.
        try {
            responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/animes/", animes, AnimeException.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test07GetOneAnime() throws UnsupportedEncodingException {
        // Given - Instantiate Anime at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int directorExpected = 1;
        int producersExpected = 4;

        String title = "Sakura, chasseuse de cartes";
        String originalTitle = "Kādokyaputā Sakura";
        String synopsis = "Cardcaptor Sakura 4ever <3";

        List<MediaGenre> categories = new ArrayList<MediaGenre>();
        categories.add(MediaGenre.FANTASY);
        categories.add(MediaGenre.ROMANTIC);
        categories.add(MediaGenre.COMEDY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Eizo", "Kondo"));
        producers.add(new Producer("Tatsuya", "Ono"));
        producers.add(new Producer("Yasuko", "Uchisawa"));
        producers.add(new Producer("Akira", "Watanabe"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Morio", "Asaka"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.ja);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);

        int averageEpisodeRuntime = 22;
        int numberOfSeasons = 3;
        int currentSeason   = 1;
        int numberOfEpisode = 36;
        int maxEpisodes = 70;

        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime animes = new Anime(
                title, originalTitle, synopsis,
                directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        // When - Get anime from persistent system.
        ResponseEntity<Anime> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/animes/search/title/" + URLEncoder.encode(animes.getTitle(), URL_ENCODER) + "/" + animes.getCurrentSeason(), Anime.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and anime retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(animes.getTitle());
        assertThat(responseEntity.getBody().getReleaseDate().get(Calendar.YEAR)).isEqualTo(animes.getReleaseDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getEndDate().get(Calendar.YEAR)).isEqualTo(animes.getEndDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getGenres()).isEqualTo(animes.getGenres());
        assertThat(responseEntity.getBody().getSupports()).isEqualTo(animes.getSupports());
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(animes.getSynopsis());
        assertThat(responseEntity.getBody().getAverageEpisodeRuntime()).isEqualTo(animes.getAverageEpisodeRuntime());
        assertThat(responseEntity.getBody().getCurrentSeason()).isEqualTo(animes.getCurrentSeason());
        assertThat(responseEntity.getBody().getNumberOfSeasons()).isEqualTo(animes.getNumberOfSeasons());
        assertThat(responseEntity.getBody().getNumberOfEpisode()).isEqualTo(animes.getNumberOfEpisode());
        assertThat(responseEntity.getBody().getDirectors().size()).isEqualTo(directorExpected);
        assertThat(responseEntity.getBody().getProducers().size()).isEqualTo(producersExpected);

        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void test08GetAnimeNotFoundOnPersistentSystem() {
        // Given - Instantiate Anime at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Sakura, chasseuse de jetons";
        String originalTitle = "Kādokyaputā Sakura";
        String synopsis = "Cardcaptor Sakura 4ever <3";

        List<MediaGenre> categories = new ArrayList<MediaGenre>();
        categories.add(MediaGenre.FANTASY);
        categories.add(MediaGenre.ROMANTIC);
        categories.add(MediaGenre.COMEDY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Eizo", "Kondo"));
        producers.add(new Producer("Tatsuya", "Ono"));
        producers.add(new Producer("Yasuko", "Uchisawa"));
        producers.add(new Producer("Akira", "Watanabe"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Morio", "Asaka"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.ja);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);

        int averageEpisodeRuntime = 22;
        int numberOfSeasons = 3;
        int currentSeason   = 1;
        int numberOfEpisode = 36;
        int maxEpisodes = 70;

        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime animes = new Anime(
                title, originalTitle, synopsis,
                directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        // When - Get anime from persistent system.
        ResponseEntity<Anime> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/search/title/" + URLEncoder.encode(animes.getTitle(), URL_ENCODER), Anime.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test09GetAllAnimes() {
        // Given - Instantiate a anime to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Sakura, chasseuse de cartes";
        String originalTitle = "Kādokyaputā Sakura";
        String synopsis = "Cardcaptor Sakura 4ever <3";

        List<MediaGenre> categories = new ArrayList<MediaGenre>();
        categories.add(MediaGenre.FANTASY);
        categories.add(MediaGenre.ROMANTIC);
        categories.add(MediaGenre.COMEDY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Eizo", "Kondo"));
        producers.add(new Producer("Tatsuya", "Ono"));
        producers.add(new Producer("Yasuko", "Uchisawa"));
        producers.add(new Producer("Akira", "Watanabe"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Morio", "Asaka"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.ja);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);

        int averageEpisodeRuntime = 22;
        int numberOfSeasons = 3;
        int currentSeason   = 2;
        int numberOfEpisode = 10;
        int maxEpisodes = 70;

        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime animes = new Anime(
                title, originalTitle, synopsis,
                directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        this.restTemplate.postForEntity(REST_SERVICE_URI + "/animes/", animes, Anime.class);

        // When - Get all animess from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/animes/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void test10UpdateAnime() throws UnsupportedEncodingException {
        // Given - Instantiate an anime to update on persistent system.
        int id = 1;
        String title = "Sakura, chasseuse de cartes";
        String originalTitle = "Kādokyaputā Sakura";
        String synopsis = "Cardcaptor Sakura 4ever <3. Kero Chan <3<3<3<3<3<3<3";

        List<MediaGenre> categories = new ArrayList<MediaGenre>();
        categories.add(MediaGenre.FANTASY);
        categories.add(MediaGenre.ROMANTIC);
        categories.add(MediaGenre.COMEDY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Eizo", "Kondo"));
        producers.add(new Producer("Tatsuya", "Ono"));
        producers.add(new Producer("Yasuko", "Uchisawa"));
        producers.add(new Producer("Akira", "Watanabe"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Morio", "Asaka"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.ja);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);

        int averageEpisodeRuntime = 22;
        int numberOfSeasons = 3;
        int currentSeason   = 1;
        int numberOfEpisode = 36;
        int maxEpisodes = 70;

        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime animes = new Anime(
                id, title, originalTitle, synopsis,
                directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );
        this.restTemplate.put(REST_SERVICE_URI + "/animes/" + animes.getId(), animes, Anime.class);

        // When - Get anime update and check if the difference appear.
        ResponseEntity<Anime> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/animes/search/title/" + URLEncoder.encode(title, URL_ENCODER)+ "/" + animes.getCurrentSeason(), Anime.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void test11UpdateAnimeNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate an anime to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Sakura, chasseuse de cartes";
        String originalTitle = "Kādokyaputā Sakura";
        String synopsis = "Cardcaptor Sakura 4ever <3";

        List<MediaGenre> categories = new ArrayList<MediaGenre>();
        categories.add(MediaGenre.FANTASY);
        categories.add(MediaGenre.ROMANTIC);
        categories.add(MediaGenre.COMEDY);

        Set<Producer> producers = new HashSet<Producer>();
        producers.add(new Producer("Eizo", "Kondo"));
        producers.add(new Producer("Tatsuya", "Ono"));
        producers.add(new Producer("Yasuko", "Uchisawa"));
        producers.add(new Producer("Akira", "Watanabe"));

        Set<Director> directors = new HashSet<Director>();
        directors.add(new Director("Morio", "Asaka"));

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.DVD);

        List<LanguageCode> languageSpoken = new ArrayList<LanguageCode>();
        languageSpoken.add(LanguageCode.fr);
        languageSpoken.add(LanguageCode.ja);

        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);

        int averageEpisodeRuntime = 22;
        int numberOfSeasons = 3;
        int currentSeason   = 1;
        int numberOfEpisode = 36;
        int maxEpisodes = 70;

        Calendar startDate = new GregorianCalendar(1998, GregorianCalendar.APRIL, 7);
        Calendar endDate   = new GregorianCalendar(2000, GregorianCalendar.MARCH, 21);

        Anime animes = new Anime(
                title, originalTitle, synopsis,
                directors, producers, categories, supports, languageSpoken, subtitles,
                startDate, endDate, numberOfSeasons, currentSeason, maxEpisodes, numberOfEpisode, averageEpisodeRuntime
        );

        try {
            // When - Try to update anime not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/animes/" + animes.getId(), animes, Anime.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test12DeleteAnime() {
        // Given - id of anime at delete and all elements expected.
        int id = 2;
        String title = "Star Gate SG2";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete animes
        this.restTemplate.delete(REST_SERVICE_URI + "/animes/" + id);

        try {
            ResponseEntity<Anime> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/animes/search/title/" + URLEncoder.encode(title, URL_ENCODER), Anime.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test13DeleteAnimesNotFoundOnPersistentSystem() {
        // Given - id of animes at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete animes
            this.restTemplate.delete(REST_SERVICE_URI + "/animes/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }
}
