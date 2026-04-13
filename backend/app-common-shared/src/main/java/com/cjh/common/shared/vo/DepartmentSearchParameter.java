package com.cjh.common.shared.vo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DepartmentSearchParameter {
    // 최상위 부서 ID
    private String topDepartmentId;
    // 부서 ID
    private String departmentId;
}
