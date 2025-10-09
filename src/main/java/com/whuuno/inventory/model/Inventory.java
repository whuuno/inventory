package com.whuuno.inventory.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
@Schema(description = "Inventory entity representing a product in the inventory system")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @Schema(description = "Unique identifier of the product", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 255)
    @Schema(description = "Name of the product", example = "Laptop", required = true)
    private String pdtName;

    @Column(name = "quantity", nullable = false)
    @Schema(description = "Available quantity of the product", example = "50", required = true)
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

    public Long getId() {
        return id;
    }

    public String getPdtName() {
        return pdtName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPdtName(String pdtName) {
        this.pdtName = pdtName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "productId=" + id +
                ", productName='" + pdtName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
