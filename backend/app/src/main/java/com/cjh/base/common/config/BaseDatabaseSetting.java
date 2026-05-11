package com.cjh.base.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.cjh.common.shared.config.HikariSetting;
import com.cjh.common.shared.enums.DatabaseDriverType;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "spring.datasource.base.app")
@Component
@Getter	@Setter
public class BaseDatabaseSetting {
    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private DatabaseDriverType type;
    private boolean encryption;
    private HikariSetting hikari;
}
