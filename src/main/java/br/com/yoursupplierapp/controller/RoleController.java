package br.com.yoursupplierapp.controller;

import br.com.yoursupplierapp.api.RoleApi;
import br.com.yoursupplierapp.api.model.RoleRequest;
import br.com.yoursupplierapp.api.model.RoleResponse;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController implements RoleApi {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<Void> createRole(RoleRequest roleRequest) {
        try {
            roleService.createRole(roleRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<RoleResponse>> listRoles() {
        return ResponseEntity.ok(roleService.listRoles());
    }

    @Override
    public ResponseEntity<RoleResponse> getRoleById(Long id) {
        try {
            return ResponseEntity.ok(roleService.findRoleById(id));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<String> updateRoleById(Long id, RoleRequest roleRequest) {
        try {
            roleService.updateRoleById(id, roleRequest);
            return ResponseEntity.ok("Role updated successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating role: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteRoleById(Long id) {
        try {
            roleService.deleteById(id);
            return ResponseEntity.ok("Role removed successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar role: " + e.getMessage());
        }
    }
}
