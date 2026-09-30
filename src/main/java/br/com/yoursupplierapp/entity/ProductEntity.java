package br.com.yoursupplierapp.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    //Caracteristicas de produtos e roles exemplos: Pereciveis, não pereciveis, congelados, frios, carnes e condimentos.
    private String name;

    private double price;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private WarehouseEntity warehouse;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;



}
