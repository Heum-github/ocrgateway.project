package com.cjh.common.shared.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchStepExecutionInfo  {
    private String scheduleId;
    private String jobName;                     // Job 이름
    private Long jobExecutionId;                // Job Execution ID
    private String stepName;                    // Step 이름
    private Long totalItems;                    // 총 읽은 아이템 수 (READ_COUNT)
    private Long successItems;                  // 성공한 아이템 수 (WRITE_COUNT)
    private Long failedOrSkippedItems;          // 실패하거나 스킵된 아이템 수
    private Long commitCount;                   // 커밋 횟수
    private Long rollbackCount;                 // 롤백 횟수
    private String status;                      // 상태
    private String exitCode;                    // 종료 코드
    private LocalDateTime startTime;            // 시작 시간
    private LocalDateTime endTime;              // 종료 시간
}
