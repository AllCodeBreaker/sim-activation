package com.sim.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerPersonalDetailsRequest {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$", message = "Firstname should be maximum of 15 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$", message = "Lastname should be maximum of 15 characters")
    private String lastName;

    @NotBlank(message = "Confirm email is required")
    @Pattern(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,3}$", message = "Invalid email")
    private String confirmEmail;
}
