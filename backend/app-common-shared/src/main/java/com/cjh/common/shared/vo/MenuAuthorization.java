package com.cjh.common.shared.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuAuthorization {

    private int menuAuthorizationId;  // 메뉴 권한 ID (PK)
    private int menuId;               // 메뉴 ID
    private String authorizationCode; // 권한 코드
    private String createUserId;      // 생성자 ID
    private LocalDateTime createDateTime; // 생성 날짜

}
