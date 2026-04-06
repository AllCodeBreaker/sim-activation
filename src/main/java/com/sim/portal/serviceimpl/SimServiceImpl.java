package com.sim.portal.serviceimpl;

import com.sim.portal.dto.SimValidationRequest;
import com.sim.portal.entity.SimDetails;
import com.sim.portal.entity.SimOffers;
import com.sim.portal.exception.SimActivationException;
import com.sim.portal.repository.SimDetailsRepository;
import com.sim.portal.repository.SimOffersRepository;
import com.sim.portal.service.SimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimServiceImpl implements SimService {

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private SimOffersRepository simOffersRepository;

    /**
     * Validates the SIM and Service number combination.
     * If valid and inactive, returns the list of available offers.
     * Throws SimActivationException for invalid or already active SIM.
     */
    @Override
    public List<SimOffers> validateSimAndGetOffers(SimValidationRequest request) {

        // Check if SIM and Service number combination exists
        Optional<SimDetails> simDetailsOpt = simDetailsRepository
                .findBySimNumberAndServiceNumber(request.getSimNumber(), request.getServiceNumber());

        if (simDetailsOpt.isEmpty()) {
            throw new SimActivationException(
                    "Invalid details, please check again SIM number/Service number!");
        }

        SimDetails simDetails = simDetailsOpt.get();

        // Check if SIM is already active
        if ("active".equalsIgnoreCase(simDetails.getSimStatus())) {
            throw new SimActivationException("SIM already active");
        }

        // Return offers for this SIM
        return simOffersRepository.findBySimDetails(simDetails);
    }
}
