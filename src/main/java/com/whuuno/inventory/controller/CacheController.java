package com.whuuno.inventory.controller;

import com.whuuno.inventory.service.CacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cache")
@Tag(name = "Cache Management", description = "Cache management and monitoring APIs")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @Operation(
            summary = "Get all cache statistics",
            description = "Retrieve statistics for all configured caches including hit rate, miss rate, and eviction count"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved cache statistics"
            )
    })
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Map<String, Object>>> getAllCacheStats() {
        Map<String, Map<String, Object>> stats = cacheService.getAllCacheStatistics();
        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "Get specific cache statistics",
            description = "Retrieve statistics for a specific cache by name"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved cache statistics"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cache not found"
            )
    })
    @GetMapping("/stats/{cacheName}")
    public ResponseEntity<Map<String, Object>> getCacheStats(
            @Parameter(description = "Name of the cache", required = true)
            @PathVariable String cacheName) {
        Map<String, Object> stats = cacheService.getCacheStatistics(cacheName);
        return ResponseEntity.ok(stats);
    }

    @Operation(
            summary = "Clear all caches",
            description = "Evict all entries from all configured caches"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All caches cleared successfully"
            )
    })
    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearAllCaches() {
        cacheService.evictAllCaches();
        Map<String, String> response = new HashMap<>();
        response.put("message", "All caches cleared successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Clear specific cache",
            description = "Evict all entries from a specific cache by name"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cache cleared successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cache not found"
            )
    })
    @DeleteMapping("/clear/{cacheName}")
    public ResponseEntity<Map<String, String>> clearCache(
            @Parameter(description = "Name of the cache to clear", required = true)
            @PathVariable String cacheName) {
        cacheService.evictCache(cacheName);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cache '" + cacheName + "' cleared successfully");
        return ResponseEntity.ok(response);
    }
}
