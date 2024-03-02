package com.example.switchwonapi.global.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server local = new Server()
                .description("로컬")
                .url("http://localhost:8080");

        return new OpenAPI()
                .info(new Info().title("스위치온 API")
                        .description("스위치온 API 명세")
                        .version("1.0.0")
                )
                .addServersItem(local)
                ;
    }

//    @Bean
//    public GroupedOpenApi searchGroupApi() {
//        return GroupedOpenApi.builder()
//                .group("payment")
//                .pathsToMatch("/api/payment/**")
//                .build();
//    }
}
