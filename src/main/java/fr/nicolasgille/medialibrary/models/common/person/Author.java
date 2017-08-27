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

package fr.nicolasgille.medialibrary.models.common.person;

import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Author present the author of the Book.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @see Person
 * @see Book
 * @since Media-Library 0.4
 */
@Entity
@DiscriminatorValue(value = "author")
public class Author extends Person {

    /**
     * List of all books written by the author.
     *
     * @since 1.0
     */
    @Transient
    private Set<Book> books;

    /**
     * Empty constructor.
     *
     * @version 1.0
     * @since 1.0
     */
    public Author() {}

    /**
     * Constructor used to create Author on Database.
     *
     * @param firstName First name of the Author.
     * @param lastName Last name of the Author.
     *
     * @version 1.0
     * @since 1.0
     */
    public Author(String firstName, String lastName) {
        super.firstName = firstName;
        super.lastName = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id Identifier stored on database.
     * @param firstName First name.
     * @param lastName Last name.
     * @param books All books written by the author.
     *
     * @version 1.0
     * @since 1.0
     */
    public Author(long id, String firstName, String lastName, Set<Book> books) {
        super.id = id;
        super.firstName = firstName;
        super.lastName = lastName;
        this.books = books;
    }

    /**
     * List of all books written by the authors.
     *
     * @return List of all books written by the author.
     *
     * @version 1.0
     * @since 1.0
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Set the list of all books written by the author.
     *
     * @param books New list of books.
     *
     * @version 1.0
     * @since 1.0
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Display content of the Author.
     *
     * @return A simple representation of the Author.
     *
     * @version 1.1
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Author{" +
               ", id=" + super.id + '\'' +
               ", firstName='" + super.firstName + '\'' +
               ", lastName='" + super.lastName + '\'' +
               "', books='" + CollectionAsString.collectionToString(this.books) + '\'' +
               '}';
    }
}
