package com.whuuno.inventory.service;

import com.whuuno.inventory.model.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory createInventory(Inventory inventory);
    Inventory getInventoryById(Long id);
    List<Inventory> getAllInventory();
    Inventory updateInventory(Long id, Inventory inventory);
    void deleteInventory(Long id);
}
