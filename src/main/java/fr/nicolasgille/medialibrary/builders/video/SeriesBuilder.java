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

package fr.nicolasgille.medialibrary.builders.video;

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.builders.MediaBuilder;
import fr.nicolasgille.medialibrary.models.IMedia;
import fr.nicolasgille.medialibrary.models.common.person.Actor;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.Series;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the interface IMediaBuilder used to build Series media.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.5
 */
public class SeriesBuilder extends MediaBuilder {

    /**
     * Create a specific media thanks to a list of string who contains all information needed for create the media.
     *
     * @param data A list of all data needed to build the object.
     *
     * @return The media entirely build.
     *
     * @version 1.0
     * @since 1.0
     */
    @Override
    public IMedia build(List<String> data) {
        String title = this.checkMissingArguments(data.get(0)) ? "" : data.get(0);
        String originalTitle = this.checkMissingArguments(data.get(1)) ? "" : data.get(1);
        String synopsis = this.checkMissingArguments(data.get(2)) ? "" : data.get(2);
        List<VideoGenre> genres = this.buildGenreVideoList(data.get(3));
        List<MediaSupport> supports = this.buildSupportList(data.get(4));
        List<LanguageCode> languages = this.buildLanguageList(data.get(5));
        List<LanguageCode> subtitles = this.buildLanguageList(data.get(6));
        Calendar releaseDate = this.buildDate(data.get(7));
        Calendar endDate = this.buildDate(data.get(8));
        int nbSeasons = Integer.parseInt(data.get(9));
        int currentSeason = Integer.parseInt(data.get(10));
        int nbEpisodes = Integer.parseInt(data.get(11));
        int nbMaxEpisodes = Integer.parseInt(data.get(12));
        int averageTime = Integer.parseInt(data.get(13));
        Set<Actor> actors = this.buildActorSet(data.get(14));
        Set<Director> directors = this.buildDirectorSet(data.get(15));
        Set<Producer> producers = this.buildProducerSet(data.get(16));

        return new Series(title, originalTitle, synopsis, actors, directors, producers, genres, supports,
                          languages, subtitles, releaseDate, endDate, nbSeasons, currentSeason, nbMaxEpisodes,
                          nbEpisodes, averageTime);

    }
}
