package br.com.compass.pb.MsHistory.framework.helper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8082"));
        configuration.setAllowedMethods(List.of("GET"));
        configuration.setAllowedHeaders(List.of("Content-Type"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
