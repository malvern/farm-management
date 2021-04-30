package zw.co.malvern.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.api.create.ProductionResource;
import zw.co.malvern.business.create.ProductionService;
import zw.co.malvern.utils.exceptions.ProductionException;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorBasicResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zw.co.malvern.utils.TestApi.productionControllerUrl;
import static zw.co.malvern.utils.TestData.createSampleProductionRequest;

@WebMvcTest(controllers = {ProductionResource.class})
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ProductionResourceUnitTest {

    @MockBean
    private ProductionService productionService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ProductionRequest productionRequest;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        productionRequest = createSampleProductionRequest();
    }

    @Test
    @DisplayName("success : creating production record")
    void givenProductionRequest_whenCreatingProductionRecord_shouldReturnSuccessResponse() throws Exception {
        final BasicResponse response = new BasicResponse("production record successfully saved", true);
        given(productionService.createProductionRecord(any(ProductionRequest.class))).willReturn(response);
        createProductionRecord(productionRequest, response,HttpStatus.OK);
        verify(productionService, times(1)).createProductionRecord(any(ProductionRequest.class));
    }

    @Test
    @DisplayName("failed : creating production record")
    void givenProductionInvalidRequest_whenCreatingProductionRecord_shouldReturnFailedResponse() throws Exception {
        final ErrorBasicResponse response = new ErrorBasicResponse("attendance name cannot be empty",
                "attendance name cannot be empty",
                false);
        given(productionService.createProductionRecord(any(ProductionRequest.class)))
                .willThrow(new ProductionException("attendance name cannot be empty"));
        createProductionRecord(productionRequest, response,HttpStatus.BAD_REQUEST);
        verify(productionService, times(1)).createProductionRecord(any(ProductionRequest.class));
    }

    @Test
    @DisplayName("failed  with internal error: creating production record")
    void givenProductionRequestThatCausesInternalError_whenCreatingProductionRecord_shouldReturnFailedResponse()
            throws Exception {
        final ErrorBasicResponse response = new ErrorBasicResponse("Failed to create request",
                "attendance name cannot be empty",
                false);
        given(productionService.createProductionRecord(any(ProductionRequest.class)))
                .willThrow(new ArithmeticException("Internal error occurred"));
        createProductionRecordThrowException(productionRequest, response,HttpStatus.INTERNAL_SERVER_ERROR);
        verify(productionService, times(1)).createProductionRecord(any(ProductionRequest.class));
    }



    void createProductionRecord(ProductionRequest productionRequest,
                                BasicResponse expectedResponse, HttpStatus status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(productionControllerUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productionRequest)).characterEncoding("UTF-8"))
                .andExpect(status().is(status.value()))
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("narrative").value(expectedResponse.getNarrative()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(expectedResponse.isSuccess()))
                .andDo(document("create-record",
                        responseFields(fieldWithPath("narrative").description("response narrative"),
                                fieldWithPath("success").description("truthy field"))));
    }


    void createProductionRecordThrowException(ProductionRequest productionRequest,
                                              BasicResponse expectedResponse,HttpStatus status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(productionControllerUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productionRequest)).characterEncoding("UTF-8"))
                .andExpect(status().is(status.value()))
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("narrative").value(expectedResponse.getNarrative()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(expectedResponse.isSuccess()))
                .andDo(document("create-record",
                        responseFields(fieldWithPath("narrative").description("response narrative"),
                                fieldWithPath("success").description("truthy field"),
                                fieldWithPath("errorNarrative").description("error narrative"))));
    }


}
