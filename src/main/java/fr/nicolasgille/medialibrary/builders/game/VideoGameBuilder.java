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

package fr.nicolasgille.medialibrary.builders.game;

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.builders.MediaBuilder;
import fr.nicolasgille.medialibrary.models.IMedia;
import fr.nicolasgille.medialibrary.models.common.company.Developer;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.VideoGamePlatform;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGameGenre;
import fr.nicolasgille.medialibrary.models.game.VideoGame;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the interface IMediaBuilder used to build VideoGame media.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.5
 */
public class VideoGameBuilder extends MediaBuilder {

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
        Calendar releaseDate = this.buildDate(data.get(3));
        List<VideoGameGenre> genres = this.buildGenreVideoGameList(data.get(4));
        List<MediaSupport> supports = this.buildSupportList(data.get(5));
        boolean multiplayers = Boolean.parseBoolean(data.get(6));
        List<LanguageCode> languages = this.buildLanguageList(data.get(7));
        Set<Developer> developers = this.buildDeveloperSet(data.get(8));
        Set<Publisher> publishers = this.buildPublisherSet(data.get(9));
        List<VideoGamePlatform> platforms = this.buildPlatformList(data.get(10));

        return new VideoGame(title, originalTitle, synopsis, releaseDate, genres, supports, multiplayers, languages,
                             developers, publishers, platforms);
    }
}
