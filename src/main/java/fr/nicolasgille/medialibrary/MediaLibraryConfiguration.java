package fr.nicolasgille.medialibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main controller of the MediaLibrary app.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
@SpringBootApplication
public class MediaLibraryConfiguration {
    /**
     * Main of the application used to start app.
     *
     * @param args
     *  List of arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(MediaLibraryConfiguration.class, args);
    }
}
