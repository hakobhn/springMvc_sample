package com.sample.config;

import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(MvcConfig.class);

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        FilterRegistration.Dynamic corsFilter = container.addFilter("apiCorsFilter", new CORSFilter());

        corsFilter.setInitParameter("cors.supportedMethods", "GET,POST,HEAD,OPTIONS,PUT, DELETE");
        corsFilter.setInitParameter("cors.supportedHeaders", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified, Authorization");
        corsFilter.setInitParameter("cors.supportedMethods", "GET, HEAD, POST, PUT, DELETE, OPTIONS");
        corsFilter.setInitParameter("cors.supportedHeaders", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        corsFilter.setInitParameter("cors.supportsCredentials ", "false");
        corsFilter.setInitParameter("cors.allowOrigin ", "*");
        corsFilter.addMappingForUrlPatterns(null, false, "/*");

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
    }

 }
