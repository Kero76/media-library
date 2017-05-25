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
package fr.nicolasgille.medialibrary.models.video;

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.utils.CollectionAsString;
import fr.nicolasgille.medialibrary.utils.DateFormatter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Model class for Cartoon instance.
 *
 * It extends the class Video to get all main attributes for video type.
 * The attribute added on the media is :
 * <ul>
 *     <li>The <code>runtime</code> of the movie.</li>
 * </ul>
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 2.0
 */
@Entity
@DiscriminatorValue(value = "cartoon")
public class Cartoon extends Video {

    /**
     * Runtime of the cartoons (in minutes).
     *
     * @since 1.0
     */
    @NotNull
    private Integer runtime;

    /**
     * Empty constructor.
     *
     * @since 1.0
     * @version 1.0
     */
    public Cartoon() {}

    /**
     * Constructor of the cartoon object.
     *
     * @param title
     *  Title of the cartoon.
     * @param originalTitle
     *  Original title of the cartoon.
     * @param synopsis
     *  Synopsis of the cartoon.
     * @param directors
     *  List of all directors of the cartoon.
     * @param producers
     *  List of all producers of the cartoon.
     * @param genres
     *  List of all genres for the cartoon.
     * @param supports
     *  Supports present for the cartoon.
     * @param languagesSpoken
     *  List of languages spoken available on cartoon.
     * @param subtitles
     *  List of subtitle languages available on cartoon.
     * @param releaseDate
     *  Date of release of the cartoon.
     * @param runtime
     *  Duration of the cartoon in minute.
     * @since 1.0
     * @version 1.1
     */
    public Cartoon(String title, String originalTitle, String synopsis,
                   Set<Director> directors, Set<Producer> producers,
                   List<MediaGenre> genres, List<MediaSupport> supports,
                   List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                   Calendar releaseDate, int runtime) {
        super.title = title;
        super.originalTitle = originalTitle;
        super.synopsis = synopsis;
        super.directors = directors;
        super.producers = producers;
        super.genres = genres;
        super.supports = supports;
        super.languagesSpoken = languagesSpoken;
        super.subtitles = subtitles;
        super.releaseDate = releaseDate;
        this.runtime = runtime;
    }

    /**
     * Constructor of the cartoon object.
     * This constructor is used to stored information retrieve from the persistent system.
     *
     * @param title
     *  Title of the cartoon.
     * @param originalTitle
     *  Original title of the cartoon.
     * @param synopsis
     *  Synopsis of the cartoon.
     * @param directors
     *  List of all directors of the cartoon.
     * @param producers
     *  List of all producers of the cartoon.
     * @param genres
     *  List of all genres for the cartoon.
     * @param supports
     *  Supports present for the cartoon.
     * @param languagesSpoken
     *  List of languages spoken available on cartoon.
     * @param subtitles
     *  List of subtitle languages available on cartoon.
     * @param releaseDate
     *  Date of release of the cartoon.
     * @param runtime
     *  Duration of the cartoon in minute.
     * @since 1.0
     * @version 1.1
     */
    public Cartoon(long id, String title, String originalTitle, String synopsis,
                   Set<Director> directors, Set<Producer> producers,
                   List<MediaGenre> genres, List<MediaSupport> supports,
                   List<LanguageCode> languagesSpoken, List<LanguageCode> subtitles,
                   Calendar releaseDate, int runtime) {
        super.id = id;
        super.title = title;
        super.originalTitle = originalTitle;
        super.synopsis = synopsis;
        super.directors = directors;
        super.producers = producers;
        super.genres = genres;
        super.supports = supports;
        super.languagesSpoken = languagesSpoken;
        super.subtitles = subtitles;
        super.releaseDate = releaseDate;
        this.runtime = runtime;
    }

    /**
     * Constructor use to update attribute of the current cartoon by the cartoon passed on parameter.
     *
     * @param cartoon
     *  New content of each attribute of this.
     * @since 1.0
     * @version 1.1
     */
    public Cartoon(Cartoon cartoon) {
        super.id = cartoon.getId();
        super.title = cartoon.getTitle();
        super.originalTitle = cartoon.getOriginalTitle();
        super.synopsis = cartoon.getSynopsis();
        super.genres = cartoon.getGenres();
        super.supports = cartoon.getSupports();
        this.directors = cartoon.getDirectors();
        this.producers = cartoon.getProducers();
        super.languagesSpoken = cartoon.getLanguagesSpoken();
        super.subtitles = cartoon.getSubtitles();
        this.releaseDate = cartoon.getReleaseDate();
        this.runtime = cartoon.getRuntime();
    }

    /**
     * Return the runtime of the cartoon in minute.
     *
     * @return
     *  The runtime in minute.
     * @since 1.0
     * @version 1.0
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Set runtime in minute.
     *
     * @param runtime
     *  New runtime in minute.
     * @since 1.0
     * @version 1.0
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Display Movie information.
     *
     * @return
     *  A short description of the content of the cartoon's attribute.
     * @since 1.0
     * @version 2.0
     */
    @Override
    public String toString() {
        return "Cartoon{" +
                "id=" + super.id +
                ", title='" + super.title + '\'' +
                ", originalTitle='" + super.originalTitle + '\'' +
                ", genres=" + CollectionAsString.listToString(super.genres) +
                ", releaseDate=" + DateFormatter.frenchDate(super.releaseDate) +
                ", runtime=" + this.runtime +
                ", synopsis='" + super.synopsis + '\'' +
                ", producers='" + CollectionAsString.setToString(super.producers) + '\'' +
                ", directors='" + CollectionAsString.setToString(super.directors) + '\'' +
                ", supports='" + CollectionAsString.listToString(supports) +
                ", languageSpoken='" + CollectionAsString.listToString(languagesSpoken) +
                ", subtitles='" + CollectionAsString.listToString(subtitles) +
                '}';
    }
}
