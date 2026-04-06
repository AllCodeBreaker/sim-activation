package com.sim.portal;

import com.sim.portal.dto.CustomerBasicDetailsRequest;
import com.sim.portal.dto.CustomerPersonalDetailsRequest;
import com.sim.portal.entity.Customer;
import com.sim.portal.exception.ResourceNotFoundException;
import com.sim.portal.exception.SimActivationException;
import com.sim.portal.repository.CustomerAddressRepository;
import com.sim.portal.repository.CustomerIdentityRepository;
import com.sim.portal.repository.CustomerRepository;
import com.sim.portal.repository.SimDetailsRepository;
import com.sim.portal.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @Mock
    private CustomerIdentityRepository customerIdentityRepository;

    @Mock
    private SimDetailsRepository simDetailsRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        sampleCustomer = new Customer();
        sampleCustomer.setUniqueIdNumber("1234567891234567");
        sampleCustomer.setFirstName("Smith");
        sampleCustomer.setLastName("John");
        sampleCustomer.setEmailAddress("smith@abc.com");
        sampleCustomer.setDateOfBirth(LocalDate.of(1990, 12, 12));
    }

    // ─── Basic Details Tests ───────────────────────────────────────────────────

    @Test
    void validateBasicDetails_ValidEmailAndDob_ReturnsSuccess() {
        CustomerBasicDetailsRequest req = new CustomerBasicDetailsRequest();
        req.setEmail("smith@abc.com");
        req.setDob("1990-12-12");

        when(customerRepository.findByEmailAddressAndDateOfBirth(
                "smith@abc.com", LocalDate.of(1990, 12, 12)))
                .thenReturn(Optional.of(sampleCustomer));

        String result = customerService.validateCustomerBasicDetails(req);
        assertTrue(result.contains("validated successfully"));
    }

    @Test
    void validateBasicDetails_InvalidEmailOrDob_ThrowsException() {
        CustomerBasicDetailsRequest req = new CustomerBasicDetailsRequest();
        req.setEmail("wrong@abc.com");
        req.setDob("2000-01-01");

        when(customerRepository.findByEmailAddressAndDateOfBirth(anyString(), any()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> customerService.validateCustomerBasicDetails(req));
        assertEquals("No request placed for you.", ex.getMessage());
    }

    // ─── Personal Details Tests ────────────────────────────────────────────────

    @Test
    void validatePersonalDetails_ValidDetails_ReturnsCustomer() {
        CustomerPersonalDetailsRequest req = new CustomerPersonalDetailsRequest();
        req.setFirstName("Smith");
        req.setLastName("John");
        req.setConfirmEmail("smith@abc.com");

        when(customerRepository.findByFirstNameAndLastName("Smith", "John"))
                .thenReturn(Optional.of(sampleCustomer));

        Customer result = customerService.validateCustomerPersonalDetails(req);
        assertNotNull(result);
        assertEquals("smith@abc.com", result.getEmailAddress());
    }

    @Test
    void validatePersonalDetails_CustomerNotFound_ThrowsException() {
        CustomerPersonalDetailsRequest req = new CustomerPersonalDetailsRequest();
        req.setFirstName("Unknown");
        req.setLastName("Person");
        req.setConfirmEmail("unknown@abc.com");

        when(customerRepository.findByFirstNameAndLastName("Unknown", "Person"))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> customerService.validateCustomerPersonalDetails(req));
        assertEquals("No customer found for the provided details", ex.getMessage());
    }

    @Test
    void validatePersonalDetails_EmailMismatch_ThrowsException() {
        CustomerPersonalDetailsRequest req = new CustomerPersonalDetailsRequest();
        req.setFirstName("Smith");
        req.setLastName("John");
        req.setConfirmEmail("wrongemail@abc.com");

        when(customerRepository.findByFirstNameAndLastName("Smith", "John"))
                .thenReturn(Optional.of(sampleCustomer));

        SimActivationException ex = assertThrows(SimActivationException.class,
                () -> customerService.validateCustomerPersonalDetails(req));
        assertEquals("Invalid email details!!", ex.getMessage());
    }
}
