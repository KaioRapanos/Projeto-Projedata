package com.autoflex.productioncontrol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.autoflex.productioncontrol.service.RawMaterialService;
import com.autoflex.productioncontrol.dto.RawMaterialDTO;
import com.autoflex.productioncontrol.dto.RawMaterialCreateDTO;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    // --------------------- CREATE ---------------------
    @PostMapping
    public ResponseEntity<RawMaterialDTO> create(@RequestBody RawMaterialCreateDTO dto) {
        RawMaterialDTO saved = rawMaterialService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // --------------------- UPDATE ---------------------
    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialDTO> update(@PathVariable Long id,
                                                 @RequestBody RawMaterialCreateDTO dto) {
        RawMaterialDTO updated = rawMaterialService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // --------------------- READ ALL ---------------------
    @GetMapping
    public ResponseEntity<List<RawMaterialDTO>> findAll() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    // --------------------- READ BY ID ---------------------
    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialDTO> findById(@PathVariable Long id) {
        RawMaterialDTO dto = rawMaterialService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // --------------------- DELETE ---------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
