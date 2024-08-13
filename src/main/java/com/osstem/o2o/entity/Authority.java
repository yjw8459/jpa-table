package com.osstem.o2o.entity;

import com.osstem.o2o.entity.enumerate.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
public class Authority {
    @Id
    private Role name;
}
