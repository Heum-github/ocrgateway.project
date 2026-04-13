package com.cjh.common.shared.vo;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

/*
 * html body 구성에 필요한 기본 객체 - Pagination 적용
 */
@Getter
@Setter
public class CommonBodyInfo {

    private String fromDate;
	private String toDate;
	
	// private String baseDate;
	// private String fromMonths;
	// private String toMonths;
	// private String searchString;
	// private String searchType;

	private int offset;
	private int pageSize;
	private int pageNumber;
    
	private Timestamp fromDateTime;
    private Timestamp toDateTime;

    public void setOffset(int pageNumber, int pageSize) {
	    this.offset = pageSize * (pageNumber - 1);
	}

    public void setDateTimeRange() {
        setDateTimeRange("yyyyMMdd");
    }

    public void setDateTimeRange(String pattern) {
        if (fromDate != null && toDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            this.fromDateTime = Timestamp.valueOf(LocalDate.parse(fromDate, formatter).atStartOfDay());
            this.toDateTime = Timestamp.valueOf(LocalDate.parse(toDate, formatter).atTime(LocalTime.MAX));
        }
    }
}
