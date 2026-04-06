package com.sim.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SimValidationRequest {

    @NotBlank(message = "SIM number is required")
    @Pattern(regexp = "\\d{13}", message = "SIM number should be 13-digit numeric value")
    private String simNumber;

    @NotBlank(message = "Service number is required")
    @Pattern(regexp = "\\d{10}", message = "Service number should be 10-digit numeric value")
    private String serviceNumber;
}
