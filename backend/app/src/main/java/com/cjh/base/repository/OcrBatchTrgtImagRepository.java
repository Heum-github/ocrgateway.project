package com.cjh.base.repository;

import com.cjh.domain.ocr.shared.entity.OcrBatchTrgtImag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcrBatchTrgtImagRepository extends JpaRepository<OcrBatchTrgtImag, String> {
}
