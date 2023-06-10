package com.vsarzhynskyi.shop.items.demo.configuration;

import com.vsarzhynskyi.shop.items.demo.properties.ApplicationSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ApplicationSecurityProperties.class)
public class WebSecurityConfiguration {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String ADMIN_ENDPOINTS_PATH_PATTERN = "/admin/**";
    private static final String SHOPPING_ITEMS_ENDPOINTS_PATH_PATTERN = "/shopping-items/**";
    private static final String ERROR_ENDPOINTS_PATH_PATTERN = "/error";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authorizeHttpRequestsCustomizer) -> authorizeHttpRequestsCustomizer
                        .requestMatchers(ADMIN_ENDPOINTS_PATH_PATTERN).hasRole(ADMIN_ROLE)
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(SHOPPING_ITEMS_ENDPOINTS_PATH_PATTERN, ERROR_ENDPOINTS_PATH_PATTERN);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(ApplicationSecurityProperties applicationSecurityProperties) {
        var adminRole = applicationSecurityProperties.getAdminRole();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(adminRole.getUsername())
                .password(adminRole.getPassword())
                .roles(ADMIN_ROLE)
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
