package test.myblog.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = {"test.myblog.mapper"})//, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisConfig {
	
    @Autowired
    @Qualifier("myblogDatasource")
    private DataSource myblogDs;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(myblogDs);
        Resource[] mapperLocations = (Resource[]) new PathMatchingResourcePatternResolver().getResources("/mappers/*.xml");
        factoryBean.setMapperLocations(mapperLocations);
        return factoryBean.getObject();
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
        return template;
    }

}
