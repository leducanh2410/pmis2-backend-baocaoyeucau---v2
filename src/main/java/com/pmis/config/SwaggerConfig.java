package com.pmis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("Pmis2 BCTC API")
                .version("1.0");
        return new OpenAPI().info(info);
    }
}
