package com.osstem.o2o.entity;

import com.osstem.o2o.entity.enumerate.RegistrationState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    private String name;

    @Column
    private String license;

    @Column
    private RegistrationState state;

    @OneToMany(mappedBy = "customer")
    private Set<Dentist> dentist;

}
