package com.junxtar.transportationcardsbe.service.extract;

import static com.junxtar.transportationcardsbe.constant.CommonConstant.WEEKDAYS;

import com.junxtar.transportationcardsbe.domain.CommuteType;
import com.junxtar.transportationcardsbe.domain.UserCategory;
import com.junxtar.transportationcardsbe.dto.request.RecommendTransportationCardRequestDto;
import java.util.HashMap;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;

@Service
public class KpassCardExtract {

    public Integer calculationKpass(RecommendTransportationCardRequestDto dto) {
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

    private Integer getUserCategoryApplyBenefitCost(
            UserCategory userCategory, Integer totalCost) {
        return switch (userCategory) {
            case LOW_INCOME -> (int) (totalCost * 0.47);
            case MULTI_CHILD_3 -> (int) (totalCost * 0.5);
            case MULTI_CHILD_2, YOUTH -> (int) (totalCost * 0.7);
            case GENERAL -> (int) (totalCost * 0.8);
        };
    }
    public int singleNumber(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int answer = 0;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (value == 1) {
                return key;
            }
        }
        return -1;
    }

    private Integer getCommuteCost(CommuteType commuteType) {
        return switch (commuteType) {
            case SEOUL_BUS, GENERAL_BUS -> 1500;
            case SEOUL_SUBWAY, GYEONGGI_SUBWAY -> 1450;
            case WIDE_AREA_BUS -> 2800;
        };
    }

    private Integer calculateSeoulBikeCost(
            Integer userCategoryApplyBenefitCost, boolean isSeoulBike) {
        if (isSeoulBike) {
            return userCategoryApplyBenefitCost + 5000;
        }
        return userCategoryApplyBenefitCost;
    }
}
