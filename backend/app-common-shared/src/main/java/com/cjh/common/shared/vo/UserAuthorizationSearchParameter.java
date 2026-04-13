package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthorizationSearchParameter {
    private String employeeNumber;
    private String employeeName;
    private String userId;
    private String updateUserId;
    private String updateDateTime;
    private String authorizationHistoryType;
    private String activated;
    private String  fromDate;
    private String  toDate;
    private int maxDays;
    private int baseDate;
}
