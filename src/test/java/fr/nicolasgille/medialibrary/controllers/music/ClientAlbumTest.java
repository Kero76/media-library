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
package fr.nicolasgille.medialibrary.controllers.music;

import fr.nicolasgille.medialibrary.exceptions.music.AlbumException;
import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import fr.nicolasgille.medialibrary.models.common.person.Singer;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.genre.MusicGenre;
import fr.nicolasgille.medialibrary.models.music.Album;
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
 * Unit class test used to test AlbumController class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientAlbumTest {

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
            this.restTemplate.delete(REST_SERVICE_URI + "/musics/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test02UpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding album.
        String messageExcepted = "404 null";
        int id = 666;
        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Album album = new Album(
                id, "My Title", "", releaseDate, new ArrayList<MusicGenre>(), new ArrayList<MediaSupport>(),
                12, 48.42, new HashSet<LabelRecords>(), new HashSet<Singer>()
        );

        // When - Try to update album.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/musics/" + album.getId(), album);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test03GetAllAlbumsWithEmptyPersistentSystem() {
        // Given / When - Get all albums from persistent system.
        ResponseEntity<List> albums = this.restTemplate.getForEntity(REST_SERVICE_URI + "/musics/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(albums.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(albums.getBody()).isNull();
    }

    @Test
    public void test04GetAlbumWithEmptyPersistentSystem() {
        // Given - Instantiate title of album.
        String title = "Crusader";

        // When - Get one album from persistent system.
        ResponseEntity<Album> cartoon = this.restTemplate.getForEntity(REST_SERVICE_URI + "/musics/search/title/" + title, Album.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(cartoon.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(cartoon.getBody()).isNull();
    }

    @Test
    public void test05AddAlbumOnPersistentSystem() {
        // Given - Instantiate Album at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/musics/search/id/" + id;

        String title = "Crusader";
        String synopsis = "";

        List<MusicGenre> genres = new ArrayList<MusicGenre>();
        genres.add(MusicGenre.METAL);

        Calendar releaseDate = new GregorianCalendar(1984, GregorianCalendar.APRIL, 16);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        int nbTracks = 10;
        double length = 39.10;

        Set<LabelRecords> labelRecords = new HashSet<LabelRecords>();
        labelRecords.add(new LabelRecords("EMI"));

        Set<Singer> group = new HashSet<Singer>();
        group.add(new Singer("Saxon", ""));

        Album album = new Album(
            title, synopsis, releaseDate, genres, supports, nbTracks, length, labelRecords, group
        );

        // When - Send album to save it on persistent system.
        ResponseEntity<Album> responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/musics/", album, Album.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders().getLocation().toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void test06AddAlbumAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate Album at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Crusader";
        String synopsis = "";

        List<MusicGenre> genres = new ArrayList<MusicGenre>();
        genres.add(MusicGenre.METAL);

        Calendar releaseDate = new GregorianCalendar(1984, GregorianCalendar.APRIL, 16);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        int nbTracks = 10;
        double length = 39.10;

        Set<LabelRecords> labelRecords = new HashSet<LabelRecords>();
        labelRecords.add(new LabelRecords("EMI"));

        Set<Singer> group = new HashSet<Singer>();
        group.add(new Singer("Saxon", ""));

        Album album = new Album(
                title, synopsis, releaseDate, genres, supports, nbTracks, length, labelRecords, group
        );

        ResponseEntity<AlbumException> responseEntity = null;
        // When - Send album to save it on persistent system.
        try {
            responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/musics/", album, AlbumException.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test07GetOneAlbum() throws UnsupportedEncodingException {
        // Given - Instantiate Album at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Crusader";
        String synopsis = "";

        List<MusicGenre> genres = new ArrayList<MusicGenre>();
        genres.add(MusicGenre.METAL);

        Calendar releaseDate = new GregorianCalendar(1984, GregorianCalendar.APRIL, 16);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        int nbTracks = 10;
        double length = 39.10;

        Set<LabelRecords> labelRecords = new HashSet<LabelRecords>();
        labelRecords.add(new LabelRecords("EMI"));

        Set<Singer> group = new HashSet<Singer>();
        group.add(new Singer("Saxon", ""));

        Album album = new Album(
                title, synopsis, releaseDate, genres, supports, nbTracks, length, labelRecords, group
        );

        // When - Get album from persistent system.
        ResponseEntity<Album> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/musics/search/title/" + URLEncoder.encode(album.getTitle(), URL_ENCODER), Album.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and album retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(album.getTitle());
        assertThat(responseEntity.getBody().getReleaseDate().get(Calendar.YEAR)).isEqualTo(album.getReleaseDate().get(Calendar.YEAR));
        assertThat(responseEntity.getBody().getGenres()).isEqualTo(album.getGenres());
        assertThat(responseEntity.getBody().getSupports()).isEqualTo(album.getSupports());
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(album.getSynopsis());
        assertThat(responseEntity.getBody().getLabelRecords().size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody().getSingers().size()).isEqualTo(sizeExpected);
        System.out.println(responseEntity.getBody().toString());
    }

    @Test
    public void test08GetAlbumNotFoundOnPersistentSystem() {
        // Given - Instantiate Album at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Lionheart";
        String synopsis = "";

        List<MusicGenre> genres = new ArrayList<MusicGenre>();
        genres.add(MusicGenre.METAL);

        Calendar releaseDate = new GregorianCalendar(1984, GregorianCalendar.APRIL, 16);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        int nbTracks = 10;
        double length = 39.10;

        Set<LabelRecords> labelRecords = new HashSet<LabelRecords>();
        labelRecords.add(new LabelRecords("EMI"));

        Set<Singer> group = new HashSet<Singer>();
        group.add(new Singer("Saxon", ""));

        Album album = new Album(
                title, synopsis, releaseDate, genres, supports, nbTracks, length, labelRecords, group
        );

        // When - Get album from persistent system.
        ResponseEntity<Album> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/musics/search/title/" + URLEncoder.encode(album.getTitle(), URL_ENCODER), Album.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test09GetAllAlbums() {
        // Given - Instantiate an album to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Lionheart";
        String synopsis = "";

        List<MusicGenre> genres = new ArrayList<MusicGenre>();
        genres.add(MusicGenre.METAL);

        Calendar releaseDate = new GregorianCalendar(2004, GregorianCalendar.SEPTEMBER, 28);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        int nbTracks = 10;
        double length = 45.10;

        Set<LabelRecords> labelRecords = new HashSet<LabelRecords>();
        labelRecords.add(new LabelRecords("SPV"));
        labelRecords.add(new LabelRecords("Steamhammer"));

        Set<Singer> group = new HashSet<Singer>();
        group.add(new Singer("Saxon", ""));

        Album album = new Album(
                title, synopsis, releaseDate, genres, supports, nbTracks, length, labelRecords, group
        );
        this.restTemplate.postForEntity(REST_SERVICE_URI + "/musics/", album, Album.class);

        // When - Get all albums from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/musics/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody().size()).isEqualTo(sizeExpected);
    }

    @Test
    public void test10UpdateAlbum() throws UnsupportedEncodingException {
        // Given - Instantiate an album to update on persistent system.
        int id = 2;
        String title = "Lionheart";
        String synopsis = "Witchfinder General";

        List<MusicGenre> genres = new ArrayList<MusicGenre>();
        genres.add(MusicGenre.METAL);

        Calendar releaseDate = new GregorianCalendar(2004, GregorianCalendar.SEPTEMBER, 28);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        int nbTracks = 10;
        double length = 45.10;

        Set<LabelRecords> labelRecords = new HashSet<LabelRecords>();
        labelRecords.add(new LabelRecords("SPV"));
        labelRecords.add(new LabelRecords("Steamhammer"));

        Set<Singer> group = new HashSet<Singer>();
        group.add(new Singer("Saxon", ""));

        Album album = new Album(
                id, title, synopsis, releaseDate, genres, supports, nbTracks, length, labelRecords, group
        );
        this.restTemplate.put(REST_SERVICE_URI + "/musics/" + album.getId(), album, Album.class);

        // When - Get album update and check if the difference appear.
        ResponseEntity<Album> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/musics/search/title/" + URLEncoder.encode(title, URL_ENCODER), Album.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody().getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void test11UpdateAlbumNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate an album to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Lionheart";
        String synopsis = "Witchfinder General";

        List<MusicGenre> genres = new ArrayList<MusicGenre>();
        genres.add(MusicGenre.METAL);

        Calendar releaseDate = new GregorianCalendar(2004, GregorianCalendar.SEPTEMBER, 28);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.CD);

        int nbTracks = 10;
        double length = 45.10;

        Set<LabelRecords> labelRecords = new HashSet<LabelRecords>();
        labelRecords.add(new LabelRecords("SPV"));
        labelRecords.add(new LabelRecords("Steamhammer"));

        Set<Singer> group = new HashSet<Singer>();
        group.add(new Singer("Saxon", ""));

        Album album = new Album(
                id, title, synopsis, releaseDate, genres, supports, nbTracks, length, labelRecords, group
        );

        try {
            // When - Try to update album not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/musics/" + album.getId(), album, Album.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test12DeleteAlbum() {
        // Given - id of album at delete and all elements expected.
        int id = 2;
        String title = "Lionheart";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete album
        this.restTemplate.delete(REST_SERVICE_URI + "/musics/" + id);

        try {
            ResponseEntity<Album> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/musics/search/title/" + URLEncoder.encode(title, URL_ENCODER), Album.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test13DeleteAlbumNotFoundOnPersistentSystem() {
        // Given - id of album at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete album
            this.restTemplate.delete(REST_SERVICE_URI + "/musics/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }
}
