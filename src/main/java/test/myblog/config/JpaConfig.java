package test.myblog.config;

import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.JpaTransactionManager;



@Configuration
@EnableJpaRepositories(entityManagerFactoryRef="jpaEntityManagerFactory", transactionManagerRef="jpaTransactionManager", basePackages ="test.myblog.batch.repository")
@EnableTransactionManagement
public class JpaConfig {
	
    @Autowired
    @Qualifier("batchDatasource")
    private DataSource batchDataSource;

    @Bean(name ="jpaEntityManager")
    @Primary
    public EntityManager entityManager() {
        return entityManagerFactory().getObject().createEntityManager();
    }

    @Bean(name ="jpaEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        HibernateJpaVendorAdapter japVendor =new HibernateJpaVendorAdapter();
        japVendor.setGenerateDdl(true);
        japVendor.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        japVendor.setShowSql(true);
        LocalContainerEntityManagerFactoryBean entityManagerFactory =new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(batchDataSource);
        entityManagerFactory.setJpaVendorAdapter(japVendor);
        entityManagerFactory.setPackagesToScan("test.myblog.batch.model");
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "false");
        entityManagerFactory.setJpaProperties(properties);
        return entityManagerFactory;
    }

    @Bean(name ="jpaTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
    {
        JpaTransactionManager manager =new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);
        return manager;
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceAnnotationBeanPostProcessor();
    }
}
