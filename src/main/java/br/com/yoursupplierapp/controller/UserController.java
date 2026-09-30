package br.com.yoursupplierapp.controller;

import br.com.yoursupplierapp.api.UserApi;
import br.com.yoursupplierapp.api.model.UserRequest;
import br.com.yoursupplierapp.api.model.UserResponse;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> createUser(UserRequest userRequest) {
        try {
            userService.createUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created with success");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<UserResponse>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(Long id) {
        try {
            return ResponseEntity.ok(userService.findUserById(id));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<String> updateUserById(Long id, UserRequest userRequest) {
        try {
            userService.updateUserById(id, userRequest);
            return ResponseEntity.ok("User updated successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteUserById(Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting user: " + e.getMessage());
        }
    }
}