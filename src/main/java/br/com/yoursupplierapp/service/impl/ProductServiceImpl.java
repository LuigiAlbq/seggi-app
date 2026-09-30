package br.com.yoursupplierapp.service.impl;

import br.com.yoursupplierapp.api.model.ProductRequest;
import br.com.yoursupplierapp.api.model.ProductResponse;
import br.com.yoursupplierapp.entity.ProductEntity;
import br.com.yoursupplierapp.entity.WarehouseEntity;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.mapper.ProductMapper;
import br.com.yoursupplierapp.repository.ProductRepository;
import br.com.yoursupplierapp.repository.WareHouseRepository;
import br.com.yoursupplierapp.service.ProductService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static br.com.yoursupplierapp.utils.ConstantUtils.DUPLICATED_USER;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, WareHouseRepository wareHouseRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.productMapper = productMapper;
    }

    @Override
    public void createProducts(ProductRequest productRequest) {
        try {
            WarehouseEntity warehouse = null;
            if (productRequest.getWarehouseId() != null) {
                warehouse = wareHouseRepository.findById(productRequest.getWarehouseId()).orElse(null);
            }

            ProductEntity productEntity = productMapper.toEntity(productRequest, warehouse, null);
            productRepository.save(productEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(DUPLICATED_USER);
        }
    }

    @Override
    public List<ProductResponse> listProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse findProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product with ID number: " + id + " was not found in the system!"));
        return productMapper.toResponse(product);
    }

    @Override
    public void updateProductById(Long id, ProductRequest productRequest) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product id number: " + id + " not found in system!"));

        if (StringUtils.hasText(productRequest.getName())) {
            existingProduct.setName(productRequest.getName());
        }
        if (productRequest.getPrice() != null) {
            existingProduct.setPrice(productRequest.getPrice());
        }
        if (productRequest.getWarehouseId() != null) {
            WarehouseEntity warehouse = wareHouseRepository.findById(productRequest.getWarehouseId()).orElse(null);
            existingProduct.setWarehouse(warehouse);
        }
        productRepository.save(existingProduct);
    }

    @Override
    public void deleteById(Long id) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Product id number: " + id + " not found in system!"));
        productRepository.delete(existingProduct);
    }
}
