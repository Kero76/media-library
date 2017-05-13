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

import com.neovisionaries.i18n.LanguageCode;
import fr.nicolasgille.medialibrary.models.common.Actor;
import fr.nicolasgille.medialibrary.models.common.Director;
import fr.nicolasgille.medialibrary.models.common.Producer;
import fr.nicolasgille.medialibrary.models.video.Movie;
import fr.nicolasgille.medialibrary.models.video.Series;
import fr.nicolasgille.medialibrary.models.video.utils.VideoGenre;
import fr.nicolasgille.medialibrary.models.video.utils.VideoSupport;

import java.util.*;

/**
 * Util class used to generate all medias and persons used in Unit test.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.2.1
 * @version 1.0
 */
public class DataUtils {

    private Map<String, String> titles;
    private Map<String, String> originalTitles;
    private Map<String, String> synopsis;
    private Map<String, List<VideoGenre>> genres;
    private Map<String, List<VideoSupport>> supports;
    private Map<String, List<LanguageCode>> spokens;
    private Map<String, List<LanguageCode>> subtitles;
    private Map<String, Calendar> releasedDates;
    private Map<String, Integer> runtimes;
    private Map<String, List<Actor>> actors;
    private Map<String, List<Director>> directors;
    private Map<String, List<Producer>> producers;
    private Map<String, Movie> movies;
    private Map<String, Series> series;
    private Map<String, Calendar> startDates;
    private Map<String, Calendar> endDates;
    private Map<String, Integer> numberOfSeasons;
    private Map<String, Integer> currentSeasons;
    private Map<String, Integer> maxEpisodes;
    private Map<String, Integer> numberOfEpisodes;
    private Map<String, Integer> averageRuntimes;


    private Map<String, List<String>> keys;


    /**
     * Unique instance of DataUtils instantiate at null.
     * @since 1.0
     */
    private static DataUtils instance = null;

    /**
     * Constructor of the class DataUtils.
     *
     * @since 1.0
     * @version 1.0
     */
    private DataUtils() {
        // Keys
        this.initKeys();

        // Information
        this.initTitle();
        this.initOriginalTitle();
        this.initSynopsis();
        this.initSupport();
        this.initGenres();
        this.initLanguageSpoken();
        this.initSubtitles();
        this.initReleaseDate();
        this.initRuntime();
        this.initAverageRuntimes();
        this.initCurrentSeasons();
        this.initNumberOfSeasons();
        this.initMaxEpisodes();
        this.initAverageRuntimes();
        this.initStartDates();
        this.initEndDates();

        // Human part.
        this.initActors();
        this.initDirectors();
        this.initProducers();

        // Media part.
        this.initMovies();
        this.initSeries();
    }

    /**
     * Get the instance of DataUtils.
     *
     * @return
     *  The one instance of DataUtils.
     * @since 1.0
     * @version 1.0
     */
    public static DataUtils getInstance() {
        if (DataUtils.instance == null) {
            DataUtils.instance = new DataUtils();
        }
        return DataUtils.instance;
    }

    /**
     * Get movies instantiate.
     *
     * @return
     *  Map of all movies can get by their titles as key.
     * @since 1.0
     * @version 1.0
     */
    public Map<String, Movie> getMovies() {
        return movies;
    }

    /**
     * Get series instantiate.
     *
     * @return
     *  Map of all series can get by their titles as key.
     * @since 1.0
     * @version 1.0
     */
    public Map<String, Series> getSeries() {
        return series;
    }


