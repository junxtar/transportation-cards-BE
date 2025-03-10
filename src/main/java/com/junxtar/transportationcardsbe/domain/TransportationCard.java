package com.junxtar.transportationcardsbe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transportation_card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransportationCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 유형 (저소득, 다자녀, 일반, 청년 등)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserCategory userCategory;

    // 서울 따릉이 이용 여부
    @Column(nullable = false)
    private Boolean isSeoulBike;

    // 출근 교통수단
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommuteType commuteType;

    // 퇴근 교통수단
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommuteType returnCommuteType;

    @Column(nullable = false)
    private Integer kPassPrice;

    @Column(nullable = false)
    private Integer climateActionPrice;

    @Builder
    private TransportationCard(UserCategory userCategory, Boolean isSeoulBike,
        CommuteType commuteType,
        CommuteType returnCommuteType, Integer kPassPrice, Integer climateActionPrice) {
        this.userCategory = userCategory;
        this.isSeoulBike = isSeoulBike;
        this.commuteType = commuteType;
        this.returnCommuteType = returnCommuteType;
        this.kPassPrice = kPassPrice;
        this.climateActionPrice = climateActionPrice;
    }
}
