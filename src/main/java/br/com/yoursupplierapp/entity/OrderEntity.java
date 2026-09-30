package br.com.yoursupplierapp.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @ManyToOne
    private CustomerEntity customerEntity;

    @ManyToOne
    private SupplierEntity supplierEntity;

    @ManyToOne
    private PromoterEntity promoterEntity;

    @OneToOne(mappedBy = "order")
    private PaymentEntity paymentEntity;

    @OneToMany(mappedBy = "order")
    private List<ProductEntity> products;

}
