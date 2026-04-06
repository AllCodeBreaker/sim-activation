package com.sim.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String uniqueIdNumber;

    private LocalDate dateOfBirth;

    private String emailAddress;

    private String firstName;

    private String lastName;

    private String idType;

    private String state;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerAddress_addressId")
    private CustomerAddress customerAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "simId")
    private SimDetails simDetails;
}
