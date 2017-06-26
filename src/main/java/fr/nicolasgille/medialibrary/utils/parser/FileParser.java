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
package fr.nicolasgille.medialibrary.utils;

import java.util.List;

/**
 * An interface to specified methods present on file parser.
 *
 * In fact, Media Library can parse data with specific extension to get all information for precise media.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
public interface FileParser {

    /**
     * Parse the file send in parameter and return a list of list of string who contains all elements parse during process.
     *
     * @param filename
     *  Name of the file at parse.
     * @return
     *  A list composed by List of all information get during parsing.
     * @since 1.0
     * @version 1.0
     */
    List<List<String>> parse(String filename);
}
