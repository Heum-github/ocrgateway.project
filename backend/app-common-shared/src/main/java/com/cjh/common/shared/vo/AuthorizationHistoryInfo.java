package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthorizationHistoryInfo {
    private int userAuthorizationHistoryId; 
    private String userId;
    private String roleValue;
    private String roleText;
    private String authorizationHistoryType;
    private String workRequestId;
    private String remark;
    private String createUserId;
    private String createDateTime;
}
