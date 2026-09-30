package br.com.yoursupplierapp.controller;

import br.com.yoursupplierapp.api.ProductApi;
import br.com.yoursupplierapp.api.model.ProductRequest;
import br.com.yoursupplierapp.api.model.ProductResponse;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<String> createProduct(ProductRequest productRequest) {
        try {
            productService.createProducts(productRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created with success");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<ProductResponse>> listProducts() {
        return ResponseEntity.ok(productService.listProducts());
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(Long id) {
        try {
            return ResponseEntity.ok(productService.findProductById(id));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<String> updateProductById(Long id, ProductRequest productRequest) {
        try {
            productService.updateProductById(id, productRequest);
            return ResponseEntity.ok("Product updated successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteProductById(Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.ok("Product removed successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting product: " + e.getMessage());
        }
    }
}
