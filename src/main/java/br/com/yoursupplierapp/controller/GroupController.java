package br.com.yoursupplierapp.controller;

import br.com.yoursupplierapp.api.GroupApi;
import br.com.yoursupplierapp.api.model.GroupRequest;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController implements GroupApi {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public ResponseEntity<Void> createGroup(GroupRequest groupRequest) {
        try {
            groupService.createGroup(groupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
