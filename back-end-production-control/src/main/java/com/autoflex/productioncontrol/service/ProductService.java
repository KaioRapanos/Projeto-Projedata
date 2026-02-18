package com.autoflex.productioncontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;
import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.dto.ProductProductionDTO;
import com.autoflex.productioncontrol.dto.ProductResponseDTO;
import com.autoflex.productioncontrol.dto.RawMaterialDTO;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRawMaterialRepository productRawMaterialRepository;
    private final ProductRawMaterialService productRawMaterialService;

    // --------------------- CRUD ---------------------

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        return productRepository.save(existing);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    public void delete(Long productId) {
        // Deleta todos os links primeiro
        productRawMaterialRepository.deleteByProductId(productId);
        // Agora deleta o produto
        productRepository.deleteById(productId);
    }

    // --------------------- Produtos com matérias-primas ---------------------

    public List<ProductResponseDTO> getAllProductsWithRawMaterials() {
        List<Product> products = productRepository.findAll(); // ou JOIN FETCH para otimizar

        return products.stream()
                .map(product -> {
                    // Agrupa matérias-primas por ID e soma quantidade
                    Map<Long, RawMaterialDTO> groupedMaterials = new HashMap<>();

                    for (ProductRawMaterial prm : product.getProductRawMaterials()) {
                        Long rawId = prm.getRawMaterial().getId();
                        RawMaterialDTO existing = groupedMaterials.get(rawId);

                        if (existing != null) {
                            // soma a quantidade
                            int newQty = existing.getQuantity() + prm.getQuantity();
                            groupedMaterials.put(rawId, new RawMaterialDTO(
                                    rawId,
                                    prm.getRawMaterial().getName(),
                                    newQty
                            ));
                        } else {
                            groupedMaterials.put(rawId, new RawMaterialDTO(
                                    rawId,
                                    prm.getRawMaterial().getName(),
                                    prm.getQuantity()
                            ));
                        }
                    }

                    return new ProductResponseDTO(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getQuantity(),
                            new ArrayList<>(groupedMaterials.values())
                    );
                })
                .collect(Collectors.toList());
    }

    // --------------------- Produção sugerida ---------------------

    public List<ProductProductionDTO> getProductionSuggestions() {
        List<Product> products = productRepository.findAll();
        List<ProductProductionDTO> result = new ArrayList<>();

        for (Product product : products) {
            List<ProductRawMaterial> materials = productRawMaterialRepository.findByProductId(product.getId());

            if (materials.isEmpty()) continue;

            int maxQuantity = Integer.MAX_VALUE;

            for (ProductRawMaterial prm : materials) {
                int availableStock = prm.getRawMaterial().getQuantity(); // estoque disponível
                int required = prm.getQuantity(); // quantidade necessária por produto
                int possible = availableStock / required; // quantidade máxima que pode ser produzida

                if (possible < maxQuantity) maxQuantity = possible;
            }

            double totalValue = maxQuantity * product.getPrice();

            result.add(new ProductProductionDTO(
                    product.getId(),
                    product.getName(),
                    maxQuantity,
                    totalValue
            ));
        }

        // Ordena do maior valor total para o menor
        result.sort(Comparator.comparing(ProductProductionDTO::getTotalValue).reversed());

        return result;
    }
}
