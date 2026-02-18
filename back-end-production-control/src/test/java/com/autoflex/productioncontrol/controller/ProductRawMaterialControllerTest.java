package com.autoflex.productioncontrol.controller;

import com.autoflex.productioncontrol.dto.ProductRawMaterialDTO;
import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRawMaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRawMaterialRepository productRawMaterialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProductRawMaterial() throws Exception {

        Product product = new Product();
        product.setName("Product A");
        product.setQuantity(10);
        product.setPrice(100.0);
        product = productRepository.save(product);

        RawMaterial raw = new RawMaterial();
        raw.setName("Material X");
        raw.setQuantity(20);
        raw = rawMaterialRepository.save(raw);

        // 🔹 agora usamos DTO
        ProductRawMaterialDTO dto = new ProductRawMaterialDTO();
        dto.setProductId(product.getId());
        dto.setRawMaterialId(raw.getId());
        dto.setQuantity(5);

        mockMvc.perform(post("/product-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void testGetProductRawMaterial() throws Exception {

        Product product = new Product();
        product.setName("Product B");
        product.setQuantity(15);
        product.setPrice(150.0);
        product = productRepository.save(product);

        RawMaterial raw = new RawMaterial();
        raw.setName("Material Y");
        raw.setQuantity(30);
        raw = rawMaterialRepository.save(raw);

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(raw);
        prm.setQuantity(10);

        ProductRawMaterial saved = productRawMaterialRepository.save(prm);

        mockMvc.perform(get("/product-materials/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.rawMaterial.name").value("Material Y"));
    }
}