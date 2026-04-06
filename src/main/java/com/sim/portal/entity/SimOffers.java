package com.sim.portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SimOffers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimOffers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    private Integer callQty;

    private Double cost;

    private Integer dataQty;

    private Integer duration;

    private String offerName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "simId")
    private SimDetails simDetails;
}
