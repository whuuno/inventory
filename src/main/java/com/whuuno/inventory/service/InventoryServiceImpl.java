package com.whuuno.inventory.service;

import com.whuuno.inventory.dao.InventoryDAO;
import com.whuuno.inventory.exception.ResourceNotFoundException;
import com.whuuno.inventory.model.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    InventoryDAO inventoryDAO;

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryDAO.save(inventory);
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryDAO.findById(id).get();
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryDAO.findAll();
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventory) {
        Inventory existingInventory = inventoryDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
        existingInventory.setPdtName(inventory.getPdtName());
        existingInventory.setQuantity(inventory.getQuantity());

        return inventoryDAO.update(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        if(!inventoryDAO.existsById(id)){
            throw new ResourceNotFoundException("Inventory not found with id: " + id);
        }
        inventoryDAO.deleteById(id);
    }
}
