package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrganizationModel {

    private Long organizationId;
    private String organizationCode;
    private String organizationName;
    private String openDate = "19000101";;
    private String closeDate= "99990101";;
    private String parentOrganizationCode;
    private String organizationDivision;
    private String createDateTime;
    private String createUserId;
    private String updateUserId;
    private String updateDateTime;

}
