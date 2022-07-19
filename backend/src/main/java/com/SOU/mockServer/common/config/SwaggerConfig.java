package com.SOU.mockServer.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
            .title("SOU mock server")
            .version(springdocVersion)
            .description("mock server API specification");

        return new OpenAPI()
            .components(new Components())
            .info(info);

    }

//    @Bean
//    public Docket api2() {
//        return new Docket(DocumentationType.OAS_30)
//            .useDefaultResponseMessages(false)
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.SOU.mockServer.external.controller"))
//            .paths(PathSelectors.any())
//            .build()
//            .apiInfo(new ApiInfoBuilder().title("TCP mock server Swagger")
//                .description("specification")
//                .version("1.0")
//                .build());
//    }
}
