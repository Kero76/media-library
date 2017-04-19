package fr.nicolasgille.medialibrary.Exception;

/**
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1
 * @version 1.0
 */
public class MovieException extends Exception {

    /**
     * Message at display when the exception was throw.
     */
    private String message;

    /**
     * Constructor of the exception.
     *
     * @param message
     *  Message at display when the error was throw.
     */
    public MovieException(String message) {
        this.message = message;
    }

    /**
     * Message at display when the exception was throw.
     *
     * @return
     *  The message at display.
     */
    @Override
    public String toString() {
        return this.message;
    }
}

