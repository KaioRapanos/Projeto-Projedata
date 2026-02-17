package com.autoflex.productioncontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import java.util.List;

public interface ProductRawMaterialRepository extends JpaRepository<ProductRawMaterial, Long> {

    List<ProductRawMaterial> findByProductId(Long productId);

    List<ProductRawMaterial> findByRawMaterialId(Long rawMaterialId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductRawMaterial prm WHERE prm.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
}