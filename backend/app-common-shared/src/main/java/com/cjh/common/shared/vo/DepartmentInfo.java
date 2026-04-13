package com.cjh.common.shared.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentInfo {
    private String departmentId;                // 부서 ID
    private String departmentName;              // 부서명
    private String departmentType;              // 부서 타입
    private int departmentLevel;                // 부서 레벨
    private String parentDepartmentId;          // 상위 부서 ID
    private int orderBy;                      // 정렬 순서
    private Character useYn;                    // 사용 여부 ('Y' 또는 'N')
    private String remark;                      // 설명
    private String createUserId;                // 등록자 ID
    private LocalDateTime createDateTime;       // 등록 일시
    private String updateUserId;                // 수정자 ID
    private LocalDateTime updateDateTime;       // 수정 일시
}
