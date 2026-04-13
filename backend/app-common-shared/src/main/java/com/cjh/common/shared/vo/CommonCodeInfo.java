package com.cjh.common.shared.vo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonCodeInfo {
	private int commonCodeId;
	private String code;
	private String codeType;
	private String commonCodeName;
	private String useYN;
	private String remark;
	private String createUserId;
	private String createDateTime;
	private String changeUserId;
	private String changeDateTime;
}
