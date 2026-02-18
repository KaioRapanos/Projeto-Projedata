package com.autoflex.productioncontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private List<RawMaterialDTO> rawMaterials;
}
