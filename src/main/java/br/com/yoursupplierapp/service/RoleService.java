package br.com.yoursupplierapp.service;

import br.com.yoursupplierapp.api.model.RoleRequest;
import br.com.yoursupplierapp.api.model.RoleResponse;

import java.util.List;

public interface RoleService {

    void createRole(RoleRequest roleRequest);

    List<RoleResponse> listRoles();

    RoleResponse findRoleById(Long id);

    void updateRoleById(Long id, RoleRequest roleRequest);

    void deleteById(Long id);
}
