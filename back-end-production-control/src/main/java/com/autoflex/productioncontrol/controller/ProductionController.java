package com.autoflex.productioncontrol.controller;

import com.autoflex.productioncontrol.dto.ProductProductionDTO;
import com.autoflex.productioncontrol.service.ProductionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/production")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite chamadas de qualquer front-end
@Tag(name = "Production", description = "Endpoints for production suggestions")
public class ProductionController {

    private final ProductionService productionService;

    // Endpoint para sugerir produção
    @Operation(
        summary = "Get production suggestions based on stock",
        description = "Returns the list of products and max quantities that can be produced"
    )
    @GetMapping("/suggestions")
    public List<ProductProductionDTO> getProductionSuggestions() {
        return productionService.calculateProduction();
    }
}