package zw.co.malvern.business;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import zw.co.malvern.api.report.ReportResponse;
import zw.co.malvern.business.report.ReportService;
import zw.co.malvern.business.report.ReportServiceImpl;
import zw.co.malvern.domain.Egg;
import zw.co.malvern.repository.ProductionRepository;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static zw.co.malvern.utils.TestData.*;

public class ReportServiceUnitTest {

    @Mock
    private ProductionRepository productionRepository;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        productionRepository = mock(ProductionRepository.class);
        reportService = new ReportServiceImpl(productionRepository);
    }

    @Test
    @DisplayName("success : retrieve production report")
    void givenDates_whenRetrievingProductionReport_shouldReturnProductionResults() {
        Page<Egg> eggPage =  new PageImpl<Egg>(Collections.singletonList(savedEgg()));
        given(productionRepository.findAllByRecordCreationTimeBetween(anyString(),
                anyString(),any(Pageable.class)))
                .willReturn(eggPage);
        final ReportResponse response = new ReportResponse("production records",
                true,1,0,Collections.singletonList(getEggDto()));
        processRequest(response);
      }

    @Test
    @DisplayName("success : no results")
    void givenDates_whenRetrievingProductionReport_shouldReturnNoResultResponse() {
        given(productionRepository.findAllByRecordCreationTimeBetween(anyString(),anyString(),
                any(Pageable.class)))
                .willReturn(Page.empty());
        final ReportResponse response = new ReportResponse("no production records available",
                false,1,0,Collections.emptyList());
        processRequest(response);

    }

    private void processRequest(ReportResponse expectedResponse) {
        final ReportResponse response = reportService
                .retrieveDailyProductionReport(startDate, endDate, 0, 10);
        assertThat(response).as("production response").isNotNull();
        assertThat(response.getNarrative()).as("narrative").isEqualTo(expectedResponse.getNarrative());
        assertThat(response.isSuccess()).as("truthy").isEqualTo(expectedResponse.isSuccess());
        assertThat(response.getEggDtoList()).as("empty production result")
                .containsAnyElementsOf(expectedResponse.getEggDtoList());
        assertThat(response.getTotalPages()).as("total pages").isEqualTo(expectedResponse.getTotalPages());
        assertThat(response.getPage()).as("current page").isEqualTo(expectedResponse.getPage());
    }

    @AfterEach
    void verifyMock(){
        verify(productionRepository,times(1))
                .findAllByRecordCreationTimeBetween(anyString(),anyString(),any(Pageable.class));
    }
}
