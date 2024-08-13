package com.osstem.o2o.repository;

import com.osstem.o2o.entity.sample.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
