package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EmployeesModel {

    private int employeeId;
    private String employeeEngId;
    private String employeeNumber;
    private String employeeName;
    private String centerCode;
    private String groupCode;
    private String teamCode;
    private String centerName;
    private String groupName;
    private String teamName;
    private String roleCode;
    private LocalDate resignationDate;
    private String createDateTim;
    private String createUserId;
    private String updateUserId;
    private String updateDateTime;

    private List<String> cutMessage;
}
