package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductionStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductionStepRepository extends JpaRepository<ProductionStep, Integer> {

    // Trova uno Step da prodotto di input e prodotto di output (con oggetti)
    List<ProductionStep> findByInputProductAndOutputProduct(Product inputProduct, Product outputProduct);

    // Trova uno Step da prodotto di input e prodotto di output (con id)
    List<ProductionStep> findByInputProductIdAndOutputProductId(int inputProductId, int outputProductId);

    // Trova una lista di Step in base al prodotto di output
    List<ProductionStep> findByOutputProduct(Product outputProduct);

    // Trova una lista di prodotti in base al prodotto di output
    @Query("SELECT ps.inputProduct FROM ProductionStep ps WHERE ps.outputProduct = :outputProduct")
    List<Product> findInputsByOutputProduct(@Param("outputProduct") Product outputProduct);

    @Query("SELECT ps.inputProduct FROM ProductionStep  ps WHERE ps.outputProduct.id = :outputProductId")
    List<Product> findByOutputProductId(int outputProductId);

}