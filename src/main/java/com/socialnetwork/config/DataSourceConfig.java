package com.socialnetwork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ComponentScan
//@PropertySource(name = "applicationProperties", value = "classpath:application.properties")
public class DataSourceConfig {
    @Autowired
    private Environment env;

    @Bean(name="pgDataSource")
    //@Profile("dev")
    @Primary
    public DataSource postgresqlDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(env.getProperty("social.network.datasource.url"));
        dataSourceBuilder.username(env.getProperty("social.network.datasource.username"));
        dataSourceBuilder.password(env.getProperty("social.network.datasource.password"));

        return dataSourceBuilder.build();
    }
}
