package com.sim.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "CustomerIdentity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerIdentity {

    @Id
    private String uniqueIdNumber;

    private LocalDate dateOfbirth;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String state;
}
