/*
 * MediaLibrary.
 * Copyright (C) 2017 Nicolas GILLE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.nicolasgille.medialibrary.controllers.book;

import fr.nicolasgille.medialibrary.exceptions.book.BookException;
import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.models.components.BookFormat;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.genre.BookGenre;
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
 * Unit class test used to test BookController class.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.4
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientBookControllerTest {

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
            this.restTemplate.delete(REST_SERVICE_URI + "/books/" + id);
        } catch (Exception e) {
            // Then - Exception throws give the expected message.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test02UpdateWithEmptyPersistentSystem() {
        // Given - Instantiate id at update and corresponding book.
        String messageExcepted = "404 null";
        int id = 666;
        Calendar releaseDate = new GregorianCalendar(2016, GregorianCalendar.APRIL, GregorianCalendar.THURSDAY);

        Book book = new Book(
                id, "", "", "", releaseDate, 0, "", new HashSet<Author>(), new HashSet<Publisher>(),
                new ArrayList<BookGenre>(), new ArrayList<MediaSupport>(), BookFormat.CLASSICAL
        );

        // When - Try to update book.
        try {
            this.restTemplate.put(REST_SERVICE_URI + "/books/" + book.getId(), book);
        } catch (Exception e) {
            // Then - Exception throws is null.
            assertThat(e.getMessage()).isEqualTo(messageExcepted);
        }
    }

    @Test
    public void test03GetAllBooksWithEmptyPersistentSystem() {
        // Given / When - Get all books from persistent system.
        ResponseEntity<List> videoGames = this.restTemplate.getForEntity(REST_SERVICE_URI + "/books/", List.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(videoGames.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(videoGames.getBody()).isNull();
    }

    @Test
    public void test04GetBookWithEmptyPersistentSystem() {
        // Given - Instantiate title of book.
        String title = "Da Vinci Code";

        // When - Get one book from persistent system.
        ResponseEntity<Book> book =
                this.restTemplate.getForEntity(REST_SERVICE_URI + "/books/search/title/" + title, Book.class);

        // Then - Error HTTP.No_CONTENT was encounter.
        assertThat(book.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(book.getBody()).isNull();
    }

    @Test
    public void test05AddBookOnPersistentSystem() {
        // Given - Instantiate book at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CREATED;
        int id = 1;
        String uriExpected = "http://localhost:8080/media-library/books/search/id/" + id;

        String title = "Da Vinci Code";
        String originalTitle = "The Da Vinci Code";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2004, Calendar.MARCH, 4);
        int nbPages = 571;
        String isbn = "0-385-50420-9";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Dan", "Brown"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Jean-Claude Lattès"));

        List<BookGenre> genres = new ArrayList<BookGenre>();
        genres.add(BookGenre.THRILLER);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.CLASSICAL;

        Book book = new Book(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports,
                format
        );

        // When - Send book to save it on persistent system.
        ResponseEntity<Book> responseEntity =
                this.restTemplate.postForEntity(REST_SERVICE_URI + "/books/", book, Book.class);

        // Then - Compare HTTP status and uri.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getHeaders()
                                 .getLocation()
                                 .toASCIIString()).isEqualTo(uriExpected);
    }

    @Test
    public void test06AddBookAlreadyPresentOnPersistentSystem() {
        // Given - Instantiate book at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.CONFLICT;
        String httpClientExceptionExpected = "409 null";

        String title = "Da Vinci Code";
        String originalTitle = "The Da Vinci Code";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2004, Calendar.MARCH, 4);
        int nbPages = 571;
        String isbn = "0-385-50420-9";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Dan", "Brown"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Jean-Claude Lattès"));

        List<BookGenre> genres = new ArrayList<BookGenre>();
        genres.add(BookGenre.THRILLER);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.CLASSICAL;

        Book book = new Book(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports,
                format
        );

        ResponseEntity<BookException> responseEntity = null;
        // When - Send book to save it on persistent system.
        try {
            responseEntity = this.restTemplate.postForEntity(REST_SERVICE_URI + "/books/", book, BookException.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test07GetOneBook() throws UnsupportedEncodingException {
        // Given - Instantiate book at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 1;

        String title = "Da Vinci Code";
        String originalTitle = "The Da Vinci Code";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2004, Calendar.MARCH, 4);
        int nbPages = 571;
        String isbn = "0-385-50420-9";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Dan", "Brown"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Jean-Claude Lattès"));

        List<BookGenre> genres = new ArrayList<BookGenre>();
        genres.add(BookGenre.THRILLER);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.CLASSICAL;

        Book book = new Book(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports,
                format
        );

        // When - Get book from persistent system.
        ResponseEntity<Book> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(
                    REST_SERVICE_URI + "/books/search/title/" + URLEncoder.encode(book.getTitle(), URL_ENCODER),
                    Book.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Then - Compare Http code and book retrieve.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody()
                                 .getTitle()).isEqualTo(book.getTitle());
        assertThat(responseEntity.getBody()
                                 .getOriginalTitle()).isEqualTo(book.getOriginalTitle());
        assertThat(responseEntity.getBody()
                                 .getSynopsis()).isEqualTo(book.getSynopsis());
        assertThat(responseEntity.getBody()
                                 .getReleaseDate()
                                 .get(Calendar.YEAR)).isEqualTo(book.getReleaseDate()
                                                                    .get(Calendar.YEAR));
        assertThat(responseEntity.getBody()
                                 .getGenres()).isEqualTo(book.getGenres());
        assertThat(responseEntity.getBody()
                                 .getSupports()).isEqualTo(book.getSupports());
        assertThat(responseEntity.getBody()
                                 .getAuthors()
                                 .size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody()
                                 .getPublishers()
                                 .size()).isEqualTo(sizeExpected);
        assertThat(responseEntity.getBody()
                                 .getFormat()).isEqualTo(format);
        System.out.println(responseEntity.getBody()
                                         .toString());
    }

    @Test
    public void test08GetBookNotFoundOnPersistentSystem() {
        // Given - Instantiate book at insert on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        String title = "Anges et Démons";
        String originalTitle = "Angels & Demons";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2004, Calendar.MARCH, 4);
        int nbPages = 571;
        String isbn = "0-385-50420-9";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Dan", "Brown"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Jean-Claude Lattès"));

        List<BookGenre> genres = new ArrayList<BookGenre>();
        genres.add(BookGenre.THRILLER);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.CLASSICAL;

        Book book = new Book(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports,
                format
        );

        // When - Get book from persistent system.
        ResponseEntity<Book> responseEntity = null;
        try {
            responseEntity = this.restTemplate.getForEntity(
                    REST_SERVICE_URI + "/books/search/title/" + URLEncoder.encode(book.getTitle(), URL_ENCODER),
                    Book.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test09GetAllBooks() {
        // Given - Instantiate a book to push on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 2;

        String title = "Anges et Démons";
        String originalTitle = "Angels and Demons";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2004, Calendar.MARCH, 4);
        int nbPages = 571;
        String isbn = "0-671-02735-2";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Dan", "Brown"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Jean-Claude Lattès"));

        List<BookGenre> genres = new ArrayList<BookGenre>();
        genres.add(BookGenre.THRILLER);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.CLASSICAL;

        Book book = new Book(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports,
                format
        );
        this.restTemplate.postForEntity(REST_SERVICE_URI + "/books/", book, Book.class);

        // When - Get all books from persistent system.
        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity(REST_SERVICE_URI + "/books/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody()
                                 .size()).isEqualTo(sizeExpected);
    }

    @Test
    public void test10UpdateBook() throws UnsupportedEncodingException {
        // Given - Instantiate a book to update on persistent system.
        int id = 2;
        String title = "Anges et Démons";
        String originalTitle = "Angels and Demons";
        String synopsis = "Robert Langdon 4ever.";
        Calendar releaseDate = new GregorianCalendar(2004, Calendar.MARCH, 4);
        int nbPages = 571;
        String isbn = "0-671-02735-2";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Dan", "Brown"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Jean-Claude Lattès"));

        List<BookGenre> genres = new ArrayList<BookGenre>();
        genres.add(BookGenre.THRILLER);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.CLASSICAL;

        Book book = new Book(
                id, title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports,
                format
        );
        this.restTemplate.put(REST_SERVICE_URI + "/books/" + book.getId(), book, Book.class);

        // When - Get book update and check if the difference appear.
        ResponseEntity<Book> responseEntity = this.restTemplate.getForEntity(
                REST_SERVICE_URI + "/books/search/title/" + URLEncoder.encode(title, URL_ENCODER), Book.class);

        // Then - Compare synopsis.
        assertThat(responseEntity.getBody()
                                 .getSynopsis()).isEqualTo(synopsis);
    }

    @Test
    public void test11UpdateBookNotFoundOnPersistentSystem() throws UnsupportedEncodingException {
        // Given - Instantiate a book to update on persistent system.
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;

        int id = 666;
        String title = "Anges et Démons";
        String originalTitle = "Angels and Demons";
        String synopsis = "";
        Calendar releaseDate = new GregorianCalendar(2004, Calendar.MARCH, 4);
        int nbPages = 571;
        String isbn = "0-671-02735-2";

        Set<Author> authors = new HashSet<Author>();
        authors.add(new Author("Dan", "Brown"));

        Set<Publisher> publishers = new HashSet<Publisher>();
        publishers.add(new Publisher("Jean-Claude Lattès"));

        List<BookGenre> genres = new ArrayList<BookGenre>();
        genres.add(BookGenre.THRILLER);

        List<MediaSupport> supports = new ArrayList<MediaSupport>();
        supports.add(MediaSupport.PAPER);

        BookFormat format = BookFormat.CLASSICAL;

        Book book = new Book(
                title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports,
                format
        );

        try {
            // When - Try to update book not present on persistent system.
            this.restTemplate.put(REST_SERVICE_URI + "/books/" + book.getId(), book, Book.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare http code error.
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test12DeleteBook() {
        // Given - id of book at delete and all elements expected.
        int id = 2;
        String title = "Anges et Démons";
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        // When - Delete book
        this.restTemplate.delete(REST_SERVICE_URI + "/books/" + id);

        try {
            ResponseEntity<Book> responseEntity = this.restTemplate.getForEntity(
                    REST_SERVICE_URI + "/books/search/title/" + URLEncoder.encode(title, URL_ENCODER), Book.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }

    @Test
    public void test13DeleteBookNotFoundOnPersistentSystem() {
        // Given - id of book at delete and all elements expected.
        int id = 2;
        HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
        String httpClientExceptionExpected = "404 null";

        try {
            // When - Delete book
            this.restTemplate.delete(REST_SERVICE_URI + "/books/" + id);
        } catch (HttpClientErrorException httpClientErrorException) {
            // Then - Compare HTTP code error and message.
            assertThat(httpClientErrorException.getMessage()).isEqualTo(httpClientExceptionExpected);
            assertThat(httpClientErrorException.getStatusCode()).isEqualTo(httpStatusExpected);
        }
    }
}
