package br.com.yoursupplierapp.service.impl;

import br.com.yoursupplierapp.api.model.UserRequest;
import br.com.yoursupplierapp.api.model.UserResponse;
import br.com.yoursupplierapp.entity.GroupEntity;
import br.com.yoursupplierapp.entity.UserEntity;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.mapper.UserMapper;
import br.com.yoursupplierapp.repository.GroupRepository;
import br.com.yoursupplierapp.repository.UserRepository;
import br.com.yoursupplierapp.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse findUserById(Long idUser) {
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(() -> new BusinessException("User id number: " + idUser + " not found in system!"));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void createUser(UserRequest userRequest) {
        if (userRequest.getEmail() != null && userRepository.findUserNameByEmail(userRequest.getEmail()).isPresent()) {
            throw new BusinessException("User email: " + userRequest.getEmail() + " already registered in the system!");
        }

        try {
            List<GroupEntity> groups = null;
            if (userRequest.getGroupIds() != null && !userRequest.getGroupIds().isEmpty()) {
                groups = groupRepository.findAllById(userRequest.getGroupIds());
            }
            UserEntity userEntity = userMapper.toEntity(userRequest, groups);
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @Override
    public void updateUserById(Long id, UserRequest userRequest) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User id number: " + id + " not found in system!"));

        if (StringUtils.hasText(userRequest.getUserName())) {
            user.setUserName(userRequest.getUserName());
        }
        if (StringUtils.hasText(userRequest.getEmail())) {
            user.setEmail(userRequest.getEmail());
        }
        if (StringUtils.hasText(userRequest.getPassword())) {
            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getCardStatus() != null) {
            user.setCardStatus(br.com.yoursupplierapp.utils.CardStatus.valueOf(userRequest.getCardStatus().getValue()));
        }
        if (userRequest.getGroupIds() != null) {
            List<GroupEntity> groups = groupRepository.findAllById(userRequest.getGroupIds());
            user.setGroups(groups);
        }
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User id number: " + id + " not found in system!"));
        userRepository.delete(user);
    }
}
