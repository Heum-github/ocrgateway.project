package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAuthorizationInfo{
    private int employeeId; 
    private String employeeEngId;
    private String employeeNumber; 
    private String employeeName;
    private String centerName;
    private String centerCode;
    private String groupName;
    private String groupCode;
    private String teamName;
    private String teamCode;
    private String resignationDate;
    private String roleCode;
    private String updateUserId;
    private String updateDateTime;
    private String Activated;
    private String fromDate;
    private String toDate;

    private int maxDays;
    private int baseDate;


    private String roleName;
    private String roleValue;
    


}
