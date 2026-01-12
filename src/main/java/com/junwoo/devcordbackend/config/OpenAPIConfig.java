package com.junwoo.devcordbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("데브코드 API")
                        .description("Devcord Application")
                        .version("v0.0.1")
                );
    }
}
