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
package fr.nicolasgille.medialibrary.builders;

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.models.IMedia;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.video.Anime;

import java.util.*;

/**
 * Implementation of the interface IMediaBuilder used to build Anime media.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
public class AnimeBuilderI extends MediaBuilder {

    /**
     * Create a specific media thanks to a list of string who contains all information needed for create the media.
     *
     * @param data
     *  A list of all data needed to build the object.
     * @return
     *  The media entirely build.
     * @version 1.0
     * @since 1.0
     */
    @Override
    public IMedia build(List<String> data) {
        // Réalisateurs	Producteurs
        String title         = this.checkMissingArguments(data.get(0)) ? "" : data.get(0);
        String originalTitle = this.checkMissingArguments(data.get(1)) ? "" : data.get(1);
        String synopsis      = this.checkMissingArguments(data.get(2)) ? "" : data.get(2);

        List<MediaGenre>   genres   = this.buildGenreList(data.get(3));
        List<MediaSupport> supports = this.buildSupportList(data.get(4));
        List<LanguageCode> languages = this.buildLanguageList(data.get(5));
        List<LanguageCode> subtitles = this.buildLanguageList(data.get(6));
        Calendar releaseDate = this.buildDate(data.get(7));
        Calendar endDate     = this.buildDate(data.get(8));

        int nbSeasons     = Integer.parseInt(data.get(9));
        int currentSeason = Integer.parseInt(data.get(10));
        int nbEpisodes    = Integer.parseInt(data.get(11));
        int nbMaxEpisodes = Integer.parseInt(data.get(12));
        int averageTime   = Integer.parseInt(data.get(13));

        Set<Director> directors = new HashSet<>();
        Set<Producer> producers = new HashSet<>();

        Anime anime = new Anime(title, originalTitle, synopsis, directors, producers, genres, supports,
                languages, subtitles, releaseDate, endDate, nbSeasons, currentSeason, nbMaxEpisodes, nbEpisodes, averageTime);

        return anime;
    }
}
