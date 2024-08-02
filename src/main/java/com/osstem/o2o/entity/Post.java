package com.osstem.o2o.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.naming.Name;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="category_id")
    private PostCategory category;

    @Column
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String author;

    @CreatedDate
    private LocalDateTime date;
}
