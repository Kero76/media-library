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
import fr.nicolasgille.medialibrary.models.common.company.Developer;
import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import fr.nicolasgille.medialibrary.models.common.company.Publisher;
import fr.nicolasgille.medialibrary.models.common.person.*;
import fr.nicolasgille.medialibrary.models.components.BookFormat;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.components.VideoGamePlatform;
import fr.nicolasgille.medialibrary.models.components.genre.BookGenre;
import fr.nicolasgille.medialibrary.models.components.genre.MusicGenre;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGameGenre;
import fr.nicolasgille.medialibrary.models.components.genre.VideoGenre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Abstract class where all methods to build set of Company or Person are implement.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.1
 */
public abstract class MediaBuilder implements IMediaBuilder {

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
     * @since 1.1
     * @version 1.0
     */
    protected List<VideoGenre> buildGenreVideoList(String genres) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(genres)) {
            return new ArrayList<>();
        }

        // Split genres to get one genre.
        List<VideoGenre> genreList = new ArrayList<>();
        String[] genresSplit = genres.split(",");

        // Loop on each genre, media genre and check if the genre and the media genre is the same.
        for (String s : genresSplit) {
            for (VideoGenre mg : VideoGenre.values()) {
                if (mg.getName().equals(s.trim())) {
                    genreList.add(mg);
                }
            }
        }

        return genreList;
    }

    /**
     * Split a string and return the list of MediaGenre for the media.
     *
     * @param genres
     *  All genres of the media on string format.
     * @return
     *  An empty list if the genre not specified, or a list with all genres get from the string.
     * @since 1.1
     * @version 1.0
     */
    protected List<MusicGenre> buildGenreMusicList(String genres) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(genres)) {
            return new ArrayList<>();
        }

        // Split genres to get one genre.
        List<MusicGenre> genreList = new ArrayList<>();
        String[] genresSplit = genres.split(",");

        // Loop on each genre, media genre and check if the genre and the media genre is the same.
        for (String s : genresSplit) {
            for (MusicGenre mg : MusicGenre.values()) {
                if (mg.getName().equals(s.trim())) {
                    genreList.add(mg);
                }
            }
        }

        return genreList;
    }

    /**
     * Split a string and return the list of MediaGenre for the media.
     *
     * @param genres
     *  All genres of the media on string format.
     * @return
     *  An empty list if the genre not specified, or a list with all genres get from the string.
     * @since 1.1
     * @version 1.0
     */
    protected List<VideoGameGenre> buildGenreVideoGameList(String genres) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(genres)) {
            return new ArrayList<>();
        }

        // Split genres to get one genre.
        List<VideoGameGenre> genreList = new ArrayList<>();
        String[] genresSplit = genres.split(",");

        // Loop on each genre, media genre and check if the genre and the media genre is the same.
        for (String s : genresSplit) {
            for (VideoGameGenre mg : VideoGameGenre.values()) {
                if (mg.getName().equals(s.trim())) {
                    genreList.add(mg);
                }
            }
        }

        return genreList;
    }

    /**
     * Split a string and return the list of MediaGenre for the media.
     *
     * @param genres
     *  All genres of the media on string format.
     * @return
     *  An empty list if the genre not specified, or a list with all genres get from the string.
     * @since 1.1
     * @version 1.0
     */
    protected List<BookGenre> buildGenreBookList(String genres) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(genres)) {
            return new ArrayList<>();
        }

        // Split genres to get one genre.
        List<BookGenre> genreList = new ArrayList<>();
        String[] genresSplit = genres.split(",");

        // Loop on each genre, media genre and check if the genre and the media genre is the same.
        for (String s : genresSplit) {
            for (BookGenre mg : BookGenre.values()) {
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
     * Split a string and return the list of VideoGamePlatform for the media.
     *
     * @param platforms
     *  All platforms of the media on string format.
     * @return
     *  An empty list if the support not specified, or a list with all platforms get from the string.
     * @since 1.0
     * @version 1.0
     */
    protected List<VideoGamePlatform> buildPlatformList(String platforms) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(platforms)) {
            return new ArrayList<>();
        }

        // Split platforms to get one support.
        List<VideoGamePlatform> platformList = new ArrayList<>();
        String[] platformsSplit = platforms.split(",");

        // Loop on each platform, video game platform and check if the platforms and the video game platform is the same.
        for (String s : platformsSplit) {
            for (VideoGamePlatform vgp : VideoGamePlatform.values()) {
                if (vgp.getName().equals(s.trim())) {
                    platformList.add(vgp);
                }
            }
        }

        return platformList;
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

        // Split languages to get one language.
        List<LanguageCode> languageCodes = new ArrayList<>();
        String[] languagesSplit = languages.split(",");

        // Loop on each language or subtitle, LanguageCode and check if the language/subtitle and the LanguageCode is the same.
        for (String s : languagesSplit) {
            for (LanguageCode lc : LanguageCode.values()) {
                if (lc.name().equals(s.trim())) {
                    languageCodes.add(lc);
                }
            }
        }

        return languageCodes;
    }

    /**
     * Split a string and return the list of format for the media.
     *
     * @param format
     *  Format of the Book/Comic
     * @return
     *  The format of the book.
     * @since 1.0
     * @version 1.0
     */
    protected BookFormat buildBookFormat(String format) {
        // If argument is empty, return a format not specified.
        if (this.checkMissingArguments(format)) {
            return BookFormat.UNSPECIFIED;
        }

        // Loop on each format and compare with the format send on parameter
        for (BookFormat bf : BookFormat.values()) {
            if (bf.getName().equals(format.trim())) {
                return bf;
            }
        }

        return BookFormat.UNSPECIFIED;
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
        if (date.equals("pending")) {
            return Calendar.getInstance();
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    /**
     * Transform the string representation of the actors into Actor object.
     *
     * @param persons
     *  Persons at insert on Set.
     * @return
     *  A set of Actor composed by all actors present on the string.
     * @since 1.0
     * @version 1.0.1
     */
    protected Set<Actor> buildActorSet(String persons) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(persons)) {
            return new HashSet<>();
        }

        // Split persons to get one person.
        Set<Actor> personSet = new HashSet<>();
        String[] personsSplit = persons.split(",");

        // Loop on each persons, split first and last name and added it on HashSet.
        for (String s : personsSplit) {
            String[] personName = s.trim().split(" ");
            // If personName is equal 2, so the person have only one first name and one last name.
            if (personName.length == 2) {
                personSet.add(new Actor(personName[0], personName[1]));
            } else if (personName.length == 1) {
                personSet.add(new Actor(personName[0], ""));
            } else {
                // In other case, he can be have two first name or composed last name.
                if (personName[1].matches("^[A-Za-z]\\.")) {
                    personSet.add(new Actor(personName[0], personName[1] + " " + personName[2]));
                } else {
                    personSet.add(new Actor(personName[0] + " " + personName[1], personName[2]));
                }
            }
        }

        return personSet;
    }

    /**
     * Transform the string representation of the authors into Authors object.
     *
     * @param persons
     *  Persons at insert on Set.
     * @return
     *  A set of Authors composed by all authors present on the string.
     * @since 1.0
     * @version 1.0.1
     */
    protected Set<Author> buildAuthorSet(String persons) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(persons)) {
            return new HashSet<>();
        }

        // Split persons to get one person.
        Set<Author> personSet = new HashSet<>();
        String[] personsSplit = persons.split(",");

        // Loop on each persons, split first and last name and added it on HashSet.
        for (String s : personsSplit) {
            String[] personName = s.trim().split(" ");
            // If personName is equal 2, so the person have only one first name and one last name.
            if (personName.length == 2) {
                personSet.add(new Author(personName[0], personName[1]));
            } else if (personName.length == 1) {
                personSet.add(new Author(personName[0], ""));
            } else {
                // In other case, he can be have two first name or composed last name.
                if (personName[1].matches("^[A-Za-z]\\.")) {
                    personSet.add(new Author(personName[0], personName[1] + " " + personName[2]));
                } else {
                    personSet.add(new Author(personName[0] + " " + personName[1], personName[2]));
                }
            }
        }

        return personSet;
    }

    /**
     * Transform the string representation of the director into Director object.
     *
     * @param persons
     *  Persons at insert on Set.
     * @return
     *  A set of Director composed by all directors present on the string.
     * @since 1.0
     * @version 1.0.1
     */
    protected Set<Director> buildDirectorSet(String persons) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(persons)) {
            return new HashSet<>();
        }

        // Split persons to get one person.
        Set<Director> personSet = new HashSet<>();
        String[] personsSplit = persons.split(",");

        // Loop on each persons, split first and last name and added it on HashSet.
        for (String s : personsSplit) {
            String[] personName = s.trim().split(" ");
            // If personName is equal 2, so the person have only one first name and one last name.
            if (personName.length == 2) {
                personSet.add(new Director(personName[0], personName[1]));
            } else if (personName.length == 1) {
                personSet.add(new Director(personName[0], ""));
            } else {
                // In other case, he can be have two first name or composed last name.
                if (personName[1].matches("^[A-Za-z]\\.")) {
                    personSet.add(new Director(personName[0], personName[1] + " " + personName[2]));
                } else {
                    personSet.add(new Director(personName[0] + " " + personName[1], personName[2]));
                }
            }
        }

        return personSet;
    }

    /**
     * Transform the string representation of the illustrator into Illustrator object.
     *
     * @param persons
     *  Persons at insert on Set.
     * @return
     *  A set of Illustrator composed by all illustrators present on the string.
     * @since 1.0
     * @version 1.0.1
     */
    protected Set<Illustrator> buildIllustratorSet(String persons) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(persons)) {
            return new HashSet<>();
        }

        // Split persons to get one person.
        Set<Illustrator> personSet = new HashSet<>();
        String[] personsSplit = persons.split(",");

        // Loop on each persons, split first and last name and added it on HashSet.
        for (String s : personsSplit) {
            String[] personName = s.trim().split(" ");
            // If personName is equal 2, so the person have only one first name and one last name.
            if (personName.length == 2) {
                personSet.add(new Illustrator(personName[0], personName[1]));
            } else if (personName.length == 1) {
                personSet.add(new Illustrator(personName[0], ""));
            } else {
                // In other case, he can be have two first name or composed last name.
                if (personName[1].matches("^[A-Za-z]\\.")) {
                    personSet.add(new Illustrator(personName[0], personName[1] + " " + personName[2]));
                } else {
                    personSet.add(new Illustrator(personName[0] + " " + personName[1], personName[2]));
                }
            }
        }

        return personSet;
    }

    /**
     * Transform the string representation of the producers into Producer object.
     *
     * @param persons
     *  Persons at insert on Set.
     * @return
     *  A set of Producer composed by all producers present on the string.
     * @since 1.0
     * @version 1.0.1
     */
    protected Set<Producer> buildProducerSet(String persons) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(persons)) {
            return new HashSet<>();
        }

        // Split persons to get one person.
        Set<Producer> personSet = new HashSet<>();
        String[] personsSplit = persons.split(",");

        // Loop on each persons, split first and last name and added it on HashSet.
        for (String s : personsSplit) {
            String[] personName = s.trim().split(" ");
            // If personName is equal 2, so the person have only one first name and one last name.
            if (personName.length == 2) {
                personSet.add(new Producer(personName[0], personName[1]));
            } else if (personName.length == 1) {
                personSet.add(new Producer(personName[0], ""));
            } else {
                // In other case, he can be have two first name or composed last name.
                if (personName[1].matches("^[A-Za-z]\\.")) {
                    personSet.add(new Producer(personName[0], personName[1] + " " + personName[2]));
                } else {
                    personSet.add(new Producer(personName[0] + " " + personName[1], personName[2]));
                }
            }
        }

        return personSet;
    }

    /**
     * Transform the string representation of the actors into singer object.
     *
     * @param persons
     *  Persons at insert on Set.
     * @return
     *  A set of Singer composed by all singers present on the string.
     * @since 1.0
     * @version 1.0.1
     */
    protected Set<Singer> buildSingerSet(String persons) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(persons)) {
            return new HashSet<>();
        }

        // Split persons to get one person.
        Set<Singer> personSet = new HashSet<>();
        String[] personsSplit = persons.split(",");

        // Loop on each persons, split first and last name and added it on HashSet.
        for (String s : personsSplit) {
            String[] personName = s.trim().split(" ");
            // If personName is equal 2, so the person have only one first name and one last name.
            if (personName.length == 2) {
                personSet.add(new Singer(personName[0], personName[1]));
            } else if (personName.length == 1) {
                personSet.add(new Singer(personName[0], ""));
            } else {
                // In other case, he can be have two first name or composed last name.
                if (personName[1].matches("^[A-Za-z]\\.")) {
                    personSet.add(new Singer(personName[0], personName[1] + " " + personName[2]));
                } else {
                    personSet.add(new Singer(personName[0] + " " + personName[1], personName[2]));
                }
            }
        }

        return personSet;
    }

    /**
     * Transform the string representation of the developers into developers object.
     *
     * @param companies
     *  Companies at insert on Set.
     * @return
     *  A set of Developer composed by all developers present on the string.
     * @since 1.0
     * @version 1.0
     */
    protected Set<Developer> buildDeveloperSet(String companies) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(companies)) {
            return new HashSet<>();
        }

        // Split  to get one person.
        Set<Developer> companiesSet = new HashSet<>();
        String[] companiesSplit = companies.split(",");

        // Loop on each companies, split first and last name and added it on HashSet.
        for (String s : companiesSplit) {
            companiesSet.add(new Developer(s.trim()));
        }

        return companiesSet;
    }

    /**
     * Transform the string representation of the label records into label records object.
     *
     * @param companies
     *  Companies at insert on Set.
     * @return
     *  A set of Label Records composed by all label records present on the string.
     * @since 1.0
     * @version 1.0
     */
    protected Set<LabelRecords> buildLabelRecordsSet(String companies) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(companies)) {
            return new HashSet<>();
        }

        // Split  to get one person.
        Set<LabelRecords> companiesSet = new HashSet<>();
        String[] companiesSplit = companies.split(",");

        // Loop on each companies, split first and last name and added it on HashSet.
        for (String s : companiesSplit) {
            companiesSet.add(new LabelRecords(s.trim()));
        }

        return companiesSet;
    }

    /**
     * Transform the string representation of the publishers into publishers object.
     *
     * @param companies
     *  Companies at insert on Set.
     * @return
     *  A set of Publisher composed by all publishers present on the string.
     * @since 1.0
     * @version 1.0
     */
    protected Set<Publisher> buildPublisherSet(String companies) {
        // If argument is empty, return an empty ArrayList.
        if (this.checkMissingArguments(companies)) {
            return new HashSet<>();
        }

        // Split  to get one person.
        Set<Publisher> companiesSet = new HashSet<>();
        String[] companiesSplit = companies.split(",");

        // Loop on each companies, split first and last name and added it on HashSet.
        for (String s : companiesSplit) {
            companiesSet.add(new Publisher(s.trim()));
        }

        return companiesSet;
    }
}
