package com.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;

@EnableWebMvc
@Configuration
@ComponentScan({"com.sample"})
@PropertySource("classpath:config/config.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    Environment env;


    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        // Turn off suffix-based content negotiation
        configurer.favorPathExtension(false);
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

    @Bean(name = "errormessageSource")
    public ReloadableResourceBundleMessageSource getErrorMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:errormessages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

    @Bean(name = "config")
    public ReloadableResourceBundleMessageSource getConfig() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:config/config");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

}
