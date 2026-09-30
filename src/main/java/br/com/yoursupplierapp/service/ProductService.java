package br.com.yoursupplierapp.service;

import br.com.yoursupplierapp.api.model.ProductRequest;
import br.com.yoursupplierapp.api.model.ProductResponse;

import java.util.List;

public interface ProductService {

    void createProducts(ProductRequest productRequest);

    List<ProductResponse> listProducts();

    ProductResponse findProductById(Long id);

    void updateProductById(Long id, ProductRequest productRequest);

    void deleteById(Long id);
}
