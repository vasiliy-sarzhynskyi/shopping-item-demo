package com.vsarzhynskyi.shop.items.demo.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import com.vsarzhynskyi.shop.items.demo.properties.CacheConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
@Slf4j
@EnableConfigurationProperties(CacheConfigurationProperties.class)
public class CacheConfiguration {

    public static final String GET_ALL_SHOPPING_ITEMS_CACHE_KEY = "getAllItems";

    @Bean
    public CacheManager cacheManager(Ticker ticker, CacheConfigurationProperties cacheConfigurationProperties) {
        SimpleCacheManager manager = new SimpleCacheManager();
        List<CaffeineCache> caches = cacheConfigurationProperties.getSpecs().entrySet().stream()
                .map(entry -> buildCache(entry.getKey(),
                        entry.getValue(), ticker))
                .collect(Collectors.toList());
        manager.setCaches(caches);
        return manager;
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

    private CaffeineCache buildCache(String name, CacheConfigurationProperties.CacheSpec cacheSpec, Ticker ticker) {
        log.info("Cache {} specified timeout of {} min, max of {}", name, cacheSpec.getTimeoutSeconds(), cacheSpec.getMax());
        final Caffeine<Object, Object> caffeineBuilder = Caffeine.newBuilder()
                .expireAfterWrite(cacheSpec.getTimeoutSeconds(), TimeUnit.SECONDS)
                .maximumSize(cacheSpec.getMax())
                .ticker(ticker);
        return new CaffeineCache(name, caffeineBuilder.build());
    }

}
