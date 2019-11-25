package top.imuster.domain.config;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @ClassName: MybatisConfig
 * @Description:
 * @author: hmr
 * @date: 2019/11/25 10:32
 */
@Configuration
public class MybatisConfig {

    @Autowired
    DataSource druidDataSource;

    /**
     * @Description:
     * @Author: hmr
     * @Date: 2019/11/25 10:59
     * @param sqlSessionFactory
     * @reture: org.mybatis.spring.SqlSessionTemplate
     **/
    @Bean("sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSource);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * @Description: 创建一个用于批量操作的SqlSessionTemplate
     * @Author: hmr
     * @Date: 2019/11/25 10:59
     * @param sqlSessionFactory
     * @reture: org.mybatis.spring.SqlSessionTemplate
     **/
    @Bean("batchSqlSessionTemplate")
    public SqlSessionTemplate getBatchSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

}
