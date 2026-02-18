package com.autoflex.productioncontrol.controller;

import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RawMaterialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateRawMaterial() throws Exception {

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName("Steel");
        rawMaterial.setQuantity(100);

        mockMvc.perform(post("/raw-materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rawMaterial)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Steel"));
    }

    @Test
    void testGetRawMaterialById() throws Exception {

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName("Aluminum");
        rawMaterial.setQuantity(50);
        rawMaterial = rawMaterialRepository.save(rawMaterial);

        mockMvc.perform(get("/raw-materials/{id}", rawMaterial.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aluminum"));
    }

    @Test
    void testGetAllRawMaterials() throws Exception {

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName("Copper");
        rawMaterial.setQuantity(30);
        rawMaterialRepository.save(rawMaterial);

        mockMvc.perform(get("/raw-materials"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateRawMaterial() throws Exception {

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName("Plastic");
        rawMaterial.setQuantity(200);
        rawMaterial = rawMaterialRepository.save(rawMaterial);

        rawMaterial.setQuantity(250);

        mockMvc.perform(put("/raw-materials/{id}", rawMaterial.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rawMaterial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(250));
    }

    @Test
    void testDeleteRawMaterial() throws Exception {

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName("Rubber");
        rawMaterial.setQuantity(80);
        rawMaterial = rawMaterialRepository.save(rawMaterial);

        mockMvc.perform(delete("/raw-materials/{id}", rawMaterial.getId()))
                .andExpect(status().isNoContent());
    }
}