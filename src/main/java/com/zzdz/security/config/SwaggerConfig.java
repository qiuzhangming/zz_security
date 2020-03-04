package com.zzdz.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * http://127.0.0.1:8080/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket1() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("ALL");
    }

    @Bean
    public Docket docket(Environment environment) {

        boolean flag = environment.acceptsProfiles(Profiles.of("dev", "test"));
        System.out.println(flag);

        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("Authorization").description("Authorization")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(tokenPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("My")
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.zzdz.security.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                ;
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("使用 Swagger2 构建的 RESTful API 测试环境")
                //创建人
                .contact(new Contact("joe", "https://github.com/qiuzhangming", "870508366@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("测试环境，随便折腾。")
                .build();
    }
}