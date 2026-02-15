package com.autoflex.productioncontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductProductionDTO {
    private Long productId;
    private String name;
    private Integer maxQuantity;
    private Double totalValue;
}