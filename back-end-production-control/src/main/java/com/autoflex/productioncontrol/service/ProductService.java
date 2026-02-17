package com.autoflex.productioncontrol.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;
import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.dto.ProductProductionDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public List<Product> findAll() {
        return productRepository.findAll();
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

    // --------------------- Produção sugerida ---------------------
    public List<ProductProductionDTO> getProductionSuggestions() {
        List<Product> products = findAll();
        List<ProductProductionDTO> result = new ArrayList<>();

        for (Product product : products) {
            List<ProductRawMaterial> materials = productRawMaterialRepository.findByProductId(product.getId());

            if (materials.isEmpty()) continue;

            int maxQuantity = Integer.MAX_VALUE;

            for (ProductRawMaterial prm : materials) {
                int availableStock = prm.getRawMaterial().getQuantity(); // use getQuantity()
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