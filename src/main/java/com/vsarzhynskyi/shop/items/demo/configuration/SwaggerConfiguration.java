package com.vsarzhynskyi.shop.items.demo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final Optional<BuildProperties> buildProperties;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .packagesToScan("com.vsarzhynskyi.shop.items.demo.controller")
                .pathsToMatch("/shopping-items/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .packagesToScan("com.vsarzhynskyi.shop.items.demo.controller")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .version(buildProperties.map(BuildProperties::getVersion).orElse("UNKNOWN"))
                        .title("Shopping Items Demo Service"));
    }
}
