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
package fr.nicolasgille.medialibrary.exception.common.person;

import fr.nicolasgille.medialibrary.exception.MediaException;

/**
 * Exception send at the client when a problem occurred during CRUD process.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.1.1
 * @version 1.0
 */
public class ActorException extends MediaException {

    /**
     * Constructor of the exception.
     *
     * @param message
     *  Message at display when the error was throw.
     * @since 1.0
     * @version 1.0
     */
    public ActorException(String message) {
        super(message);
    }
}
