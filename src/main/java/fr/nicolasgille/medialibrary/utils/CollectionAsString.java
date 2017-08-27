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
package fr.nicolasgille.medialibrary.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Util class used to represent a Collection as a String.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.4
 */
public class CollectionAsString {

    /**
     * Generate a String with content of collection.
     *
     * @param collection collection at convert into a String representation.
     *
     * @return A string representation of the collection.
     *
     * @version 1.0
     * @since 1.0
     */
    public static String collectionToString(Collection<?> collection) {
        StringBuilder str = new StringBuilder();
        str.append('{');
        if (collection != null) {
            for (int i = 0; i < collection.size(); ++i) {
                str.append(collection.toArray()[i].toString());
                if (i != collection.size() - 1) {
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
