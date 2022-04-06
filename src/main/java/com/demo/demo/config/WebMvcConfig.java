package com.demo.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Configuration

public class WebMvcConfig implements WebMvcConfigurer {

    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tashkent"));

        System.out.println("Date in UTC: " + new Date().toString());
    }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Content-type:application/json","multipart/form-data");
    }



  @Bean(name = "messageSource")
        public MessageSource getMessageResource()  {
            ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
            messageResource.setBasename("classpath:i18n/messages");
            messageResource.setDefaultEncoding("UTF-8");
            return messageResource;
        }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageResource = new ResourceBundleMessageSource();
        messageResource.setBasename("classpath:i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }

   /* @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
       // CookieLocaleResolver resolver = new CookieLocaleResolver();
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");


        registry.addInterceptor(localeInterceptor);
    }*/
}
