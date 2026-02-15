package com.autoflex.productioncontrol.service;

import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RawMaterialServiceTest {

    @Autowired
    private RawMaterialService rawMaterialService;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Test
    void testCreateAndGetRawMaterial() {
        RawMaterial raw = new RawMaterial();
        raw.setName("Test Material");
        raw.setQuantity(100);

        RawMaterial saved = rawMaterialService.create(raw);
        assertNotNull(saved.getId());

        RawMaterial fetched = rawMaterialService.findById(saved.getId());
        assertEquals("Test Material", fetched.getName());
        assertEquals(100, fetched.getQuantity());
    }

    @Test
    void testUpdateRawMaterial() {
        RawMaterial raw = new RawMaterial();
        raw.setName("Update Material");
        raw.setQuantity(50);

        RawMaterial saved = rawMaterialService.create(raw);
        saved.setQuantity(75);
        RawMaterial updated = rawMaterialService.update(saved.getId(), saved);

        assertEquals(75, updated.getQuantity());
    }

    @Test
    void testDeleteRawMaterial() {
        RawMaterial raw = new RawMaterial();
        raw.setName("Delete Material");
        raw.setQuantity(20);

        RawMaterial saved = rawMaterialService.create(raw);
        rawMaterialService.delete(saved.getId());

        assertFalse(rawMaterialRepository.findById(saved.getId()).isPresent());
    }
}