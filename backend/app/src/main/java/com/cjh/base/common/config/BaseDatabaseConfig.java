package com.cjh.base.common.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.cjh.domain.ocr.core.constants.OcrDomainConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = {
    "com.fdx.common.core.mapper",
    "com.fdx.domain.ocr.core.mapper"
})
public class BaseDatabaseConfig {

    @Autowired
	private BaseDatabaseSetting _setting;

    // 멀티모듈 전체의 mapper/**/*.xml을 모두 스캔하도록 변경
    private static final String MAPPER_LOCATION = "classpath*:mapper/**/**.xml";

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() throws Exception {

        try {

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(_setting.getDriverClassName());
            hikariConfig.setJdbcUrl(_setting.getUrl());
            hikariConfig.setUsername(_setting.getUsername());
            hikariConfig.setPassword(_setting.getPassword());
            
            hikariConfig.setMaximumPoolSize(_setting.getHikari().getMaximumPoolSize());
            hikariConfig.setMinimumIdle(_setting.getHikari().getMinimumIdle());
            hikariConfig.setConnectionTimeout(_setting.getHikari().getConnectionTimeout());
            hikariConfig.setIdleTimeout(_setting.getHikari().getIdleTimeout());
            hikariConfig.setMaxLifetime(_setting.getHikari().getMaxLifetime());
            hikariConfig.setPoolName("collection-pool");
            hikariConfig.setInitializationFailTimeout(-1);

            HikariDataSource ds = new HikariDataSource(hikariConfig);

            // 여기까지 왔으면 일단 "생성 성공"이므로 true로 올림(원하면)

            return healthWrapped(ds);

        } catch (Exception e) {
            return failingDataSource("Hikari DataSource init failed: " + _setting.getUrl(), e);
        }
    }

    private DataSource failingDataSource(String message, Exception cause) {
        // DB 문제 발생 시 플래그 OFF
        OcrDomainConstants.DB.DATABASE_CONNECT_HEALTH.set(false);

        return new org.springframework.jdbc.datasource.AbstractDataSource() {
            @Override
            public java.sql.Connection getConnection() throws java.sql.SQLException {
                throw new java.sql.SQLException(message, cause);
            }
            @Override
            public java.sql.Connection getConnection(String username, String password) throws java.sql.SQLException {
                throw new java.sql.SQLException(message, cause);
            }
        };
    }

    private DataSource healthWrapped(DataSource delegate) {
        return new org.springframework.jdbc.datasource.DelegatingDataSource(delegate) {
            @Override
            public java.sql.Connection getConnection() throws java.sql.SQLException {
                try {
                    var con = super.getConnection();
                    OcrDomainConstants.DB.DATABASE_CONNECT_HEALTH.set(true);
                    return con;
                } catch (java.sql.SQLException e) {
                    OcrDomainConstants.DB.DATABASE_CONNECT_HEALTH.set(false);
                    throw e;
                }
            }

            @Override
            public java.sql.Connection getConnection(String username, String password) throws java.sql.SQLException {
                try {
                    var con = super.getConnection(username, password);
                    OcrDomainConstants.DB.DATABASE_CONNECT_HEALTH.set(true);
                    return con;
                } catch (java.sql.SQLException e) {
                    OcrDomainConstants.DB.DATABASE_CONNECT_HEALTH.set(false);
                    throw e;
                }
            }

            @Override public <T> T unwrap(Class<T> iface) throws SQLException { return delegate.unwrap(iface); }
            @Override public boolean isWrapperFor(Class<?> iface) throws SQLException { return delegate.isWrapperFor(iface); }
        };
    }

    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Primary
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "batchSqlSessionTemplate")
    public SqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }
}