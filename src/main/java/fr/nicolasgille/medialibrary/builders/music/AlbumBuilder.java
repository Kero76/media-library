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
package fr.nicolasgille.medialibrary.builders.music;

import fr.nicolasgille.medialibrary.builders.MediaBuilder;
import fr.nicolasgille.medialibrary.models.IMedia;
import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import fr.nicolasgille.medialibrary.models.common.person.Singer;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.music.Album;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the interface IMediaBuilder used to build Album media.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
public class AlbumBuilder extends MediaBuilder {

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
        String tracks        = this.checkMissingArguments(data.get(1)) ? "" : data.get(1);
        Calendar releaseDate = this.buildDate(data.get(2));
        List<MediaGenre>   genres    = this.buildGenreList(data.get(3));
        List<MediaSupport> supports  = this.buildSupportList(data.get(4));
        double length =  this.checkMissingArguments(data.get(5)) ? -1 : Double.parseDouble(data.get(5));
        int nbTracks  = this.checkMissingArguments(data.get(6))  ? 0  : Integer.parseInt(data.get(6));
        Set<LabelRecords> labelRecords = this.buildLabelRecordsSet(data.get(7));
        Set<Singer>       singers      = this.buildSingerSet(data.get(8));

        return new Album(title, tracks, releaseDate, genres, supports, nbTracks, length, labelRecords, singers);
    }
}
