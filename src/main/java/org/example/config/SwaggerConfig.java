package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Le dice a Spring que esta clase contiene configuraciones
public class SwaggerConfig {

    @Bean // @Bean indica que el resultado de este método debe ser registrado como un bean en el contexto de Spring
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mutant Detector API")
                        .version("1.0.0")
                        .description("API REST para detectar si un humano es mutante basado en su secuencia de ADN. " +
                                "Desarrollado como examen técnico para MercadoLibre."));
    }
}