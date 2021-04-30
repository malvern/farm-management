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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;
import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.api.report.ReportResource;
import zw.co.malvern.api.report.ReportResponse;
import zw.co.malvern.business.report.ReportService;
import zw.co.malvern.utils.exceptions.ProductionException;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorBasicResponse;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static zw.co.malvern.utils.TestApi.reportControllerUrl;
import static zw.co.malvern.utils.TestData.*;

@WebMvcTest(controllers = {ReportResource.class})
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ReportResourceUnitTest {

    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;




    @Test
    @DisplayName("success : production results")
    void givenDates_whenRetrievingProductionReport_shouldReturnProductionResults() throws Exception {
        final ReportResponse response = new ReportResponse("production records",true,1,1,
                Collections.singletonList(getEggDto()));
        given(reportService.retrieveDailyProductionReport(anyString(),anyString(),anyInt(),anyInt()))
                .willReturn(response);
        generateProductionRecord(response,HttpStatus.OK);
        verify(reportService,times(1))
                .retrieveDailyProductionReport(anyString(),anyString(),anyInt(),anyInt());
    }

    @Test
    @DisplayName("success : production no results")
    void givenDatesWithNoProduction_whenRetrievingProductionReport_shouldReturnProductionResults() throws Exception {
        final ReportResponse response = new ReportResponse("no production records available",false,
                0,1, Collections.emptyList());
        given(reportService.retrieveDailyProductionReport(anyString(),anyString(),anyInt(),anyInt()))
                .willReturn(response);
        generateProductionRecord(response,HttpStatus.OK);
        verify(reportService,times(1))
                .retrieveDailyProductionReport(anyString(),anyString(),anyInt(),anyInt());
    }

    @Test
    @DisplayName("failed : internal error occurred")
    void givenDatesWhenInternalError_whenRetrievingProductionReport_shouldReturnErrorResponse() throws Exception {
        final ErrorBasicResponse response = new
                ErrorBasicResponse("SQL Error occurred",
                "Internal Error error while processing",false);
        given(reportService.retrieveDailyProductionReport(anyString(),anyString(),anyInt(),anyInt()))
                .willThrow(new ProductionException("SQL Error occurred"));
        generateProductionRecordThrowException(response,HttpStatus.INTERNAL_SERVER_ERROR);
        verify(reportService,times(1))
                .retrieveDailyProductionReport(anyString(),anyString(),anyInt(),anyInt());
    }

    void generateProductionRecord(BasicResponse response,HttpStatus status) throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.get(reportControllerUrl, startDate, endDate)
                .characterEncoding("UTF-8").queryParam("page", "1").queryParam("size", "1"))
                .andExpect(status().is(status.value()))
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("narrative").value(response.getNarrative()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(response.isSuccess()))
                .andDo(document("retrieve-record",
                        responseFields(fieldWithPath("narrative").description("response narrative"),
                                fieldWithPath("success").description("truthy field"),
                                fieldWithPath("totalPages").description("total pages"),
                                fieldWithPath("page").description("total pages"),
                                subsectionWithPath("eggDtoList.[]").description("production record")
                        )));

    }

    void generateProductionRecordThrowException(BasicResponse response,HttpStatus status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(reportControllerUrl, startDate, endDate)
                .characterEncoding("UTF-8").queryParam("page", "1").queryParam("size", "1"))
                .andExpect(status().is(status.value()))
                .andExpect(jsonPath("narrative").isString())
                .andExpect(jsonPath("narrative").value(response.getNarrative()))
                .andExpect(jsonPath("success").isBoolean())
                .andExpect(jsonPath("success").value(response.isSuccess()))
                .andDo(document("retrieve-record",
                        responseFields(fieldWithPath("narrative").description("response narrative"),
                                fieldWithPath("success").description("truthy field"),
                                fieldWithPath("errorNarrative").description("error narrative")
                        )));

    }
}
