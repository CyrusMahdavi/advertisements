package ads.Config;


import java.net.URL;

import org.springframework.*;

@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.company.project"})
public class SwaggerConfig
{
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
                .paths(PathSelectors.any())
                .build();
    }
}