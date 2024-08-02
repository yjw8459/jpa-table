package com.osstem.o2o.entity;

import com.osstem.o2o.entity.ProductOption;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToOne
    private ProductOption productOption;

    @OneToMany(mappedBy = "product")
    private Set<Post> post;

}
