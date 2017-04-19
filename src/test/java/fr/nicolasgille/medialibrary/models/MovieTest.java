package fr.nicolasgille.medialibrary.models;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
public class MovieTest {

    @Test
    public void testGetMovieCategory() {
        // Given - Instantiate a movie.
        MovieCategory categoryExpected = MovieCategory.ACTION;
        Movie movie = new Movie("Title", categoryExpected, 2017, 99, "", "");

        // When - Get category.
        MovieCategory category = movie.getCategory();

        // Then - category must equals to category expected.
        assertThat(category).isEqualTo(categoryExpected);
    }
}
