package com.cjh.domain.ocr.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "OCR_BATCH_TRGT_IMAG", catalog = "ocrgw")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OcrBatchTrgtImag {

    @Id
    @Column(name = "OCR_WORK_TRGT_IMAG_ID", length = 255)
    private String ocrWorkTrgtImagId;

    @Column(name = "SYS_DT")
    private LocalDateTime sysDt;

    @Column(name = "STATUS", length = 20)
    private String status;

    public void updateStatus(String status) {
        this.status = status;
    }
}
