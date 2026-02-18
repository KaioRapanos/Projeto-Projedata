package com.autoflex.productioncontrol.controller;

import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.service.ProductRawMaterialService;
import com.autoflex.productioncontrol.dto.ProductRawMaterialDTO;

import java.util.List;

@RestController
@RequestMapping("/product-materials")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ProductRawMaterialController {

    private final ProductRawMaterialService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRawMaterial create(@RequestBody ProductRawMaterialDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProductRawMaterial update(@PathVariable Long id, @RequestBody ProductRawMaterial prm) {
        return service.update(id, prm);
    }

    @GetMapping
    public List<ProductRawMaterial> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductRawMaterial findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // ✅ Endpoint que o front precisa
    @GetMapping("/product/{productId}")
    public List<ProductRawMaterial> findByProductId(@PathVariable Long productId) {
        return service.findByProductId(productId);
    }

    @GetMapping("/raw-material/{rawMaterialId}")
    public List<ProductRawMaterial> findByRawMaterialId(@PathVariable Long rawMaterialId) {
        return service.findByRawMaterialId(rawMaterialId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
