package com.autoflex.productioncontrol.service;

import com.autoflex.productioncontrol.dto.RawMaterialCreateDTO;
import com.autoflex.productioncontrol.dto.RawMaterialDTO;
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
        RawMaterialCreateDTO createDTO = new RawMaterialCreateDTO();
        createDTO.setName("Test Material");
        createDTO.setQuantity(100);

        RawMaterialDTO saved = rawMaterialService.create(createDTO);
        assertNotNull(saved.getId());

        RawMaterialDTO fetched = rawMaterialService.findById(saved.getId());
        assertEquals("Test Material", fetched.getName());
        assertEquals(100, fetched.getQuantity());
    }

    @Test
    void testUpdateRawMaterial() {
        RawMaterialCreateDTO createDTO = new RawMaterialCreateDTO();
        createDTO.setName("Update Material");
        createDTO.setQuantity(50);

        RawMaterialDTO saved = rawMaterialService.create(createDTO);

        // Preparar DTO de update
        RawMaterialCreateDTO updateDTO = new RawMaterialCreateDTO();
        updateDTO.setName("Update Material"); // mantém mesmo nome
        updateDTO.setQuantity(75); // altera quantidade

        RawMaterialDTO updated = rawMaterialService.update(saved.getId(), updateDTO);

        assertEquals(75, updated.getQuantity());
    }

    @Test
    void testDeleteRawMaterial() {
        RawMaterialCreateDTO createDTO = new RawMaterialCreateDTO();
        createDTO.setName("Delete Material");
        createDTO.setQuantity(20);

        RawMaterialDTO saved = rawMaterialService.create(createDTO);
        rawMaterialService.delete(saved.getId());

        assertFalse(rawMaterialRepository.findById(saved.getId()).isPresent());
    }
}
