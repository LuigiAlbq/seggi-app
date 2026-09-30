package br.com.yoursupplierapp.service.impl;

import br.com.yoursupplierapp.api.model.RoleRequest;
import br.com.yoursupplierapp.api.model.RoleResponse;
import br.com.yoursupplierapp.entity.RoleEntity;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.mapper.RoleMapper;
import br.com.yoursupplierapp.repository.RoleRepository;
import br.com.yoursupplierapp.service.RoleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public void createRole(RoleRequest roleRequest) {
        if (!StringUtils.hasText(roleRequest.getRoleName())) {
            throw new IllegalArgumentException("O nome da role não pode ser nulo ou em branco");
        }
        if (roleRepository.findRoleByRoleName(roleRequest.getRoleName()).isPresent()) {
            throw new BusinessException("Role name: " + roleRequest.getRoleName() + " already registered in the system!");
        }

        try {
            RoleEntity roleEntity = roleMapper.toEntity(roleRequest);
            roleRepository.save(roleEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao criar role: " + e.getMessage());
        }
    }

    @Override
    public List<RoleResponse> listRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponse findRoleById(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Role id number: " + id + " not found in system!"));
        return roleMapper.toResponse(role);
    }

    @Override
    public void updateRoleById(Long id, RoleRequest roleRequest) {
        RoleEntity existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Role id number: " + id + " not found in system!"));

        if (StringUtils.hasText(roleRequest.getRoleName())) {
            existingRole.setRoleName(roleRequest.getRoleName());
        }
        roleRepository.save(existingRole);
    }

    @Override
    public void deleteById(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Role with number ID: " + id + " not found in system!"));
        roleRepository.delete(role);
    }
}
