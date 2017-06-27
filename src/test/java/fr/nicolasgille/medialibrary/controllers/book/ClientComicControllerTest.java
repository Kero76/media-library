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
package fr.nicolasgille.medialibrary.controllers.book;

import fr.nicolasgille.medialibrary.exceptions.book.ComicException;
import fr.nicolasgille.medialibrary.models.book.Comic;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.models.common.person.Illustrator;
import fr.nicolasgille.medialibrary.models.components.BookFormat;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
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
 * Unit class test used to test ComicController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
public class ClientComicControllerTest {

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
            this.restTemplate.delete(REST_SERVICE_URI + "/comics/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void testUpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding book.
        String messageExcepted = "404 null";
        int id = 666;
        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Comic comic = new Comic(
                id, "", "", "", releaseDate, 0, "",
                new HashSet<Author>(), new HashSet<Publisher>(), new ArrayList<MediaGenre>(), new ArrayList<MediaSupport>(), BookFormat.CLASSICAL,
                17, 15, new HashSet<Illustrator>()
        );

        // When - Try to update comic.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/comics/" + comic.getId(), comic);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void testGetAllComicsWithEmptyPersistentSystem() {
        // Given / When - Get all comics from persistent system.
        ResponseEntity<List> videoGames = this.restTemplate.getForEntity(REST_SERVICE_URI + "/comics/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(videoGames.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(videoGames.getBody()).isNull();
    }

    @Test
    public void testGetComicWithEmptyPersistentSystem() {
        // Given - Instantiate title of comic.
        String title = "Nisekoi";

        // When - Get one comic from persistent system.
        ResponseEntity<Comic> comic = this.restTemplate.getForEntity(REST_SERVICE_URI + "/comics/search/title/" + title, Comic.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(comic.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(comic.getBody()).isNull();
    }

    @Test
    public void testAddComicOnPersistentSystem() {
        // Given - Instantiate comic at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/comics/search/title/" + id;

        String title = "Nisekoi : Amours, Mensonges et Yakuzas";
        String originalTitle = "Nisekoi";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2011, Calendar.NOVEMBER, 7);
        int nbPages = 196;
        String isbn = "978-2-82032-835-9";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Naoshi", "Komi"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Shūeisha"));

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.ROMANTIC);
        genres.add(MediaGenre.COMEDY);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.POCKET;
        int volumes = 25;
        int currentVolume = 24;

        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        illustrators.add(new Illustrator("Naoshi", "Komi"));

        Comic comic = new Comic(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers,
                genres, supports, format, volumes, currentVolume, illustrators
        );

