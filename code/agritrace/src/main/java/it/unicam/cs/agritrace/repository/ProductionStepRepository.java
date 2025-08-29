package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductionStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionStepRepository extends JpaRepository<ProductionStep, Integer> {
    ProductionStep findProductionStepByInputProductAndOutputProduct(int inputProduct, int outputProduct);
    List<Product> findProdutcsByOutputProduct(int outputProduct);
}