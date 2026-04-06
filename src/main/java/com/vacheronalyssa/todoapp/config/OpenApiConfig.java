package com.vacheronalyssa.todoapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TodoApp API",
                version = "v1",
                description = "API REST pour la gestion des tâches TODO",
                contact = @Contact(name = "TodoApp Team"),
                license = @License(name = "Propriétaire")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Serveur local")
        }
)
public class OpenApiConfig {
}
