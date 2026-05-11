package com.cjh.base.common.constants;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.cjh.common.shared.constants.CommonSiteConstants;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;

@Configuration
@PropertySource({
        "classpath:application-${spring.profiles.active}.properties",
        "classpath:application-${spring.profiles.active}.api.properties",
        "classpath:application-${spring.profiles.active}.authorize.properties",
})
public class SiteConstantInitializer {
    // @Value("${system.os}")
    // private String os;

    // @Value("${system.ip}")
    // private String ip;

    // @Value("${server.name}")
    // private String name;

    @Value("${server.port}")
    private int port;

    @Value("${spring.profiles.active}")
    private String serverActive;

    @PostConstruct
    public void init() {
        try {

            var localHost = InetAddress.getLocalHost();
            var osName = System.getProperty("os.name");
            var hostName = localHost.getHostName();

            CommonSiteConstants.ENV.IP = localHost.getHostAddress();
            CommonSiteConstants.ENV.PORT = port;
            CommonSiteConstants.ENV.OS = osName;
            CommonSiteConstants.ENV.HOST_NAME = hostName;
            CommonSiteConstants.ENV.SERVER_ACTIVE = serverActive;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            // 예외 발생 시 기본값 설정
            // CommonSiteConstants.ENV.IP = ip;
        }
    }
}
