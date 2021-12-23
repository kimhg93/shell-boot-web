package com.boot.shell.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
public class JdbcConfig {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * DataSource 설정
     * <bean id="dataSource"
     *  class="org.springframework.jdbc.datasource.DriverManagerDataSource">
     *
     *  <property name="driverClassName" value="oracle.jdbc.driver.OracleDriver" />
     *  <property name="url" value="jdbc:oracle:thin:@localhost:1521:xe" />
     *  <property name="username" value="intercast" />
     *  <property name="password" value="pass123!" />
     * </bean>
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:@localhost:1521:xe");
        dataSource.setUsername("test");
        dataSource.setPassword("123");
        return dataSource;
    }

    /**
     * TransactionManager설정
     *
     * <bean id="transactionManager"
     *  class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
     *  <property name="dataSource" ref="dataSource"></property>
     * </bean>
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * SqlSessionFactory 설정
     *
     * <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
     *  <property name="dataSource" ref="dataSource" />
     *  <property name="configLocation" value="classpath:mybatis/configuration.xml" />
     * <property name="mapperLocations" value="classpath:mybatis/mappers/** /*.xml" />
     * </bean>
     *
     * @param dataSource
     * @param applicationContext
     * @return
     * @throws IOException
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource,
                                                       ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/configuration.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/mappers/**/*.xml"));
        return factoryBean;
    }


    /**
     * SqlSessionTemplate 설정
     *
     * <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
     *  <constructor-arg ref="sqlSessionFactory" />
     * </bean>
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
