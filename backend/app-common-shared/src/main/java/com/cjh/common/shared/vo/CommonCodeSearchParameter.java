package com.cjh.common.shared.vo;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonCodeSearchParameter {
    private String code;
    private List<String> codes;
    private String codeValue;
    private int commonCodeDetailId;
    private String useYN;
}