package com.sim.portal.repository;

import com.sim.portal.entity.SimDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimDetailsRepository extends JpaRepository<SimDetails, Long> {

    Optional<SimDetails> findBySimNumberAndServiceNumber(String simNumber, String serviceNumber);

    Optional<SimDetails> findBySimNumber(String simNumber);
}
