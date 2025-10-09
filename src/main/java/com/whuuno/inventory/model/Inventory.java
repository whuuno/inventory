package com.whuuno.inventory.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 255)
    private String pdtName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Constructors
    public Inventory() {
    }

    public Inventory(String productName, Integer quantity) {
        this.pdtName = productName;
        this.quantity = quantity;
    }

    public Inventory(Long productId, String productName, Integer quantity) {
        this.id = productId;
        this.pdtName = productName;
        this.quantity = quantity;
    }
}
