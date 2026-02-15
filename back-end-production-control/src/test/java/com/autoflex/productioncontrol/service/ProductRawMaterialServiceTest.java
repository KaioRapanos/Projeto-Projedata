package com.autoflex.productioncontrol.service;

import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductRawMaterialServiceTest {

    @Autowired
    private ProductRawMaterialService service;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRawMaterialRepository prmRepository;

    @Test
    void testCreateAndGet() {
        // Cria Product
        Product product = new Product();
        product.setName("Product A");
        product.setPrice(100.0);
        product.setQuantity(10);
        product = productRepository.save(product);

        // Cria RawMaterial
        RawMaterial raw = new RawMaterial();
        raw.setName("Material X");
        raw.setQuantity(20);
        raw = rawMaterialRepository.save(raw);

        // Cria ProductRawMaterial
        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(raw);
        prm.setQuantity(5);

        ProductRawMaterial saved = service.create(prm);
        assertNotNull(saved.getId());

        ProductRawMaterial fetched = service.findById(saved.getId());
        assertEquals(5, fetched.getQuantity());
        assertEquals("Product A", fetched.getProduct().getName());
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        product.setName("Product B");
        product.setPrice(150.0);
        product.setQuantity(15);
        product = productRepository.save(product);

        RawMaterial raw = new RawMaterial();
        raw.setName("Material Y");
        raw.setQuantity(30);
        raw = rawMaterialRepository.save(raw);

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(raw);
        prm.setQuantity(10);

        ProductRawMaterial saved = service.create(prm);
        saved.setQuantity(20);

        ProductRawMaterial updated = service.update(saved.getId(), saved);
        assertEquals(20, updated.getQuantity());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setName("Product C");
        product.setPrice(200.0);
        product.setQuantity(20);
        product = productRepository.save(product);

        RawMaterial raw = new RawMaterial();
        raw.setName("Material Z");
        raw.setQuantity(40);
        raw = rawMaterialRepository.save(raw);

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(raw);
        prm.setQuantity(8);

        ProductRawMaterial saved = service.create(prm);
        service.delete(saved.getId());

        assertFalse(prmRepository.findById(saved.getId()).isPresent());
    }
}