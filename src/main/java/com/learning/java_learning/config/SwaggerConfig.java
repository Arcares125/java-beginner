package com.learning.java_learning.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@OpenAPIDefinition(info = @Info(title = "Java Learning API", version = "v1"))
public class SwaggerConfig {
} 