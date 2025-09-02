package com.app;

import com.app.configuration.SecurityConfig;
import com.app.configuration.AppConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;


public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class, SecurityConfig.class);
        servletContext.addListener(new ContextLoaderListener(ctx));

//        // ✅ Register Spring Security filter
//        servletContext.addFilter("a",
//                        new DelegatingFilterProxy("springSecurityFilterChain"))
//                .addMappingForUrlPatterns(null, false, "/*");


        servletContext.addFilter("springSecurityFilterChain",
                new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, false, "/*");

        ServletRegistration.Dynamic appServlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
}
