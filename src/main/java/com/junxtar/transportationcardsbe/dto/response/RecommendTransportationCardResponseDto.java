package com.junxtar.transportationcardsbe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendTransportationCardResponseDto {

    private Integer kPassPrice;
    private Integer climateActionPrice;

    @Builder
    private RecommendTransportationCardResponseDto(Integer kPassPrice, Integer climateActionPrice) {
        this.kPassPrice = kPassPrice;
        this.climateActionPrice = climateActionPrice;
    }
}
