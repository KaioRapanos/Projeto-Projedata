package com.autoflex.productioncontrol.service;

import com.autoflex.productioncontrol.dto.ProductProductionDTO;
import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductRepository productRepository;
    private final ProductRawMaterialRepository prmRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public List<ProductProductionDTO> calculateProduction() {

        // 1️⃣ Ordena produtos por maior valor
        List<Product> products = productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .toList();

        // 2️⃣ Copia estoque atual para simulação
        Map<Long, Integer> stock = rawMaterialRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        RawMaterial::getId,
                        RawMaterial::getQuantity
                ));

        List<ProductProductionDTO> result = new ArrayList<>();

        for (Product product : products) {

            List<ProductRawMaterial> relations =
                    prmRepository.findByProductId(product.getId());

            int maxProduction = Integer.MAX_VALUE;

            for (ProductRawMaterial prm : relations) {

                Long rawMaterialId = prm.getRawMaterial().getId();
                Integer available = stock.get(rawMaterialId);

                int possible = available / prm.getQuantity();
                maxProduction = Math.min(maxProduction, possible);
            }

            if (maxProduction > 0 && maxProduction != Integer.MAX_VALUE) {

                // Atualiza estoque simulado
                for (ProductRawMaterial prm : relations) {

                    Long rawMaterialId = prm.getRawMaterial().getId();
                    int updated = stock.get(rawMaterialId)
                            - (prm.getQuantity() * maxProduction);

                    stock.put(rawMaterialId, updated);
                }

                result.add(new ProductProductionDTO(
                        product.getId(),
                        product.getName(),
                        maxProduction,
                        product.getPrice() * maxProduction
                ));
            }
        }

        return result;
    }
}