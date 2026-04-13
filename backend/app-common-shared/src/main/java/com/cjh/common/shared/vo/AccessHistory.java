package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessHistory extends CommonBodyInfo {

    private long id;
    private String userId;
    private String userName;
    private String departmentName;
    private String ipAddress;
    private String accessUrl;
    private int accessCount;
    private int actionType;
    private String actionName;
    private String accessCustomerKey;
    private String controllerName;
    private String accessHistoryName;
    private String accessHistoryParameter;
    private String adminYN;
    private String createDateTime;

}
