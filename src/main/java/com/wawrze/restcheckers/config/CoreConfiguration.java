package com.wawrze.restcheckers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Errors;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableScheduling
@EnableSwagger2
@Configuration
public class CoreConfiguration implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wawrze.restcheckers"))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // Required by Swagger UI configuration
        registry.addResourceHandler("/lib/**").addResourceLocations("/lib/").setCachePeriod(0);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(0);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(0);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Do nothing because it's not used in project
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Do nothing because it's not used in project
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // Do nothing because it's not used in project
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // Do nothing because it's not used in project
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Do nothing because it's not used in project
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Do nothing because it's not used in project
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Do nothing because it's not used in project
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Do nothing because it's not used in project
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // Do nothing because it's not used in project
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // Do nothing because it's not used in project
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        // Do nothing because it's not used in project
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Do nothing because it's not used in project
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Do nothing because it's not used in project
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // Do nothing because it's not used in project
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // Do nothing because it's not used in project
    }

    @Override
    public Validator getValidator() {
        return new Validator() {
            @Override
            public boolean supports(Class<?> clazz) {
                return false;
            }

            @Override
            public void validate(Object target, Errors errors) {
                // Do nothing because it's not used in project
            }
        };
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return new MessageCodesResolver() {
            @Override
            public String[] resolveMessageCodes(String errorCode, String objectName) {
                return new String[0];
            }

            @Override
            public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class<?> fieldType) {
                return new String[0];
            }
        };
    }

}