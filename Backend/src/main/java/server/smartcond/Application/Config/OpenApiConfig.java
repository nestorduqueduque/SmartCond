package server.smartcond.Application.Config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "smartCond",
                version = "1.0.0",
                description = "This is de Api to admin condominiums"
        )
)
public class OpenApiConfig {
}
