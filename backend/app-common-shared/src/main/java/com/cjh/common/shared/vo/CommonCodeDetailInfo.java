package com.cjh.common.shared.vo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonCodeDetailInfo {

    private int commonCodeId;
    private int commonCodeDetailId;
    private String code;
    private String codeText;
    private String codeValue; 
    private String useYN;
    private int orderBy;
    private String remark;
    private String option1;
    private String option2;
    private String option3;
	private String createUserId;
	private String createDateTime;
	private String changeUserId;
	private String changeDateTime;

}
