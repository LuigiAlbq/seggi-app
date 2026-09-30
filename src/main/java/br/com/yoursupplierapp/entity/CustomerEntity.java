package br.com.yoursupplierapp.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity extends UserEntity {

}
