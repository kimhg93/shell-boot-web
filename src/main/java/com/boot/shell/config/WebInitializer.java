package com.boot.shell.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public class WebInitializer implements WebApplicationInitializer {

    private static final String CONFIG_LOCATION = "com.boot.shell.config";
    private static final String MAPPING_URL = "/";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        registerDispatcherServlet(servletContext);
        registerCharacterEncodingFilter(servletContext);
    }

    /**
     *
     * <filter>
     *         <filter-name>CharacterEncodingFilter</filter-name>
     *         <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
     *         <init-param>
     *             <param-name>encoding</param-name>
     *             <param-value>UTF-8</param-value>
     *         </init-param>
     *     </filter>
     *     <filter-mapping>
     *         <filter-name>CharacterEncodingFilter</filter-name>
     *         <url-pattern>/*</url-pattern>
     *     </filter-mapping>
     */
    private void registerCharacterEncodingFilter(ServletContext servletContext) {
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
    }

    /**
     *
     * <servlet>
     *         <servlet-name>appServlet</servlet-name>
     *         <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     *         <init-param>
     *             <param-name>contextConfigLocation</param-name>
     *             <param-value>/WEB-INF/config/dispatcher-servlet.xml</param-value>
     *         </init-param>
     *         <load-on-startup>1</load-on-startup>
     *     </servlet>
     *     <servlet-mapping>
     *         <servlet-name>appServlet</servlet-name>
     *         <url-pattern>*.htm</url-pattern>
     *     </servlet-mapping>
     *
     */
    private void registerDispatcherServlet(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setDisplayName("Intercast");
        context.setConfigLocation(CONFIG_LOCATION);
        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(MAPPING_URL);
    }

}
