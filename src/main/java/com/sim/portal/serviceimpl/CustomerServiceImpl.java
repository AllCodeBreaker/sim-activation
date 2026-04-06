package com.sim.portal.serviceimpl;

import com.sim.portal.dto.CustomerBasicDetailsRequest;
import com.sim.portal.dto.CustomerPersonalDetailsRequest;
import com.sim.portal.dto.IdProofValidationRequest;
import com.sim.portal.dto.UpdateAddressRequest;
import com.sim.portal.entity.Customer;
import com.sim.portal.entity.CustomerAddress;
import com.sim.portal.entity.CustomerIdentity;
import com.sim.portal.entity.SimDetails;
import com.sim.portal.exception.ResourceNotFoundException;
import com.sim.portal.exception.SimActivationException;
import com.sim.portal.repository.CustomerAddressRepository;
import com.sim.portal.repository.CustomerIdentityRepository;
import com.sim.portal.repository.CustomerRepository;
import com.sim.portal.repository.SimDetailsRepository;
import com.sim.portal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerIdentityRepository customerIdentityRepository;

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    /**
     * Validates customer email and date of birth against stored data.
     * Returns success message or throws exception for invalid details.
     */
    @Override
    public String validateCustomerBasicDetails(CustomerBasicDetailsRequest request) {

        LocalDate dob = LocalDate.parse(request.getDob());

        Optional<Customer> customerOpt = customerRepository
                .findByEmailAddressAndDateOfBirth(request.getEmail(), dob);

        if (customerOpt.isEmpty()) {
            throw new ResourceNotFoundException("No request placed for you.");
        }

        return "Customer basic details validated successfully.";
    }

    /**
     * Validates customer personal details: first name, last name, and confirm email.
     * Retrieves customer by name and compares stored email with provided confirm email.
     */
    @Override
    public Customer validateCustomerPersonalDetails(CustomerPersonalDetailsRequest request) {

        Optional<Customer> customerOpt = customerRepository
                .findByFirstNameAndLastName(request.getFirstName(), request.getLastName());

        if (customerOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No customer found for the provided details");
        }

        Customer customer = customerOpt.get();

        // Validate confirm email against stored email
        if (!customer.getEmailAddress().equalsIgnoreCase(request.getConfirmEmail())) {
            throw new SimActivationException("Invalid email details!!");
        }

        return customer;
    }

    /**
     * Updates customer address if exists, or creates a new address record.
     */
    @Override
    public CustomerAddress updateCustomerAddress(UpdateAddressRequest request) {

        CustomerAddress customerAddress;

        // If addressId is provided, update existing record
        if (request.getAddressId() != null) {
            customerAddress = customerAddressRepository
                    .findById(request.getAddressId())
                    .orElse(new CustomerAddress());
        } else {
            customerAddress = new CustomerAddress();
        }

        customerAddress.setAddress(request.getAddress());
        customerAddress.setCity(request.getCity());
        customerAddress.setPincode(request.getPincode());
        customerAddress.setState(request.getState());

        return customerAddressRepository.save(customerAddress);
    }

    /**
     * Validates customer ID proof (Aadhar) against CustomerIdentity table.
     * Also cross-checks with Customer table using first name, last name, and DOB.
     * On success, activates the SIM by changing status from "inactive" to "active".
     */
    @Override
    public String validateIdProofAndActivateSim(IdProofValidationRequest request) {

        LocalDate dob = LocalDate.parse(request.getDateOfBirth());

        // Validate against CustomerIdentity table
        Optional<CustomerIdentity> identityOpt = customerIdentityRepository
                .findByUniqueIdNumber(request.getUniqueIdNumber());

        if (identityOpt.isEmpty()) {
            throw new SimActivationException("Invalid details");
        }

        CustomerIdentity identity = identityOpt.get();

        // Validate first name and last name against ID proof
        if (!identity.getFirstName().equalsIgnoreCase(request.getFirstName()) ||
                !identity.getLastName().equalsIgnoreCase(request.getLastName())) {
            throw new SimActivationException("Invalid details");
        }

        // Validate date of birth against ID proof
        if (!identity.getDateOfbirth().equals(dob)) {
            throw new SimActivationException("Incorrect date of birth details");
        }

        // Cross-validate with Customer table using first name and last name
        Optional<Customer> customerOpt = customerRepository
                .findByFirstNameAndLastName(request.getFirstName(), request.getLastName());

        if (customerOpt.isEmpty()) {
            throw new SimActivationException("Invalid details");
        }

        Customer customer = customerOpt.get();

        // Validate DOB against Customer record
        if (!customer.getDateOfBirth().equals(dob)) {
            throw new SimActivationException("Incorrect date of birth details");
        }

        // Activate the SIM
        SimDetails simDetails = customer.getSimDetails();
        if (simDetails == null) {
            throw new ResourceNotFoundException("No SIM associated with this customer.");
        }

        if ("active".equalsIgnoreCase(simDetails.getSimStatus())) {
            throw new SimActivationException("SIM already active");
        }

        simDetails.setSimStatus("active");
        simDetailsRepository.save(simDetails);

        return "SIM activated successfully!";
    }
}
