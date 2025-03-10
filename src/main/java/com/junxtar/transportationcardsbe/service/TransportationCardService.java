package com.junxtar.transportationcardsbe.service;

import com.junxtar.transportationcardsbe.domain.CommuteType;
import com.junxtar.transportationcardsbe.domain.TransportationCard;
import com.junxtar.transportationcardsbe.domain.UserCategory;
import com.junxtar.transportationcardsbe.dto.request.RecommendTransportationCardRequestDto;
import com.junxtar.transportationcardsbe.dto.response.RecommendTransportationCardResponseDto;
import com.junxtar.transportationcardsbe.repository.TransportationCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransportationCardService {

    private final static Integer WEEKDAYS = 21;
    private final TransportationCardRepository transportationCardRepository;

    public RecommendTransportationCardResponseDto recommendTransportationCard(
        RecommendTransportationCardRequestDto request) {
        UserCategory userCategory = request.getUserCategory();
        CommuteType commuteType = request.getCommuteType();
        CommuteType returnCommuteType = request.getReturnCommuteType();
        boolean seoulBike = request.isSeoulBike();

        // 기후동행을 사용 못하는 가격이 여기서는 잠시 빠져있음
        if (commuteType.name().startsWith("SEOUL") && returnCommuteType.name().startsWith("SEOUL")) {
            // 기후동행 가격 온전히
        } else {
            // 만약 경기권 버스 혹은 지하철을 탔음에도 불구하고 기후동행을 사용하겠다고 하면 기후동행 이용료 + 별도의 교통비 지출
        }
        Integer commuteCost = getCommuteCost(commuteType);
        Integer returnCommuteCost = getCommuteCost(returnCommuteType);

        Integer totalCost = (commuteCost + returnCommuteCost) * WEEKDAYS;
        RecommendTransportationCardResponseDto userCategoryApplyBenefitCost = getUserCategoryApplyBenefitCost(
            userCategory, totalCost);

        RecommendTransportationCardResponseDto recommendTransportationCardResponseDto = calculateSeoulBikeCost(
            userCategoryApplyBenefitCost, seoulBike);

        TransportationCard transportationCard = TransportationCard.builder()
            .userCategory(userCategory)
            .isSeoulBike(seoulBike)
            .commuteType(commuteType)
            .returnCommuteType(returnCommuteType)
            .kPassPrice(recommendTransportationCardResponseDto.getKPassPrice())
            .climateActionPrice(recommendTransportationCardResponseDto.getClimateActionPrice())
            .build();

        transportationCardRepository.save(transportationCard);

        return recommendTransportationCardResponseDto;
    }

    private RecommendTransportationCardResponseDto calculateSeoulBikeCost(
        RecommendTransportationCardResponseDto dto, boolean isSeoulBike) {
        if (isSeoulBike) {
            return RecommendTransportationCardResponseDto.builder()
                .kPassPrice(dto.getKPassPrice() + 5000)
                .climateActionPrice(dto.getClimateActionPrice() + 3000)
                .build();
        }
        return dto;
    }

    private RecommendTransportationCardResponseDto getUserCategoryApplyBenefitCost(
        UserCategory userCategory, Integer totalCost) {
        return switch (userCategory) {
            case LOW_INCOME -> {
                Integer kPassPrice = (int) (totalCost * 0.47);
                Integer climateActionPrice = 45000;
                yield RecommendTransportationCardResponseDto.builder()
                    .kPassPrice(kPassPrice)
                    .climateActionPrice(climateActionPrice)
                    .build();
            }

            case MULTI_CHILD_3 -> {
                Integer kPassPrice = (int) (totalCost * 0.5);
                Integer climateActionPrice = 45000;
                yield RecommendTransportationCardResponseDto.builder()
                    .kPassPrice(kPassPrice)
                    .climateActionPrice(climateActionPrice)
                    .build();
            }

            case MULTI_CHILD_2, YOUTH -> {
                Integer kPassPrice = (int) (totalCost * 0.7);
                Integer climateActionPrice = 55000;
                yield RecommendTransportationCardResponseDto.builder()
                    .kPassPrice(kPassPrice)
                    .climateActionPrice(climateActionPrice)
                    .build();
            }

            case GENERAL -> {
                Integer kPassPrice = (int) (totalCost * 0.8);
                Integer climateActionPrice = 62000;
                yield RecommendTransportationCardResponseDto.builder()
                    .kPassPrice(kPassPrice)
                    .climateActionPrice(climateActionPrice)
                    .build();
            }
        };
    }

    private Integer getCommuteCost(CommuteType commuteType) {
        return switch (commuteType) {
            case SEOUL_BUS, GENERAL_BUS -> 1500;
            case SEOUL_SUBWAY, GYEONGGI_SUBWAY -> 1450;
            case WIDE_AREA_BUS -> 2800;
        };
    }
}
