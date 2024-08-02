package com.osstem.o2o.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Dentist dentist;

    @Column
    private BigDecimal latitude;

    @Column
    private BigDecimal longitude;
}
