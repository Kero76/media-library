package fr.nicolasgille.medialibrary.exception;

/**
 * Exception send at the client when a problem occurred during CRUD process.
 *
 * @author Nicolas GILLE
 * @since Media-Library 1.0
 * @see fr.nicolasgille.medialibrary.controllers.MovieController
 * @version 1.0
 */
public class MovieException extends Exception {

    /**
     * Message at display when the exception was throw.
     *
     * @since 1.0
     */
    private String message;

    /**
     * Constructor of the exception.
     *
     * @param message
     *  Message at display when the error was throw.
     * @since 1.0
     * @version 1.0
     */
    public MovieException(String message) {
        this.message = message;
    }

    /**
     * Message at display when the exception was throw.
     *
     * @return
     *  The message at display.
     * @since 1.0
     * @version 1.0
     */
    @Override
    public String toString() {
        return this.message;
    }
}

