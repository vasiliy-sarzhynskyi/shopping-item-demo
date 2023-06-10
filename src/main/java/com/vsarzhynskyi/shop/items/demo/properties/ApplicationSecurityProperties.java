package com.vsarzhynskyi.shop.items.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("application.security")
public class ApplicationSecurityProperties {

    private RoleCredentials adminRole;


    @Data
    public static class RoleCredentials {

        private String username;
        private String password;

    }

}
