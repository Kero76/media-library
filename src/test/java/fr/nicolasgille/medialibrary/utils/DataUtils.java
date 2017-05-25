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
import fr.nicolasgille.medialibrary.models.common.person.Actor;
import fr.nicolasgille.medialibrary.models.common.person.Director;
import fr.nicolasgille.medialibrary.models.common.person.Producer;
import fr.nicolasgille.medialibrary.models.components.MediaGenre;
import fr.nicolasgille.medialibrary.models.components.MediaSupport;
import fr.nicolasgille.medialibrary.models.video.Movie;
import fr.nicolasgille.medialibrary.models.video.Series;

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
    private Map<String, List<MediaGenre>> genres;
    private Map<String, List<MediaSupport>> supports;
    private Map<String, List<LanguageCode>> spokens;
    private Map<String, List<LanguageCode>> subtitles;
    private Map<String, Calendar> releasedDates;
    private Map<String, Integer> runtimes;
    private Map<String, Set<Actor>> actors;
    private Map<String, Set<Director>> directors;
    private Map<String, Set<Producer>> producers;
    private Map<String, Movie> movies;
    private Map<String, Series> series;
    private Map<String, Calendar> startDates;
    private Map<String, Calendar> endDates;
    private Map<String, Integer> numberOfSeasons;
    private Map<String, Integer> currentSeasons;
    private Map<String, Integer> maxEpisodes;
    private Map<String, Integer> numberOfEpisodes;
    private Map<String, Integer> averageRuntimes;


    /**
     * List of all keys used for Medias.
     * @since 1.0
     */
    private Map<String, List<String>> keys;

    /**
     * Unique instance of DataUtils instantiate at null.
     * @since 1.0
     */
    private static DataUtils INSTANCE = null;

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
        this.initNumberOfEpisodes();
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
        if (DataUtils.INSTANCE == null) {
            DataUtils.INSTANCE = new DataUtils();
        }
        return DataUtils.INSTANCE;
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
     * Get all keys.
     *
     * @return
     *  Map with all keys at insert on Database.
     * @since 1.0
     * @version 1.0
     */
    public Map<String, List<String>> getKeys() {
        return keys;
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
        k.add("Rush Hour");
        k.add("Rush Hour 2");
        k.add("Rush Hour 3");
        k.add("Police Academy");
        k.add("Police Academy 2");
        k.add("Police Academy 3");
        k.add("Police Academy 4");
        k.add("Police Academy 5");
        k.add("Police Academy 6");
        k.add("Police Academy 7");

        this.keys.put("movies", k);
        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        k = new ArrayList<String>();
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
        this.titles.put("Rush Hour", "Rush Hour");
        this.titles.put("Rush Hour 2", "Rush Hour 2");
        this.titles.put("Rush Hour 3", "Rush Hour 3");
        this.titles.put("Police Academy", "Police Academy");
        this.titles.put("Police Academy 2", "Police Academy 2 : Au Boulot !");
        this.titles.put("Police Academy 3", "Police Academy 3 : Instructeurs de Choc");
        this.titles.put("Police Academy 4", "Police Academy 4 : Aux Armes Citoyens");
        this.titles.put("Police Academy 5", "Police Academy 5 : Débarquement à Miami Beach");
        this.titles.put("Police Academy 6", "Police Academy 6 : S.O.S Ville en état de Choc");
        this.titles.put("Police Academy 7", "Police Academy 7 : Mission à Moscou");


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
        this.originalTitles = new HashMap<String, String>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        this.originalTitles.put("Star Wars IV", "Star Wars : A New Hope");
        this.originalTitles.put("Star Wars V", "Star Wars : The Empire Strikes Back");
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
        this.originalTitles.put("Le Smoking", "The Tuxedo");
        this.originalTitles.put("Le Médaillon", "The Medallion");
        this.originalTitles.put("Contre-attaque", "Police Story 4 : First Strike");
        this.originalTitles.put("Rush Hour", "Rush Hour");
        this.originalTitles.put("Rush Hour 2", "Rush Hour 2");
        this.originalTitles.put("Rush Hour 3", "Rush Hour 3");
        this.originalTitles.put("Police Academy", "Police Academy");
        this.originalTitles.put("Police Academy 2", "Police Academy 2 : Their First Assignment");
        this.originalTitles.put("Police Academy 3", "Police Academy 3 : Back in Training");
        this.originalTitles.put("Police Academy 4", "Police Academy 4 : Citizens on Patrol");
        this.originalTitles.put("Police Academy 5", "Police Academy 5 : Assignment Miami Beach");
        this.originalTitles.put("Police Academy 6", "Police Academy 6 : City Under Siege");
        this.originalTitles.put("Police Academy 7", "Police Academy 7 : Mission to Moscow");


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
        this.synopsis.put("Sister Act", "");
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
        this.synopsis.put("Rush Hour", "");
        this.synopsis.put("Rush Hour 2", "");
        this.synopsis.put("Rush Hour 3", "");
        this.synopsis.put("Police Academy", "");
        this.synopsis.put("Police Academy 2", "");
        this.synopsis.put("Police Academy 3", "");
        this.synopsis.put("Police Academy 4", "");
        this.synopsis.put("Police Academy 5", "");
        this.synopsis.put("Police Academy 6", "");
        this.synopsis.put("Police Academy 7", "");


        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        this.synopsis.put("Falling Skies 1", "");
        this.synopsis.put("Falling Skies 2", "");
        this.synopsis.put("Falling Skies 3", "");
        this.synopsis.put("Falling Skies 4", "");
        this.synopsis.put("Falling Skies 5", "");
    }

    /**
     * Instantiate Genres
     *
     * @since 1.0
     * @version 1.0
     */
    private void initGenres() {
        this.genres = new HashMap<String, List<MediaGenre>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Science Fiction
        List<MediaGenre> g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.SCIENCE_FICTION);
        this.genres.put("Star Wars IV", g);
        this.genres.put("Star Wars V", g);
        this.genres.put("Star Wars VI", g);

        // Comedy / Spy
        g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.COMEDY);
        g.add(MediaGenre.SPY);
        this.genres.put("Johnny English", g);

        // Action
        g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.ACTION);
        this.genres.put("Bienvenue dans la Jungle", g);
        this.genres.put("Espion Amateur", g);
        this.genres.put("Contre-attaque", g);

        // Comedy / Romantic
        g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.COMEDY);
        g.add(MediaGenre.ROMANTIC);
        this.genres.put("Un Prince a New-York", g);

        // Musical
        g.add(MediaGenre.MUSICAL);
        this.genres.put("Sister Act", g);
        this.genres.put("Sister Act 2", g);

        // Comedy
        g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.COMEDY);
        this.genres.put("Maman, je m'occupe des méchants !", g);
        this.genres.put("Police Academy", g);
        this.genres.put("Police Academy 2", g);
        this.genres.put("Police Academy 3", g);
        this.genres.put("Police Academy 4", g);
        this.genres.put("Police Academy 5", g);
        this.genres.put("Police Academy 6", g);
        this.genres.put("Police Academy 7", g);
        this.genres.put("Y a-t-il un pilote dans l'avion ?", g);
        this.genres.put("Hot Shots", g);
        this.genres.put("Hot Shots 2", g);

        // Le tour du monde en 80
        g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.ACTION);
        g.add(MediaGenre.ADVENTURE);
        g.add(MediaGenre.COMEDY);
        this.genres.put("Le Tour du Monde en 80 Jours", g);

        // Action / Comedy
        g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.ACTION);
        g.add(MediaGenre.COMEDY);
        this.genres.put("Shanghai Kid 2", g);
        this.genres.put("Le Smoking", g);
        this.genres.put("Le Médaillon", g);
        this.genres.put("Rush Hour", g);
        this.genres.put("Rush Hour 2", g);
        this.genres.put("Rush Hour 3", g);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        g = new ArrayList<MediaGenre>();
        g.add(MediaGenre.ACTION);
        g.add(MediaGenre.ADVENTURE);
        g.add(MediaGenre.DRAMA);
        g.add(MediaGenre.SCIENCE_FICTION);
        g.add(MediaGenre.THRILLER);
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
        this.supports = new HashMap<String, List<MediaSupport>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Movies
        List<MediaSupport> s = new ArrayList<MediaSupport>();
        s.add(MediaSupport.DVD);
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
        this.supports.put("Rush Hour", s);
        this.supports.put("Rush Hour 2", s);
        this.supports.put("Rush Hour 3", s);
        this.supports.put("Police Academy", s);
        this.supports.put("Police Academy 2", s);
        this.supports.put("Police Academy 3", s);
        this.supports.put("Police Academy 4", s);
        this.supports.put("Police Academy 5", s);
        this.supports.put("Police Academy 6", s);
        this.supports.put("Police Academy 7", s);


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
        this.spokens.put("Rush Hour", l);
        this.spokens.put("Rush Hour 2", l);
        this.spokens.put("Rush Hour 3", l);

        // French | English | Italian | Spanish
        l = new ArrayList<LanguageCode>();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        l.add(LanguageCode.it);
        l.add(LanguageCode.es);
        this.spokens.put("Un Prince a New-York", l);

        // French | English | Italian
        l = new ArrayList<LanguageCode>();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        l.add(LanguageCode.it);
        this.spokens.put("Maman, je m'occupe des méchants !", l);
        this.spokens.put("Y a-t-il un pilote dans l'avion ?", l);
        this.spokens.put("Shanghai Kid 2", l);
        this.spokens.put("Police Academy", l);
        this.spokens.put("Police Academy 2", l);
        this.spokens.put("Police Academy 3", l);
        this.spokens.put("Police Academy 4", l);
        this.spokens.put("Police Academy 5", l);
        this.spokens.put("Police Academy 6", l);
        this.spokens.put("Police Academy 7", l);

        // French | English | Deutsh
        l = new ArrayList<LanguageCode>();
        l.add(LanguageCode.fr);
        l.add(LanguageCode.en);
        l.add(LanguageCode.de);
        this.spokens.put("Le Smoking", l);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        l = new ArrayList<LanguageCode>();
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
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.fr);
        this.subtitles.put("Johnny English", s);
        this.subtitles.put("Le Tour du Monde en 80 Jours", s);
        this.subtitles.put("Espion Amateur", s);
        this.subtitles.put("Le Médaillon", s);
        this.subtitles.put("Contre-attaque", s);
        this.subtitles.put("Rush Hour", s);
        this.subtitles.put("Rush Hour 2", s);
        this.subtitles.put("Rush Hour 3", s);

        // French | English | Arab
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.en);
        s.add(LanguageCode.fr);
        s.add(LanguageCode.ar);
        this.subtitles.put("Bienvenue dans la Jungle", s);

        // English | Croatian | Spanish | French | Greek | Hebrew | Italian | Portugues
        s = new ArrayList<LanguageCode>();
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
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.en);
        s.add(LanguageCode.fr);
        s.add(LanguageCode.it);
        s.add(LanguageCode.el);
        s.add(LanguageCode.nl);
        this.subtitles.put("Maman, je m'occupe des méchants !", s);

        // French | English
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        this.subtitles.put("Sister Act", s);
        this.subtitles.put("Sister Act 2", s);
        this.subtitles.put("Hot Shots", s);
        this.subtitles.put("Hot Shots 2", s);

        // French | English | Italian | Spanish
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.it);
        s.add(LanguageCode.es);
        this.subtitles.put("Y a-t-il un pilote dans l'avion ?", s);

        // French | English | Italian | Portuguese
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.it);
        s.add(LanguageCode.pt);
        this.subtitles.put("Shanghai Kid 2", s);

        // French | English | Bosch | Dutch | Bulgarian | Arab
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.de);
        s.add(LanguageCode.nl);
        s.add(LanguageCode.bg);
        s.add(LanguageCode.ar);
        this.subtitles.put("Le Smoking", s);

        // French / English / Italian / Bosch / Spanish / Arab / Bulgarian / Romanian / Dutch
        s = new ArrayList<LanguageCode>();
        s.add(LanguageCode.fr);
        s.add(LanguageCode.en);
        s.add(LanguageCode.it);
        s.add(LanguageCode.de);
        s.add(LanguageCode.es);
        s.add(LanguageCode.ar);
        s.add(LanguageCode.bg);
        s.add(LanguageCode.ro);
        s.add(LanguageCode.nl);
        this.subtitles.put("Police Academy", s);
        this.subtitles.put("Police Academy 2", s);
        this.subtitles.put("Police Academy 3", s);
        this.subtitles.put("Police Academy 4", s);
        this.subtitles.put("Police Academy 5", s);
        this.subtitles.put("Police Academy 6", s);
        this.subtitles.put("Police Academy 7", s);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        s = new ArrayList<LanguageCode>();
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
        this.runtimes.put("Rush Hour", 93);
        this.runtimes.put("Rush Hour 2", 93);
        this.runtimes.put("Rush Hour 3", 87);
        this.runtimes.put("Police Academy", 92);
        this.runtimes.put("Police Academy 2", 83);
        this.runtimes.put("Police Academy 3", 80);
        this.runtimes.put("Police Academy 4", 84);
        this.runtimes.put("Police Academy 5", 86);
        this.runtimes.put("Police Academy 6", 80);
        this.runtimes.put("Police Academy 7", 79);
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
        this.releasedDates.put("Y a-t-il un pilote dans l'avion ?", new GregorianCalendar(1980, Calendar.JULY, 2));
        this.releasedDates.put("Hot Shots", new GregorianCalendar(1991, Calendar.JULY, 31));
        this.releasedDates.put("Hot Shots 2", new GregorianCalendar(1993, Calendar.MAY, 21));
        this.releasedDates.put("Shanghai Kid 2", new GregorianCalendar(2003, Calendar.FEBRUARY, 7));
        this.releasedDates.put("Le Tour du Monde en 80 Jours", new GregorianCalendar(2004, Calendar.JUNE, 13));
        this.releasedDates.put("Espion Amateur", new GregorianCalendar(2001, Calendar.JANUARY, 18));
        this.releasedDates.put("Le Smoking", new GregorianCalendar(2002, Calendar.SEPTEMBER, 27));
        this.releasedDates.put("Le Médaillon", new GregorianCalendar(2003, Calendar.AUGUST, 15));
        this.releasedDates.put("Contre-attaque", new GregorianCalendar(1996, Calendar.FEBRUARY, 10));
        this.releasedDates.put("Rush Hour", new GregorianCalendar(1998, Calendar.SEPTEMBER, 18));
        this.releasedDates.put("Rush Hour 2", new GregorianCalendar(2001, Calendar.AUGUST, 3));
        this.releasedDates.put("Rush Hour 3", new GregorianCalendar(2007, Calendar.JULY, 30));
        this.releasedDates.put("Police Academy", new GregorianCalendar(1984, Calendar.MARCH, 3));
        this.releasedDates.put("Police Academy 2", new GregorianCalendar(1985, Calendar.MARCH, 29));
        this.releasedDates.put("Police Academy 3", new GregorianCalendar(1986, Calendar.MARCH, 21));
        this.releasedDates.put("Police Academy 4", new GregorianCalendar(1987, Calendar.APRIL, 3));
        this.releasedDates.put("Police Academy 5", new GregorianCalendar(1988, Calendar.MARCH, 18));
        this.releasedDates.put("Police Academy 6", new GregorianCalendar(1989, Calendar.MARCH, 10));
        this.releasedDates.put("Police Academy 7", new GregorianCalendar(1994, Calendar.AUGUST, 26));
    }

    /**
     * Instantiate Actors.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initActors() {
        this.actors = new HashMap<String, Set<Actor>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Star Wars : A New Hope
        Set<Actor> a = new HashSet<Actor>();
        a.add(new Actor("Mark", "Hamill"));
        a.add(new Actor("Harrison", "Ford"));
        a.add(new Actor("Carrie", "Fisher"));
        a.add(new Actor("Peter", "Cushing"));
        a.add(new Actor("Alec", "Guinness"));
        this.actors.put("Star Wars IV", a);

        // Star Wars : The Empire Strikes Back & Star Wats : Return of the Jedi
        a = new HashSet<Actor>();
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
        a = new HashSet<Actor>();
        a.add(new Actor("Rowan", "Atkinson"));
        a.add(new Actor("Natalie", "Imbruglia"));
        a.add(new Actor("Ben", "Miller"));
        a.add(new Actor("John", "Malkovich"));
        this.actors.put("Johnny English", a);

        // Bienvenue dans la Jungle
        a = new HashSet<Actor>();
        a.add(new Actor("Dwayne", "Johnson"));
        a.add(new Actor("Seann William", "Scott"));
        a.add(new Actor("Christopher", "Walken"));
        a.add(new Actor("Rosario", "Dawson"));
        a.add(new Actor("Ewen", "Bremner"));
        a.add(new Actor("Jon", "Gries"));
        this.actors.put("Bienvenue dans la Jungle", a);

        // Un Prince a New-York
        a = new HashSet<Actor>();
        a.add(new Actor("Eddie", "Murphy"));
        a.add(new Actor("Arsenio", "Hall"));
        a.add(new Actor("James Earl", "Jones"));
        a.add(new Actor("John", "Amos"));
        a.add(new Actor("Madge", "Sinclair"));
        a.add(new Actor("Shari", "Headley"));
        this.actors.put("Un Prince a New-York", a);

        // Maman, je m'occupe des méchants !
        a = new HashSet<Actor>();
        a.add(new Actor("Alex D.", "Linz"));
        a.add(new Actor("Haviland", "Morris"));
        this.actors.put("Maman, je m'occupe des méchants !", a);

        // Sister Act
        a = new HashSet<Actor>();
        a.add(new Actor("Whoopi", "Goldberg"));
        a.add(new Actor("Maggie", "Smith"));
        a.add(new Actor("Harvey", "Keitel"));
        this.actors.put("Sister Act", a);

        // Sister Act 2
        a = new HashSet<Actor>();
        a.add(new Actor("Whoopi", "Goldberg"));
        a.add(new Actor("Kathy", "Najimy"));
        a.add(new Actor("James", "Coburn"));
        a.add(new Actor("Maggie", "Smith"));
        this.actors.put("Sister Act 2", a);

        // Y a-t-il un pilote dans l'avion ?
        a = new HashSet<Actor>();
        a.add(new Actor("Robert", "Hays"));
        a.add(new Actor("Julie", "Hagerty"));
        this.actors.put("Y a-t-il un pilote dans l'avion ?", a);

        // Hot Shots
        a = new HashSet<Actor>();
        a.add(new Actor("Charlie", "Sheen"));
        a.add(new Actor("Cary", "Elwes"));
        a.add(new Actor("Valeria", "Golino"));
        a.add(new Actor("Jon", "Cryer"));
        a.add(new Actor("Kevin", "Dunn"));
        a.add(new Actor("Bill", "Irwin"));
        a.add(new Actor("Lloyd", "Bridges"));
        this.actors.put("Hot Shots", a);

        // Hot Shots 2
        a = new HashSet<Actor>();
        a.add(new Actor("Charlie", "Sheen"));
        a.add(new Actor("Lloyd", "Bridges"));
        a.add(new Actor("Valeria", "Golino"));
        a.add(new Actor("Brenda", "Bakke"));
        a.add(new Actor("Richard", "Crenna"));
        this.actors.put("Hot Shots 2", a);

        // Shanghai Kid 2
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Owen", "Wilson"));
        a.add(new Actor("Donnie", "Yen"));
        a.add(new Actor("Aidan", "Gillen"));
        a.add(new Actor("Fann", "Wong"));
        a.add(new Actor("Tom", "Fisher"));
        a.add(new Actor("Kim", "Chan"));
        this.actors.put("Shanghai Kid 2", a);

        // Le tour du monde en 80 jours
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Steve", "Coogan"));
        a.add(new Actor("Cécile", "de France"));
        a.add(new Actor("Jim", "Broadbent"));
        a.add(new Actor("Ian", "McNeice"));
        a.add(new Actor("Karen", "Mok"));
        this.actors.put("Le Tour du Monde en 80 Jours", a);

        // Espion Amateur
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Eric", "Tsang"));
        a.add(new Actor("Vivian", "Hsu"));
        a.add(new Actor("Wu", "Hsing-kuo"));
        this.actors.put("Espion Amateur", a);

        // Le Smoking
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Jennifer", "Love Hewitt"));
        this.actors.put("Le Smoking", a);

        // Le medaillon
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Lee", "Evans"));
        a.add(new Actor("Claire", "Forlani"));
        a.add(new Actor("Julian", "Sands"));
        this.actors.put("Le Médaillon", a);

        // Contre Attaque
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Wu", "Chen-chun"));
        a.add(new Actor("Jackson", "Lou"));
        this.actors.put("Contre-attaque", a);

        // Rush Hour
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Chris", "Tucker"));
        a.add(new Actor("Tom", "Wilkinson"));
        a.add(new Actor("Chris", "Penn"));
        a.add(new Actor("Elizabeth", "Peña"));
        this.actors.put("Rush Hour", a);

        // Rush Hour 2
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Chris", "Tucker"));
        a.add(new Actor("John", "Lone"));
        a.add(new Actor("Alan", "King"));
        a.add(new Actor("Roselyn", "Sánchez"));
        a.add(new Actor("Harris", "Yulin"));
        a.add(new Actor("Zhang", "Ziyi"));
        this.actors.put("Rush Hour 2", a);

        // Rush Hour 3
        a = new HashSet<Actor>();
        a.add(new Actor("Jackie", "Chan"));
        a.add(new Actor("Chris", "Tucker"));
        a.add(new Actor("Hiroyuki", "Sanada"));
        a.add(new Actor("Youki", "Kudoh"));
        a.add(new Actor("Max", "von Sydow"));
        this.actors.put("Rush Hour 3", a);

        // Police Academy
        a = new HashSet<Actor>();
        a.add(new Actor("Steve", "Guttenberg"));
        a.add(new Actor("Kim", "Cattrall"));
        a.add(new Actor("Bubba", "Smith"));
        a.add(new Actor("George", "Gaynes"));
        this.actors.put("Police Academy", a);

        // Police Academy 2
        a = new HashSet<Actor>();
        a.add(new Actor("Steve", "Guttenberg"));
        a.add(new Actor("Bubba", "Smith"));
        a.add(new Actor("David", "Graf"));
        a.add(new Actor("Michael", "Winslow"));
        a.add(new Actor("Bruce", "Mahler"));
        a.add(new Actor("Colleen", "Camp"));
        a.add(new Actor("Art", "Metrano"));
        a.add(new Actor("Marion", "Ramsey"));
        a.add(new Actor("Howard", "Hesseman"));
        a.add(new Actor("George", "Gaynes"));
        this.actors.put("Police Academy 2", a);

        // Police Academy 3
        a = new HashSet<Actor>();
        a.add(new Actor("Steve", "Guttenberg"));
        a.add(new Actor("Bubba", "Smith"));
        a.add(new Actor("David", "Graf"));
        a.add(new Actor("Michael", "Winslow"));
        a.add(new Actor("Marion", "Ramsey"));
        a.add(new Actor("Leslie", "Easterbrook"));
        a.add(new Actor("Art", "Metrano"));
        a.add(new Actor("Tim", "Kazurinsky"));
        a.add(new Actor("Bobcat", "Goldthwait"));
        this.actors.put("Police Academy 3", a);

        // Police Academy 4
        a = new HashSet<Actor>();
        a.add(new Actor("Steve", "Guttenberg"));
        a.add(new Actor("Bubba", "Smith"));
        a.add(new Actor("Michael", "Winslow"));
        a.add(new Actor("David", "Graf"));
        a.add(new Actor("Tim", "Kazurinsky"));
        a.add(new Actor("Sharon", "Stone"));
        a.add(new Actor("Marion", "Ramsey"));
        a.add(new Actor("Lance", "Kinsey"));
        a.add(new Actor("Leslie", "Easterbrook"));
        a.add(new Actor("Colleen", "Camp"));
        a.add(new Actor("G. W.", "Bailey"));
        a.add(new Actor("Bobcat", "Goldthwait"));
        this.actors.put("Police Academy 4", a);

        // Police Academy 5
        a = new HashSet<Actor>();
        a.add(new Actor("Bubba", "Smith"));
        a.add(new Actor("David", "Graf"));
        a.add(new Actor("Michael", "Winslow"));
        a.add(new Actor("Leslie", "Easterbrook"));
        a.add(new Actor("Marion", "Ramsey"));
        a.add(new Actor("Janet", "Jones"));
        a.add(new Actor("Lance", "Kinsey"));
        a.add(new Actor("Matt", "McCoy"));
        a.add(new Actor("G. W.", "Bailey"));
        a.add(new Actor("George", "Gaynes"));
        this.actors.put("Police Academy 5", a);

        // Police Academy 6
        a = new HashSet<Actor>();
        a.add(new Actor("Bubba", "Smith"));
        a.add(new Actor("Michael", "Winslow"));
        a.add(new Actor("David", "Graf"));
        a.add(new Actor("Marion", "Ramsey"));
        a.add(new Actor("Leslie", "Easterbrook"));
        a.add(new Actor("Lance", "Kinsey"));
        a.add(new Actor("Bruce", "Mahler"));
        a.add(new Actor("Kenneth", "Mars"));
        a.add(new Actor("Matt", "McCoy"));
        a.add(new Actor("G. W.", "Bailey"));
        a.add(new Actor("George", "Gaynes"));
        this.actors.put("Police Academy 6", a);

        // Police Academy 7
        a = new HashSet<Actor>();
        a.add(new Actor("George", "Gaynes"));
        a.add(new Actor("Michael", "Winslow"));
        a.add(new Actor("David", "Graf"));
        a.add(new Actor("Leslie", "Easterbrook"));
        a.add(new Actor("Claire", "Forlani"));
        a.add(new Actor("Ron", "Perlman"));
        a.add(new Actor("Christopher", "Lee"));
        a.add(new Actor("Charlie", "Schlatter"));
        a.add(new Actor("G. W.", "Bailey"));
        this.actors.put("Police Academy 7", a);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Series - Falling Skies
        a = new HashSet<Actor>();
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
        this.directors = new HashMap<String, Set<Director>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Star Wars : A New Hope
        Set<Director> d = new HashSet<Director>();
        d.add(new Director("George", "Lucas"));
        this.directors.put("Star Wars IV", d);

        // Star Wars : The Empire Strikes Back
        d = new HashSet<Director>();
        d.add(new Director("Irvin", "Kershner"));
        this.directors.put("Star Wars V", d);

        // Star Wats : Return of the Jedi
        d = new HashSet<Director>();
        d.add(new Director("Richard", "Marquand"));
        this.directors.put("Star Wars VI", d);

        // Johnny English
        d = new HashSet<Director>();
        d.add(new Director("Peter", "Howitt"));
        this.directors.put("Johnny English", d);

        // Bienvenue dans la Jungle
        d = new HashSet<Director>();
        d.add(new Director("Peter", "Berg"));
        this.directors.put("Bienvenue dans la Jungle", d);

        // Un Prince a New-York
        d = new HashSet<Director>();
        d.add(new Director("John", "Landis"));
        this.directors.put("Un Prince a New-York", d);

        // Maman, je m'occupe des méchants !
        d = new HashSet<Director>();
        d.add(new Director("Raja", "Gosnell"));
        this.directors.put("Maman, je m'occupe des méchants !", d);

        // Sister Act
        d = new HashSet<Director>();
        d.add(new Director("Emile", "Ardolino"));
        this.directors.put("Sister Act", d);

        // Sister Act 2
        d = new HashSet<Director>();
        d.add(new Director("Bill", "Duke"));
        this.directors.put("Sister Act 2", d);

        // Y a-t-il un pilote dans l'avion ?
        d = new HashSet<Director>();
        d.add(new Director("Jim", "Abrahams"));
        d.add(new Director("David", "Zucker"));
        d.add(new Director("Jerry", "Zucker"));
        this.directors.put("Y a-t-il un pilote dans l'avion ?", d);

        // Hot Shots 1 & 2
        d = new HashSet<Director>();
        d.add(new Director("Jim", "Abrahams"));
        this.directors.put("Hot Shots", d);
        this.directors.put("Hot Shots 2", d);

        // Shanghai Kid 2
        d = new HashSet<Director>();
        d.add(new Director("David", "Dobkin"));
        this.directors.put("Shanghai Kid 2", d);

        // Le tour du monde en 80 jours
        d = new HashSet<Director>();
        d.add(new Director("Frank", "Coraci"));
        this.directors.put("Le Tour du Monde en 80 Jours", d);

        // Espion Amateur
        d = new HashSet<Director>();
        d.add(new Director("Teddy", "Chan"));
        this.directors.put("Espion Amateur", d);

        // Le Smoking
        d = new HashSet<Director>();
        d.add(new Director("Kevin", "Donovan"));
        this.directors.put("Le Smoking", d);

        // Le medaillon
        d = new HashSet<Director>();
        d.add(new Director("Gordon", "Chan"));
        this.directors.put("Le Médaillon", d);

        // Contre Attaque
        d = new HashSet<Director>();
        d.add(new Director("Stanley", "Tong"));
        this.directors.put("Contre-attaque", d);

        // Rush Hour
        d = new HashSet<Director>();
        d.add(new Director("Brett", "Ratner"));
        this.directors.put("Rush Hour", d);
        this.directors.put("Rush Hour 2", d);
        this.directors.put("Rush Hour 3", d);

        // Police Academy
        d = new HashSet<Director>();
        d.add(new Director("Hugh", "Wilson"));
        this.directors.put("Police Academy", d);

        // Police Academy 2
        d = new HashSet<Director>();
        d.add(new Director("Jerry", "Paris"));
        this.directors.put("Police Academy 2", d);
        this.directors.put("Police Academy 3", d);

        // Police Academy 4
        d = new HashSet<Director>();
        d.add(new Director("Jim", "Drake"));
        this.directors.put("Police Academy 4", d);

        // Police Academy 5
        d = new HashSet<Director>();
        d.add(new Director("Alan", "Myerson"));
        this.directors.put("Police Academy 5", d);

        // Police Academy 6
        d = new HashSet<Director>();
        d.add(new Director("Peter", "Bonerz"));
        this.directors.put("Police Academy 6", d);

        // Police Academy 7
        d = new HashSet<Director>();
        d.add(new Director("Alan", "Metter"));
        this.directors.put("Police Academy 7", d);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        d = new HashSet<Director>();
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
        this.producers = new HashMap<String, Set<Producer>>();

        ////////////////////////////////////////////
        ///                 MOVIES              ////
        ////////////////////////////////////////////
        // Star Wars - A New Hope | The Empire Strikes Back
        Set<Producer> p = new HashSet<Producer>();
        p.add(new Producer("Gary", "Kurtz"));
        this.producers.put("Star Wars IV", p);
        this.producers.put("Star Wars V", p);

        // Star Wars - Return of the Jedi
        p = new HashSet<Producer>();
        p.add(new Producer("Howard", "Kazanjian"));
        this.producers.put("Star Wars VI", p);

        // Johnny English
        p = new HashSet<Producer>();
        p.add(new Producer("Tim", "Bevan"));
        p.add(new Producer("Eric", "Fellner"));
        p.add(new Producer("Mark", "Huffam"));
        this.producers.put("Johnny English", p);

        // Bienvenue dans la Jungle
        p = new HashSet<Producer>();
        p.add(new Producer("Marc", "Abraham"));
        p.add(new Producer("Bill", "Corless"));
        p.add(new Producer("Karen", "Glasser"));
        p.add(new Producer("Kevin", "Misher"));
        this.producers.put("Bienvenue dans la Jungle", p);

        // Un Prince a New-York
        p = new HashSet<Producer>();
        p.add(new Producer("George", "Folsey, Jr."));
        p.add(new Producer("Robert D.", "Wachs"));
        this.producers.put("Un Prince a New-York", p);

        // Maman, je m'occupe des méchants !
        p = new HashSet<Producer>();
        p.add(new Producer("John", "Hughes"));
        p.add(new Producer("Hilton", "Green"));
        this.producers.put("Maman, je m'occupe des méchants !", p);

        // Sister Act
        p = new HashSet<Producer>();
        p.add(new Producer("Scott", "Rudin"));
        p.add(new Producer("Teri", "Schwartz"));
        this.producers.put("Sister Act", p);

        // Sister Act 2
        p = new HashSet<Producer>();
        p.add(new Producer("Scott", "Rudin"));
        p.add(new Producer("Dawn", "Steel"));
        this.producers.put("Sister Act 2", p);

        // Y a-t-il un pilote dans l'avion ?
        p = new HashSet<Producer>();
        p.add(new Producer("Jon", "Davison"));
        this.producers.put("Y a-t-il un pilote dans l'avion ?", p);

        // Hot Shots
        p = new HashSet<Producer>();
        p.add(new Producer("Jim", "Abrahams"));
        p.add(new Producer("Pat", "Proft"));
        this.producers.put("Hot Shots", p);
        this.producers.put("Hot Shots 2", p);

        // Shanghai Kid 2
        p = new HashSet<Producer>();
        p.add(new Producer("Roger", "Birnbaum"));
        p.add(new Producer("Gary", "Barber"));
        p.add(new Producer("Jonathan", "Glickman"));
        this.producers.put("Shanghai Kid 2", p);

        // Le tour du monde en 80 jours
        p = new HashSet<Producer>();
        p.add(new Producer("Bill", "Badalato"));
        p.add(new Producer("Hal", "Lieberman"));
        this.producers.put("Le Tour du Monde en 80 Jours", p);

        // Espion Amateur
        p = new HashSet<Producer>();
        p.add(new Producer("Jackie", "Chan"));
        p.add(new Producer("Raymond", "Chow"));
        this.producers.put("Espion Amateur", p);

        // Le Smoking
        p = new HashSet<Producer>();
        p.add(new Producer("Adam", "Schroeder"));
        p.add(new Producer("John", "H. Williams"));
        this.producers.put("Le Smoking", p);

        // Le medaillon
        p = new HashSet<Producer>();
        p.add(new Producer("Alfred", "Cheung"));
        this.producers.put("Le Médaillon", p);

        // Contre Attaque
        p = new HashSet<Producer>();
        p.add(new Producer("Barbie", "Tung"));
        this.producers.put("Contre-attaque", p);

        // Rush Hour
        p = new HashSet<Producer>();
        p.add(new Producer("Roger", "Birnbaum"));
        p.add(new Producer("Jonathan", "Glickman"));
        p.add(new Producer("Arthur", "Sarkissian"));
        this.producers.put("Rush Hour", p);

        // Rush Hour 2
        p = new HashSet<Producer>();
        p.add(new Producer("Arthur", "Sarkissian"));
        this.producers.put("Rush Hour 2", p);

        // Rush Hour 3
        p = new HashSet<Producer>();
        p.add(new Producer("Roger", "Birnbaum"));
        p.add(new Producer("Michael", "Poryes"));
        p.add(new Producer("Jonathan", "Glickman"));
        p.add(new Producer("Jay", "Stern"));
        this.producers.put("Rush Hour 3", p);

        // Police Academy
        p = new HashSet<Producer>();
        p.add(new Producer("Paul", "Maslansky"));
        this.producers.put("Police Academy", p);
        this.producers.put("Police Academy 7", p);

        // Police Academy 2
        p = new HashSet<Producer>();
        p.add(new Producer("Paul", "Maslansky"));
        p.add(new Producer("Leonard", "Croll"));
        this.producers.put("Police Academy 2", p);

        // Police Academy 3
        p = new HashSet<Producer>();
        p.add(new Producer("Paul", "Maslansky"));
        p.add(new Producer("West", "Donald"));
        this.producers.put("Police Academy 3", p);
        this.producers.put("Police Academy 4", p);
        this.producers.put("Police Academy 5", p);
        this.producers.put("Police Academy 6", p);

        ////////////////////////////////////////////
        ///                 SERIES              ////
        ////////////////////////////////////////////
        // Falling Skies
        p = new HashSet<Producer>();
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

        // Loop on each title of movie.
        for (String key : this.keys.get("movies")) {
            this.movies.put(key, new Movie(
                this.titles.get(key),
                this.originalTitles.get(key),
                this.synopsis.get(key),
                this.actors.get(key),
                this.directors.get(key),
                this.producers.get(key),
                this.genres.get(key),
                this.supports.get(key),
                this.spokens.get(key),
                this.subtitles.get(key),
                this.releasedDates.get(key),
                this.runtimes.get(key)
            ));
        }
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

        // Loop on each title of series.
        for (String key : this.keys.get("series")) {
            this.series.put(key, new Series(
                    this.titles.get(key),
                    this.originalTitles.get(key),
                    this.synopsis.get(key),
                    this.actors.get(key),
                    this.directors.get(key),
                    this.producers.get(key),
                    this.genres.get(key),
                    this.supports.get(key),
                    this.spokens.get(key),
                    this.subtitles.get(key),
                    this.startDates.get(key),
                    this.endDates.get(key),
                    this.numberOfSeasons.get(key),
                    this.currentSeasons.get(key),
                    this.maxEpisodes.get(key),
                    this.numberOfEpisodes.get(key),
                    this.averageRuntimes.get(key)
            ));
        }
    }
}
