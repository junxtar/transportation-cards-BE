package com.junxtar.transportationcardsbe.repository;

import com.junxtar.transportationcardsbe.domain.TransportationCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationCardRepository extends JpaRepository<TransportationCard, Long> {

}
