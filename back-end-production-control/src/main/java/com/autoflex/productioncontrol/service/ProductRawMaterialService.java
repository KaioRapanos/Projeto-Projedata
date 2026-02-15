package com.autoflex.productioncontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRawMaterialService {

    private final ProductRawMaterialRepository repository;

    public ProductRawMaterial create(ProductRawMaterial prm) {
        return repository.save(prm);
    }

    public ProductRawMaterial update(Long id, ProductRawMaterial prm) {
        ProductRawMaterial existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductRawMaterial not found"));
        existing.setProduct(prm.getProduct());
        existing.setRawMaterial(prm.getRawMaterial());
        existing.setQuantity(prm.getQuantity());
        return repository.save(existing);
    }

    public List<ProductRawMaterial> findAll() {
        return repository.findAll();
    }

    public ProductRawMaterial findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductRawMaterial not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<ProductRawMaterial> findByProductId(Long productId) {
        return repository.findByProductId(productId);
    }

    public List<ProductRawMaterial> findByRawMaterialId(Long rawMaterialId) {
        return repository.findByRawMaterialId(rawMaterialId);
    }
}