package com.cjh.domain.ocr.shared.config;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiAuthenticationSetting {
	private String appkey;
    private String secretkey;
    private String bearer;
    private String mapping;
    private String name;
	private String value;
}
