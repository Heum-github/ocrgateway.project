package com.cjh.base.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "spring.datasource.jndi")
@PropertySource({"classpath:application-${spring.profiles.active}.db.properties"})
@Getter @Setter
public class BaseDatabaseJndiSetting {
    private boolean use;
    private String name;
}
