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
import java.util.Set;

/**
 * Util class used to represent a Collection as a String.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
public class CollectionAsString {

    /**
     * Generate a String with content of Set.
     *
     * @param set
     *  Set at convert into a String representation.
     * @return
     *  A string representation of the Set.
     * @since 1.0
     * @version 1.0
     */
    public static String setToString(Set<?> set) {
        StringBuilder str = new StringBuilder();
        str.append('{');
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
        str.append('}');
        return str.toString();
    }

    /**
     * Generate a String with content of Set.
     *
     * @param list
     *  List at convert into a String representation.
     * @return
     *  A string representation of the List.
     * @since 1.0
     * @version 1.0
     */
    public static String listToString(List<?> list) {
        StringBuilder str = new StringBuilder();
        str.append('{');
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                str.append(list.toArray()[i].toString());
                if (i != list.size() - 1) {
                    str.append(", ");
                }
            }
        } else {
            str.append("");
        }
        str.append('}');
        return str.toString();
    }
}
