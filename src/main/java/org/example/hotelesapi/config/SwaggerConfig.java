package org.example.hotelesapi.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Hoteles API")
                        .description("Servicio de búsqueda de hoteles")
                        .contact(new Contact()
                                .name("Ángel Chicote")
                                .email("angel.chiveg@educa.jcyl.es")
                                .url("https://github.com/AngelChv"))
                        .version("1.0"));
    }
}

