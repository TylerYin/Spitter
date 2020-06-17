package com.spitter.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.spitter.security.encoder.MD5Encoder;

/**
 * @author Tyler Yin
 */

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
public class DataConfig implements TransactionManagementConfigurer {

    @Autowired
    private Environment env;

    //基于JDBC驱动的数据源，没有进行池化的管理

    /**
     * @Bean public DataSource dataSource() {
     * DriverManagerDataSource dataSource = new DriverManagerDataSource();
     * dataSource.setDriverClassName("com.mysql.jdbc.Driver");
     * dataSource.setUsername("yjfruby");
     * dataSource.setPassword("yjf090214");
     * dataSource.setUrl("jdbc:mysql://localhost:3306/Spitter?characterEncoding=utf-8&useSSL=true");
     * return dataSource;
     * }
     **/

    // 基于数据源连接池
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        dataSource.setInitialSize(3);
        dataSource.setMaxTotal(5);
        dataSource.setUrl("jdbc:mysql://" + env.getProperty("ip") + ":" + env.getProperty("port") + "/" + env.getProperty("database") + "?characterEncoding=utf-8&useSSL=true");
        return dataSource;
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean());
        return transactionManager;
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(name = "md5Encoder")
    public MD5Encoder md5Encoder() {
        return new MD5Encoder();
    }

    @Bean
    public SessionFactory sessionFactoryBean() {
        try {
            LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
            lsfb.setPackagesToScan("com.spitter.orm.domain");
            Properties props = new Properties();
            props.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
            props.put("hibernate.show_sql", "true");
            props.put("hibernate.format_sql", "true");

            /**
             *
             其实这个hibernate.hbm2ddl.auto参数的作用主要用于：自动创建|更新|验证数据库表结构。如果不是此方面的需求建议set value="none"。
             create：
             每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
             create-drop ：
             每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
             update：
             最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），以后加载hibernate时根据 model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
             validate ：
             每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。
             */

            //props.put("hibernate.hbm2ddl.auto", "create");
            lsfb.setHibernateProperties(props);
            lsfb.setDataSource(dataSource());
            lsfb.afterPropertiesSet();
            return lsfb.getObject();
        } catch (IOException e) {
            return null;
        }
    }
}
