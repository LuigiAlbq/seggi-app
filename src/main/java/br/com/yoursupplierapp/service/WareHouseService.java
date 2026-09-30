package br.com.yoursupplierapp.service;

import br.com.yoursupplierapp.api.model.WarehouseRequest;
import br.com.yoursupplierapp.api.model.WarehouseResponse;

import java.util.List;

public interface WareHouseService {

    void createWareHouse(WarehouseRequest warehouseRequest);

    List<WarehouseResponse> listWarehouses();

    WarehouseResponse findWarehouseById(Long id);

    void updateWarehouseById(Long id, WarehouseRequest warehouseRequest);

    void deleteById(Long id);
}
