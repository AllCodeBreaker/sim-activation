package com.sim.portal.repository;

import com.sim.portal.entity.SimDetails;
import com.sim.portal.entity.SimOffers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimOffersRepository extends JpaRepository<SimOffers, Long> {

    List<SimOffers> findBySimDetails(SimDetails simDetails);
}
