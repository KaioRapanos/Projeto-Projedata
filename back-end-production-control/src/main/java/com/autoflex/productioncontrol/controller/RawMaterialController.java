package com.autoflex.productioncontrol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.service.RawMaterialService;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
@RequiredArgsConstructor
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @PostMapping
    public RawMaterial create(@RequestBody RawMaterial rawMaterial) {
        return rawMaterialService.create(rawMaterial);
    }

    @PutMapping("/{id}")
    public RawMaterial update(@PathVariable Long id, @RequestBody RawMaterial rawMaterial) {
        return rawMaterialService.update(id, rawMaterial);
    }

    @GetMapping
    public List<RawMaterial> findAll() {
        return rawMaterialService.findAll();
    }

    @GetMapping("/{id}")
    public RawMaterial findById(@PathVariable Long id) {
        return rawMaterialService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
    }
}