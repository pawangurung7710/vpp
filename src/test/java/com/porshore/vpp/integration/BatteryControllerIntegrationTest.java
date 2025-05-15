package com.porshore.vpp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.porshore.vpp.AbstractContainerBaseTest;
import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.BatteryStatsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BatteryControllerIntegrationTest extends AbstractContainerBaseTest {

    private static final String BATTERIES_URL = "/api/batteries";

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testAddBatteries_successfullyAddsTwoBatteries() {
        List<BatteryRequest> requestList = List.of(
                new BatteryRequest("BatteryA", 6001, 5000L),
                new BatteryRequest("BatteryB", 6057, 10000L)
        );

        ResponseEntity<String> postResponse = restTemplate.postForEntity(BATTERIES_URL, requestList, String.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).contains("Batteries added successfully");
    }

    @Test
    void testAddBatteries_withEmptyList_returnsBadRequest() {
        ResponseEntity<String> postResponse = restTemplate.postForEntity(BATTERIES_URL, List.of(), String.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testGetBatteryStats_returnsCorrectStats() throws Exception {
        String getUrl = BATTERIES_URL + "?postcodeStart=6000&postcodeEnd=6100&minCapacity=4000&maxCapacity=15000";
        ResponseEntity<String> getResponse = restTemplate.getForEntity(getUrl, String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String getResponseBody = getResponse.getBody();
        assertThat(getResponseBody).isNotNull();

        BatteryStatsResponse statsResponse = objectMapper.readValue(getResponseBody, BatteryStatsResponse.class);
        assertThat(statsResponse.getBatteryNames()).contains("BatteryA", "BatteryB");
    }

    @Test
    void testGetBatteryStats_withInvalidPostcodeRange_returnsEmptyStats() throws Exception {
        String getUrl = BATTERIES_URL + "?postcodeStart=15000&postcodeEnd=20000";
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BatteryStatsResponse statsResponse = objectMapper.readValue(response.getBody(), BatteryStatsResponse.class);
        assertThat(statsResponse.getBatteryNames()).isEmpty();
    }

}