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
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.utils.DateFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
abstract class MediaBuilder implements IMediaBuilder {

    private final static String MISSING_ARGUMENTS = "MISSING_ARGUMENTS";

    /**
     * Check if the argument passed on parameter is fill or missing and return the corresponding boolean.
     *
     * @param argument
     *  Argument to check if is indicate as MISSING_ARGUMENTS or valid.
     * @return
     *  True if the argument is indicate as missing. False in other case.
     * @since 1.0
     * @version 1.0
     */
    protected boolean checkMissingArguments(String argument) {
        return argument.equals(MISSING_ARGUMENTS);
    }

    /**
     * Split a string and return the list of MediaGenre for the media.
     *
     * @param genres
     *  All genres of the media on string format.
     * @return
     *  An empty list if the genre not specified, or a list with all genres get from the string.
     * @since 1.0
     * @version 1.0
     */
    protected List<MediaGenre> buildGenreList(String genres) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(genres)) {
            return new ArrayList<>();
        }

        // Split genres to get one genre.
        List<MediaGenre> genreList = new ArrayList<>();
        String[] genresSplit = genres.split(",");

        // Loop on each genre, media genre and check if the genre and the media genre is the same.
        for (String s : genresSplit) {
            for (MediaGenre mg : MediaGenre.values()) {
                if (mg.getName().equals(s.trim())) {
                    genreList.add(mg);
                }
            }
        }

        return genreList;
    }

    /**
     * Split a string and return the list of MediaSupport for the media.
     *
     * @param supports
     *  All supports of the media on string format.
     * @return
     *  An empty list if the support not specified, or a list with all supports get from the string.
     * @since 1.0
     * @version 1.0
     */
    protected List<MediaSupport> buildSupportList(String supports) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(supports)) {
            return new ArrayList<>();
        }

        // Split supports to get one support.
        List<MediaSupport> supportList = new ArrayList<>();
        String[] supportsSplit = supports.split(",");

        // Loop on each support, media support and check if the support and the media support is the same.
        for (String s : supportsSplit) {
            for (MediaSupport ms : MediaSupport.values()) {
                if (ms.getName().equals(s.trim())) {
                    supportList.add(ms);
                }
            }
        }

        return supportList;
    }

    /**
     * Split a string and return the list of LanguageCode for the media.
     *
     * @param languages
     *  All languages/subtitles of the media on string format.
     * @return
     *  An empty list if the languages/subtitles not specified, or a list with all languages/subtitles get from the string.
     * @since 1.0
     * @version 1.0
     */
    protected List<LanguageCode> buildLanguageList(String languages) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(languages)) {
            return new ArrayList<>();
        }

        // Split languages to get one support.
        List<LanguageCode> languageCodes = new ArrayList<>();
        String[] languagesSplit = languages.split(",");

        // Loop on each language or subtitle, LanguageCode and check if the language/subtitle and the LanguageCode is the same.
        for (String s : languagesSplit) {
            for (LanguageCode lc : LanguageCode.values()) {
                if (lc.getName().equals(s.trim())) {
                    languageCodes.add(lc);
                }
            }
        }

        return languageCodes;
    }

    /**
     * Transform the String representation of the date into a Calendar object.
     *
     * The date follow this format : dd/mm/YYYY and must be transform into a Calendar object.
     *
     * @param date
     *  String representation of the date at format.
     * @return
     *  An object Calendar which represent the date.
     * @throws ParseException
     * @since 1.0
     * @version 1.0
     */
    protected Calendar buildDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }
}
