package com.osstem.o2o.entity.sample;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "items")
    private Set<Category> categories = new HashSet<>();

    public Item() {
    }

    @Builder
    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
        this.categories = new HashSet<>();
    }

    public Item(String name) {
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

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

}