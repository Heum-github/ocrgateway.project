package com.cjh.lib.converter.common.utils;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class MultiImageFileSplitter {

    private static final Logger logger = LoggerFactory.getLogger(MultiImageFileSplitter.class);

	@Value("${magick.path}")
    private String magickPath;
    @Value("${magick.command.density}")
    private int density;
    @Value("${ghostscript.path}")
    private String ghostscriptPath;
    

    // DPI 조절하여 컨버트
    public boolean splitV2(String inputPath, String outputPath, int customDensity) {
        return splitDefault(inputPath, outputPath, customDensity);
    }

    // 기존 컨버트
    public boolean split(String inputPath, String outputPath) {
        return splitDefault(inputPath, outputPath, density);
    }

    public boolean splitPdfToSinglePdfPage(String inputPath, String outputPath) {
        return splitPdfToSinglePdfPageDefault(inputPath, outputPath);
    }

    private boolean splitDefault(String inputPath, String outputPath, int selectDensity) {            

        String outputPngFilePath = outputPath; // 파일 경로
        String outputPngFileName = FilenameUtils.getBaseName(inputPath);

        logger.info("멀티파일 분할작업 - PDF작업대상 파일경로 =>" + inputPath + " PDF분할후 저장경로 =>" + outputPngFilePath + " PDF분할후 저장파일명 =>" + outputPngFileName);

        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            logger.error("멀티파일 분할작업 - PDF작업대상 파일이 존재하지 않습니다: " + inputPath);
            return false;
        }

        logger.info("멀티파일 분할작업 - PDF작업대상 파일이 확인되었습니다: " + inputPath);

        // 출력 디렉토리 존재 여부 확인 및 생성
        File outputDir = new File(outputPngFilePath);
        if (!outputDir.exists()) {
            logger.info("파일 경로가 없음 - 생성 =>" + outputDir);
            outputDir.mkdirs();
        }

        // 여러 이미지 파일 저장을 위한 처리
        String outputPngFileFullPath = outputPngFilePath + File.separator + outputPngFileName + "_" + new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date()) + "_%d.png";
        logger.info("멀티파일 분할작업 - 전체저장경로형식 => " + outputPngFileFullPath + " 이미지매직경로 =>" + magickPath);

        List<String> command = new ArrayList<>();
        command.add(magickPath);
        // 운영 체제에 따른 명령어 구성 - Windows일 경우 convert 명령어를 추가, Linux일 경우 추가하지 않음
        // - Linux 환경에서는 magick 명령어를 사용하지 않고, convert 명령어를 직접 사용, /usr/bin 경로에 convert 바이너리가 설치되어 있음
        // - Linux에서 convert 명령어를 호출할 때, convert는 독립적인 실행 파일로 인식되며 직접 실행됨. 
        // - magick convert와 같은 방식으로 호출할 경우, magick 명령어는 convert 도구를 호출하지 않고 전체 명령어를 처리하려고 하므로 실패할 수 있음
        // - Windows에서는 magick 명령어가 모든 도구의 진입점이 되므로 magick convert와 같이 호출해야 함
        if(isWindows()){
            command.add("convert");
        }
        command.add("-verbose");
        command.add("-density");
        command.add(Integer.toString(selectDensity));
        command.add(inputPath);
        command.add("-background");
        command.add("white");
        command.add("-alpha");
        command.add("remove");
    //  command.add("-resize");
    //  command.add("200x200");
        command.add(outputPngFileFullPath);

        logger.info("멀티파일 분할작업 - 이미지매직 - 커맨드 => " + command.toString());

        try {   
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
    
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                logger.info("멀티파일 분할작업 - PNG 분리 성공");

                File[] pngFiles = outputDir.listFiles((dir, name) -> name.startsWith(outputPngFileName));
                if (pngFiles != null) {
                    logger.info("멀티파일 분할작업 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일갯수 =>  " + pngFiles.length);
                    for (File pngFile : pngFiles) {
                        logger.info("멀티파일 분할작업 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일명 =>  " + pngFile.getName());
                    }
                }
                return true;
            } else {
                logger.error("멀티파일 분할작업 - PNG 분리 실패: " + outputBuilder.toString());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("멀티파일 분할작업 - PNG 분리 중 오류발생", e);
            return false;
        }
    }

    private boolean splitPdfToSinglePdfPageDefault(String inputPath, String outputPath) {
        String outputPdfFilePath = outputPath; // 파일 경로
        String outputPdfFileName = FilenameUtils.getBaseName(inputPath);

        logger.info("멀티파일 분할작업 - PDF작업대상 파일경로 =>" + inputPath + " PDF분할후 저장경로 =>" + outputPdfFilePath + " PDF분할후 저장파일명 =>" + outputPdfFileName);

        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            logger.error("멀티파일 분할작업 - PDF작업대상 파일이 존재하지 않습니다: " + inputPath);
            return false;
        }

        logger.info("멀티파일 분할작업 - PDF작업대상 파일이 확인되었습니다: " + inputPath);

        // 출력 디렉토리 존재 여부 확인 및 생성
        File outputDir = new File(outputPdfFilePath);
        if (!outputDir.exists()) {
            logger.info("파일 경로가 없음 - 생성 =>" + outputDir);
            outputDir.mkdirs();
        }

        int pageCount = getPageCount(inputPath);
        if (pageCount == 0) {
            return false;
        }

        for (int page = 1; page <= pageCount; page++) {
            String outputPdfFileFullPath = outputPdfFilePath + File.separator + outputPdfFileName + "_" + new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date()) + "_" + String.format("%03d", page) + ".pdf";
    
            List<String> command = new ArrayList<>();
            command.add(ghostscriptPath);     
            command.add("-sDEVICE=pdfwrite");
            command.add("-dBATCH");
            command.add("-dNOPAUSE");
            command.add("-dSAFER");
            command.add("-dFirstPage=" + page);
            command.add("-dLastPage=" + page);
            command.add("-sOutputFile=" + outputPdfFileFullPath);
            command.add(inputPath);
    
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
    
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder outputBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    outputBuilder.append(line).append("\n");
                }
    
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    logger.error("멀티파일 분할작업 - PDF 분리 실패 (페이지 " + page + "): " + outputBuilder.toString());
                    return false;
                }
    
                logger.info("멀티파일 분할작업 - 저장로컬경로 =>" + outputPdfFilePath + " 파일명 =>" + outputPdfFileFullPath);
    
            } catch (IOException | InterruptedException e) {
                logger.error("멀티파일 분할작업 - PDF 분리 중 오류발생 (페이지 " + page + ")", e);
                return false;
            }
        }

        return true;
    }

    public int getPageCount(String inputPath) {
        List<String> command = new ArrayList<>();
        if(isWindows()) {
            command.add(magickPath);
        }
        command.add("identify");
        command.add("-format");
        command.add("%n\\n");
        command.add(inputPath);

        logger.info("이미지 페이지 수 조회 - 커맨드: " + command);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int pageCount = 0;

            while ((line = reader.readLine()) != null) {
                try {
                    pageCount++; // 페이지 숫자 업데이트
                } catch (NumberFormatException e) {
                    pageCount = 0;
                    break;
                }
            }

            //String output = reader.readLine();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                 // 길이가 페이지 수
                logger.info("이미지 페이지 수 조회 성공 - 페이지 수: " + pageCount);
                return pageCount;
            } else {
                logger.error("이미지 페이지 수 조회 실패");
                return 0;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("이미지 페이지 수 조회 중 오류 발생", e);
            return 0;
        }
    }

    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.toLowerCase().contains("win");
    }
}
