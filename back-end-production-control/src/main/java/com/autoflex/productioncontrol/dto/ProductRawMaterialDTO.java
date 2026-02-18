package com.autoflex.productioncontrol.dto;

import lombok.Data;

@Data
public class ProductRawMaterialDTO {

    private Long productId;

    private Long rawMaterialId;

    private Integer quantity;
}
