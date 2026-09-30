package br.com.yoursupplierapp.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "warehouses")
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWarehouse;

    private String name;

    private String address;

    private int capacity;

    @OneToMany(mappedBy = "warehouse")
    private List<ProductEntity> products;


}
