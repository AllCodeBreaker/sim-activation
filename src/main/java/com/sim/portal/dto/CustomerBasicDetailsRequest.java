package com.sim.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerBasicDetailsRequest {

    @NotBlank(message = "Email value is required")
    @Pattern(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,3}$", message = "Invalid email")
    private String email;

    @NotBlank(message = "Dob value is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth should be in yyyy-mm-dd format")
    private String dob;
}
