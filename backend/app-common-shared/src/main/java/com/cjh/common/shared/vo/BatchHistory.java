package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchHistory extends CommonBodyInfo{

    private Long id; 
    private String batchName; 
    private String startDateTime; 
    private String endDateTime; 
    private double elapsedTime = 0; 
    private int count = 0; 
    private int successCount = 0; 
    private int failCount = 0; 
    private String result; 
    private String schedule; 
    private String message = ""; 
    private String createDateTime; 
    private String createUserId; 

    private Long batchHistoryId;
    private String hostName; 
    private String ipAddress; 
    private String targetFilePath; 
    private String directStartMessage; 


    private String filterType;

} 