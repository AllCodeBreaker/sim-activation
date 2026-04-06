package com.sim.portal.service;

import com.sim.portal.dto.SimValidationRequest;
import com.sim.portal.entity.SimOffers;

import java.util.List;

public interface SimService {

    List<SimOffers> validateSimAndGetOffers(SimValidationRequest request);
}
