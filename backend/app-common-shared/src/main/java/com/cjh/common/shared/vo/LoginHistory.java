package com.cjh.common.shared.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginHistory  extends CommonBodyInfo {
    private long id;    
    private String userId;
    private String type;
    private String ipAddress;
    private LocalDateTime createDateTime;
    private String userName;
    private String departmentName;
}
