package com.autoflex.productioncontrol.service;

import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") 
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreateAndGetProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(150.0);
        product.setQuantity(10);

        Product saved = productService.create(product);
        assertNotNull(saved.getId());

        Product fetched = productService.findById(saved.getId());
        assertEquals("Test Product", fetched.getName());
        assertEquals(150.0, fetched.getPrice());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setName("Update Test");
        product.setPrice(200.0);
        product.setQuantity(15);

        Product saved = productService.create(product);
        saved.setPrice(250.0);
        Product updated = productService.update(saved.getId(), saved);

        assertEquals(250.0, updated.getPrice());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setName("Delete Test");
        product.setPrice(50.0);
        product.setQuantity(5);

        Product saved = productService.create(product);
        productService.delete(saved.getId());

        assertFalse(productRepository.findById(saved.getId()).isPresent());
    }
}