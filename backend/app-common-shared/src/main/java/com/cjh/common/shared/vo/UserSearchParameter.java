package com.cjh.common.shared.vo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchParameter {
    private String userRole;
    private String userId;
    private String userName;
    private int maxDays;
}