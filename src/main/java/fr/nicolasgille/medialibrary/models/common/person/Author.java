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
package fr.nicolasgille.medialibrary.models.common.person;

import fr.nicolasgille.medialibrary.models.book.Book;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Author present the author of the Book.
 *
 * @see Person
 * @see Book
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
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
     * @since 1.0
     * @version 1.0
     */
    public Author() {}

    /**
     * Constructor used to create Actor on Database.
     *
     * @param firstName
     *  First name of the Actor.
     * @param lastName
     *  Last name of the Actor.
     * @since 1.0
     * @version 1.0
     */
    public Author(String firstName, String lastName) {
        super.firstName = firstName;
        super.lastName  = lastName;
    }

    /**
     * Constructor with all parameters.
     *
     * @param id
     *  Identifier stored on database.
     * @param firstName
     *  First name.
     * @param lastName
     *  Last name.
     * @param books
     *  All books written by the author..
     * @since 1.0
     * @version 1.0
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
     * @return
     *  List of all books written by the author.
     * @since 1.0
     * @version 1.0
     */
    public Set<Book> getVideos() {
        return books;
    }

    /**
     * Set the list of all books written by the author.
     *
     * @param books
     *  New list of books.
     * @since 1.0
     * @version 1.0
     */
    public void setVideos(Set<Book> books) {
        this.books = books;
    }

    /**
     * Display content of the Author.
     *
     * @return
     *  A simple representation of the Author.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        StringBuilder books = new StringBuilder();
        if (this.books != null) {
            for (Book b : this.books) {
                books.append(b.toString());
                books.append(";");
            }
        } else {
            books.append("");
        }

        return "Author{" +
                ", id=" + super.id + '\'' +
                ", firstName='" + super.firstName + '\'' +
                ", lastName='" + super.lastName + '\'' +
                "', books='" + books.toString() + '\'' +
                '}';
    }
}
