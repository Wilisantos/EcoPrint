package com.ecoprint.control_center.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenApiConfig {

    private final Environment env;

    public OpenApiConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final String activeProfile = env.getActiveProfiles()[0];
        String serverUrl = "http://localhost:9000";

        // Caso o ambiente seja produção, defina a URL do servidor.
        if ("prod".equalsIgnoreCase(activeProfile)) {
            serverUrl = "https://api.ecoprint.com"; // URL do servidor de produção
        }

        return new OpenAPI()
                .info(new Info()
                        .title("EcoPrint Control Center API")
                        .version("v1.0.0")
                        .description("API para gerenciamento de impressoras e tipos de impressoras"))
                .addServersItem(new Server().url(serverUrl));
    }
}
