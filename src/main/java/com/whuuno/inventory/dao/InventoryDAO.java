package com.whuuno.inventory.dao;

import com.whuuno.inventory.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryDAO {

    Inventory save(Inventory inventory);

    Optional<Inventory> findById(Long id);

    List<Inventory> findAll();

    Inventory update(Inventory inventory);

    void deleteById(Long id);

    boolean existsById(Long id);
}