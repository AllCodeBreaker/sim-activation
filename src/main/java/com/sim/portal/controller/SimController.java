package com.sim.portal.controller;

import com.sim.portal.dto.ApiResponse;
import com.sim.portal.dto.SimValidationRequest;
import com.sim.portal.entity.SimOffers;
import com.sim.portal.service.SimService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sim")
public class SimController {

    @Autowired
    private SimService simService;

    /**
     * POST /api/sim/validate
     * Validates SIM number and Service number combination.
     * Returns available offers on success.
     *
     * Request body:
     * {
     *   "simNumber": "1234567891235",
     *   "serviceNumber": "1234567892"
     * }
     */
    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<List<SimOffers>>> validateSim(
            @Valid @RequestBody SimValidationRequest request) {

        List<SimOffers> offers = simService.validateSimAndGetOffers(request);
        ApiResponse<List<SimOffers>> response = new ApiResponse<>(
                true, "SIM validated successfully. Available offers:", offers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
