package com.autoflex.productioncontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRawMaterialDTO {

    private Long productId;

    private Long rawMaterialId;

    private Integer quantity;

    private RawMaterialNameDTO rawMaterial; // objeto aninhado

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RawMaterialNameDTO {
        private String name;
    }
}
