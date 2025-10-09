package com.whuuno.inventory.controller;

import com.whuuno.inventory.model.Inventory;
import com.whuuno.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Inventory Management APIs")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    /**
     * Get an inventory item by ID
     * GET /api/inventory/{id}
     */
    @Operation(
            summary = "Get inventory item by ID",
            description = "Retrieves a specific inventory item by its product ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory item found",
                    content = @Content(schema = @Schema(implementation = Inventory.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory item not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventory(
            @Parameter(description = "ID of the inventory item to retrieve", required = true)
            @PathVariable Long id){
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }


    /**
     * Get all inventory items
     * GET /api/inventory
     */
    @Operation(
            summary = "Get all inventory items",
            description = "Retrieves a list of all inventory items in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of inventory items retrieved successfully"
            )
    })
    @GetMapping
    public ResponseEntity<List<Inventory> > getAllInventory(){
        List<Inventory> newInventory = inventoryService.getAllInventory();
        return ResponseEntity.ok(newInventory);
    }


    /**
     * Create a new inventory item
     * POST /api/inventory
     */
    @Operation(
            summary = "Create a new inventory item",
            description = "Creates a new inventory item with product name and quantity"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Inventory item created successfully",
                    content = @Content(schema = @Schema(implementation = Inventory.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            )
    })
    @PostMapping
    public ResponseEntity<Inventory> createInventory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Inventory item to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Inventory.class)))
            @RequestBody @Valid Inventory inventory){
        Inventory createdInventory = inventoryService.createInventory(inventory);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }


    /**
     * Update an inventory item
     * PUT /api/inventory/{id}
     */
    @Operation(
            summary = "Update an inventory item",
            description = "Updates an existing inventory item with new product name and quantity"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory item updated successfully",
                    content = @Content(schema = @Schema(implementation = Inventory.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory item not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(
            @Parameter(description = "ID of the inventory item to update", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated inventory item data",
            required = true,
            content = @Content(schema = @Schema(implementation = Inventory.class)))
            @RequestBody @Valid Inventory inventory){
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);
        return ResponseEntity.ok(updatedInventory);
    }


    /**
     * Delete an inventory item
     * DELETE /api/inventory/{id}
     */
    @Operation(
            summary = "Delete an inventory item",
            description = "Deletes an inventory item from the system by its product ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Inventory item deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Inventory item not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(
            @Parameter(description = "ID of the inventory item to delete", required = true)
            @PathVariable Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}