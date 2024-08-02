package com.osstem.o2o.repository;

import com.osstem.o2o.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
