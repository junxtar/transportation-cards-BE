package com.junxtar.transportationcardsbe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendTransportationCardResponseDto {

    private Long kPassPrice;
    private Long climateActionPrice;

    @Builder
    private RecommendTransportationCardResponseDto(Long kPassPrice, Long climateActionPrice) {
        this.kPassPrice = kPassPrice;
        this.climateActionPrice = climateActionPrice;
    }
}
