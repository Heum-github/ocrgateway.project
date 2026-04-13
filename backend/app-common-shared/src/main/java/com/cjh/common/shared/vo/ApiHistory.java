package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiHistory extends CommonBodyInfo{
    private long id;
    private String apiName;
    private String startDateTime;
    private String endDateTime;
    private Double elapsedTime;
    private String data;
    private String source;
    private String destination;
    private String requestKey;
    private int recognitionId;
    private int classificationId;
    private String parameterString1;
    private String parameterString2;
    private String parameterString3;
    private int parameterInt1;
    private int parameterInt2;
    private int parameterInt3;
    private String result;
    private String message;
    private String requestData;
    private String responseData;
    private String createDateTime;
    private String createUserId;

    private String filterType;

}
