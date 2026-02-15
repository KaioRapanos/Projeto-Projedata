package com.autoflex.productioncontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;
import com.autoflex.productioncontrol.entity.RawMaterial;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    // --------------------- CRUD ---------------------

    public RawMaterial create(RawMaterial rawMaterial) {
        return rawMaterialRepository.save(rawMaterial);
    }

    public RawMaterial update(Long id, RawMaterial rawMaterial) {
        RawMaterial existing = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found"));

        existing.setName(rawMaterial.getName());

        // Aqui usamos o getter correto dependendo do campo na entidade:
        // Se o campo for 'quantity', use existing.setQuantity(...)
        existing.setQuantity(rawMaterial.getQuantity());

        return rawMaterialRepository.save(existing);
    }

    public List<RawMaterial> findAll() {
        return rawMaterialRepository.findAll();
    }

    public RawMaterial findById(Long id) {
        return rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found"));
    }

    public void delete(Long id) {
        rawMaterialRepository.deleteById(id);
    }
}