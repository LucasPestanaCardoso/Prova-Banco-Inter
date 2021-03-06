package br.com.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig  extends WebMvcConfigurationSupport {
	
	
	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	    		 .select()                                  
	             .apis(RequestHandlerSelectors.basePackage("br.com.controller"))              
	         .build()  
	        .apiInfo(metaData());

	  }

	
	private ApiInfo metaData() {
		return  new ApiInfo("Api Prova Banco Inter", "Lucas Pestana - lucaspestanaa@gmail.com", "1.0", "urn:tos",
				new Contact("", "" , ""), "", "", new ArrayList<VendorExtension>());
     }
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");
	 
	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
}
