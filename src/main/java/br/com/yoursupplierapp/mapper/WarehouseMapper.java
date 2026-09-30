package br.com.yoursupplierapp.mapper;

import br.com.yoursupplierapp.api.model.WarehouseRequest;
import br.com.yoursupplierapp.api.model.WarehouseResponse;
import br.com.yoursupplierapp.entity.WarehouseEntity;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public WarehouseEntity toEntity(WarehouseRequest request) {
        if (request == null) {
            return null;
        }
        WarehouseEntity entity = new WarehouseEntity();
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());
        if (request.getCapacity() != null) {
            entity.setCapacity(request.getCapacity());
        }
        return entity;
    }

    public WarehouseResponse toResponse(WarehouseEntity entity) {
        if (entity == null) {
            return null;
        }
        WarehouseResponse response = new WarehouseResponse();
        response.setIdWarehouse(entity.getIdWarehouse());
        response.setName(entity.getName());
        response.setAddress(entity.getAddress());
        response.setCapacity(entity.getCapacity());
        return response;
    }
}
