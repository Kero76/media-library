/*
 * MediaLibrary.
 * Copyright (C) 2017 Nicolas GILLE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.nicolasgille.medialibrary.exceptions;

/**
 * Exception send at the client when a problem occurred during request process.
 * <p>
 * This exception is a super class of each exception of Media-Library.
 * Thanks that, it can possible to add different behavior of each subclasses of the exception
 * or add a general behavior of each subclasses present on the service.
 * So, this main exception return only a message with a specific content.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.3
 */
public class MediaException extends Exception {

    /**
     * Message at display when the exception was throw.
     *
     * @since 1.0
     */
    protected String message;

    /**
     * Constructor of the exception.
     *
     * @param message Message at display when the error was throw.
     *
     * @version 1.0
     * @since 1.0
     */
    public MediaException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * Message at display when the exception was throw.
     *
     * @return The message at display.
     *
     * @version 1.0
     * @since 1.0
     */
    @Override
    public String toString() {
        return this.message;
    }
}
