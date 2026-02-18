package com.autoflex.productioncontrol.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    // Criar nova associação
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRawMaterialDTO create(@RequestBody ProductRawMaterialDTO dto) {
        ProductRawMaterial prm = service.create(dto);
        return new ProductRawMaterialDTO(
                prm.getProduct().getId(),
                prm.getRawMaterial().getId(),
                prm.getQuantity(),
                new ProductRawMaterialDTO.RawMaterialNameDTO(prm.getRawMaterial().getName())
        );
    }

    // Atualizar associação existente
    @PutMapping("/{id}")
    public ProductRawMaterialDTO update(@PathVariable Long id, @RequestBody ProductRawMaterialDTO dto) {
        ProductRawMaterial prm = service.update(id, dto);
        return new ProductRawMaterialDTO(
                prm.getProduct().getId(),
                prm.getRawMaterial().getId(),
                prm.getQuantity(),
                new ProductRawMaterialDTO.RawMaterialNameDTO(prm.getRawMaterial().getName())
        );
    }

    // Buscar todas as associações
    @GetMapping
    public List<ProductRawMaterialDTO> findAll() {
        return service.findAll().stream()
                .map(prm -> new ProductRawMaterialDTO(
                        prm.getProduct().getId(),
                        prm.getRawMaterial().getId(),
                        prm.getQuantity(),
                        new ProductRawMaterialDTO.RawMaterialNameDTO(prm.getRawMaterial().getName())
                ))
                .toList();
    }

    // Buscar associação por ID
    @GetMapping("/{id}")
    public ProductRawMaterialDTO findById(@PathVariable Long id) {
        ProductRawMaterial prm = service.findById(id);
        return new ProductRawMaterialDTO(
                prm.getProduct().getId(),
                prm.getRawMaterial().getId(),
                prm.getQuantity(),
                new ProductRawMaterialDTO.RawMaterialNameDTO(prm.getRawMaterial().getName())
        );
    }

    // Buscar todas as matérias-primas de um produto
    @GetMapping("/product/{productId}")
    public List<ProductRawMaterialDTO> findByProductId(@PathVariable Long productId) {
        return service.findByProductId(productId).stream()
                .map(prm -> new ProductRawMaterialDTO(
                        prm.getProduct().getId(),
                        prm.getRawMaterial().getId(),
                        prm.getQuantity(),
                        new ProductRawMaterialDTO.RawMaterialNameDTO(prm.getRawMaterial().getName())
                ))
                .toList();
    }

    // Buscar todos os produtos de uma matéria-prima
    @GetMapping("/raw-material/{rawMaterialId}")
    public List<ProductRawMaterialDTO> findByRawMaterialId(@PathVariable Long rawMaterialId) {
        return service.findByRawMaterialId(rawMaterialId).stream()
                .map(prm -> new ProductRawMaterialDTO(
                        prm.getProduct().getId(),
                        prm.getRawMaterial().getId(),
                        prm.getQuantity(),
                        new ProductRawMaterialDTO.RawMaterialNameDTO(prm.getRawMaterial().getName())
                ))
                .toList();
    }

    // Deletar associação
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
