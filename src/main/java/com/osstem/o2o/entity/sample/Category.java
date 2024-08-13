package com.osstem.o2o.entity.sample;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private Set<Item> items = new HashSet<>();

    public Category() {
    }

    @Builder
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
        this.items = new HashSet<>();
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public void addItem(Item item) {
        items.add(item);
    }

}