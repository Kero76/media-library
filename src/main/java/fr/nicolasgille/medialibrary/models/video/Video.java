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
import fr.nicolasgille.medialibrary.models.Media;
import fr.nicolasgille.medialibrary.models.video.utils.VideoSupport;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Main class of the Video media type.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 1.0
 */
@MappedSuperclass
public abstract class Video extends Media {

    /**
     * List of Support for the video.
     *
     * @see VideoSupport
     * @since 1.0
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = VideoSupport.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<VideoSupport> supports;

    /**
     * List of language spoken available on the video.
     *
     * @see LanguageCode
     * @since 1.0
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = LanguageCode.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<LanguageCode> languagesSpoken;

    /**
     * List of language present as subtitle available on the video.
     *
     * @see LanguageCode
     * @since 1.0
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = LanguageCode.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<LanguageCode> subtitles;

    /**
     * Return all supports for the movie.
     *
     * @return
     *  List of all supports.
     * @since 1.0
     * @version 1.0
     */
    public List<VideoSupport> getSupports() {
        return supports;
    }

    /**
     * Set the supports for the movie.
     *
     * @param supports
     *  New Supports.
     * @since 1.0
     * @version 1.0
     */
    public void setSupports(List<VideoSupport> supports) {
        this.supports = supports;
    }

    /**
     * Get the list of languages spoken.
     *
     * @return
     *  A list of languages spoken.
     * @since 1.0
     * @version 1.0
     */
    public List<LanguageCode> getLanguagesSpoken() {
        return languagesSpoken;
    }

    /**
     * Set the list of languages spoken available on the video.
     *
     * @param languagesSpoken
     *  New list of languages spoken.
     * @since 1.0
     * @version 1.0
     */
    public void setLanguagesSpoken(List<LanguageCode> languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }

    /**
     * Get the list of subtitle languages.
     *
     * @return
     *  A list of subtitle languages.
     * @since 1.0
     * @version 1.0
     */
    public List<LanguageCode> getSubtitles() {
        return subtitles;
    }

    /**
     * Set the list of subtitle languages available on the video.
     *
     * @param subtitles
     *  New list of subtitle languages.
     * @since 1.0
     * @version 1.0
     */
    public void setSubtitles(List<LanguageCode> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Generate a String with content of Set.
     *
     * @param set
     *  Set used to displayed element.
     * @return
     *  A string representation of the Set.
     * @since 1.0
     * @version 1.0
     */
    protected String setStringBuilder(Set<?> set) {
        StringBuilder str = new StringBuilder();
        if (set != null) {
            for (int i = 0; i < set.size(); ++i) {
                str.append(set.toArray()[i].toString());
                if (i != set.size() - 1) {
                    str.append(", ");
                }
            }
        } else {
            str.append("");
        }
        return str.toString();
    }
}
