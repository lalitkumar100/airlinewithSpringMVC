package com.crimsonlogic.arilinemanangmentsystem.config;

import com.crimsonlogic.arilinemanangmentsystem.interceptor.AdminJwtInterceptor;
import com.crimsonlogic.arilinemanangmentsystem.interceptor.JwtInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.crimsonlogic.arilinemanangmentsystem")
@MapperScan(value = "com.crimsonlogic.arilinemanangmentsystem.dao", annotationClass = org.apache.ibatis.annotations.Mapper.class)
@EnableJpaRepositories({"com.crimsonlogic.arilinemanangmentsystem.repository", "com.crimsonlogic.arilinemanangmentsystem.dao"})
@PropertySource("classpath:application.properties")
public class AppConfig implements WebMvcConfigurer {

    private final Environment env;
    private final JwtInterceptor jwtInterceptor;
    private final AdminJwtInterceptor adminJwtInterceptor;

    public AppConfig(Environment env, JwtInterceptor jwtInterceptor, AdminJwtInterceptor adminJwtInterceptor) {
        this.env = env;
        this.jwtInterceptor = jwtInterceptor;
        this.adminJwtInterceptor = adminJwtInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. Interceptor for Regular Users & protected web routes
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/v1/user/**", "/bookings/**", "/flights/add")
                .excludePathPatterns("/api/login");

        // 2. Strict Interceptor for Admin Only
        registry.addInterceptor(adminJwtInterceptor)
                .addPathPatterns("/api/v1/admin/**");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/airline_management_system2");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }



    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource());

        em.setPackagesToScan(
                "com.crimsonlogic.arilinemanangmentsystem.model"
        );

        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();

        jpaProperties.setProperty(
                "hibernate.hbm2ddl.auto",
                "none"
        );

        jpaProperties.setProperty(
                "hibernate.dialect",
                "org.hibernate.dialect.MySQL8Dialect"
        );

        jpaProperties.setProperty(
                "hibernate.show_sql",
                "true"
        );

        jpaProperties.setProperty(
                "hibernate.format_sql",
                "true"
        );

        em.setJpaProperties(jpaProperties);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
