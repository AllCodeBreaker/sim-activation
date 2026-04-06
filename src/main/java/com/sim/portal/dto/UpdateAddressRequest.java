package com.sim.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateAddressRequest {

    @NotBlank(message = "Address is required")
    @Pattern(regexp = "^.{1,25}$", message = "Address should be maximum of 25 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "City should not contain any special characters except space")
    private String city;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "\\d{6}", message = "Pin should be 6 digit number")
    private String pincode;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "State should not contain any special characters except space")
    private String state;

    // Optional: to identify which address to update
    private Long addressId;
}
