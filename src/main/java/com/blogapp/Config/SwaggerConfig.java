package com.blogapp.Config;

import java.util.Arrays; 
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private ApiKey apiskeys() {

		return new ApiKey("JWT", AUTHORIZATION_HEADER, "Header");

	}

	private List<SecurityReference> SR() {

		AuthorizationScope scope = new AuthorizationScope("Global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }));

	}

	private List<SecurityContext> securityContext() {

		return Arrays.asList(SecurityContext.builder().securityReferences(SR()).build());

	}

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiskeys())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo getInfo() {

		return new ApiInfo("Blog-Api : Backend Courese", "This contains all api related to the blog app", "1.0",
				"Terms of Service", new Contact("Ankush Wadode", "nowebdomain", "ankushwadode154@gmail.com"),
				"License of api", "API License URL", Collections.emptyList());
	};

}
