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
package fr.nicolasgille.medialibrary.models.book;

import fr.nicolasgille.medialibrary.models.Media;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.BookFormat;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;
import fr.nicolasgille.medialibrary.utils.DateFormatter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Representation of all type of book available on the Media-Library.
 * It must be extends by all subtype of book like <code>Manga</code> or <code>Comic</code>.
 * This class isn't abstract because books like <em>Novel</em> can be represented by this class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = "book")
public class Book extends Media {

    /**
     * ISBN of the book.
     *
     * @since 1.0
     */
    private String isbn;

    /**
     * The original title of the book
     *
     * @since 1.0
     */
    private String originalTitle;

    /**
     * Number of page present on the book.
     *
     * @since 1.0
     */
    private int nbPages;

    /**
     * Set of all authors who written books.
     *
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "authors_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Producer.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Author> authors;

    /**
     * Publisher of the book.
     *
     * @since 1.0
     */
    @NotNull
    @JoinTable(
            name = "books_publisher",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = {@JoinColumn(name = "publisher_id", referencedColumnName = "id")}
    )
    @ManyToMany(targetEntity = Publisher.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Publisher> publishers;

    /**
     * Format of the book.
     *
     * @see BookFormat
     * @since 1.0
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private BookFormat format;

    /**
     * Empty constructor.
     *
     * @since 1.0
     * @version 1.0
     */
    public Book() {}

    /**
     * Constructor of the Book.
     *
     * @param title
     *  Title of the book.
     * @param originalTitle
     *  Original title of the book.
     * @param synopsis
     *  Synopsis of the book.
     * @param releaseDate
     *  Release date of the book.
     * @param nbPages
     *  Number of page of the book.
     * @param isbn
     *  ISBN of the book.
     * @param authors
     *  Authors of the book.
     * @param publishers
     *  Publishers of the book.
     * @param genres
     *  Genres of the book.
     * @param supports
     *  Supports of the book.
     * @param format
     *  Format of the book.
     * @since 1.0
     * @version 1.0
     */
    public Book(String title, String originalTitle, String synopsis,
                Calendar releaseDate, int nbPages, String isbn,
                Set<Author> authors, Set<Publisher> publishers,
                List<MediaGenre> genres, List<MediaSupport> supports, BookFormat format) {
        super.title = title;
        this.originalTitle = originalTitle;
        super.synopsis = synopsis;
        super.releaseDate = releaseDate;
        this.nbPages = nbPages;
        this.isbn = isbn;
        this.authors = authors;
        this.publishers = publishers;
        super.genres = genres;
        super.supports = supports;
        this.format = format;
    }

    /**
     * Constructor of the Book.
     *
     * @param id
     *  Identifier of the book.
     * @param title
     *  Title of the book.
     * @param originalTitle
     *  Original title of the book.
     * @param synopsis
     *  Synopsis of the book.
     * @param releaseDate
     *  Release date of the book.
     * @param nbPages
     *  Number of page of the book.
     * @param isbn
     *  ISBN of the book.
     * @param authors
     *  Authors of the book.
     * @param publishers
     *  Publishers of the book.
     * @param genres
     *  Genres of the book.
     * @param supports
     *  Supports of the book.
     * @param format
     *  Format of the book.
     * @since 1.0
     * @version 1.0
     */
    public Book(long id, String title, String originalTitle, String synopsis,
                Calendar releaseDate, int nbPages, String isbn,
                Set<Author> authors, Set<Publisher> publishers,
                List<MediaGenre> genres, List<MediaSupport> supports, BookFormat format) {
        super.id = id;
        super.title = title;
        this.originalTitle = originalTitle;
        super.synopsis = synopsis;
        super.releaseDate = releaseDate;
        this.nbPages = nbPages;
        this.isbn = isbn;
        this.authors = authors;
        this.publishers = publishers;
        super.genres = genres;
        super.supports = supports;
        this.format = format;
    }

    /**
     * Constructor used to copy another book.
     *
     * @param book
     *  Book at copying.
     * @since 1.0
     * @version 1.0
     */
    public Book(Book book) {
        super.id = book.getId();
        super.title = book.getTitle();
        this.originalTitle = book.getOriginalTitle();
        super.synopsis = book.getSynopsis();
        super.releaseDate = book.getReleaseDate();
        this.nbPages = book.getNbPages();
        this.isbn = book.getIsbn();
        this.authors = book.getAuthors();
        this.publishers = book.getPublisher();
        super.genres = book.getGenres();
        super.supports = book.getSupports();
        this.format = book.getFormat();
    }

    /**
     * Get the ISBN of the book.
     *
     * @return
     *  The ISBN of the book.
     * @since 1.0
     * @version 1.0
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Set the ISBN of the book.
     *
     * @param isbn
     *  New ISBN.
     * @since 1.0
     * @version 1.0
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Get the original title of the book.
     *
     * @return
     *  The original title of the book.
     * @since 1.0
     * @version 1.0
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Set the original title of the book.
     *
     * @param originalTitle
     *  New original title for the book.
     * @since 1.0
     * @version 1.0
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Get the number of pages available on the book.
     *
     * @return
     *  The number of pages on the book.
     * @since 1.0
     * @version 1.0
     */
    public int getNbPages() {
        return nbPages;
    }

    /**
     * Set the number of pages available on the book.
     *
     * @param nbPages
     *  New number of pages.
     * @since 1.0
     * @version 1.0
     */
    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    /**
     * Return all authors who written the book.
     *
     * @return
     *  All authors who written the book.
     * @since 1.0
     * @version 1.0
     */
    public Set<Author> getAuthors() {
        return authors;
    }

    /**
     * Set all authors who write the book.
     *
     * @param authors
     *  New st of Authors.
     * @since 1.0
     * @version 1.0
     */
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    /**
     * Get the publishers of the book.
     *
     * @return
     *  The publishers of the book.
     * @since 1.0
     * @version 1.0
     */
    public Set<Publisher> getPublisher() {
        return publishers;
    }

    /**
     * Set the publisher of the book.
     *
     * @param publishers
     *  The new publisher.
     * @since 1.0
     * @version 1.0
     */
    public void setPublisher(Set<Publisher> publishers) {
        this.publishers = publishers;
    }

    /**
     * Get the format of the book.
     *
     * @return
     *  The format of the book.
     * @since 1.0
     * @version 1.0
     */
    public BookFormat getFormat() {
        return format;
    }

    /**
     * Set the format of the book.
     *
     * @param format
     *  New format for the book.
     * @since 1.0
     * @version 1.0
     */
    public void setFormat(BookFormat format) {
        this.format = format;
    }

    /**
     * Some information about the Book.
     *
     * @return
     *  The information about the book.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + super.id +
                "isbn='" + isbn + '\'' +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", genres=" + CollectionAsString.listToString(super.getGenres()) +
                ", releaseDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", supports='" + CollectionAsString.listToString(super.getSupports()) +
                ", format=" + format.getName() +
                ", nbPages=" + nbPages +
                ", authors=" + CollectionAsString.setToString(this.authors) +
                ", publishers=" + CollectionAsString.setToString(this.publishers) +
                '}';
    }
}