    /**
     * Instantiate all keys used to stored media on Database.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initKeys() {
        this.keys = new HashMap<String, List<String>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        List<String> k = new ArrayList<String>();
        k.add("Star Wars IV");
        k.add("Star Wars V");
        k.add("Star Wars VI");
        k.add("Johnny English");
        k.add("Bienvenue dans la Jungle");
        k.add("Un Prince a New-York");
        k.add("Maman, je m'occupe des méchants !");
        k.add("Sister Act");
        k.add("Sister Act 2");
        k.add("Y a-t-il un pilote dans l'avion ?");
        k.add("Hot Shots");
        k.add("Hot Shots 2");
        k.add("Shanghai Kid 2");
        k.add("Le Tour du Monde en 80 Jours");
        k.add("Espion Amateur");
        k.add("Le Smoking");
        k.add("Le Médaillon");
        k.add("Contre-attaque");

        this.keys.put("movies", k);
        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        k.clear();
        k.add("Falling Skies 1");
        k.add("Falling Skies 2");
        k.add("Falling Skies 3");
        k.add("Falling Skies 4");
        k.add("Falling Skies 5");
        this.keys.put("series", k);
    }

    /**
     * Instantiate Titles.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initTitle() {
        this.titles = new HashMap<String, String>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        this.titles.put("Star Wars IV", "Star Wars : Un Nouvel Espoir");
        this.titles.put("Star Wars V", "Star Wars : L'Empire Contre-Attaque");
        this.titles.put("Star Wars VI", "Star Wars : Le Retour Du Jedi");
        this.titles.put("Johnny English", "Johnny English");
        this.titles.put("Bienvenue dans la Jungle", "Bienvenue dans la Jungle");
        this.titles.put("Un Prince a New-York", "Un Prince a New-York");
        this.titles.put("Maman, je m'occupe des méchants !", "Maman, je m'occupe des méchants !");
        this.titles.put("Sister Act", "Sister Act");
        this.titles.put("Sister Act 2", "Sister Act Acte 2");
        this.titles.put("Y a-t-il un pilote dans l'avion ?", "Y a-t-il un pilote dans l'avion ?");
        this.titles.put("Hot Shots", "Hot Shots!");
        this.titles.put("Hot Shots 2", "Hot Shots! 2");
        this.titles.put("Shanghai Kid 2", "Shanghai Kid 2");
        this.titles.put("Le Tour du Monde en 80 Jours", "Le Tour du Monde en 80 Jours");
        this.titles.put("Espion Amateur", "Espion Amateur");
        this.titles.put("Le Smoking", "Le Smoking");
        this.titles.put("Le Médaillon", "Le Médaillon");
        this.titles.put("Contre-attaque", "Contre-Attaque");


        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        this.titles.put("Falling Skies 1", "Falling Skies");
        this.titles.put("Falling Skies 2", "Falling Skies");
        this.titles.put("Falling Skies 3", "Falling Skies");
        this.titles.put("Falling Skies 4", "Falling Skies");
        this.titles.put("Falling Skies 5", "Falling Skies");
    }

    /**
     * Instantiate Original Titles.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initOriginalTitle() {
        this.titles = new HashMap<String, String>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        this.originalTitles.put("Star Wars IV", "Star Wars : A New Hope");
        this.originalTitles.put("Star Wars V","Star Wars V : The Empire Strikes Back");
        this.originalTitles.put("Star Wars VI", "Star Wars : Return of the Jedi");
        this.originalTitles.put("Johnny English", "Johnny English");
        this.originalTitles.put("Bienvenue dans la Jungle", "The Rundown");
        this.originalTitles.put("Un Prince a New-York", "Coming to America");
        this.originalTitles.put("Maman, je m'occupe des méchants !", "Home Alone 3");
        this.originalTitles.put("Sister Act", "Sister Act");
        this.originalTitles.put("Sister Act 2", "Sister Act 2 : Back in the Habit");
        this.originalTitles.put("Y a-t-il un pilote dans l'avion ?", "Airplane!");
        this.originalTitles.put("Hot Shots", "Hot Shots!");
        this.originalTitles.put("Hot Shots 2", "Hot Shots! Part Deux");
        this.originalTitles.put("Shanghai Kid 2", "Shanghai Knights");
        this.originalTitles.put("Le Tour du Monde en 80 Jours", "Around the World in 80 Days");
        this.originalTitles.put("Espion Amateur", "The Accidental Spy");
        this.originalTitles.put("Le Smoking", "The tuxedo");
        this.originalTitles.put("Le Médaillon", "The Medallion");
        this.originalTitles.put("Contre-attaque", "Police Story 4 : First Strike");


        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        this.originalTitles.put("Falling Skies 1", "Falling Skies");
        this.originalTitles.put("Falling Skies 2", "Falling Skies");
        this.originalTitles.put("Falling Skies 3", "Falling Skies");
        this.originalTitles.put("Falling Skies 4", "Falling Skies");
        this.originalTitles.put("Falling Skies 5", "Falling Skies");
    }

    /**
     * Instantiate Synopsis.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initSynopsis() {
        this.synopsis = new HashMap<String, String>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        this.synopsis.put("Star Wars IV", "");
        this.synopsis.put("Star Wars V", "");
        this.synopsis.put("Star Wars VI", "");
        this.synopsis.put("Johnny English", "");
        this.synopsis.put("Bienvenue dans la Jungle", "");
        this.synopsis.put("Un Prince a New-York", "");
        this.synopsis.put("Maman, je m'occupe des méchants !", "");
        this.synopsis.put("Sister Act !", "");
        this.synopsis.put("Sister Act 2", "");
        this.synopsis.put("Y a-t-il un pilote dans l'avion ?", "");
        this.synopsis.put("Hot Shots", "");
        this.synopsis.put("Hot Shots 2", "");
        this.synopsis.put("Shanghai Kid 2", "");
        this.synopsis.put("Le Tour du Monde en 80 Jours", "");
        this.synopsis.put("Espion Amateur", "");
        this.synopsis.put("Le Smoking", "");
        this.synopsis.put("Le Médaillon", "");
        this.synopsis.put("Contre-attaque", "");


        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        this.titles.put("Falling Skies 1", "Falling Skies");
        this.titles.put("Falling Skies 2", "Falling Skies");
        this.titles.put("Falling Skies 3", "Falling Skies");
        this.titles.put("Falling Skies 4", "Falling Skies");
        this.titles.put("Falling Skies 5", "Falling Skies");
    }

    /**
     * Instantiate Genres
     *
     * @since 1.0
     * @version 1.0
     */
    private void initGenres() {
        this.genres = new HashMap<String, List<VideoGenre>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Star Wars
        List<VideoGenre> g = new ArrayList<VideoGenre>();
        g.add(VideoGenre.SCIENCE_FICTION);
        this.genres.put("Star Wars IV", g);
        this.genres.put("Star Wars V", g);
        this.genres.put("Star Wars VI", g);

        // Johnny English
        g.clear();
        g.add(VideoGenre.COMEDY);
        g.add(VideoGenre.SPY);
        this.genres.put("Johnny English", g);

        // Bienvenue dans la Jungle
        g.clear();
        g.add(VideoGenre.ACTION);
        this.genres.put("Bienvenue dans la Jungle", g);

        // Un Prince a New-York
        g.clear();
        g.add(VideoGenre.COMEDY);
        g.add(VideoGenre.ROMANTIC);
        this.genres.put("Un Prince a New-York", g);

        // Maman, je m'occupe des méchants !
        g.clear();
        g.add(VideoGenre.COMEDY);
        this.genres.put("Maman, je m'occupe des méchants !", g);

        // Sister Act & Sister Act 2
        g.add(VideoGenre.MUSICAL);
        this.genres.put("Sister Act", g);
        this.genres.put("Sister Act 2", g);

        // Y a-t-il un pilote dans l'avion ? | Hot Shots 1 & 2 |
        g.clear();
        g.add(VideoGenre.COMEDY);
        this.genres.put("Y a-t-il un pilote dans l'avion ?", g);
        this.genres.put("Hot Shots", g);
        this.genres.put("Hot Shots 2", g);

        // Shanghai Kid 2
        g.clear();
        this.genres.put("Shanghai Kid 2", g);

        // Le tour du monde en 80
        g.clear();
        this.genres.put("Le Tour du Monde en 80 Jours", g);

        // Espion Amateur
        g.clear();
        this.genres.put("Espion Amateur", g);

        // Le Smoking
        g.clear();
        this.genres.put("Le Smoking", g);

        // Le medaillon
        g.clear();
        this.genres.put("Le Médaillon", g);

        // Contre Attaque
        g.clear();
        this.genres.put("Contre-attaque", g);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        g.clear();
        g.add(VideoGenre.ACTION);
        g.add(VideoGenre.ADVENTURE);
        g.add(VideoGenre.DRAMA);
        g.add(VideoGenre.SCIENCE_FICTION);
        g.add(VideoGenre.THRILLER);
        this.genres.put("Falling Skies 1", g);
        this.genres.put("Falling Skies 2", g);
        this.genres.put("Falling Skies 3", g);
        this.genres.put("Falling Skies 4", g);
        this.genres.put("Falling Skies 5", g);
    }

