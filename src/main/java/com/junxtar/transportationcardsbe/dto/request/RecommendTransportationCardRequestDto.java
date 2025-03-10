package com.junxtar.transportationcardsbe.dto.request;

import com.junxtar.transportationcardsbe.domain.CommuteType;
import com.junxtar.transportationcardsbe.domain.UserCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendTransportationCardRequestDto {

    private UserCategory userCategory;
    private boolean isSeoulBike;
    private CommuteType commuteType;
    private CommuteType returnCommuteType;

    @Builder
    private RecommendTransportationCardRequestDto(UserCategory userCategory, boolean isSeoulBike,
        CommuteType commuteType, CommuteType returnCommuteType) {
        this.userCategory = userCategory;
        this.isSeoulBike = isSeoulBike;
        this.commuteType = commuteType;
        this.returnCommuteType = returnCommuteType;
    }
}
