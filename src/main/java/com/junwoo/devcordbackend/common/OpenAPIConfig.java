package com.junwoo.devcordbackend.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
        String jwtSchemeName = "JwtAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components().addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                .name(jwtSchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"));

        return new OpenAPI()
                .info(new Info().title("데브코드 API")
                        .description("Devcord Application")
                        .version("v0.0.1")
                )
                .components(components);
    }
}
