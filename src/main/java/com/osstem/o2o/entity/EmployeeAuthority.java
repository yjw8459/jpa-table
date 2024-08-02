package com.osstem.o2o.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class EmployeeAuthority {

    @Id
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name="name")
    private Authority authority;

}
