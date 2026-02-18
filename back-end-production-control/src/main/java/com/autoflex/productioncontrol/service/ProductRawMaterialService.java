package com.autoflex.productioncontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;
import com.autoflex.productioncontrol.dto.ProductRawMaterialDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRawMaterialService {

    private final ProductRawMaterialRepository repository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    // ✅ CREATE usando DTO
    public ProductRawMaterial create(ProductRawMaterialDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(rawMaterial);
        prm.setQuantity(dto.getQuantity());

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
