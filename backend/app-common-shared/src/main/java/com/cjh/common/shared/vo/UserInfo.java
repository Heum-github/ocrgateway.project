package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

    private int id;
    private String userName;
    private String userId;

    private String userRole;
    private String userBizCode;
    private String userBizName;

    private String operatorId;
    private String office;
    private String departmentId;
    private String departmentName;
    private String departmentIdL1;
    private String departmentNameL1;

    private String userRoleName;
    
    private String officeName;
    private String officeNumber;   
    private String contactNumber;
    private String faxNumber;
    private String emailAddress;

    private String token;
    private char activated;
    private String expireTime;
    private String effectiveDate;
    
    private String userPassword;
    private String previousPassword;
    private int passwordErrorCount;
    
    private String ipAddress;
    
    private String lastLoginDate;
    private String lastPasswordChangeDate;
    private String loginBySSO;

    private String lastLoginDateTime;
    private String createDateTime;
    private String updateDateTime;
    private String createUserId;
    private String updateUserId;

    private char approved;
    private String approvedDateTime;

    private String[] departmentIds;
}
