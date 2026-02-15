package com.autoflex.productioncontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autoflex.productioncontrol.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}