package com.osstem.o2o.entity;

import com.osstem.o2o.entity.enumerate.RegistrationState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Employee extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    private String name;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String representative;

    @OneToMany(mappedBy = "employee")
    private Set<EmployeeAuthority> employeeAuthority;

    @OneToMany(mappedBy = "employee")
    private Set<Post> post;


}
