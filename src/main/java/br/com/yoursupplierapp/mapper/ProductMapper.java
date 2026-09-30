package br.com.yoursupplierapp.mapper;

import br.com.yoursupplierapp.api.model.ProductRequest;
import br.com.yoursupplierapp.api.model.ProductResponse;
import br.com.yoursupplierapp.entity.OrderEntity;
import br.com.yoursupplierapp.entity.ProductEntity;
import br.com.yoursupplierapp.entity.WarehouseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(ProductRequest request, WarehouseEntity warehouse, OrderEntity order) {
        if (request == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setName(request.getName());
        if (request.getPrice() != null) {
            entity.setPrice(request.getPrice());
        }
        entity.setWarehouse(warehouse);
        entity.setOrder(order);
        return entity;
    }

    public ProductResponse toResponse(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        ProductResponse response = new ProductResponse();
        response.setIdProduct(entity.getIdProduct());
        response.setName(entity.getName());
        response.setPrice(entity.getPrice());
        if (entity.getWarehouse() != null) {
            response.setWarehouseId(entity.getWarehouse().getIdWarehouse());
        }
        if (entity.getOrder() != null) {
            response.setOrderId(entity.getOrder().getIdOrder());
        }
        return response;
    }
}
