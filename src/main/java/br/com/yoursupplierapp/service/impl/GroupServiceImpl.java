package br.com.yoursupplierapp.service.impl;

import br.com.yoursupplierapp.api.model.GroupRequest;
import br.com.yoursupplierapp.entity.GroupEntity;
import br.com.yoursupplierapp.entity.RoleEntity;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.mapper.GroupMapper;
import br.com.yoursupplierapp.repository.GroupRepository;
import br.com.yoursupplierapp.repository.RoleRepository;
import br.com.yoursupplierapp.service.GroupService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository groupRepository, RoleRepository roleRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public void createGroup(GroupRequest groupRequest) {
        if (!StringUtils.hasText(groupRequest.getGroupName())) {
            throw new IllegalArgumentException("O nome do grupo não pode ser nulo ou em branco");
        }

        if (groupRepository.findGroupByGroupName(groupRequest.getGroupName()).isPresent()) {
            throw new BusinessException("Group name: " + groupRequest.getGroupName() + " already registered in the system!");
        }

        try {
            List<RoleEntity> roles = null;
            if (groupRequest.getRoleIds() != null && !groupRequest.getRoleIds().isEmpty()) {
                roles = roleRepository.findAllById(groupRequest.getRoleIds());
            }

            GroupEntity groupEntity = groupMapper.toEntity(groupRequest, roles);
            groupRepository.save(groupEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao criar grupo: " + e.getMessage());
        }
    }
}
