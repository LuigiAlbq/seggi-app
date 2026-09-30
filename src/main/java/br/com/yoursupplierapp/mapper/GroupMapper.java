package br.com.yoursupplierapp.mapper;

import br.com.yoursupplierapp.api.model.GroupRequest;
import br.com.yoursupplierapp.entity.GroupEntity;
import br.com.yoursupplierapp.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupMapper {

    public GroupEntity toEntity(GroupRequest request, List<RoleEntity> roles) {
        if (request == null) {
            return null;
        }
        GroupEntity entity = new GroupEntity();
        entity.setGroupName(request.getGroupName());
        if (roles != null) {
            entity.setRoles(roles);
        }
        return entity;
    }
}
