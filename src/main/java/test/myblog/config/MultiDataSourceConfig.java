package test.myblog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
public class MultiDataSourceConfig {
	
	@Autowired
    private Environment env;
 
    @Bean(name="batchDatasource")
    @Primary
    @ConfigurationProperties(prefix="spring.batchmetadata.datasource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create()
           .driverClassName(env.getProperty("spring.batchmetadata.datasource.driver-class-name"))
           .url(env.getProperty("spring.batchmetadata.datasource.url"))
           .username(env.getProperty("spring.batchmetadata.datasource.username"))
           .password(env.getProperty("spring.batchmetadata.datasource.password"))
           .build();
    }

    @Bean(name="myblogDatasource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource myblogDataSource() {
        return DataSourceBuilder.create()
           .driverClassName(env.getProperty("spring.datasource.driver-class-name"))
           .url(env.getProperty("spring.datasource.url"))
           .username(env.getProperty("spring.datasource.username"))
           .password(env.getProperty("spring.datasource.password"))
           .build();
    }
    
    
}
