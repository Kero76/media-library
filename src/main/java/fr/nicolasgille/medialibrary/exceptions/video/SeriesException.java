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
package fr.nicolasgille.medialibrary.exceptions.video;

import fr.nicolasgille.medialibrary.exceptions.MediaException;

/**
 * Exception send on the client of the service when a problem occurred during a request process for the series.
 *
 * @see MediaException
 * @see fr.nicolasgille.medialibrary.controllers.video.SeriesController
 * @author Nicolas GILLE
 * @since Media-Library 0.2
 * @version 2.0
 */
public class SeriesException extends MediaException {

    /**
     * Message at display when the exception was throw.
     *
     * @since 1.0
     */
    private String message;

    /**
     * Constructor of the exception.
     *
     * @param message Message at display when the error was throw.
     * @since 1.0
     * @version 1.0
     */
    public SeriesException(String message) {
        super(message);
    }
}
