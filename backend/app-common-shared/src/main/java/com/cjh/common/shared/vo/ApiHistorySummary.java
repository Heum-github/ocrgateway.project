package com.cjh.common.shared.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiHistorySummary {
    private int totalCount;
	private int successCount;
	private int errorCount;
	
	private double averageSeconds;
	private double maxSeconds;
	private double minSeconds;
}
