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

import java.util.Calendar;

/**
 * Util class used for format Calender object as precise format.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.4
 * @version 1.0
 */
public class DateFormatter {

    /**
     * Format a <code>Calendar</code> object as specific format.
     *
     * The format return by this method is <em>dd/mm/YYYY</em>.
     *
     * @param date
     *  Date at formatted.
     * @return
     *  A representation of the date with the following format : <em>dd/mm/YYYY</em>.
     * @since 1.0
     * @version 1.0
     */
    public static String frenchDate(Calendar date) {
        StringBuilder str = new StringBuilder();
        str.append(DateFormatter.addPreviousZero(date.get(Calendar.DAY_OF_MONTH)));
        str.append("/");
        str.append(DateFormatter.addPreviousZero(date.get(Calendar.MONTH) + 1));
        str.append("/");
        str.append(date.get(Calendar.YEAR));
        return str.toString();
    }

    /**
     * Add a 0 before the number to get a format like <em>02</em> instead of <em>2</em>.
     *
     * @param n
     *  Number to return with or without a 0 as a String.
     * @return
     *  The String representation of the number n with a 0 before or not.
     * @since 1.0
     * @version 1.1
     */
    private static String addPreviousZero(int n) {
        if (n >= 10) {
            return "" + n;
        }
        return "0" + n;
    }
}
