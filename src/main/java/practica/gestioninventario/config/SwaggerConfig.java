package practica.gestioninventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI inventarioApi() {
        return new OpenAPI().info(new Info()
                .title("GestionInventario - API REST")
                .version("1.0")
                .description("CRUD de productos con Java 21, Spring Boot y PostgreSQL"));
    }
}
