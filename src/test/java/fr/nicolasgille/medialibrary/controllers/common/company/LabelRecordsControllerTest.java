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

package fr.nicolasgille.medialibrary.controllers.common.company;

import fr.nicolasgille.medialibrary.models.common.company.LabelRecords;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit class test used to test LabelRecordsController class.
 *
 * @author Nicolas GILLE
 * @version 1.0
 * @since Media-Library 0.4
 */
public class LabelRecordsControllerTest {

    /**
     * URI of the Rest service.
     */
    private static final String REST_SERVICE_URI = "http://localhost:8080";

    /**
     * Encoding used to encoded URL.
     */
    private static final String URL_ENCODER = "UTF-8";

    /**
     * RestTemplate used to interact with Rest service.
     */
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.restTemplate = new RestTemplate();
    }

    @Test
    public void getAll() {
        // Given - @see setUp()
        HttpStatus httpStatusExpected = HttpStatus.OK;
        int sizeExpected = 3;

        // When - Get all actors from persistent system.
        ResponseEntity<List> responseEntity =
                this.restTemplate.getForEntity(REST_SERVICE_URI + "/label-records/", List.class);

        // Then - Compare size of elements and http code.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody()
                                 .size()).isEqualTo(sizeExpected);
    }

    @Test
    public void getOneLabelRecords() {
        // Given - @see setUp() and instantiate name of the label records to request.
        HttpStatus httpStatusExpected = HttpStatus.OK;
        String nameExpected = "EMI";

        // When - Get Nicolas Cage from persistent system.
        ResponseEntity<LabelRecords> responseEntity = this.restTemplate.getForEntity(
                REST_SERVICE_URI + "/search/label-records/" + nameExpected, LabelRecords.class);

        // Then - Compare HTTP code and name.
        assertThat(responseEntity.getStatusCode()).isEqualTo(httpStatusExpected);
        assertThat(responseEntity.getBody()
                                 .getName()).isEqualTo(nameExpected);
    }
}
