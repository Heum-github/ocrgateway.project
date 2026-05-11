package com.cjh.base.common.config;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cjh.domain.ocr.core.constants.OcrDomainConstants;
import com.cjh.domain.ocr.core.interfaces.FileParsingService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
// 1. MapperScan 대신 EnableJpaRepositories를 사용하여 JPA 리포지토리 위치를 지정합니다.
@EnableJpaRepositories(basePackages = {
    "com.fdx.common.core.repository",
    "com.fdx.domain.ocr.core.repository"
})
// 2. Entity 객체들이 위치한 패키지를 스캔하도록 지정합니다.
@EntityScan(basePackages = {
    "com.fdx.common.core.entity",
    "com.fdx.domain.ocr.core.entity"
})
public class BaseDatabaseConfig {

    private BaseDatabaseSetting setting;

    public BaseDatabaseConfig(BaseDatabaseSetting setting) {
        this.setting = setting;
    }

    // XML 로케이션 변수 삭제됨 (MAPPER_LOCATION)

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() throws Exception {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(setting.getDriverClassName());
            hikariConfig.setJdbcUrl(setting.getUrl());
            hikariConfig.setUsername(setting.getUsername());
            hikariConfig.setPassword(setting.getPassword());
            
            hikariConfig.setMaximumPoolSize(setting.getHikari().getMaximumPoolSize());
            hikariConfig.setMinimumIdle(setting.getHikari().getMinimumIdle());
            hikariConfig.setConnectionTimeout(setting.getHikari().getConnectionTimeout());
            hikariConfig.setIdleTimeout(setting.getHikari().getIdleTimeout());
            hikariConfig.setMaxLifetime(setting.getHikari().getMaxLifetime());
            hikariConfig.setPoolName("collection-pool");
            hikariConfig.setInitializationFailTimeout(-1);

            HikariDataSource ds = new HikariDataSource(hikariConfig);

            // DB Health 상태 래핑 로직 유지
            return healthWrapped(ds);

        } catch (Exception e) {
            return failingDataSource("Hikari DataSource init failed: " + setting.getUrl(), e);
        }
    }

    private DataSource failingDataSource(String message, Exception cause) {
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

}