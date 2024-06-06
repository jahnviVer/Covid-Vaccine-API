package com.lab.api.covidvaccination.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class CovidVaccinationConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Covid Vaccination Details")
                .apiInfo(metaData())
                .select()
                .paths(regex("/covid-vaccination.*"))
                .build();
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Covid Vaccination Details Service - Swagger Configuration")
                .description("\"Swagger Configuration for Vaccination Details Application\"")
                .version("1.0")
                .build();
    }
}
