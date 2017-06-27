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
package fr.nicolasgille.medialibrary.utils.parser;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test the CSV parser class.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
public class CsvParserTest {

    private FileParser parser;

    @Before
    public void setUp() {
        this.parser = new CsvParser();
    }

    @Test
    public void parseCsvFile() throws Exception {
        // Given - @see setUp() and instantiate the object return from the method parse.
        List<List<String>> dataParse = new ArrayList<>();

        // When - Parse file.
        dataParse = this.parser.parse("src/test/resources/animes.csv");

        // Then - Check content of the result.
        for (List l : dataParse) {
            System.out.println(l.toString());
        }
    }
}
