package com.sim.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class IdProofValidationRequest {

    @NotBlank(message = "Unique ID number is required")
    @Pattern(regexp = "\\d{16}", message = "Id should be 16 digit")
    private String uniqueIdNumber;

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$", message = "Firstname should be maximum of 15 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$", message = "Lastname should be maximum of 15 characters")
    private String lastName;

    @NotBlank(message = "Date of birth is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth should be in yyyy-mm-dd format")
    private String dateOfBirth;
}
