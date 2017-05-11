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
    public DataUtils getInstance() {
        if (DataUtils.instance == null) {
            DataUtils.instance = new DataUtils();
        }
        return DataUtils.instance;
    }

    /**
     * Instantiate some actors and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initActors() {
        this.actors = new HashMap<String, Actor>();

        // Star Wars - A New Hope | The Empire Strikes Back | Return of the Jedi
        this.actors.put("Mark Hamill", new Actor("Mark", "Hamill"));
        this.actors.put("Harrison Ford", new Actor("Harrison", "Ford"));
        this.actors.put("Carrie Fisher", new Actor("Carrie", "Fisher"));

        // Star Wars - New Hope
        this.actors.put("Peter Cushing", new Actor("Peter", "Cushing"));
        this.actors.put("Alec Guinness", new Actor("Alec", "Guinness"));

        // Star Wars - The Empire Strikes Back | Return of the Jedi
        this.actors.put("Billy Dee Williams", new Actor("Billy Dee", "Williams"));
        this.actors.put("Anthony Daniels", new Actor("Anthony", "Daniels"));
        this.actors.put("David Prowse", new Actor("David", "Prowse"));
        this.actors.put("Kenny Baker", new Actor("Kenny", "Baker"));
        this.actors.put("Peter Mayhew", new Actor("Peter", "Mayhew"));
        this.actors.put("Frank Oz", new Actor("Frank", "Oz"));
    }

    /**
     * Instantiate some directors and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initDirectors() {
        this.directors = new HashMap<String, Director>();

        // Star Wars - A New Hope
        this.directors.put("George Lucas", new Director("George", "Lucas"));

        // Star Wars - The Empire Strikes Back
        this.directors.put("Irvin Kershner", new Director("Irvin", "Kershner"));

        // Star Wars - Return of the Jedi
        this.directors.put("Richard Marquand", new Director("Richard", "Marquand"));
    }

    /**
     * Instantiate some producers and put it on Map.
     *
     * @since 1.0
     * @version 1.0
     */
    private void initProducers() {
        this.producers = new HashMap<String, Producer>();

        // Star Wars - A New Hope | The Empire Strikes Back
        this.producers.put("Gary Kurtz", new Producer("Gary", "Kurtz"));

        // Star Wars - Return of the Jedi
        this.producers.put("Howard Kazanjian", new Producer("Howard", "Kazanjian"));
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
        List<VideoGenre> starWarsGenres = new ArrayList<VideoGenre>();
        starWarsGenres.add(VideoGenre.SCIENCE_FICTION);

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
                "Star Wars : Un Nouvel Espoir", "Star Wars : A New Hope", starWarsGenres, new GregorianCalendar(1977, Calendar.MAY, 25),121,
                "The Imperial Forces, under orders from cruel Darth Vader, hold Princess Leia hostage in their efforts to quell the rebellion against the Galactic Empire. Luke Skywalker and Han Solo, captain of the Millennium Falcon, work together with the companionable droid duo R2-D2 and C-3PO to rescue the beautiful princess, help the Rebel Alliance and restore freedom and justice to the Galaxy.",
                actors, producers, directors, supports, languagesSpoken, subtitles
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
                "Star Wars : L'Empire Contre Attaque", "Star Wars : The Empire Strikes Back", starWarsGenres, new GregorianCalendar(1980, Calendar.MAY, 17),124,
                "Luke Skywalker, Han Solo, Princess Leia and Chewbacca face attack by the Imperial forces and its AT-AT walkers on the ice planet Hoth. While Han and Leia escape in the Millennium Falcon, Luke travels to Dagobah in search of Yoda. Only with the Jedi master's help will Luke survive when the dark side of the Force beckons him into the ultimate duel with Darth Vader.",
                actors, producers, directors, supports, languagesSpoken, subtitles
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
                "Star Wars : Le Retour du Jedi", "Star Wars : Return of the Jedi", starWarsGenres, new GregorianCalendar(1983, Calendar.MAY, 25),131,
                "Darth Vader and the Empire are building a new, indestructible Death Star. Meanwhile, Han Solo has been imprisoned, and Luke Skywalker has sent R2-D2 and C-3PO to try and free him. Princess Leia - disguised as a bounty hunter - and Chewbacca go along as well. The final battle takes place on the moon of Endor, with its natural inhabitants, the Ewoks, lending a hand to the Rebels. Will Darth Vader and the Dark Side overcome the Rebels and take over the universe ?",
                actors, producers, directors, supports, languagesSpoken, subtitles
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

    }
}
