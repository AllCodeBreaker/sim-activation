package com.sim.portal.controller;

import com.sim.portal.dto.*;
import com.sim.portal.entity.Customer;
import com.sim.portal.entity.CustomerAddress;
import com.sim.portal.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * POST /api/customer/validate/basic
     * Validates customer email and date of birth.
     *
     * Request body:
     * {
     *   "email": "smith@abc.com",
     *   "dob": "1990-12-12"
     * }
     */
    @PostMapping("/validate/basic")
    public ResponseEntity<ApiResponse<String>> validateBasicDetails(
            @Valid @RequestBody CustomerBasicDetailsRequest request) {

        String message = customerService.validateCustomerBasicDetails(request);
        ApiResponse<String> response = new ApiResponse<>(true, message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /api/customer/validate/personal
     * Validates customer first name, last name and confirm email.
     *
     * Request body:
     * {
     *   "firstName": "Smith",
     *   "lastName": "John",
     *   "confirmEmail": "smith@abc.com"
     * }
     */
    @PostMapping("/validate/personal")
    public ResponseEntity<ApiResponse<Customer>> validatePersonalDetails(
            @Valid @RequestBody CustomerPersonalDetailsRequest request) {

        Customer customer = customerService.validateCustomerPersonalDetails(request);
        ApiResponse<Customer> response = new ApiResponse<>(
                true, "Customer personal details validated successfully.", customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PUT /api/customer/address/update
     * Updates or creates customer address.
     *
     * Request body:
     * {
     *   "addressId": 1,
     *   "address": "New Street 12",
     *   "city": "Bangalore",
     *   "pincode": "560041",
     *   "state": "Karnataka"
     * }
     */
    @PutMapping("/address/update")
    public ResponseEntity<ApiResponse<CustomerAddress>> updateAddress(
            @Valid @RequestBody UpdateAddressRequest request) {

        CustomerAddress updatedAddress = customerService.updateCustomerAddress(request);
        ApiResponse<CustomerAddress> response = new ApiResponse<>(
                true, "Address updated successfully.", updatedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /api/customer/validate/idproof
     * Validates customer ID proof and activates SIM on success.
     *
     * Request body:
     * {
     *   "uniqueIdNumber": "1234567891234567",
     *   "firstName": "Smith",
     *   "lastName": "John",
     *   "dateOfBirth": "1990-12-12"
     * }
     */
    @PostMapping("/validate/idproof")
    public ResponseEntity<ApiResponse<String>> validateIdProof(
            @Valid @RequestBody IdProofValidationRequest request) {

        String result = customerService.validateIdProofAndActivateSim(request);
        ApiResponse<String> response = new ApiResponse<>(true, result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
