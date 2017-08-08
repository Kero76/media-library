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
package fr.nicolasgille.medialibrary.repositories.book;

import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.models.book.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * Repository used to interact with all books available on Database.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.1
 */
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find a book by his name.
     *
     * @param title
     *  Title of the book at search on Database.
     * @return
     *  An instance of book search by the name.
     * @since 1.0
     * @version 1.0
     */
    List<Book> findByTitleIgnoreCase(String title);

    /**
     * Find a book by his name, his duration and date of release.
     *
     * @param title
     *  Title of the book.
     * @param releaseDate
     *  Date of release of the book search.
     * @return
     *  An instance of book search by title, duration and releaseDate.
     * @since 1.0
     * @version 1.0
     */
    Book findByTitleAndReleaseDate(String title, Calendar releaseDate);

    /**
     * Find all books present on Media Library.
     *
     * @return
     *  List of all Books.
     * @since 1.1
     * @version 1.0
     */
    @Query(value = "SELECT * FROM media WHERE media_type = \"book\"", nativeQuery = true)
    List<Book> findAll();
}
