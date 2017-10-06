package com.gpsolutions.todolist.config;

import static com.gpsolutions.todolist.config.ResourceServerConfig.RESOURCE_ID;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ImplicitGrant;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.gpsolutions.todolist.controller"))
            .paths(PathSelectors.ant("/api/**"))
            .build()
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts());
    }

    private List<SecurityScheme> securitySchemes() {
        final AuthorizationScope readScope = new AuthorizationScope("read", "read");
        final AuthorizationScope writeScope = new AuthorizationScope("write", "write");

        final GrantType grantType = new ImplicitGrant(
            new LoginEndpoint("http://localhost:8089/oauth/authorize"), "token");

        final SecurityScheme scheme = new OAuth("oauth2scheme",
            Arrays.asList(readScope, writeScope), Collections.singletonList(grantType));

        return Collections.singletonList(scheme);
    }

    private List<SecurityContext> securityContexts() {
        final SecurityReference reference = new SecurityReference("oauth2scheme",
            new AuthorizationScope[]{
                new AuthorizationScope("read", "read"),
                new AuthorizationScope("write", "write")
            });

        return Collections.singletonList(SecurityContext.builder()
            .securityReferences(Collections.singletonList(reference))
            .build());
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
            "swagger-ui",
            "secret",
            RESOURCE_ID,
            "todolist",
            "todo_api_key",
            ApiKeyVehicle.HEADER,
            "todo_api",
            " "
        );
    }

}
