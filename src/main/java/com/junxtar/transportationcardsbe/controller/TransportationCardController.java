package com.junxtar.transportationcardsbe.controller;

import com.junxtar.transportationcardsbe.dto.request.RecommendTransportationCardRequestDto;
import com.junxtar.transportationcardsbe.dto.response.RecommendTransportationCardResponseDto;
import com.junxtar.transportationcardsbe.service.action.TransportationCardAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransportationCardController {

    private final TransportationCardAction transportationCardAction;

    @PostMapping("/transportation-cards/recommend")
    public ResponseEntity<RecommendTransportationCardResponseDto> recommendTransportationCard(@RequestBody RecommendTransportationCardRequestDto request) {
        RecommendTransportationCardResponseDto result = transportationCardAction.recommendTransportationCard(request);
        return ResponseEntity.ok(result);
    }
}
