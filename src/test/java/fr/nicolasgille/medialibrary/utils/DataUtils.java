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

    /**
     * Map composed by a key who represent the actor by his name and the instance of the Actor.
     * @since 1.0
     */
    private Map<String, Actor> actors;

    /**
     * Map composed by a key who represent the director by his name and the instance of the Director.
     * @since 1.0
     */
    private Map<String, Director> directors;

    /**
     * Map composed by a key who represent the producer by his name and the instance of the Producer.
     * @since 1.0
     */
    private Map<String, Producer> producers;

    /**
     * Map composed by a key who represent the movie by his title and the instance of the Movie.
     * @since 1.0
     */
    private Map<String, Movie> movies;

    /**
     * Map composed by a key who represent the series by his name and the instance of the Series.
     * @since 1.0
     */
    private Map<String, Series> series;

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
     * Instantiate some actors and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initActors() {
        this.actors = new HashMap<String, Actor>();

        // Movie - Star Wars - A New Hope | The Empire Strikes Back | Return of the Jedi
        this.actors.put("Mark Hamill", new Actor("Mark", "Hamill"));
        this.actors.put("Harrison Ford", new Actor("Harrison", "Ford"));
        this.actors.put("Carrie Fisher", new Actor("Carrie", "Fisher"));

        // Movie - Star Wars - New Hope
        this.actors.put("Peter Cushing", new Actor("Peter", "Cushing"));
        this.actors.put("Alec Guinness", new Actor("Alec", "Guinness"));

        // Movie - Star Wars - The Empire Strikes Back | Return of the Jedi
        this.actors.put("Billy Dee Williams", new Actor("Billy Dee", "Williams"));
        this.actors.put("Anthony Daniels", new Actor("Anthony", "Daniels"));
        this.actors.put("David Prowse", new Actor("David", "Prowse"));
        this.actors.put("Kenny Baker", new Actor("Kenny", "Baker"));
        this.actors.put("Peter Mayhew", new Actor("Peter", "Mayhew"));
        this.actors.put("Frank Oz", new Actor("Frank", "Oz"));

        // Series - Falling Skies
        this.actors.put("Noah Wyle", new Actor("Noah", "Wyle"));
        this.actors.put("Moon Bloodgood", new Actor("Moon", "Bloodgood"));
        this.actors.put("Drew Roy", new Actor("Drew", "Roy"));
        this.actors.put("Jessy Schram", new Actor("Jessy", "Schram"));
        this.actors.put("Sarah Carter", new Actor("Sarah", "Carter"));
        this.actors.put("Maxim Knight", new Actor("Maxim", "Knight"));
        this.actors.put("Seychelle Gabriel", new Actor("Seychelle", "Gabriel"));
        this.actors.put("Peter Shinkoda", new Actor("Peter", "Shinkoda"));
        this.actors.put("Connor Jessup", new Actor("Connor", "Jessup"));
        this.actors.put("Mpho Koaho", new Actor("Mpho", "Koaho"));
        this.actors.put("Colin Cunningham", new Actor("", "Cunningham"));
        this.actors.put("Will Patton", new Actor("Will", "Patton"));
        this.actors.put("Doug Jones", new Actor("Doug", "Jones"));
        this.actors.put("Scarlett Byrne", new Actor("Scarlett", "Byrne"));
    }

    /**
     * Instantiate some directors and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initDirectors() {
        this.directors = new HashMap<String, Director>();

        // Movie - Star Wars - A New Hope
        this.directors.put("George Lucas", new Director("George", "Lucas"));

        // Movie - Star Wars - The Empire Strikes Back
        this.directors.put("Irvin Kershner", new Director("Irvin", "Kershner"));

        // Movie - Star Wars - Return of the Jedi
        this.directors.put("Richard Marquand", new Director("Richard", "Marquand"));

        // Series - Falling Skies
        this.directors.put("Robert Rodat", new Director("Robert",  "Rodat"));
    }

    /**
     * Instantiate some producers and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initProducers() {
        this.producers = new HashMap<String, Producer>();

        // Movie - Star Wars - A New Hope | The Empire Strikes Back
        this.producers.put("Gary Kurtz", new Producer("Gary", "Kurtz"));

        // Movie - Star Wars - Return of the Jedi
        this.producers.put("Howard Kazanjian", new Producer("Howard", "Kazanjian"));

        // Series - Falling Skies
        this.producers.put("Steven Spielberg", new Producer("Steven", "Spielberg"));
        this.producers.put("Darryl Frank", new Producer("Darryl", "Frank"));
        this.producers.put("Justin Falvey", new Producer("Justin", "Falvey"));
        this.producers.put("Robert Rodat", new Producer("Robert", "Rodat"));
        this.producers.put("Graham Yost", new Producer("Graham", "Yost"));
        this.producers.put("Greg Beeman", new Producer("Greg", "Beeman"));
        this.producers.put("Remi Aubuchon", new Producer("Remi", "Aubuchon"));
        this.producers.put("David Eick", new Producer("David", "Eick"));
        this.producers.put("John Ryan", new Producer("John", "Ryan"));
        this.producers.put("Noah Wyle", new Producer("Noah", "Wyle"));
        this.producers.put("Darren King", new Producer("Darren", "King"));
        this.producers.put("Grace Gilroy", new Producer("Grace", "Gilroy"));
    }

    /**
     * Instantiate some movies and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initMovies() {
        this.movies = new HashMap<String, Movie>();

        // Genres - Star Wars
        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.SCIENCE_FICTION);

        // Supports - Star Wars
        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        // Languages Spoken - Star Wars
        List<LanguageCode> languagesSpoken = new ArrayList<LanguageCode>();
        languagesSpoken.add(LanguageCode.fr);
        languagesSpoken.add(LanguageCode.en);

        // Subtitles - Star Wars
        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);
        subtitles.add(LanguageCode.ar);

        /*
         * STAR WARS IV - Main Actor | Producers | Directors
         */
        // Main Actors
        Set<Actor> actors = new HashSet<Actor>();
        actors.add(this.actors.get("Mark Hamill"));
        actors.add(this.actors.get("Harrison Ford"));
        actors.add(this.actors.get("Carrie Fisher"));
        actors.add(this.actors.get("Peter Cushing"));
        actors.add(this.actors.get("Alec Guinness"));

        // Producers
        Set<Producer> producers = new HashSet<Producer>();
        producers.add(this.producers.get("Gary Kurtz"));

        // Directors
        Set<Director> directors = new HashSet<Director>();
        directors.add(this.directors.get("George Lucas"));

        this.movies.put("Star Wars IV", new Movie(
                "Star Wars : Un Nouvel Espoir", "Star Wars : A New Hope", "The Imperial Forces, under orders from cruel Darth Vader, hold Princess Leia hostage in their efforts to quell the rebellion against the Galactic Empire. Luke Skywalker and Han Solo, captain of the Millennium Falcon, work together with the companionable droid duo R2-D2 and C-3PO to rescue the beautiful princess, help the Rebel Alliance and restore freedom and justice to the Galaxy.",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles, new GregorianCalendar(1977, Calendar.MAY, 25),121
        ));


        /*
         * STAR WARS V - Main Actor | Directors
         */
        // Main Actors
        actors = new HashSet<Actor>();
        actors.add(this.actors.get("Mark Hamill"));
        actors.add(this.actors.get("Harrison Ford"));
        actors.add(this.actors.get("Carrie Fisher"));
        actors.add(this.actors.get("Billy Dee Williams"));
        actors.add(this.actors.get("Anthony Daniels"));
        actors.add(this.actors.get("David Prowse"));
        actors.add(this.actors.get("Kenny Baker"));
        actors.add(this.actors.get("Peter Mayhew"));
        actors.add(this.actors.get("Frank Oz"));

        // Directors
        directors = new HashSet<Director>();
        directors.add(this.directors.get("Irvin Kershner"));

        this.movies.put("Star Wars V", new Movie(
                "Star Wars : L'Empire Contre Attaque", "Star Wars : The Empire Strikes Back", "Luke Skywalker, Han Solo, Princess Leia and Chewbacca face attack by the Imperial forces and its AT-AT walkers on the ice planet Hoth. While Han and Leia escape in the Millennium Falcon, Luke travels to Dagobah in search of Yoda. Only with the Jedi master's help will Luke survive when the dark side of the Force beckons him into the ultimate duel with Darth Vader.",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles, new GregorianCalendar(1980, Calendar.MAY, 17),124
        ));


        /*
         * STAR WARS VI - Producers | Directors
         */
        // Producers
        producers = new HashSet<Producer>();
        producers.add(this.producers.get("Howard Kazanjian"));

        // Directors
        directors = new HashSet<Director>();
        directors.add(this.directors.get("Richard Marquand"));

        this.movies.put("Star Wars VI", new Movie(
                "Star Wars : Le Retour du Jedi", "Star Wars : Return of the Jedi", "Darth Vader and the Empire are building a new, indestructible Death Star. Meanwhile, Han Solo has been imprisoned, and Luke Skywalker has sent R2-D2 and C-3PO to try and free him. Princess Leia - disguised as a bounty hunter - and Chewbacca go along as well. The final battle takes place on the moon of Endor, with its natural inhabitants, the Ewoks, lending a hand to the Rebels. Will Darth Vader and the Dark Side overcome the Rebels and take over the universe ?",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles, new GregorianCalendar(1983, Calendar.MAY, 25),131
        ));
    }

    /**
     * Instantiate some series and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initSeries() {
        this.series = new HashMap<String, Series>();

        // Genres - Falling Skies
        List<VideoGenre> genres = new ArrayList<VideoGenre>();
        genres.add(VideoGenre.ACTION);
        genres.add(VideoGenre.ADVENTURE);
        genres.add(VideoGenre.DRAMA);
        genres.add(VideoGenre.SCIENCE_FICTION);
        genres.add(VideoGenre.THRILLER);

        // Supports - Falling Skies
        List<VideoSupport> supports = new ArrayList<VideoSupport>();
        supports.add(VideoSupport.DVD);

        // Languages Spoken - Falling Skies
        List<LanguageCode> languagesSpoken = new ArrayList<LanguageCode>();
        languagesSpoken.add(LanguageCode.fr);
        languagesSpoken.add(LanguageCode.en);
        languagesSpoken.add(LanguageCode.de);
        languagesSpoken.add(LanguageCode.es);

        // Subtitles - Falling Skies
        List<LanguageCode> subtitles = new ArrayList<LanguageCode>();
        subtitles.add(LanguageCode.fr);
        subtitles.add(LanguageCode.es);
        subtitles.add(LanguageCode.en);
        subtitles.add(LanguageCode.nl);

        /*
         * Falling Skies
         */
        // Main Actors
        Set<Actor> actors = new HashSet<Actor>();
        actors.add(this.actors.get("Noah Wyle"));
        actors.add(this.actors.get("Moon Bloodgood"));
        actors.add(this.actors.get("Drew Roy"));
        actors.add(this.actors.get("Jessy Schram"));
        actors.add(this.actors.get("Sarah Carter"));
        actors.add(this.actors.get("Maxim Knight"));
        actors.add(this.actors.get("Seychelle Gabriel"));
        actors.add(this.actors.get("Peter Shinkoda"));
        actors.add(this.actors.get("Connor Jessup"));
        actors.add(this.actors.get("Mpho Koaho"));
        actors.add(this.actors.get("Colin Cunningham"));
        actors.add(this.actors.get("Will Patton"));
        actors.add(this.actors.get("Doug Jones"));
        actors.add(this.actors.get("Scarlett Byrne"));

        // Producers
        Set<Producer> producers = new HashSet<Producer>();
        producers.add(this.producers.get("Steven Spielberg"));
        producers.add(this.producers.get("Darryl Frank"));
        producers.add(this.producers.get("Justin Falvey"));
        producers.add(this.producers.get("Robert Rodat"));
        producers.add(this.producers.get("Graham Yost"));
        producers.add(this.producers.get("Greg Beeman"));
        producers.add(this.producers.get("Remi Aubuchon"));
        producers.add(this.producers.get("David Eick"));
        producers.add(this.producers.get("John Ryan"));
        producers.add(this.producers.get("Noah Wyle"));
        producers.add(this.producers.get("Darren King"));
        producers.add(this.producers.get("Grace Gilroy"));

        // Directors
        Set<Director> directors = new HashSet<Director>();
        directors.add(this.directors.get("Robert Rodat"));

        // Add Falling Skies.
        this.series.put("Falling Skies 1", new Series(
                "Falling Skies", "Falling Skies", "The chaotic aftermath of an alien attack has left most of the world completely incapacitated. In the six months since the initial invasion, the few survivors have banded together outside major cities to begin the difficult task of fighting back. Each day is a test of survival as citizen soldiers work to protect the people in their care while also engaging in an insurgency campaign against the occupying alien force.",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles,
                new GregorianCalendar(2011, Calendar.JUNE, 19), new GregorianCalendar(2015, Calendar.AUGUST, 30),
                5, 1, 52, 10, 42
        ));
        this.series.put("Falling Skies 2", new Series(
                "Falling Skies", "Falling Skies", "The chaotic aftermath of an alien attack has left most of the world completely incapacitated. In the six months since the initial invasion, the few survivors have banded together outside major cities to begin the difficult task of fighting back. Each day is a test of survival as citizen soldiers work to protect the people in their care while also engaging in an insurgency campaign against the occupying alien force.",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles,
                new GregorianCalendar(2011, Calendar.JUNE, 19), new GregorianCalendar(2015, Calendar.AUGUST, 30),
                5, 2, 52, 10, 42
        ));
        this.series.put("Falling Skies 3", new Series(
                "Falling Skies", "Falling Skies", "The chaotic aftermath of an alien attack has left most of the world completely incapacitated. In the six months since the initial invasion, the few survivors have banded together outside major cities to begin the difficult task of fighting back. Each day is a test of survival as citizen soldiers work to protect the people in their care while also engaging in an insurgency campaign against the occupying alien force.",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles,
                new GregorianCalendar(2011, Calendar.JUNE, 19), new GregorianCalendar(2015, Calendar.AUGUST, 30),
                5, 3, 52, 10, 42
        ));
        this.series.put("Falling Skies 4", new Series(
                "Falling Skies", "Falling Skies", "The chaotic aftermath of an alien attack has left most of the world completely incapacitated. In the six months since the initial invasion, the few survivors have banded together outside major cities to begin the difficult task of fighting back. Each day is a test of survival as citizen soldiers work to protect the people in their care while also engaging in an insurgency campaign against the occupying alien force.",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles,
                new GregorianCalendar(2011, Calendar.JUNE, 19), new GregorianCalendar(2015, Calendar.AUGUST, 30),
                5, 4, 52, 12, 42
        ));
        this.series.put("Falling Skies 5", new Series(
                "Falling Skies", "Falling Skies", "The chaotic aftermath of an alien attack has left most of the world completely incapacitated. In the six months since the initial invasion, the few survivors have banded together outside major cities to begin the difficult task of fighting back. Each day is a test of survival as citizen soldiers work to protect the people in their care while also engaging in an insurgency campaign against the occupying alien force.",
                actors, directors, producers, genres, supports, languagesSpoken, subtitles,
                new GregorianCalendar(2011, Calendar.JUNE, 19), new GregorianCalendar(2015, Calendar.AUGUST, 30),
                5, 5, 52, 10, 42
        ));
    }
}
