package com.sim.portal.service;

import com.sim.portal.dto.CustomerBasicDetailsRequest;
import com.sim.portal.dto.CustomerPersonalDetailsRequest;
import com.sim.portal.dto.IdProofValidationRequest;
import com.sim.portal.dto.UpdateAddressRequest;
import com.sim.portal.entity.Customer;
import com.sim.portal.entity.CustomerAddress;

public interface CustomerService {

    String validateCustomerBasicDetails(CustomerBasicDetailsRequest request);

    Customer validateCustomerPersonalDetails(CustomerPersonalDetailsRequest request);

    CustomerAddress updateCustomerAddress(UpdateAddressRequest request);

    String validateIdProofAndActivateSim(IdProofValidationRequest request);
}
