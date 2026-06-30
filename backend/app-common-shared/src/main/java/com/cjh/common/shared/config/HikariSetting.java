package com.cjh.common.shared.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@ConfigurationProperties(prefix = "spring.datasource.base.app")
@Component
@Getter	@Setter
public class HikariSetting {
    private int connectionTimeout;
    private int maximumPoolSize;
    private int minimumIdle;
    private int idleTimeout;
    private int maxLifetime;
    private String poolName;
}
