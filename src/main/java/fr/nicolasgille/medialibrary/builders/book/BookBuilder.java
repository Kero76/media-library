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
package fr.nicolasgille.medialibrary.builders.book;

import fr.nicolasgille.medialibrary.builders.MediaBuilder;
import fr.nicolasgille.medialibrary.models.IMedia;
import fr.nicolasgille.medialibrary.models.book.Book;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.Author;
import fr.nicolasgille.medialibrary.models.components.BookFormat;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.genre.BookGenre;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the interface IMediaBuilder used to build Book media.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
public class BookBuilder extends MediaBuilder {

    /**
     * Create a specific media thanks to a list of string who contains all information needed for create the media.
     *
     * @param data
     *  A list of all data needed to build the object.
     * @return
     *  The media entirely build.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public IMedia build(List<String> data) {
        String title         = this.checkMissingArguments(data.get(0)) ? "" : data.get(0);
        String originalTitle = this.checkMissingArguments(data.get(1)) ? "" : data.get(1);
        String synopsis      = this.checkMissingArguments(data.get(2)) ? "" : data.get(2);
        Calendar releaseDate = this.buildDate(data.get(3));
        List<BookGenre>   genres   = this.buildGenreBookList(data.get(4));
        List<MediaSupport> supports = this.buildSupportList(data.get(5));
        String isbn          = this.checkMissingArguments(data.get(6)) ? "" : data.get(6);
        int nbPages = Integer.parseInt(data.get(7));
        Set<Author> authors = this.buildAuthorSet(data.get(8));
        Set<Publisher> publishers = this.buildPublisherSet(data.get(9));
        BookFormat format = this.buildBookFormat(data.get(10));

        return new Book(title, originalTitle, synopsis, releaseDate, nbPages, isbn, authors, publishers, genres, supports, format);
    }
}
