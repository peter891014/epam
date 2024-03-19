package com.epam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * swagger配置
 */
@Configuration
public class SwaggerConfig {

    Boolean swaggerEnabled = true;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)//true
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.***.***"))
                        .paths(PathSelectors.any())
                        .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("****接口文档")
                .contact(new Contact("clc", "http://www.baidu.com", "clc@x.com"))
                .version("1.0")
                .description("基本的一些接口说明")
                .license("Apache 2.0 许可") // 许可
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0") // 许可链接
                .extensions(new ArrayList<>()) // 拓展
                .build();
    }

}