        // When - Send comic to save it on persistent system.
        ResponseEntity<Comic> responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/comics/", comic, Comic.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders().getLocation().toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void testAddComicAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate comic at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Nisekoi : Amours, Mensonges et Yakuzas";
        String originalTitle = "Nisekoi";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2011, Calendar.NOVEMBER, 7);
        int nbPages = 196;
        String isbn = "978-2-82032-835-9";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Naoshi", "Komi"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Shūeisha"));

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.ROMANTIC);
        genres.add(MediaGenre.COMEDY);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.POCKET;
        int volumes = 25;
        int currentVolume = 24;

        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        illustrators.add(new Illustrator("Naoshi", "Komi"));

        Comic comic = new Comic(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers,
                genres, supports, format, volumes, currentVolume, illustrators
        );

        ResponseEntity<ComicException> responseEntity = null;
        // When - Send comic to save it on persistent system.
        try {
            responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/comics/", comic, ComicException.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void testGetOneComic() throws UnsupportedEncodingException {
        // Given - Instantiate comic at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Nisekoi : Amours, Mensonges et Yakuzas";
        String originalTitle = "Nisekoi";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2011, Calendar.NOVEMBER, 7);
        int nbPages = 196;
        String isbn = "978-2-82032-835-9";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Naoshi", "Komi"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Shūeisha"));

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.ROMANTIC);
        genres.add(MediaGenre.COMEDY);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.POCKET;
        int volumes = 25;
        int currentVolume = 24;

        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        illustrators.add(new Illustrator("Naoshi", "Komi"));

        Comic comic = new Comic(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers,
                genres, supports, format, volumes, currentVolume, illustrators
        );

        // When - Get comic from persistent system.
        ResponseEntity<Comic> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/comics/search/title/" + URLEncoder.encode(comic.getTitle(), URL_ENCODER), Comic.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and comic retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(comic.getTitle());
        assertThat(responseEntity.getBody().getOriginalTitle()).isEqualTo(comic.getOriginalTitle());
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(comic.getSynopsis());
        assertThat(responseEntity.getBody().getReleaseDate().get(Calendar.YEAR)).isEqualTo(comic.getReleaseDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getGenres()).isEqualTo(comic.getGenres());
        assertThat(responseEntity.getBody().getSupports()).isEqualTo(comic.getSupports());
        assertThat(responseEntity.getBody().getAuthors().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getPublisher().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getFormat()).isEqualTo(format);
        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void testGetComicNotFoundOnPersistentSystem() {
        // Given - Instantiate comic at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Nisekoi : Amours, Mensonges et Yakuzas";
        String originalTitle = "Nisekoi";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2011, Calendar.NOVEMBER, 7);
        int nbPages = 196;
        String isbn = "978-2-82032-809-0";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Naoshi", "Komi"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Shūeisha"));

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.ROMANTIC);
        genres.add(MediaGenre.COMEDY);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.POCKET;
        int volumes = 25;
        int currentVolume = 23;

        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        illustrators.add(new Illustrator("Naoshi", "Komi"));

        Comic comic = new Comic(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers,
                genres, supports, format, volumes, currentVolume, illustrators
        );

        // When - Get comic from persistent system.
        ResponseEntity<Comic> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/comics/search/title/" + URLEncoder.encode(comic.getTitle(), URL_ENCODER), Comic.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void testGetAllComics() {
        // Given - Instantiate a comic to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Nisekoi : Amours, Mensonges et Yakuzas";
        String originalTitle = "Nisekoi";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2011, Calendar.NOVEMBER, 7);
        int nbPages = 196;
        String isbn = "978-2-82032-809-0";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Naoshi", "Komi"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Shūeisha"));

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.ROMANTIC);
        genres.add(MediaGenre.COMEDY);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.POCKET;
        int volumes = 25;
        int currentVolume = 23;

        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        illustrators.add(new Illustrator("Naoshi", "Komi"));

        Comic comic = new Comic(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers,
                genres, supports, format, volumes, currentVolume, illustrators
        );
        this.restTemplate.postForEntity(REST_SERVICE_URI + "/comics/", comic, Comic.class);

        // When - Get all comics from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/comics/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void updateComic() throws UnsupportedEncodingException {
        // Given - Instantiate a comic to update on persistent system.
        int id = 2;
        String title = "Nisekoi : Amours, Mensonges et Yakuzas";
        String originalTitle = "Nisekoi";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2011, Calendar.NOVEMBER, 7);
        int nbPages = 196;
        String isbn = "978-2-82032-809-0";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Naoshi", "Komi"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Shūeisha"));

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.ROMANTIC);
        genres.add(MediaGenre.COMEDY);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.POCKET;
        int volumes = 25;
        int currentVolume = 5;

        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        illustrators.add(new Illustrator("Naoshi", "Komi"));

        Comic comic = new Comic(
                id, title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers,
                genres, supports, format, volumes, currentVolume, illustrators
        );
        this.restTemplate.put(REST_SERVICE_URI + "/comics/" + comic.getId(), comic, Comic.class);

        // When - Get comic update and check if the difference appear.
        ResponseEntity<Comic> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/comics/search/title/" + URLEncoder.encode(title, URL_ENCODER) + "/" + comic.getCurrentVolume(), Comic.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void updateComicNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate a comic to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Nisekoi : Amours, Mensonges et Yakuzas";
        String originalTitle = "Nisekoi";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2011, Calendar.NOVEMBER, 7);
        int nbPages = 196;
        String isbn = "978-2-82032-809-0";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Naoshi", "Komi"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Shūeisha"));

        List<MediaGenre> genres = new ArrayList<MediaGenre>();
        genres.add(MediaGenre.ROMANTIC);
        genres.add(MediaGenre.COMEDY);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.POCKET;
        int volumes = 25;
        int currentVolume = 23;

        Set<Illustrator> illustrators = new HashSet<Illustrator>();
        illustrators.add(new Illustrator("Naoshi", "Komi"));

        Comic comic = new Comic(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers,
                genres, supports, format, volumes, currentVolume, illustrators
        );

        try {
            // When - Try to update comic not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/comics/" + comic.getId(), comic, Comic.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void deleteComic() {
        // Given - id of comic at delete and all elements expected.
        int id = 2;
        String title = "Anges et Démons";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete comic
        this.restTemplate.delete(REST_SERVICE_URI + "/comics/" + id);

        try {
            ResponseEntity<Comic> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/comics/search/title/" + URLEncoder.encode(title, URL_ENCODER), Comic.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void deleteComicNotFoundOnPersistentSystem() {
        // Given - id of comic at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete comic
            this.restTemplate.delete(REST_SERVICE_URI + "/comics/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }
}
