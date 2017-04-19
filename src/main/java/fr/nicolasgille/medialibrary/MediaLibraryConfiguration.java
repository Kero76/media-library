package fr.nicolasgille.medialibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main controller of the MediaLibrary app.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @version 1.0
 */
@SpringBootApplication
public class MediaLibraryConfiguration {
    /**
     * Main of the application used to start app.
     *
     * @param args
     *  List of arguments.
     * @since 1.0
     */
    public static void main(String[] args) {
        SpringApplication.run(MediaLibraryConfiguration.class, args);
    }
}
