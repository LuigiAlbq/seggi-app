package br.com.yoursupplierapp.service;

import br.com.yoursupplierapp.api.model.UserRequest;
import br.com.yoursupplierapp.api.model.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse findUserById(Long idUser);

    List<UserResponse> listUsers();

    void createUser(UserRequest userRequest);

    void updateUserById(Long id, UserRequest userRequest);

    void deleteById(Long id);
}