    /**
     * Instantiate Support.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initSupport() {
        this.supports = new HashMap<String, List<VideoSupport>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Movies
        List<VideoSupport> s = new ArrayList<VideoSupport>();
        s.add(VideoSupport.DVD);
        this.supports.put("Star Wars IV", s);
        this.supports.put("Star Wars V", s);
        this.supports.put("Star Wars VI", s);
        this.supports.put("Johnny English", s);
        this.supports.put("Bienvenue dans la Jungle", s);
        this.supports.put("Un Prince a New-York", s);
        this.supports.put("Maman, je m'occupe des méchants !", s);
        this.supports.put("Sister Act", s);
        this.supports.put("Sister Act 2", s);
        this.supports.put("Y a-t-il un pilote dans l'avion ?", s);
        this.supports.put("Hot Shots", s);
        this.supports.put("Hot Shots 2", s);
        this.supports.put("Shanghai Kid 2", s);
        this.supports.put("Le Tour du Monde en 80 Jours", s);
        this.supports.put("Espion Amateur", s);
        this.supports.put("Le Smoking", s);
        this.supports.put("Le Médaillon", s);
        this.supports.put("Contre-attaque", s);


        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        this.supports.put("Falling Skies 1", s);
        this.supports.put("Falling Skies 2", s);
        this.supports.put("Falling Skies 3", s);
        this.supports.put("Falling Skies 4", s);
        this.supports.put("Falling Skies 5", s);
    }

    /**
     * Instantiate Spoken Languages
     *
     * @since 1.0
     * @version 1.0
     */
    private void initLanguageSpoken() {
        this.spokens = new HashMap<String, List<LanguageCode>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // French | English
        List<LanguageCode> l = new ArrayList<LanguageCode>();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        this.spokens.put("Star Wars IV", l);
        this.spokens.put("Star Wars V", l);
        this.spokens.put("Star Wars VI", l);
        this.spokens.put("Johnny English", l);
        this.spokens.put("Bienvenue dans la Jungle", l);
        this.spokens.put("Sister Act", l);
        this.spokens.put("Sister Act 2", l);
        this.spokens.put("Hot Shots", l);
        this.spokens.put("Hot Shots 2", l);
        this.spokens.put("Le Tour du Monde en 80 Jours", l);
        this.spokens.put("Espion Amateur", l);
        this.spokens.put("Le Médaillon", l);
        this.spokens.put("Contre-attaque", l);

        // French | English | Italian | Spanish
        l.clear();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        l.add(LanguageCode.it);
        l.add(LanguageCode.es);
        this.spokens.put("Un Prince a New-York", l);

        // French | English | Italian
        l.clear();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        l.add(LanguageCode.it);
        this.spokens.put("Maman, je m'occupe des méchants !", l);
        this.spokens.put("Y a-t-il un pilote dans l'avion ?", l);
        this.spokens.put("Shanghai Kid 2", l);

        // French | English | Deutsh
        l.clear();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        l.add(LanguageCode.de);
        this.spokens.put("Le Smoking", l);



        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        l.clear();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        l.add(LanguageCode.de);
        l.add(LanguageCode.es);
        this.spokens.put("Falling Skies 1", l);
        this.spokens.put("Falling Skies 2", l);
        this.spokens.put("Falling Skies 3", l);
        this.spokens.put("Falling Skies 4", l);
        this.spokens.put("Falling Skies 5", l);
    }

