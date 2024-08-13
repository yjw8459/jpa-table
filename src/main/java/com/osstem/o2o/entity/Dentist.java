package com.osstem.o2o.entity;

import com.osstem.o2o.entity.enumerate.RegistrationState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dentist extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String link;

    @Column
    private String zipcode;

    @Column
    private String groups;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    private Location location;
}
