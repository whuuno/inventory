package com.whuuno.inventory.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${cache.inventory.ttl:600}")
    private long inventoryTtl;

    @Value("${cache.user.ttl:1800}")
    private long userTtl;

    @Value("${cache.userdetails.ttl:1800}")
    private long userDetailsTtl;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "inventory-cache",
                "user-cache",
                "userdetails-cache"
        );
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .recordStats();
    }

    @Bean
    public Caffeine<Object, Object> inventoryCaffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(inventoryTtl, TimeUnit.SECONDS)
                .recordStats();
    }

    @Bean
    public Caffeine<Object, Object> userCaffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(200)
                .expireAfterWrite(userTtl, TimeUnit.SECONDS)
                .recordStats();
    }

    @Bean
    public Caffeine<Object, Object> userDetailsCaffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(200)
                .expireAfterWrite(userDetailsTtl, TimeUnit.SECONDS)
                .recordStats();
    }
}