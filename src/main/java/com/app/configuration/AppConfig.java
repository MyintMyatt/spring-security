package com.app.configuration;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.app")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@PropertySource("classpath:pagination.properties")
public class AppConfig {


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }



    private Dotenv dotenv;

    public AppConfig() {
        try {
            dotenv = Dotenv.configure().filename(".env").load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static final String DRIVER = "driver";
    private static final String USER_NAME = "db_user_name";
    private static final String DB_URL = "db_url";
    private static final String DB_PW = "db_pw";
    private static final String DIALECT = "hibernate.dialect";
    private static final String DDL = "hibernate.hbm2ddl.auto";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String FORMAT_SQL = "hibernate.format_sql";

    @Bean
    public DataSource dataSource() {

        System.out.println("DB_URL" + dotenv.get(DB_URL));
        System.out.println("DRIVER" + dotenv.get(DRIVER));

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dotenv.get(DB_URL));
        config.setUsername(dotenv.get(USER_NAME));
        config.setPassword(dotenv.get(DB_PW));
        config.setDriverClassName(dotenv.get(DRIVER));

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(3);
        config.setIdleTimeout(600000);
        config.setConnectionTimeout(30000);

        return new HikariDataSource(config);
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(dotenv.get(DB_URL));
//        dataSource.setUsername(dotenv.get(USER_NAME));
//        dataSource.setPassword(dotenv.get(DB_PW));
//        dataSource.setDriverClassName(dotenv.get(DRIVER));
//        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.app.model.entity");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    private Properties hibernateProperties() {

        Properties properties = new Properties();
        properties.setProperty(DIALECT, dotenv.get(DIALECT));
        properties.setProperty(DDL, dotenv.get(DDL));
        properties.setProperty(SHOW_SQL, dotenv.get(SHOW_SQL));
        properties.setProperty(FORMAT_SQL, dotenv.get(FORMAT_SQL));
        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }


}
