package com.junxtar.transportationcardsbe.service.extract;

import static com.junxtar.transportationcardsbe.constant.CommonConstant.WEEKDAYS;

import com.junxtar.transportationcardsbe.domain.CommuteType;
import com.junxtar.transportationcardsbe.domain.UserCategory;
import com.junxtar.transportationcardsbe.dto.request.RecommendTransportationCardRequestDto;
import org.springframework.stereotype.Service;

@Service
public class ClimateCardExtract {

    public Integer calculationClimate(RecommendTransportationCardRequestDto dto) {
        UserCategory userCategory = dto.getUserCategory();
        CommuteType commuteType = dto.getCommuteType();
        CommuteType returnCommuteType = dto.getReturnCommuteType();
        boolean seoulBike = dto.isSeoulBike();

        Integer commuteCost = getCommuteCost(commuteType);
        Integer returnCommuteCost = getCommuteCost(returnCommuteType);

        Integer totalCost = (commuteCost + returnCommuteCost) * WEEKDAYS;
        Integer userCategoryApplyBenefitCost = getUserCategoryApplyBenefitCost(
                userCategory, totalCost);
        return calculateSeoulBikeCost(
                userCategoryApplyBenefitCost, seoulBike);
    }

    private Integer calculateSeoulBikeCost(
            Integer userCategoryApplyBenefitCost, boolean isSeoulBike) {
        if (isSeoulBike) {
            return userCategoryApplyBenefitCost + 3000;
        }
        return userCategoryApplyBenefitCost;
    }

    private Integer getUserCategoryApplyBenefitCost(
            UserCategory userCategory, Integer totalCost) {
        return switch (userCategory) {
            case LOW_INCOME, MULTI_CHILD_3 -> 45000 + totalCost;
            case MULTI_CHILD_2, YOUTH -> 55000 + totalCost;
            case GENERAL -> 62000 + totalCost;
        };
    }

    private Integer getCommuteCost(CommuteType commuteType) {
        return switch (commuteType) {
            case GENERAL_BUS -> 1500;
            case GYEONGGI_SUBWAY -> 1450;
            case WIDE_AREA_BUS -> 2800;
            default -> 0;
        };
    }
}
