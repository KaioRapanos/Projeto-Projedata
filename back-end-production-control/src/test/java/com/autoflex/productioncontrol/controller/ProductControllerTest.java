package com.autoflex.productioncontrol.controller;

import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {

        Product product = new Product();
        product.setName("Notebook");
        product.setQuantity(10);
        product.setPrice(3500.0);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Notebook"));
    }

    @Test
    void testGetProductById() throws Exception {

        Product product = new Product();
        product.setName("Mouse");
        product.setQuantity(20);
        product.setPrice(100.0);
        product = productRepository.save(product);

        mockMvc.perform(get("/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mouse"));
    }

    @Test
    void testGetAllProducts() throws Exception {

        Product product = new Product();
        product.setName("Keyboard");
        product.setQuantity(15);
        product.setPrice(200.0);
        productRepository.save(product);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProduct() throws Exception {

        Product product = new Product();
        product.setName("Monitor");
        product.setQuantity(5);
        product.setPrice(1200.0);
        product = productRepository.save(product);

        product.setPrice(1500.0);

        mockMvc.perform(put("/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(1500.0));
    }

    @Test
    void testDeleteProduct() throws Exception {

        Product product = new Product();
        product.setName("Headset");
        product.setQuantity(8);
        product.setPrice(300.0);
        product = productRepository.save(product);

        mockMvc.perform(delete("/products/{id}", product.getId()))
                .andExpect(status().isOk());
    }
}