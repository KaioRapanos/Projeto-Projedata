package com.autoflex.productioncontrol.service;

import com.autoflex.productioncontrol.dto.ProductProductionDTO;
import com.autoflex.productioncontrol.entity.Product;
import com.autoflex.productioncontrol.entity.ProductRawMaterial;
import com.autoflex.productioncontrol.entity.RawMaterial;
import com.autoflex.productioncontrol.repository.ProductRepository;
import com.autoflex.productioncontrol.repository.ProductRawMaterialRepository;
import com.autoflex.productioncontrol.repository.RawMaterialRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductionServiceTest {

    @Autowired
    private ProductionService productionService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRawMaterialRepository prmRepository;

    @BeforeEach
    void cleanDatabase() {
      prmRepository.deleteAll();
      productRepository.deleteAll();
      rawMaterialRepository.deleteAll();
    }

    @Test
    void shouldCalculateProductionBasedOnStockAndPriority() {

        // 🔹 Criar matéria-prima
        RawMaterial material = new RawMaterial();
        material.setName("Steel");
        material.setQuantity(10);
        material = rawMaterialRepository.save(material);

        // 🔹 Criar produto mais caro
        Product expensive = new Product();
        expensive.setName("Premium Product");
        expensive.setPrice(100.0);
        expensive.setQuantity(0);
        expensive = productRepository.save(expensive);

        // 🔹 Criar produto mais barato
        Product cheap = new Product();
        cheap.setName("Basic Product");
        cheap.setPrice(50.0);
        cheap.setQuantity(0);
        cheap = productRepository.save(cheap);

        // 🔹 Associar matéria-prima
        ProductRawMaterial prm1 = new ProductRawMaterial();
        prm1.setProduct(expensive);
        prm1.setRawMaterial(material);
        prm1.setQuantity(2); // pode produzir 5
        prmRepository.save(prm1);

        ProductRawMaterial prm2 = new ProductRawMaterial();
        prm2.setProduct(cheap);
        prm2.setRawMaterial(material);
        prm2.setQuantity(5); // só produziria 2, mas estoque já foi usado
        prmRepository.save(prm2);

        // 🔹 Executar cálculo
        List<ProductProductionDTO> result = productionService.calculateProduction();

        assertEquals(1, result.size());

        ProductProductionDTO dto = result.get(0);

        assertEquals("Premium Product", dto.getName());
        assertEquals(5, dto.getMaxQuantity());
        assertEquals(500.0, dto.getTotalValue());
    }
}