package com.autoflex.productioncontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import java.util.List;

public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {

    List<ProductRawMaterial> findByProductId(Long productId);

    List<ProductRawMaterial> findByRawMaterialId(Long rawMaterialId);
}