    /**
     * Instantiate Subtitles.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initSubtitles() {
        this.subtitles = new HashMap<String, List<LanguageCode>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // French | English | Dutch | Arab
        List<LanguageCode> s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.nl);
        s.add(LanguageCode.ar);
        this.subtitles.put("Star Wars IV", s);
        this.subtitles.put("Star Wars V", s);
        this.subtitles.put("Star Wars VI", s);

        // French
        s.clear();
        s.add(LanguageCode.fr);
        this.subtitles.put("Johnny English", s);
        this.subtitles.put("Le Tour du Monde en 80 Jours", s);
        this.subtitles.put("Espion Amateur", s);
        this.subtitles.put("Le Médaillon", s);
        this.subtitles.put("Contre-attaque", s);

        // French | English | Arab
        s.clear();
        s.add(LanguageCode.en);
        s.add(LanguageCode.fr);
        s.add(LanguageCode.ar);
        this.subtitles.put("Bienvenue dans la Jungle", s);

        // English | Croatian | Spanish | French | Greek | Hebrew | Italian | Portugues
        s.clear();
        s.add(LanguageCode.en);
        s.add(LanguageCode.hr);
        s.add(LanguageCode.es);
        s.add(LanguageCode.fr);
        s.add(LanguageCode.el);
        s.add(LanguageCode.he);
        s.add(LanguageCode.it);
        s.add(LanguageCode.pt);
        this.subtitles.put("Un Prince a New-York", s);

        // English | French | Italian | Greek | Dutch
        s.clear();
        s.add(LanguageCode.en);
        s.add(LanguageCode.fr);
        s.add(LanguageCode.it);
        s.add(LanguageCode.el);
        s.add(LanguageCode.nl);
        this.subtitles.put("Maman, je m'occupe des méchants !", s);

        // French | English
        s.clear();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        this.subtitles.put("Sister Act", s);
        this.subtitles.put("Sister Act 2", s);
        this.subtitles.put("Hot Shots", s);
        this.subtitles.put("Hot Shots 2", s);

        // French | English | Italian | Spanish
        s.clear();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.it);
        s.add(LanguageCode.es);
        this.subtitles.put("Y a-t-il un pilote dans l'avion ?", s);

        // French | English | Italian | Portuguese
        s.clear();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.it);
        s.add(LanguageCode.pt);
        this.subtitles.put("Shanghai Kid 2", s);

        // French | English | Bosch | Dutch | Bulgarian | Arab
        s.clear();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.de);
        s.add(LanguageCode.nl);
        s.add(LanguageCode.bg);
        s.add(LanguageCode.ar);
        this.subtitles.put("Le Smoking", s);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        s.clear();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.es);
        s.add(LanguageCode.en);
        s.add(LanguageCode.nl);
        this.subtitles.put("Falling Skies 1", s);
        this.subtitles.put("Falling Skies 2", s);
        this.subtitles.put("Falling Skies 3", s);
        this.subtitles.put("Falling Skies 4", s);
        this.subtitles.put("Falling Skies 5", s);

    }

    /**
     * Instantiate Runtime.
     * Only for Movie.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initRuntime() {
        this.runtimes = new HashMap<String, Integer>();

        this.runtimes.put("Star Wars IV", 121);
        this.runtimes.put("Star Wars V", 124);
        this.runtimes.put("Star Wars VI", 131);
        this.runtimes.put("Johnny English", 84);
        this.runtimes.put("Bienvenue dans la Jungle", 100);
        this.runtimes.put("Un Prince a New-York", 112);
        this.runtimes.put("Maman, je m'occupe des méchants !", 98);
        this.runtimes.put("Sister Act", 96);
        this.runtimes.put("Sister Act 2", 102);
        this.runtimes.put("Y a-t-il un pilote dans l'avion ?", 84);
        this.runtimes.put("Hot Shots", 82);
        this.runtimes.put("Hot Shots 2", 85);
        this.runtimes.put("Shanghai Kid 2", 115);
        this.runtimes.put("Le Tour du Monde en 80 Jours", 120);
        this.runtimes.put("Espion Amateur", 90);
        this.runtimes.put("Le Smoking", 95);
        this.runtimes.put("Le Médaillon", 88);
        this.runtimes.put("Contre-attaque", 80);
    }

    /**
     * Instantiate Release Date.
     * Only for Movie.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initReleaseDate() {
        this.releasedDates = new HashMap<String, Calendar>();

        this.releasedDates.put("Star Wars IV", new GregorianCalendar(1977, Calendar.MAY, 25));
        this.releasedDates.put("Star Wars V", new GregorianCalendar(1980, Calendar.MAY, 17));
        this.releasedDates.put("Star Wars VI", new GregorianCalendar(1983, Calendar.MAY, 25));
        this.releasedDates.put("Johnny English", new GregorianCalendar(2003, Calendar.APRIL, 6));
        this.releasedDates.put("Bienvenue dans la Jungle", new GregorianCalendar(2003, Calendar.SEPTEMBER, 26));
        this.releasedDates.put("Un Prince a New-York", new GregorianCalendar(1988, Calendar.JUNE, 29));
        this.releasedDates.put("Maman, je m'occupe des méchants !", new GregorianCalendar(1997, Calendar.DECEMBER, 12));
        this.releasedDates.put("Sister Act", new GregorianCalendar(1992, Calendar.MAY, 29));
        this.releasedDates.put("Sister Act 2", new GregorianCalendar(1993, Calendar.DECEMBER, 10));
        this.releasedDates.put("Y a-t-il un pilote dans l'avion ?", new GregorianCalendar(1980, Calendar.JULY, 02));
        this.releasedDates.put("Shanghai Kid 2", new GregorianCalendar(2003, Calendar.FEBRUARY, 7));
        this.releasedDates.put("Le Tour du Monde en 80 Jours", new GregorianCalendar(2004, Calendar.JUNE, 13));
        this.releasedDates.put("Espion Amateur", new GregorianCalendar(2001, Calendar.JANUARY, 18));
        this.releasedDates.put("Le Smoking", new GregorianCalendar(2002, Calendar.SEPTEMBER, 27));
        this.releasedDates.put("Le Médaillon", new GregorianCalendar(2003, Calendar.AUGUST, 15));
        this.releasedDates.put("Contre-attaque", new GregorianCalendar(1996, Calendar.FEBRUARY, 10));
    }

    /**
     * Instantiate Actors.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initActors() {
        this.actors = new HashMap<String, List<Actor>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Star Wars : A New Hope
        List<Actor> a = new ArrayList<Actor>();
        a.add(new Actor("Mark", "Hamill"));
        a.add(new Actor("Harrison", "Ford"));
        a.add(new Actor("Carrie", "Fisher"));
        a.add(new Actor("Peter", "Cushing"));
        a.add(new Actor("Alec", "Guinness"));
        this.actors.put("Star Wars IV", a);

        // Star Wars : The Empire Strikes Back & Star Wats : Return of the Jedi
        a.clear();
        a.add(new Actor("Mark", "Hamill"));
        a.add(new Actor("Harrison", "Ford"));
        a.add(new Actor("Carrie", "Fisher"));
        a.add(new Actor("Billy Dee", "Williams"));
        a.add(new Actor("Anthony", "Daniels"));
        a.add(new Actor("David", "Prowse"));
        a.add(new Actor("Kenny", "Baker"));
        a.add(new Actor("Peter", "Mayhew"));
        a.add(new Actor("Frank", "Oz"));
        this.actors.put("Star Wars V", a);
        this.actors.put("Star Wars VI", a);

        // Johnny English
        a.clear();
        a.add(new Actor("Rowan", "Atkinson"));
        a.add(new Actor("Natalie", "Imbruglia"));
        a.add(new Actor("Ben", "Miller"));
        a.add(new Actor("John", "Malkovich"));
        this.actors.put("Johnny English", a);

        // Bienvenue dans la Jungle
        a.clear();
        a.add(new Actor("Dwayne", "Johnson"));
        a.add(new Actor("Seann William", "Scott"));
        a.add(new Actor("Christopher", "Walken"));
        a.add(new Actor("Rosario", "Dawson"));
        a.add(new Actor("Ewen", "Bremner"));
        a.add(new Actor("Jon", "Gries"));
        this.actors.put("Bienvenue dans la Jungle", a);

        // Un Prince a New-York
        a.clear();
        a.add(new Actor("Eddie", "Murphy"));
        a.add(new Actor("Arsenio", "Hall"));
        a.add(new Actor("James Earl", "Jones"));
        a.add(new Actor("John", "Amos"));
        a.add(new Actor("Madge", "Sinclair"));
        a.add(new Actor("Shari", "Headley"));
        this.actors.put("Un Prince a New-York", a);

        // Maman, je m'occupe des méchants !
        a.clear();
        a.add(new Actor("Alex D.", "Linz"));
        a.add(new Actor("Haviland", "Morris"));
        this.actors.put("Maman, je m'occupe des méchants !", a);

        // Sister Act
        a.clear();
        a.add(new Actor("Whoopi", "Goldberg"));
        a.add(new Actor("Maggie", "Smith"));
        a.add(new Actor("Harvey", "Keitel"));
        this.actors.put("Sister Act", a);

        // Sister Act 2
        a.clear();
        a.add(new Actor("Whoopi", "Goldberg"));
        a.add(new Actor("Kathy", "Najimy"));
        a.add(new Actor("James", "Coburn"));
        a.add(new Actor("Maggie", "Smith"));
        this.actors.put("Sister Act 2", a);

        // Y a-t-il un pilote dans l'avion ?
        a.clear();
        a.add(new Actor("Robert", "Hays"));
        a.add(new Actor("Julie", "Hagerty"));
        this.actors.put("Y a-t-il un pilote dans l'avion ?", a);

        // Hot Shots
        a.clear();
        a.add(new Actor("Charlie", "Sheen"));
        a.add(new Actor("Cary", "Elwes"));
        a.add(new Actor("Valeria", "Golino"));
        a.add(new Actor("Jon", "Cryer"));
        a.add(new Actor("Kevin", "Dunn"));
        a.add(new Actor("Bill", "Irwin"));
        a.add(new Actor("Lloyd", "Bridges"));
        this.actors.put("Hot Shots", a);

        // Hot Shots 2
        a.clear();
        a.add(new Actor("Charlie", "Sheen"));
        a.add(new Actor("Lloyd", "Bridges"));
        a.add(new Actor("Valeria", "Golino"));
        a.add(new Actor("Brenda", "Bakke"));
        a.add(new Actor("Richard", "Crenna"));
        this.actors.put("Hot Shots 2", a);

        // Shanghai Kid 2
        a.clear();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Owen", "Wilson"));
        a.add(new Actor("Donnie", "Yen"));
        a.add(new Actor("Aidan", "Gillen"));
        a.add(new Actor("Fann", "Wong"));
        a.add(new Actor("Tom", "Fisher"));
        a.add(new Actor("Kim", "Chan"));
        this.actors.put("Shanghai Kid 2", a);

        // Le tour du monde en 80 jours
        a.clear();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Steve", "Coogan"));
        a.add(new Actor("Cécile", "de France"));
        a.add(new Actor("Jim", "Broadbent"));
        a.add(new Actor("Ian", "McNeice"));
        a.add(new Actor("Karen", "Mok"));
        this.actors.put("Le Tour du Monde en 80 Jours", a);

        // Espion Amateur
        a.clear();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Eric", "Tsang"));
        a.add(new Actor("Vivian", "Hsu"));
        a.add(new Actor("Wu", "Hsing-kuo"));
        this.actors.put("Espion Amateur", a);

        // Le Smoking
        a.clear();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Jennifer", "Love Hewitt"));
        this.actors.put("Le Smoking", a);

        // Le medaillon
        a.clear();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Lee", "Evans"));
        a.add(new Actor("Claire", "Forlani"));
        a.add(new Actor("Julian", "Sands"));
        this.actors.put("Le Médaillon", a);

        // Contre Attaque
        a.clear();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Wu", "Chen-chun"));
        a.add(new Actor("Jackson", "Lou"));
        this.actors.put("Contre-attaque", a);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Series - Falling Skies
        a.clear();
        a.add(new Actor("Noah", "Wyle"));
        a.add(new Actor("Moon", "Bloodgood"));
        a.add(new Actor("Drew", "Roy"));
        a.add(new Actor("Jessy", "Schram"));
        a.add(new Actor("Sarah", "Carter"));
        a.add(new Actor("Maxim", "Knight"));
        a.add(new Actor("Seychelle", "Gabriel"));
        a.add(new Actor("Peter", "Shinkoda"));
        a.add(new Actor("Connor", "Jessup"));
        a.add(new Actor("Mpho", "Koaho"));
        a.add(new Actor("", "Cunningham"));
        a.add(new Actor("Will", "Patton"));
        a.add(new Actor("Doug", "Jones"));
        a.add(new Actor("Scarlett", "Byrne"));
        this.actors.put("Falling Skies 1", a);
        this.actors.put("Falling Skies 2", a);
        this.actors.put("Falling Skies 3", a);
        this.actors.put("Falling Skies 4", a);
        this.actors.put("Falling Skies 5", a);
    }


    /**
     * Instantiate Directors.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initDirectors() {
        this.directors = new HashMap<String, List<Director>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Star Wars : A New Hope
        List<Director> d = new ArrayList<Director>();
        d.add(new Director("George", "Lucas"));
        this.directors.put("Star Wars IV", d);

        // Star Wars : The Empire Strikes Back
        d.clear();
        d.add(new Director("Irvin", "Kershner"));
        this.directors.put("Star Wars V", d);

        // Star Wats : Return of the Jedi
        d.clear();
        d.add(new Director("Richard", "Marquand"));
        this.directors.put("Star Wars VI", d);

        // Johnny English
        d.clear();
        d.add(new Director("Peter", "Howitt"));
        this.directors.put("Johnny English", d);

        // Bienvenue dans la Jungle
        d.clear();
        d.add(new Director("Peter", "Berg"));
        this.directors.put("Bienvenue dans la Jungle", d);

        // Un Prince a New-York
        d.clear();
        d.add(new Director("John", "Landis"));
        this.directors.put("Un Prince a New-York", d);

        // Maman, je m'occupe des méchants !
        d.clear();
        d.add(new Director("Raja", "Gosnell"));
        this.directors.put("Maman, je m'occupe des méchants !", d);

        // Sister Act
        d.clear();
        d.add(new Director("Emile", "Ardolino"));
        this.directors.put("Sister Act", d);

        // Sister Act 2
        d.clear();
        d.add(new Director("Bill", "Duke"));
        this.directors.put("Sister Act 2", d);

        // Y a-t-il un pilote dans l'avion ?
        d.clear();
        d.add(new Director("Jim", "Abrahams"));
        d.add(new Director("David", "Zucker"));
        d.add(new Director("Jerry", "Zucker"));
        this.directors.put("Y a-t-il un pilote dans l'avion ?", d);

        // Hot Shots 1 & 2
        d.clear();
        d.add(new Director("Jim", "Abrahams"));
        this.directors.put("Hot Shots", d);
        this.directors.put("Hot Shots 2", d);

        // Shanghai Kid 2
        d.clear();
        d.add(new Director("David", "Dobkin"));
        this.directors.put("Shanghai Kid 2", d);

        // Le tour du monde en 80 jours
        d.clear();
        d.add(new Director("Frank", "Coraci"));
        this.directors.put("Le Tour du Monde en 80 Jours", d);

        // Espion Amateur
        d.clear();
        d.add(new Director("Teddy", "Chan"));
        this.directors.put("Espion Amateur", d);

        // Le Smoking
        d.clear();
        d.add(new Director("Kevin", "Donovan"));
        this.directors.put("Le Smoking", d);

        // Le medaillon
        d.clear();
        d.add(new Director("Gordon", "Chan"));
        this.directors.put("Le Médaillon", d);

        // Contre Attaque
        d.clear();
        d.add(new Director("Stanley", "Tong"));
        this.directors.put("Contre-attaque", d);


        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        d.clear();
        d.add(new Director("Robert",  "Rodat"));
        this.directors.put("Falling Skies 1", d);
        this.directors.put("Falling Skies 2", d);
        this.directors.put("Falling Skies 3", d);
        this.directors.put("Falling Skies 4", d);
        this.directors.put("Falling Skies 5", d);
    }


    /**
     * Instantiate Producers.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initProducers() {
        this.producers = new HashMap<String, List<Producer>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Star Wars - A New Hope | The Empire Strikes Back
        List<Producer> p = new ArrayList<Producer>();
        p.add(new Producer("Gary", "Kurtz"));
        this.producers.put("Star Wars IV", p);
        this.producers.put("Star Wars V", p);

        // Star Wars - Return of the Jedi
        p.clear();
        p.add(new Producer("Howard", "Kazanjian"));
        this.producers.put("Star Wars VI", p);

        // Johnny English
        p.clear();
        p.add(new Producer("Tim", "Bevan"));
        p.add(new Producer("Eric", "Fellner"));
        p.add(new Producer("Mark", "Huffam"));
        this.producers.put("Johnny English", p);

        // Bienvenue dans la Jungle
        p.clear();
        p.add(new Producer("Marc", "Abraham"));
        p.add(new Producer("Bill", "Corless"));
        p.add(new Producer("Karen", "Glasser"));
        p.add(new Producer("Kevin", "Misher"));
        this.producers.put("Bienvenue dans la Jungle", p);

        // Un Prince a New-York
        p.clear();
        p.add(new Producer("George", "Folsey, Jr."));
        p.add(new Producer("Robert D.", "Wachs"));
        this.producers.put("Un Prince a New-York", p);

        // Maman, je m'occupe des méchants !
        p.clear();
        p.add(new Producer("John", "Hughes"));
        p.add(new Producer("Hilton", "Green"));
        this.producers.put("Maman, je m'occupe des méchants !", p);

        // Sister Act
        p.clear();
        p.add(new Producer("Scott", "Rudin"));
        p.add(new Producer("Teri", "Schwartz"));
        this.producers.put("Sister Act", p);

        // Sister Act 2
        p.clear();
        p.add(new Producer("Scott", "Rudin"));
        p.add(new Producer("Dawn", "Steel"));
        this.producers.put("Sister Act 2", p);

        // Y a-t-il un pilote dans l'avion ?
        p.clear();
        p.add(new Producer("Jon", "Davison"));
        this.producers.put("Y a-t-il un pilote dans l'avion ?", p);

        // Hot Shots
        p.clear();
        p.add(new Producer("Jim", "Abrahams"));
        p.add(new Producer("Pat", "Proft"));
        this.producers.put("Hot Shots", p);
        this.producers.put("Hot Shots 2", p);

        // Shanghai Kid 2
        p.clear();
        p.add(new Producer("Roger", "Birnbaum"));
        p.add(new Producer("Gary", "Barber"));
        p.add(new Producer("Jonathan", "Glickman"));
        this.producers.put("Shanghai Kid 2", p);

        // Le tour du monde en 80 jours
        p.clear();
        p.add(new Producer("Bill", "Badalato"));
        p.add(new Producer("Hal", "Lieberman"));
        this.producers.put("Le Tour du Monde en 80 Jours", p);

        // Espion Amateur
        p.clear();
        p.add(new Producer("Jackie", "Chan"));
        p.add(new Producer("Raymond", "Chow"));
        this.producers.put("Espion Amateur", p);

        // Le Smoking
        p.clear();
        p.add(new Producer("Adam", "Schroeder"));
        p.add(new Producer("John", "H. Williams"));
        this.producers.put("Le Smoking", p);

        // Le medaillon
        p.clear();
        p.add(new Producer("Alfred", "Cheung"));
        this.producers.put("Le Médaillon", p);

        // Contre Attaque
        p.clear();
        p.add(new Producer("Barbie", "Tung"));
        this.producers.put("Contre-attaque", p);


        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        p.clear();
        p.add(new Producer("Steven", "Spielberg"));
        p.add(new Producer("Darryl", "Frank"));
        p.add(new Producer("Justin", "Falvey"));
        p.add(new Producer("Robert", "Rodat"));
        p.add(new Producer("Graham", "Yost"));
        p.add(new Producer("Greg", "Beeman"));
        p.add(new Producer("Remi", "Aubuchon"));
        p.add(new Producer("David", "Eick"));
        p.add(new Producer("John", "Ryan"));
        p.add(new Producer("Noah", "Wyle"));
        p.add(new Producer("Darren", "King"));
        p.add(new Producer("Grace", "Gilroy"));
        this.producers.put("Falling Skies 1", p);
        this.producers.put("Falling Skies 2", p);
        this.producers.put("Falling Skies 3", p);
        this.producers.put("Falling Skies 4", p);
        this.producers.put("Falling Skies 5", p);
    }


    /**
     * Instantiate Movies.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initMovies() {
        this.movies = new HashMap<String, Movie>();
    }

    /**
     * Instantiate Start Date.
     * Only for Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initStartDates() {
        this.startDates = new HashMap<String, Calendar>();

        // Falling Skies
        this.startDates.put("Falling Skies 1", new GregorianCalendar(2011, Calendar.JUNE, 19));
        this.startDates.put("Falling Skies 2", new GregorianCalendar(2011, Calendar.JUNE, 19));
        this.startDates.put("Falling Skies 3", new GregorianCalendar(2011, Calendar.JUNE, 19));
        this.startDates.put("Falling Skies 4", new GregorianCalendar(2011, Calendar.JUNE, 19));
        this.startDates.put("Falling Skies 5", new GregorianCalendar(2011, Calendar.JUNE, 19));
    }

    /**
     * Instantiate end Date.
     * Only for Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initEndDates() {
        this.endDates = new HashMap<String, Calendar>();

        // Falling Skies
        this.endDates.put("Falling Skies 1", new GregorianCalendar(2015, Calendar.AUGUST, 30));
        this.endDates.put("Falling Skies 2", new GregorianCalendar(2015, Calendar.AUGUST, 30));
        this.endDates.put("Falling Skies 3", new GregorianCalendar(2015, Calendar.AUGUST, 30));
        this.endDates.put("Falling Skies 4", new GregorianCalendar(2015, Calendar.AUGUST, 30));
        this.endDates.put("Falling Skies 5", new GregorianCalendar(2015, Calendar.AUGUST, 30));
    }

    /**
     * Instantiate number of seasons.
     * Only for Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initNumberOfSeasons() {
        this.numberOfSeasons = new HashMap<String, Integer>();

        // Falling Skies
        this.numberOfSeasons.put("Falling Skies 1", 5);
        this.numberOfSeasons.put("Falling Skies 2", 5);
        this.numberOfSeasons.put("Falling Skies 3", 5);
        this.numberOfSeasons.put("Falling Skies 4", 5);
        this.numberOfSeasons.put("Falling Skies 5", 5);
    }

    /**
     * Instantiate current seasons.
     * Only for Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initCurrentSeasons() {
        this.currentSeasons = new HashMap<String, Integer>();

        // Falling Skies
        this.currentSeasons.put("Falling Skies 1", 1);
        this.currentSeasons.put("Falling Skies 2", 2);
        this.currentSeasons.put("Falling Skies 3", 3);
        this.currentSeasons.put("Falling Skies 4", 4);
        this.currentSeasons.put("Falling Skies 5", 5);
    }

    /**
     * Instantiate max Episodes.
     * Only for Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initMaxEpisodes() {
        this.maxEpisodes = new HashMap<String, Integer>();

        // Falling Skies
        this.maxEpisodes.put("Falling Skies 1", 52);
        this.maxEpisodes.put("Falling Skies 2", 52);
        this.maxEpisodes.put("Falling Skies 3", 52);
        this.maxEpisodes.put("Falling Skies 4", 52);
        this.maxEpisodes.put("Falling Skies 5", 52);
    }

    /**
     * Instantiate number of episodes.
     * Only for Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initNumberOfEpisodes() {
        this.numberOfEpisodes = new HashMap<String, Integer>();

        // Falling Skies
        this.numberOfEpisodes.put("Falling Skies 1", 10);
        this.numberOfEpisodes.put("Falling Skies 2", 10);
        this.numberOfEpisodes.put("Falling Skies 3", 10);
        this.numberOfEpisodes.put("Falling Skies 4", 12);
        this.numberOfEpisodes.put("Falling Skies 5", 10);
    }

    /**
     * Instantiate runtime.
     * Only for Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initAverageRuntimes() {
        this.averageRuntimes = new HashMap<String, Integer>();

        // Falling Skies
        this.averageRuntimes.put("Falling Skies 1", 42);
        this.averageRuntimes.put("Falling Skies 2", 42);
        this.averageRuntimes.put("Falling Skies 3", 42);
        this.averageRuntimes.put("Falling Skies 4", 42);
        this.averageRuntimes.put("Falling Skies 5", 42);
    }


    /**
     * Instantiate Series.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initSeries() {
        this.series = new HashMap<String, Series>();

    }
}
