package com.junxtar.transportationcardsbe.service;

import com.junxtar.transportationcardsbe.domain.CommuteType;
import com.junxtar.transportationcardsbe.domain.UserCategory;
import com.junxtar.transportationcardsbe.dto.request.RecommendTransportationCardRequestDto;
import com.junxtar.transportationcardsbe.dto.response.RecommendTransportationCardResponseDto;
import com.junxtar.transportationcardsbe.repository.TransportationCardRepository;
import com.junxtar.transportationcardsbe.service.action.TransportationCardAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransportationCardActionTest {

    @InjectMocks
    private TransportationCardAction transportationCardAction;

    @Mock
    private TransportationCardRepository transportationCardRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("저소득층_따릉이사용_출근_서울지하철_퇴근_서울지하철_비용을계산한다")
    void 저소득층_따릉이사용_출근_서울지하철_퇴근_서울지하철_비용을계산한다() {
        // given
        RecommendTransportationCardRequestDto requestDto = RecommendTransportationCardRequestDto.builder()
            .userCategory(UserCategory.LOW_INCOME)
            .isSeoulBike(true)
            .commuteType(CommuteType.SEOUL_SUBWAY)
            .returnCommuteType(CommuteType.SEOUL_SUBWAY)
            .build();

        // when
        RecommendTransportationCardResponseDto recommendTransportationCardResponseDto = transportationCardAction.recommendTransportationCard(
            requestDto);

        // then
        Long expectedKPassPrice = 33623L;
        Long expectedClimateActionPrice = 48000L;

        Assertions.assertEquals(expectedKPassPrice, recommendTransportationCardResponseDto.getKPassPrice());
        Assertions.assertEquals(expectedClimateActionPrice, recommendTransportationCardResponseDto.getClimateActionPrice());
     }

}