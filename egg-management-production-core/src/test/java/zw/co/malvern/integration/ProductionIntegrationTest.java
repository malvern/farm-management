package zw.co.malvern.integration;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.utils.response.BasicResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static zw.co.malvern.configuration.TestContainerConfig.composeContainer;
import static zw.co.malvern.utils.TestApi.productionUrl;
import static zw.co.malvern.utils.TestData.createSampleProductionRequest;

@Testcontainers
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
 class ProductionIntegrationTest {

    private TestRestTemplate testRestTemplate;

    @BeforeAll
    void setUp() {
        testRestTemplate =  new TestRestTemplate();
        composeContainer.start();
    }

    @Test
    @DisplayName("success : create record")
    void givenProductionRequest_whenCreatingProductionRecord_shouldReturnSuccess() {
        final BasicResponse basicResponse =  new BasicResponse("production record successfully saved",true);
        processRequest(createSampleProductionRequest(),basicResponse,200);
    }
    @Test
    @DisplayName("failed : attempt to create record without attendance field")
    void givenProductionRequestWithAttendanceField_whenCreatingProductionRecord_shouldReturnSuccess() {
        final ProductionRequest sampleProductionRequest = createSampleProductionRequest();
        sampleProductionRequest.setAttendant("");
        final BasicResponse basicResponse =  new BasicResponse("attendance cannot be empty",false);
        processRequest(sampleProductionRequest,basicResponse,400);
    }

    private void processRequest(ProductionRequest request,BasicResponse basicResponse,int status) {
        final ResponseEntity<BasicResponse> response = testRestTemplate
                .exchange(productionUrl, HttpMethod.POST, new HttpEntity<>(request), BasicResponse.class);
        assertThat(response).as("response").isNotNull();
        assertThat(response.getStatusCode().value()).as("status code").isEqualTo(status);
        assertThat(response.getBody().getNarrative()).as("narrative")
                .isEqualTo(basicResponse.getNarrative());
        assertThat(response.getBody().isSuccess()).as("truthy").isEqualTo(basicResponse.isSuccess());
    }


    @AfterAll
    void tearDown() {
        composeContainer.stop();
    }
}
