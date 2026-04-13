package com.cjh.lib.converter.common.utils.checker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PdfValidChecker implements FileValidChecker {

    private static final Logger logger = LoggerFactory.getLogger(PdfValidChecker.class);

    @Override
    public Boolean isValid(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            logger.error("대상 경로에 파일이 존재하지 않거나 파일이 비어 있습니다: " + filePath);
            return false;
        }

        if (!checkPdfHeader(file)) {
            return false;
        }

        return checkPdfLoad(filePath);
    }

    private boolean checkPdfHeader(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] header = new byte[5];
            int bytesRead = fis.read(header);
            if (bytesRead != 5 || !new String(header).equals("%PDF-")) {
                logger.error("PDF 유효성체크 - 유효하지 않은 PDF 헤더값");
                return false;
            }
            return true;
        } catch (IOException e) {
            logger.error("PDF 유효성체크 - 파일 읽기 오류", e);
            return false;
        }
    }

    private boolean checkPdfLoad(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            return true;
        } catch (InvalidPasswordException e) {
            logger.error("PDF 유효성체크 - 비밀번호가 필요한 PDF 파일: " + filePath, e);
            return false;
        } catch (IOException e) {
            logger.error("PDF 유효성체크 - 파일 로드 오류: " + filePath, e);
            return false;
        }
    }

    public static void main(String[] args) {
        //String filePath = "d:\\04.Project\\현대상선\\이메일수신\\현업메일\\첨부파일\\AHQ1704998393287_깨진파일.pdf";
        String filePath = "d:\\04.Project\\현대상선\\이메일수신\\현업메일\\첨부파일\\AHQ1530897229340.pdf";
        boolean isValid = new PdfValidChecker().isValid(filePath);
        logger.info("PDF 정상여부 => " + isValid);
    }
}