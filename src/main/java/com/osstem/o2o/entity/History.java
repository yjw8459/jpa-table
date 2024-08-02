package com.osstem.o2o.entity;

import com.osstem.o2o.entity.enumerate.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class History extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="history")
    private Employee employee;

    @Column
    @Enumerated
    private Event event;

    @CreatedDate
    private LocalDateTime issueDate;


}
