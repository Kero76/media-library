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

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A parser used to parse CSV file.
 *
 * This class parse a file who contains information about a media under the format csv.
 *
 * @author Nicolas GILLE
 * @since Media-Library 0.5
 * @version 1.0
 */
public class CSVParser implements FileParser {

    /**
     * Parse the file send in parameter and return a list of list of string who contains all elements parse during process.
     *
     * @param filename
     *  Name of the file at parse.
     * @return
     *  A list composed by List of all information get during parsing.
     * @throws IOException
     * @version 1.0
     * @since 1.0
     */
    @Override
    public List<List<String>> parse(String filename) {
        List<List<String>> data = new ArrayList<>();
        try {
            Scanner scan = new Scanner(Paths.get("src/test/resources/animes.csv"));
            // Loop until file not completed.
            while (scan.hasNext()) {
                // Get each line and split it by ','.
                String line = scan.nextLine();
                String[] split = line.split(",");

                // Add each data on List and added list on main list.
                List<String> parseData = new ArrayList<>();
                Collections.addAll(parseData, split);
                data.add(parseData);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
