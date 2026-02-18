package com.autoflex.productioncontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.dto.RawMaterialCreateDTO;
import com.autoflex.productioncontrol.dto.RawMaterialDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    // --------------------- CRUD ---------------------

    @Transactional
    public RawMaterialDTO create(RawMaterialCreateDTO dto) {
        validateDTO(dto);

        RawMaterial raw = new RawMaterial();
        raw.setName(dto.getName());
        raw.setQuantity(dto.getQuantity());

        RawMaterial saved = rawMaterialRepository.save(raw);
        return toDTO(saved);
    }

    @Transactional
    public RawMaterialDTO update(Long id, RawMaterialCreateDTO dto) {
        validateDTO(dto);

        RawMaterial existing = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found with id " + id));

        existing.setName(dto.getName());
        existing.setQuantity(dto.getQuantity());

        RawMaterial saved = rawMaterialRepository.save(existing);
        return toDTO(saved);
    }

    public List<RawMaterialDTO> findAll() {
        return rawMaterialRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RawMaterialDTO findById(Long id) {
        RawMaterial raw = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RawMaterial not found with id " + id));
        return toDTO(raw);
    }

    @Transactional
    public void delete(Long id) {
        if (!rawMaterialRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: RawMaterial not found with id " + id);
        }
        rawMaterialRepository.deleteById(id);
    }

    // --------------------- Helper ---------------------

    private RawMaterialDTO toDTO(RawMaterial raw) {
        return new RawMaterialDTO(raw.getId(), raw.getName(), raw.getQuantity());
    }

    private void validateDTO(RawMaterialCreateDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("RawMaterial name cannot be empty");
        }
        if (dto.getQuantity() == null || dto.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
    }
}
