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
package fr.nicolasgille.medialibrary.models.common.company;

import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.models.game.VideoGame;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

/**
 * Class Publisher represent a company who published book media type.
 *
 * @see Company
 * @see Book
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "publisher")
public class Publisher extends Company {

    /**
     * Set of all Books published by the publisher.
     *
     * @since 1.0
     */
    @Transient
    private Set<Book> books;

    /**
     * Set of all Video Game published by the publisher.
     *
     * @since 1.0
     */
    @Transient
    private Set<VideoGame> videoGames;

    /**
     * Empty constructor
     *
     * @since 1.0
     * @version 1.0
     */
    public Publisher() {}

    /**
     * Constructor with <code>name</code> attribute.
     *
     * @param name
     *  Name of the publisher.
     * @since 1.0
     * @version 1.0
     */
    public Publisher(String name) {
        super.name = name;
    }

    /**
     * Constructor with all attributes.
     *
     * @param id
     *  Identifier of the publisher.
     * @param name
     *  Name of the publisher.
     * @param books
     *  Set of all books published by the publisher.
     * @param videoGames
     *  List of all video game published by the video game.
     * @since 1.0
     * @version 1.0
     */
    public Publisher(long id, String name, Set<Book> books, Set<VideoGame> videoGames) {
        super.id = id;
        super.name = name;
        this.books = books;
        this.videoGames = videoGames;
    }

    /**
     * Get the list of all books published by the publisher.
     *
     * @return
     *  A set with all book published by the publisher.
     * @since 1.0
     * @version 1.0
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Set the list of books published by the publisher.
     *
     * @param books
     *  New set of books.
     * @since 1.0
     * @version 1.0
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Get all video games published by the publisher.
     *
     * @return
     *  A set with all video game published bu the publisher.
     * @since 1.0
     * @version 1.0
     */
    public Set<VideoGame> getVideoGames() {
        return videoGames;
    }

    /**
     * Set the list of video game published bt rhe publisher.
     *
     * @param videoGames
     *  List of all video games.
     * @since 1.0
     * @version 1.0
     */
    public void setVideoGames(Set<VideoGame> videoGames) {
        this.videoGames = videoGames;
    }

    /**
     * Display all information about the publisher.
     *
     * @return
     *  Information about publisher.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        if (this.books.isEmpty()) {
            return "Publisher{" +
                    "id=" + super.id +
                    ", name=" + super.name +
                    ", videoGames=" + CollectionAsString.setToString(this.videoGames) +
                    '}';
        } else {
            return "Publisher{" +
                    "id=" + super.id +
                    ", name=" + super.name +
                    ", books=" + CollectionAsString.setToString(this.books) +
                    '}';
        }
    }
}
