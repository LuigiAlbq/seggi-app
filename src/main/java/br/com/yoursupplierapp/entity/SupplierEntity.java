package br.com.yoursupplierapp.entity;


import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "suppliers")
public class SupplierEntity extends UserEntity {
}
