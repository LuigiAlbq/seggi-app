package br.com.yoursupplierapp.service.impl;

import br.com.yoursupplierapp.api.model.WarehouseRequest;
import br.com.yoursupplierapp.api.model.WarehouseResponse;
import br.com.yoursupplierapp.entity.WarehouseEntity;
import br.com.yoursupplierapp.exception.BusinessException;
import br.com.yoursupplierapp.mapper.WarehouseMapper;
import br.com.yoursupplierapp.repository.WareHouseRepository;
import br.com.yoursupplierapp.service.WareHouseService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {

    private final WareHouseRepository wareHouseRepository;
    private final WarehouseMapper warehouseMapper;

    public WareHouseServiceImpl(WareHouseRepository wareHouseRepository, WarehouseMapper warehouseMapper) {
        this.wareHouseRepository = wareHouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    public void createWareHouse(WarehouseRequest warehouseRequest) {
        try {
            WarehouseEntity warehouseEntity = warehouseMapper.toEntity(warehouseRequest);
            wareHouseRepository.save(warehouseEntity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Cannot create warehouse: " + e.getMessage());
        }
    }

    @Override
    public List<WarehouseResponse> listWarehouses() {
        return wareHouseRepository.findAll().stream()
                .map(warehouseMapper::toResponse)
                .toList();
    }

    @Override
    public WarehouseResponse findWarehouseById(Long id) {
        WarehouseEntity warehouseEntity = wareHouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Warehouse with id: " + id + " was not found in the system!"));
        return warehouseMapper.toResponse(warehouseEntity);
    }

    @Override
    public void updateWarehouseById(Long id, WarehouseRequest warehouseRequest) {
        WarehouseEntity warehouseEntity = wareHouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Warehouse with id: " + id + " not found in system!"));

        if (StringUtils.hasText(warehouseRequest.getName())) {
            warehouseEntity.setName(warehouseRequest.getName());
        }
        if (warehouseRequest.getCapacity() != null) {
            warehouseEntity.setCapacity(warehouseRequest.getCapacity());
        }
        if (StringUtils.hasText(warehouseRequest.getAddress())) {
            warehouseEntity.setAddress(warehouseRequest.getAddress());
        }
        wareHouseRepository.save(warehouseEntity);
    }

    @Override
    public void deleteById(Long id) {
        WarehouseEntity warehouseEntity = wareHouseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Warehouse with ID: " + id + " not found in system!"));
        wareHouseRepository.delete(warehouseEntity);
    }
}
