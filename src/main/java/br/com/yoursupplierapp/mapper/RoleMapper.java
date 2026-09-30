package br.com.yoursupplierapp.mapper;

import br.com.yoursupplierapp.api.model.RoleRequest;
import br.com.yoursupplierapp.api.model.RoleResponse;
import br.com.yoursupplierapp.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleEntity toEntity(RoleRequest request) {
        if (request == null) {
            return null;
        }
        RoleEntity entity = new RoleEntity();
        entity.setRoleName(request.getRoleName());
        return entity;
    }

    public RoleResponse toResponse(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        RoleResponse response = new RoleResponse();
        response.setIdRole(entity.getIdRole());
        response.setRoleName(entity.getRoleName());
        return response;
    }
}
