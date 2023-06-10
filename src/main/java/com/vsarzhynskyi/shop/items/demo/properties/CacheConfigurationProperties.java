package com.vsarzhynskyi.shop.items.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("caching")
@Data
public class CacheConfigurationProperties {

    private Map<String, CacheSpec> specs;

    @Data
    public static class CacheSpec {
        private Integer timeoutSeconds;
        private Integer max;
    }

}

