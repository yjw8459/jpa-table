package com.osstem.o2o.repository;

import com.osstem.o2o.entity.sample.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
