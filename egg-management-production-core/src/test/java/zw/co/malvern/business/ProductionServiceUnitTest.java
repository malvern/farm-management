package zw.co.malvern.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.business.create.ProductionService;
import zw.co.malvern.business.create.ProductionServiceImpl;
import zw.co.malvern.domain.Egg;
import zw.co.malvern.repository.ProductionRepository;
import zw.co.malvern.utils.exceptions.ProductionException;
import zw.co.malvern.utils.response.BasicResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static zw.co.malvern.utils.TestData.createSampleProductionRequest;
import static zw.co.malvern.utils.TestData.savedEgg;

public class ProductionServiceUnitTest {

    @Mock
    private ProductionRepository productionRepository;

    private  ProductionService productionService;

    @BeforeEach
    void setUp() {
        productionRepository = mock(ProductionRepository.class);
        productionService = new ProductionServiceImpl(productionRepository);
    }

    @Test
    @DisplayName("success: create production record")
    void givenProductionRequest_whenCreatingProductionRecord_shouldReturnSuccessResponse() {
       given(productionRepository.save(any(Egg.class))).willReturn(savedEgg());
        final BasicResponse response = productionService.createProductionRecord(createSampleProductionRequest());
        assertThat(response).as("response").isNotNull();
        assertThat(response.isSuccess()).as("truthy").isTrue();
        assertThat(response.getNarrative()).as("narrative").isEqualTo("production record successfully saved");
        verify(productionRepository,times(1)).save(any(Egg.class));
    }



    @Test
    @DisplayName("failed empty attendance name: create production record")
    void givenProductionRequestWithMissingAttendanceParameter_whenCreatingProductionRecord_shouldThrowProductionException() {
        final ProductionRequest sampleProductionRequest = createSampleProductionRequest();
        sampleProductionRequest.setAttendant(null);
        final String message = "attendance cannot be empty";
        exceptionThrown(sampleProductionRequest,message);
    }

    @Test
    @DisplayName("failed empty comment: create production record")
    void givenProductionRequestWithMissingCommentParameter_whenCreatingProductionRecord_shouldThrowProductionException() {
        final ProductionRequest sampleProductionRequest = createSampleProductionRequest();
        sampleProductionRequest.setComment("");
        final String message = "comment cannot be empty";
        exceptionThrown(sampleProductionRequest,message);
    }


    private void exceptionThrown(ProductionRequest sampleProductionRequest,String message) {
        assertThatThrownBy(()-> productionService.createProductionRecord(sampleProductionRequest))
                .as("exception caused by attendance field")
                .isInstanceOf(ProductionException.class)
                .hasMessage(message);
    }
}
