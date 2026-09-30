package br.com.yoursupplierapp.mapper;

import br.com.yoursupplierapp.api.model.CardStatus;
import br.com.yoursupplierapp.api.model.UserRequest;
import br.com.yoursupplierapp.api.model.UserResponse;
import br.com.yoursupplierapp.entity.GroupEntity;
import br.com.yoursupplierapp.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequest request, List<GroupEntity> groups) {
        if (request == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setUserName(request.getUserName());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        if (request.getCardStatus() != null) {
            entity.setCardStatus(br.com.yoursupplierapp.utils.CardStatus.valueOf(request.getCardStatus().getValue()));
        }
        if (groups != null) {
            entity.setGroups(groups);
        }
        return entity;
    }

    public UserResponse toResponse(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        response.setIdUser(entity.getIdUser());
        response.setUserName(entity.getUserName());
        response.setEmail(entity.getEmail());
        if (entity.getCardStatus() != null) {
            response.setCardStatus(CardStatus.fromValue(entity.getCardStatus().name()));
        }
        return response;
    }
}
