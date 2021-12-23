package com.boot.shell.config;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.boot.shell.*"})
public class WebConfig implements WebMvcConfigurer {

    // resource 위치 설정 ex)css, js ....
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    // view resolver (tiles, thymeleaf, jsp)

    /**
     * Tiles 설정파일
     *
     * <bean id="titlesConfigurer"
     *  class="org.springframework.web.servlet.view.tiles2.TilesConfigurer">
     *  <property name="definitions">
     *   <list>
     *    <value>/WEB-INF/config/tiles-defs.xml</value>
     *   </list>
     *  </property>
     * </bean>
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configure = new TilesConfigurer();
        configure.setDefinitions("/WEB-INF/config/tiles-config.xml");;
        return configure;
    }
    /**
     * <bean class="org.springframework.web.servlet.view.UrlBasedViewResolver">
     *  <property name="viewClass" value="org.springframework.web.servlet.view.tiles2.TilesView"></property>
     *  <property name="order" value="1"></property>
     * </bean>
     */
    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(999);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ClassLoaderTemplateResolver thymeleafTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateResolver.setOrder(999);
        return templateResolver;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        //viewResolver.setViewNames("*");
        viewResolver.setOrder(1);
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

    // json MessageConverter
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        Jackson2ObjectMapperBuilder builder =
                new Jackson2ObjectMapperBuilder()
                        .indentOutput(true)
                        .dateFormat(new SimpleDateFormat("yyyy-mm-dd"))
                        .modulesToInstall(new ParameterNamesModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(builder.build());
        converter.setSupportedMediaTypes(
                Arrays.asList(
                        new MediaType("application", "json", Charset.forName("UTF-8"))
                )
        );
        return converter;
    }

    // String MessageConverter
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(
                        new MediaType("text","html", Charset.forName("UTF-8"))
                )
        );
        return converter;
    }

    // MessageConverter 적용
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
        converters.add(stringHttpMessageConverter());
    }


//    @Bean
//    public AuthInterceptor authInterceptor() {
//        return new AuthInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new AuthInterceptor())
//                .addPathPatterns("/**/api/**")
//                .excludePathPatterns("/api/users/**");
//    }

    /**
     * <pre>
     * Return Type을 JSON으로 사용하고 싶을 경우 설정을 해줘야 한다.<br />
     * 없을 경우
     * "The resource identified by this request is only capable of generating responses with characteristics not acceptable according to the request "accept" headers."
     * 오류가 발생한다. <br />
     *
     * XML 설정은 아래와 같다.
     * <mvc:annotation-driven  content-negotiation-manager="contentNegotiationManager" />
     * <bean id="contentNegotiationManager" class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
     *      <property name="favorPathExtension" value="false" />
     *      <property name="favorParameter" value="true" />
     *      <property name="mediaTypes" >
     *           <value>
     *                json=application/json
     *                xml=application/xml
     *           </value>
     *      </property>
     * </bean>
     * </pre>
     //
     @Override
     public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
     configurer.favorPathExtension(false)
     .favorParameter(true)
     .defaultContentType(MediaType.APPLICATION_JSON)
     .mediaType("xml", MediaType.APPLICATION_ATOM_XML)
     .mediaType("json", MediaType.APPLICATION_JSON);
     }
     */

    /**
     <mvc:view-controller path="/accessDenied" view-name="error/accessDenied"/>
     @Override
     public void addViewControllers(ViewControllerRegistry registry) {
     registry.addViewController("/accessDenied.htm").setViewName("error/accessDenied");
     }
     */

    /**
     * MultipartResolver 설정
     *
     * <bean id="multipartResolver"
     *  class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
     *  <property name="maxUploadSize" value="200000000" />
     *  <property name="maxInMemorySize" value="100000000" />
     * </bean>


     @Bean
     public MultipartResolver multipartResolver() {
     CommonsMultipartResolver resolver = new CommonsMultipartResolver();
     resolver.setMaxInMemorySize(100000000);
     resolver.setMaxUploadSize(200000000);
     return resolver;
     }
     */
}
