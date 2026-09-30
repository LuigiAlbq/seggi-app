package br.com.yoursupplierapp.controller;

import br.com.yoursupplierapp.api.WarehouseApi;
import br.com.yoursupplierapp.api.model.WarehouseRequest;
import br.com.yoursupplierapp.api.model.WarehouseResponse;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.service.WareHouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WareHouseController implements WarehouseApi {

    private final WareHouseService wareHouseService;

    public WareHouseController(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @Override
    public ResponseEntity<String> createWarehouse(WarehouseRequest warehouseRequest) {
        try {
            wareHouseService.createWareHouse(warehouseRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Warehouse created successfully");
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body("Error creating warehouse: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<WarehouseResponse>> listWarehouses() {
        return ResponseEntity.ok(wareHouseService.listWarehouses());
    }

    @Override
    public ResponseEntity<WarehouseResponse> getWarehouseById(Long id) {
        try {
            return ResponseEntity.ok(wareHouseService.findWarehouseById(id));
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<String> updateWarehouseById(Long id, WarehouseRequest warehouseRequest) {
        try {
            wareHouseService.updateWarehouseById(id, warehouseRequest);
            return ResponseEntity.ok("Warehouse updated successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating warehouse: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteWarehouseById(Long id) {
        try {
            wareHouseService.deleteById(id);
            return ResponseEntity.ok("Warehouse removed successfully");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting warehouse: " + e.getMessage());
        }
    }
}
