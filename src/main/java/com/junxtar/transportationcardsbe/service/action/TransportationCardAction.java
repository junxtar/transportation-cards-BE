package com.junxtar.transportationcardsbe.service.action;

import com.junxtar.transportationcardsbe.domain.CommuteType;
import com.junxtar.transportationcardsbe.domain.TransportationCard;
import com.junxtar.transportationcardsbe.domain.UserCategory;
import com.junxtar.transportationcardsbe.dto.request.RecommendTransportationCardRequestDto;
import com.junxtar.transportationcardsbe.dto.response.RecommendTransportationCardResponseDto;
import com.junxtar.transportationcardsbe.repository.TransportationCardRepository;
import com.junxtar.transportationcardsbe.service.extract.ClimateCardExtract;
import com.junxtar.transportationcardsbe.service.extract.KpassCardExtract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransportationCardAction {

    private final TransportationCardRepository transportationCardRepository;
    private final KpassCardExtract kpassCardExtract;
    private final ClimateCardExtract climateCardExtract;

    public RecommendTransportationCardResponseDto recommendTransportationCard(
        RecommendTransportationCardRequestDto request) {

        UserCategory userCategory = request.getUserCategory();
        CommuteType commuteType = request.getCommuteType();
        CommuteType returnCommuteType = request.getReturnCommuteType();
        boolean seoulBike = request.isSeoulBike();
        Integer kPassPrice = kpassCardExtract.calculationKpass(request);
        Integer climateActionPrice = climateCardExtract.calculationClimate(request);

        TransportationCard transportationCard = TransportationCard.builder()
            .userCategory(userCategory)
            .isSeoulBike(seoulBike)
            .commuteType(commuteType)
            .returnCommuteType(returnCommuteType)
            .kPassPrice(kPassPrice)
            .climateActionPrice(climateActionPrice)
            .build();

        transportationCardRepository.save(transportationCard);

        return RecommendTransportationCardResponseDto.builder()
                .kPassPrice(kPassPrice)
                .climateActionPrice(climateActionPrice)
                .build();
    }
}
