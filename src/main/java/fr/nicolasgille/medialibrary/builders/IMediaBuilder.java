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

import fr.nicolasgille.medialibrary.models.IMedia;

import java.util.List;

/**
 * IMediaBuilder is a class used to build a specific media from different type of information get on parameter.
 *
 * It can be build a media thanks to a <code>List</code> of String who represent all information needed to build the media.
 * It can be extends in a future with new other entry type like Map or another object.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
public interface IMediaBuilder {

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
    public IMedia build(List<String> data);
}
