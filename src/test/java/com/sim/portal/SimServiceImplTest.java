package com.sim.portal;

import com.sim.portal.dto.SimValidationRequest;
import com.sim.portal.entity.SimDetails;
import com.sim.portal.entity.SimOffers;
import com.sim.portal.exception.SimActivationException;
import com.sim.portal.repository.SimDetailsRepository;
import com.sim.portal.repository.SimOffersRepository;
import com.sim.portal.serviceimpl.SimServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimServiceImplTest {

    @Mock
    private SimDetailsRepository simDetailsRepository;

    @Mock
    private SimOffersRepository simOffersRepository;

    @InjectMocks
    private SimServiceImpl simService;

    private SimDetails inactiveSim;
    private SimDetails activeSim;

    @BeforeEach
    void setUp() {
        inactiveSim = new SimDetails(1L, "1234567892", "1234567891235", "inactive");
        activeSim   = new SimDetails(2L, "1234567891", "1234567891234", "active");
    }

    @Test
    void validateSimAndGetOffers_ValidInactiveSim_ReturnsOffers() {
        SimValidationRequest req = new SimValidationRequest();
        req.setSimNumber("1234567891235");
        req.setServiceNumber("1234567892");

        SimOffers offer = new SimOffers(1L, 150, 50.0, 100, 15, "Free calls", inactiveSim);

        when(simDetailsRepository.findBySimNumberAndServiceNumber("1234567891235", "1234567892"))
                .thenReturn(Optional.of(inactiveSim));
        when(simOffersRepository.findBySimDetails(inactiveSim))
                .thenReturn(List.of(offer));

        List<SimOffers> result = simService.validateSimAndGetOffers(req);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Free calls", result.get(0).getOfferName());
    }

    @Test
    void validateSimAndGetOffers_InvalidDetails_ThrowsException() {
        SimValidationRequest req = new SimValidationRequest();
        req.setSimNumber("9999999999999");
        req.setServiceNumber("0000000000");

        when(simDetailsRepository.findBySimNumberAndServiceNumber(anyString(), anyString()))
                .thenReturn(Optional.empty());

        SimActivationException ex = assertThrows(SimActivationException.class,
                () -> simService.validateSimAndGetOffers(req));
        assertEquals("Invalid details, please check again SIM number/Service number!", ex.getMessage());
    }

    @Test
    void validateSimAndGetOffers_AlreadyActiveSim_ThrowsException() {
        SimValidationRequest req = new SimValidationRequest();
        req.setSimNumber("1234567891234");
        req.setServiceNumber("1234567891");

        when(simDetailsRepository.findBySimNumberAndServiceNumber("1234567891234", "1234567891"))
                .thenReturn(Optional.of(activeSim));

        SimActivationException ex = assertThrows(SimActivationException.class,
                () -> simService.validateSimAndGetOffers(req));
        assertEquals("SIM already active", ex.getMessage());
    }
}
