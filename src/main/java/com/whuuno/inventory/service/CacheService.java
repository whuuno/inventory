package com.whuuno.inventory.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void evictAllCaches() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }

    public void evictCache(String cacheName) {
        org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    public Map<String, Object> getCacheStatistics(String cacheName) {
        org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
        Map<String, Object> stats = new HashMap<>();

        if (cache instanceof CaffeineCache) {
            CaffeineCache caffeineCache = (CaffeineCache) cache;
            Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();

            com.github.benmanes.caffeine.cache.stats.CacheStats cacheStats = nativeCache.stats();

            stats.put("cacheName", cacheName);
            stats.put("hitCount", cacheStats.hitCount());
            stats.put("missCount", cacheStats.missCount());
            stats.put("loadSuccessCount", cacheStats.loadSuccessCount());
            stats.put("loadFailureCount", cacheStats.loadFailureCount());
            stats.put("totalLoadTime", cacheStats.totalLoadTime());
            stats.put("evictionCount", cacheStats.evictionCount());
            stats.put("estimatedSize", nativeCache.estimatedSize());

            double hitRate = cacheStats.hitRate();
            stats.put("hitRate", String.format("%.2f%%", hitRate * 100));

            double missRate = cacheStats.missRate();
            stats.put("missRate", String.format("%.2f%%", missRate * 100));
        } else {
            stats.put("cacheName", cacheName);
            stats.put("message", "Statistics not available for this cache type");
        }

        return stats;
    }

    public Map<String, Map<String, Object>> getAllCacheStatistics() {
        Map<String, Map<String, Object>> allStats = new HashMap<>();

        cacheManager.getCacheNames().forEach(cacheName -> {
            allStats.put(cacheName, getCacheStatistics(cacheName));
        });

        return allStats;
    }
}