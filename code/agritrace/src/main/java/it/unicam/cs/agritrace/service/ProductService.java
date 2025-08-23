package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /*
    public Product addProduct(Product product) {

        product.setApprovalStatus(ProductApprovalStatus.PENDING);
        return productRepository.save(product);
    }

    public Product approveProduct(Long productId, Long curatorId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setApprovalStatus(ProductApprovalStatus.APPROVED);
        return productRepository.save(product);
    }

    public Product rejectProduct(Long productId, Long curatorId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setApprovalStatus(ProductApprovalStatus.REJECTED);
        return productRepository.save(product);
    }

    public List<Product> getPendingProducts() {
        return productRepository.findByApprovalStatus(ProductApprovalStatus.PENDING);
    }

     */
}
