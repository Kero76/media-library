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
package fr.nicolasgille.medialibrary.builders;

import fr.nicolasgille.medialibrary.builders.music.AlbumBuilder;
import fr.nicolasgille.medialibrary.builders.video.AnimeBuilder;
import fr.nicolasgille.medialibrary.builders.video.CartoonBuilder;
import fr.nicolasgille.medialibrary.builders.video.MovieBuilder;
import fr.nicolasgille.medialibrary.builders.video.SeriesBuilder;
import fr.nicolasgille.medialibrary.models.music.Album;
import fr.nicolasgille.medialibrary.models.video.Anime;
import fr.nicolasgille.medialibrary.models.video.Cartoon;
import fr.nicolasgille.medialibrary.models.video.Movie;
import fr.nicolasgille.medialibrary.models.video.Series;
import fr.nicolasgille.medialibrary.utils.parser.FileParser;
import fr.nicolasgille.medialibrary.utils.parser.TsvParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Unit test for MediaBuilder's object.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
public class MediaBuilderTest {

    /**
     * Path for the resources file at load.
     */
    private final static String PATH = "src/test/resources/";

    /**
     * Parser to get data from files.
     */
    private FileParser parser;

    /**
     * Parse data get from parser into Models Object.
     */
    private IMediaBuilder builder;

    /**
     * URI of the Rest service.
     */
    private static final String REST_SERVICE_URI = "http://localhost/media-library";

    /**
     * Encoding used to encoded URL.
     */
    private static final String URL_ENCODER = "UTF-8";

    /**
     * Files name.
     */
    private String[] filename = {"animes", "cartoons", "movies", "musics", "series"};

    @Before
    public void setUp() {
        this.parser  = new TsvParser();
    }

    @Test
    public void buildAnime() throws Exception {
        // Given - @see setUp() and instantiate list of string.
        this.builder = new AnimeBuilder();
        List<List<String>> data = this.parser.parse(PATH + "animes.tsv");

        // When - Try to build Anime from first data line.
        Anime anime = (Anime) this.builder.build(data.get(6));

        // Then - Compare result.
        System.out.println(anime.toString());
    }

    @Test
    public void buildCartoon() throws Exception {
        // Given - @see setUp() and instantiate list of string.
        this.builder = new CartoonBuilder();
        List<List<String>> data = this.parser.parse(PATH + "cartoons.tsv");

        // When - Try to build Cartoon from first data line.
        Cartoon cartoon = (Cartoon) this.builder.build(data.get(1));

        // Then - Compare result.
        System.out.println(cartoon.toString());
    }

    @Test
    public void buildMovie() throws Exception {
        // Given - @see setUp() and instantiate list of string.
        this.builder = new MovieBuilder();
        List<List<String>> data = this.parser.parse(PATH + "movies.tsv");

        // When - Try to build Movie from first data line.
        Movie movie = (Movie) this.builder.build(data.get(1));

        // Then - Compare result.
        System.out.println(movie.toString());
    }

    @Test
    public void buildAlbum() throws Exception {
        // Given - @see setUp() and instantiate list of string.
        this.builder = new AlbumBuilder();
        List<List<String>> data = this.parser.parse(PATH + "musics.tsv");

        // When - Try to build Album from first data line.
        Album album = (Album) this.builder.build(data.get(1));

        // Then - Compare result.
        System.out.println(album.toString());
    }

    @Test
    public void buildSeries() throws Exception {
        // Given - @see setUp() and instantiate list of string.
        this.builder = new SeriesBuilder();
        List<List<String>> data = this.parser.parse(PATH + "series.tsv");

        // When - Try to build Album from first data line.
        Series series = (Series) this.builder.build(data.get(1));

        // Then - Compare result.
        System.out.println(series.toString());
    }

    @Test
    public void fillServer() throws Exception {
        // Given - @see setUp() and instantiate list of string.
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < filename.length; ++i) {
            List<List<String>> data = this.parser.parse(PATH + filename[i] + ".tsv");

            switch (i) {
                // Animes
                case 0:
                    this.builder = new AnimeBuilder();
                    for (int j = 1; j < data.size(); ++j) {
                        Anime anime = (Anime) this.builder.build(data.get(j));
                        restTemplate.postForEntity(REST_SERVICE_URI + "/animes/", anime, Anime.class);
                    }
                    break;

                // Cartoons
                case 1:
                    this.builder = new CartoonBuilder();
                    for (int j = 1; j < data.size(); ++j) {
                        Cartoon cartoon = (Cartoon) this.builder.build(data.get(j));
                        restTemplate.postForEntity(REST_SERVICE_URI + "/cartoons/", cartoon, Cartoon.class);
                    }
                    break;

                // Movies
                case 2:
                    this.builder = new MovieBuilder();
                    for (int j = 1; j < data.size(); ++j) {
                        Movie movie = (Movie) this.builder.build(data.get(j));
                        restTemplate.postForEntity(REST_SERVICE_URI + "/movies/", movie, Movie.class);
                    }
                    break;

                // Musics
                case 3:
                    this.builder = new AlbumBuilder();
                    for (int j = 1; j < data.size(); ++j) {
                        Album album = (Album) this.builder.build(data.get(j));
                        restTemplate.postForEntity(REST_SERVICE_URI + "/musics/", album, Album.class);
                    }
                    break;

                // Series
                case 4:
                    this.builder = new SeriesBuilder();
                    for (int j = 1; j < data.size(); ++j) {
                        Series series = (Series) this.builder.build(data.get(j));
                        restTemplate.postForEntity(REST_SERVICE_URI + "/series/", series, Series.class);
                    }
                    break;
            }
        }
    }
}
