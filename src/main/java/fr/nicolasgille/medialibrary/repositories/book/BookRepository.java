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

package fr.nicolasgille.medialibrary.repositories.book;

import fr.nicolasgille.medialibrary.models.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * Repository used to interact with all books available on Database.
 *
 * @author Nicolas GILLE
 * @version 1.3
 * @since Media-Library 0.4
 */
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find a list of books by title.
     *
     * @param title Title of the book at search on Database.
     *
     * @return An instance of book search by the name.
     *
     * @version 1.0
     * @since 1.0
     */
    List<Book> findByTitleIgnoreCase(String title);

    /**
     * Find a book by string .
     *
     * @param title Title of the book at search on Database.
     *
     * @return An instance of book search by the name.
     *
     * @version 1.0
     * @since 1.3
     */
    List<Book> findByTitleIgnoreCaseContaining(String title);

    /**
     * Find a book by his name, his duration and date of release.
     *
     * @param title Title of the book.
     * @param releaseDate Date of release of the book search.
     *
     * @return An instance of book search by title, duration and releaseDate.
     *
     * @version 1.0
     * @since 1.0
     */
    Book findByTitleAndReleaseDate(String title, Calendar releaseDate);

    /**
     * Find all books present on Media Library.
     *
     * @return List of all Books.
     *
     * @version 1.0
     * @since 1.1
     */
    @Query(value = "SELECT * FROM media WHERE media_type = \"book\"",
           nativeQuery = true)
    List<Book> findAll();
}
