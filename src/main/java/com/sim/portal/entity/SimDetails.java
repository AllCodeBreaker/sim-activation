package com.sim.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SimDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long simId;

    private String serviceNumber;

    private String simNumber;

    private String simStatus;
}
