package com.cjh.common.shared.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuAuthVO {

    private int menuId;               // 메뉴 ID
    private String menuName;          // 메뉴명
    private int orderBy;              // 정렬 순서
    private int parentMenuId;         // 상위 메뉴 ID
    private String parentMenuName;    // 상위 메뉴 명
    private String icon;              // 아이콘
    private String uri;               // 리소스 위치
    private String authorizationCode; // 권한 코드
    private String createUserId;      // 생성자 ID
    private LocalDateTime createDateTime;      // 생성 날짜

    private List<String> menuNames;
    private List<Integer> menuAuthorizationIds;

    private List<String> createUserIds;

}
