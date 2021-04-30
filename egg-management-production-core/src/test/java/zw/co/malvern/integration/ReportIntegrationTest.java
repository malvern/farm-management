package zw.co.malvern.integration;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.junit.jupiter.Testcontainers;
import zw.co.malvern.api.report.ReportResponse;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static zw.co.malvern.configuration.TestContainerConfig.composeContainer;
import static zw.co.malvern.utils.TestApi.reportingUrl;
import static zw.co.malvern.utils.TestData.*;

@Testcontainers
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReportIntegrationTest {
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    void setUp() {
        testRestTemplate =  new TestRestTemplate();
        composeContainer.start();
    }


    @Test
    @DisplayName("success : No results")
    void givenDates_whenRetrievingProductionReport_shouldReturnProductionResults(){
        final ReportResponse response = new ReportResponse("no production records available",
                false,0,1, Collections.emptyList());
        processRequest(response,HttpStatus.OK);
    }

    void processRequest(ReportResponse expectedResponse,HttpStatus status){
        final HashMap<String,Object> urlVariables = new HashMap<>();
        urlVariables.put("startDate",startDate);
        urlVariables.put("endDate",endDate);
        final UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(reportingUrl)
                .queryParam("page", 1)
                .queryParam("size", 10).uriVariables(urlVariables);
        final ResponseEntity<ReportResponse> response = testRestTemplate
                .getForEntity(builder.toUriString(), ReportResponse.class);
        assertThat(response).as("response").isNotNull();
        assertThat(response.getStatusCode()).as("status").isEqualTo(status);
        assertThat(response.getBody().isSuccess()).as("truthy").isEqualTo(expectedResponse.isSuccess());
        assertThat(response.getBody().getNarrative()).as("narrative").isEqualTo(expectedResponse.getNarrative());
        assertThat(response.getBody().getPage()).as("current page").isEqualTo(expectedResponse.getPage());
        assertThat(response.getBody().getTotalPages()).as("total pages").isEqualTo(expectedResponse.getTotalPages());
        assertThat(response.getBody().getEggDtoList()).as("production results")
                .containsAnyElementsOf(expectedResponse.getEggDtoList());
    }

    @AfterAll
    void tearDown() {
        composeContainer.stop();
    }
}
