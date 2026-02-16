package com.autoflex.productioncontrol.controller;

import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRawMaterialRepository prmRepository;

    @BeforeEach
    void cleanDatabase() {
        prmRepository.deleteAll();
        productRepository.deleteAll();
        rawMaterialRepository.deleteAll();
    }

    @Test
    void shouldReturnProductionSuggestions() throws Exception {
        // 1️⃣ Criar dados de teste
        Product product = new Product();
        product.setName("Product A");
        product.setPrice(100.0);
        product.setQuantity(10);
        product = productRepository.save(product);

        RawMaterial raw = new RawMaterial();
        raw.setName("Material X");
        raw.setQuantity(20);
        raw = rawMaterialRepository.save(raw);

        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(raw);
        prm.setQuantity(5);
        prmRepository.save(prm);

        // 2️⃣ Testa o endpoint
        mockMvc.perform(get("/production/suggestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(product.getId()))
                .andExpect(jsonPath("$[0].maxQuantity").value(4))
                .andExpect(jsonPath("$[0].totalValue").value(400.0));
    }
}