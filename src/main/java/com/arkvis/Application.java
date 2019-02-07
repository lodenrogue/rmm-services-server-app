package com.arkvis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket api() {
        Parameter authParam = new ParameterBuilder()
                .name("Authorization")
                .modelRef(new ModelRef("string"))
                .description("Authorization")
                .parameterType("header")
                .required(true)
                .build();

        List<Parameter> buildParams = Collections.singletonList(authParam);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(new HashSet<>(Collections.singletonList("http")))
                .select()
                .paths(not(regex("/error")))
                .paths(not(ant("/actuator/**")))
                .build()
                .globalOperationParameters(buildParams);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RMM REST API")
                .version("1.0")
                .build();
    }

}