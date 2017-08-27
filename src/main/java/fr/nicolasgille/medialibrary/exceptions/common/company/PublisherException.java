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

package fr.nicolasgille.medialibrary.exceptions.common.company;

import fr.nicolasgille.medialibrary.exceptions.MediaException;

/**
 * Exception send on the client of the service when a problem occurred during a request process for the publisher.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @see MediaException
 * @see fr.nicolasgille.medialibrary.controllers.common.company.PublisherController
 * @since Media-Library 0.4
 */
public class PublisherException extends MediaException {

    /**
     * Constructor of the exception.
     *
     * @param message Message at display when the error was throw.
     *
     * @version 1.0
     * @since 1.0
     */
    public PublisherException(String message) {
        super(message);
    }
}
