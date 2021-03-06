
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

package fr.nicolasgille.medialibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Main controller of the MediaLibrary app.
 *
 * @author Nicolas GILLE
 * @version 1.1
 * @since IMedia-Library 1.0
 */
@SpringBootApplication
public class MediaLibraryConfiguration extends SpringBootServletInitializer {

    /**
     * Configure the servlet to start this class as main class of the project.
     *
     * @param builder
     *
     * @return An instance of springApplicationBuilder used to configure application.
     *
     * @version 1.0
     * @since 1.1
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MediaLibraryConfiguration.class);
    }

    /**
     * Main of the application used to start app.
     *
     * @param args List of arguments.
     *
     * @since 1.0
     */
    public static void main(String[] args) {
        SpringApplication.run(MediaLibraryConfiguration.class, args);
    }
}
