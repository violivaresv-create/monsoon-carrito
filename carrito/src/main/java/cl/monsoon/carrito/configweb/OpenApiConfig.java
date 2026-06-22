package cl.monsoon.carrito.configweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    
@Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
                        .info(new Info()
                                .title("Monsoon: Microservicio de carrito")
                                .version("0.0.2")
                                .description("microservicio de carrito para la tienda de videojuegos Monsoon, Agrega juegos al carrito y consulta su contenido")
                    );
    }

}
