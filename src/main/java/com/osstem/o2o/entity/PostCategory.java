package com.osstem.o2o.entity;

import com.osstem.o2o.entity.enumerate.PostType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
public class PostCategory extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category")
    private Set<Post> post;

    @Column
    @Enumerated
    private PostType type;
}
