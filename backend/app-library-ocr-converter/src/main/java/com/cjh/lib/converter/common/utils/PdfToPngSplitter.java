package com.cjh.lib.converter.common.utils;

import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.file.PathUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.docx4j.wml.P;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class PdfToPngSplitter {

    private static final Logger logger = LoggerFactory.getLogger(PdfToPngSplitter.class);

	@Value("${magick.path}")
    private String magickPath;
    // TODO 설정값으로 처리
    private final int MAGICK_DENSITY = 150;
    private final int MAGICK_DENSITY_MINIMUM = 72;
    private final int MAGICK_QUALITY = 75;

    private final int MAGICK_LIMIT_WIDTH = 1000;
    private final int MAGICK_LIMIT_HEIGHT = 1000;
    private final int MAGICK_LIMIT_TOTAL_PAGE = 20;
//     public boolean split(String inputPath, String outputPath) {            

//         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");   
//         String outputPdfFilePath = outputPath+sdf.format(new Date())+".png";
        
//         List<String> command = new ArrayList<>();
//         command.add(magickPath);
//         command.add("-density");
//         command.add(Integer.toString(density));
//         command.add("-trim");
//         command.add(inputPath);
//         command.add("-quality");
//         command.add("100");
//         command.add("-background");
//         command.add("white");
//         command.add("-alpha");
//         command.add("remove");
// //        command.add("-resize");
// //        command.add("200x200");
//         command.add(outputPdfFilePath);

//         try {   
//             ProcessBuilder processBuilder = new ProcessBuilder(command);
//             Process process = processBuilder.start();

// 			// 출력 및 오류 처리
//             BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//             StringBuilder outputBuilder = new StringBuilder();
//             String line;
//             while ((line = outputReader.readLine()) != null) {
//                 outputBuilder.append(line).append("\n");
//             }

//             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//             StringBuilder errorBuilder = new StringBuilder();
//             while ((line = errorReader.readLine()) != null) {
//                 errorBuilder.append(line).append("\n");
//             }

//             int exitCode = process.waitFor();
//             if (exitCode == 0) {
//                 logger.info("PDF TO PNG 분리 성공");
//                 return true;
//             } else {
//                 logger.error("PDF TO PNG 분리 실패");
//                 return false;
//             }
//         } catch (IOException | InterruptedException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

    public boolean split(String inputPath, String outputPath) {            

        String outputPngFilePath = FilenameUtils.getFullPathNoEndSeparator(outputPath) + File.separator ; // 파일 경로
        //String outputPngFileName = FilenameUtils.getName(outputPath) + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String outputPngFileName = FilenameUtils.getName(outputPath);

        logger.info("문서분할 - PDF작업대상 파일경로 =>" + inputPath + " PDF분할후 저장경로 =>" + outputPngFilePath + " PDF분할후 저장파일명 =>" + outputPngFileName);

        // TODO - 임시 디버그용 - 추후제거
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            logger.error("PDF작업대상 파일이 존재하지 않습니다: " + inputPath);
            return false;
        } else {
            logger.info("PDF작업대상 파일이 확인되었습니다: " + inputPath);
        }

        // 출력 디렉토리 존재 여부 확인 및 생성
        File outputDir = new File(outputPngFilePath);
        if (!outputDir.exists()) {
            logger.info("파일 경로가 없음 - 생성 =>" + outputDir);
            outputDir.mkdirs();
        }

        // 여러 이미지 파일 저장을 위한 처리
        String outputPngFileFullPath = outputPngFilePath + outputPngFileName + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_%d.png";

        logger.info("문서분할 - 전체저장경로형식 => " + outputPngFileFullPath + " 이미지매직경로 =>" + magickPath);

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
        command.add("-density");
        command.add(Integer.toString(MAGICK_DENSITY));
        command.add(inputPath);
        command.add("-background");
        command.add("white");
        command.add("-alpha");
        command.add("remove");
    //  command.add("-resize");
    //  command.add("200x200");
        command.add(outputPngFileFullPath);

        logger.info("문서분할 - 이미지매직 - 커맨드 => " + command.toString());

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
                logger.info("PDF TO PNG 분리 성공");

                // TODO - 임시 디버그용 - 추후제거
                File[] pngFiles = outputDir.listFiles((dir, name) -> name.startsWith(outputPngFileName));
                if (pngFiles != null) {
                    logger.info("문서분할 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일갯수 =>  " + pngFiles.length);
                    for (File pngFile : pngFiles) {
                        logger.info("문서분할 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일명 =>  " + pngFile.getName());
                    }
                }

                return true;
            } else {
                logger.error("PDF TO PNG 분리 실패: " + outputBuilder.toString());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("PDF TO PNG 분리 중 오류발생", e);
            return false;
        }
    }

    public boolean splitV2(String inputPath, String outputPath, String extension) {            

        String outputPngFilePath = FilenameUtils.getFullPathNoEndSeparator(outputPath) + File.separator ; // 파일 경로
        //String outputPngFileName = FilenameUtils.getName(outputPath) + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String outputPngFileName = FilenameUtils.getName(outputPath);

        logger.info("문서분할 - PDF작업대상 파일경로 =>" + inputPath + " PDF분할후 저장경로 =>" + outputPngFilePath + " PDF분할후 저장파일명 =>" + outputPngFileName);

        // TODO - 임시 디버그용 - 추후제거
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            logger.error("PDF작업대상 파일이 존재하지 않습니다: " + inputPath);
            return false;
        } else {
            logger.info("PDF작업대상 파일이 확인되었습니다: " + inputPath);
        }

        // 출력 디렉토리 존재 여부 확인 및 생성
        File outputDir = new File(outputPngFilePath);
        if (!outputDir.exists()) {
            logger.info("파일 경로가 없음 - 생성 =>" + outputDir);
            outputDir.mkdirs();
        }

        // 여러 이미지 파일 저장을 위한 처리
        String outputPngFileFullPath = outputPngFilePath + outputPngFileName + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_%d." + extension;

        logger.info("문서분할 - 전체저장경로형식 => " + outputPngFileFullPath + " 이미지매직경로 =>" + magickPath);

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
        command.add("-density");
        command.add(Integer.toString(MAGICK_DENSITY));
        command.add("-quality");
        command.add(Integer.toString(MAGICK_QUALITY));
        command.add("-interlace");
        command.add("JPEG");
        command.add("-define");
        command.add("jpeg:fancy-upsampling=off");
        command.add("-sampling-factor");
        command.add("4:2:0");
        command.add(inputPath);
        command.add("-background");
        command.add("white");
        command.add("-alpha");
        command.add("remove");
        command.add("-resize");

        command.add("50%");
        command.add(outputPngFileFullPath);

        logger.info("문서분할 - 이미지매직 - 커맨드 => " + command.toString());

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
                logger.info("PDF TO PNG 분리 성공");

                // TODO - 임시 디버그용 - 추후제거
                File[] pngFiles = outputDir.listFiles((dir, name) -> name.startsWith(outputPngFileName));
                if (pngFiles != null) {
                    logger.info("문서분할 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일갯수 =>  " + pngFiles.length);
                    for (File pngFile : pngFiles) {
                        logger.info("문서분할 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일명 =>  " + pngFile.getName());
                    }
                }

                return true;
            } else {
                logger.error("PDF TO PNG 분리 실패: " + outputBuilder.toString());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("PDF TO PNG 분리 중 오류발생", e);
            return false;
        }
    }

    public boolean splitV3(String inputPath, String outputPath, String extension) {            

        String outputPngFilePath = FilenameUtils.getFullPathNoEndSeparator(outputPath) + File.separator ; // 파일 경로
        //String outputPngFileName = FilenameUtils.getName(outputPath) + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String outputPngFileName = FilenameUtils.getName(outputPath);

        logger.info("문서분할 - PDF작업대상 파일경로 =>" + inputPath + " PDF분할후 저장경로 =>" + outputPngFilePath + " PDF분할후 저장파일명 =>" + outputPngFileName);

        // TODO - 임시 디버그용 - 추후제거
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            logger.error("PDF작업대상 파일이 존재하지 않습니다: " + inputPath);
            return false;
        } else {
            logger.info("PDF작업대상 파일이 확인되었습니다: " + inputPath);
        }

        // 출력 디렉토리 존재 여부 확인 및 생성
        File outputDir = new File(outputPngFilePath);
        if (!outputDir.exists()) {
            logger.info("파일 경로가 없음 - 생성 =>" + outputDir);
            outputDir.mkdirs();
        }

        // 여러 이미지 파일 저장을 위한 처리
        String outputPngFileFullPath = outputPngFilePath + outputPngFileName + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_%d." + extension;
        int totalPageCount = 0;
        int pageWidth = 0;
        int pageHeight = 0;

        logger.info("문서분할 - 전체저장경로형식 => " + outputPngFileFullPath + " 이미지매직경로 =>" + magickPath);

        try (PDDocument document = PDDocument.load(new File(inputPath));){
            totalPageCount = document.getNumberOfPages();
            if(totalPageCount > 0) {
                pageWidth = Math.round(document.getPage(0).getMediaBox().getWidth());
                pageHeight = Math.round(document.getPage(0).getMediaBox().getHeight());
                logger.info("문서분할 - 페이지 사이즈 => 너비( " + pageWidth + " ) X 높이( " + pageHeight + " )");
            } else {
                logger.error("PDF TO PNG 오류 - 페이지 갯수 0");
                return false;
            }
        } catch (IOException e1) {
            logger.error("PDF TO PNG 분리 실패: ", e1);
            return false;
        } catch (Exception e2) {
            logger.error("PDF TO PNG 분리 실패: ", e2);
            return false;
        }

        List<String> command = getImageMagickCommandList(pageWidth, pageHeight, inputPath, outputPngFileFullPath);
        logger.info("문서분할 - 이미지매직 - 커맨드 => " + command.toString());

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
                logger.info("PDF TO PNG 분리 성공");

                // TODO - 임시 디버그용 - 추후제거
                File[] pngFiles = outputDir.listFiles((dir, name) -> name.startsWith(outputPngFileName));
                if (pngFiles != null) {
                    logger.info("문서분할 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일갯수 =>  " + pngFiles.length);
                    for (File pngFile : pngFiles) {
                        logger.info("문서분할 - 저장로컬경로 =>" + outputPngFilePath + " 파일명 =>" + outputPngFileName + "- 파일명 =>  " + pngFile.getName());
                    }
                }

                return true;
            } else {
                logger.error("PDF TO PNG 분리 실패: " + outputBuilder.toString());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            logger.error("PDF TO PNG 분리 중 오류발생", e);
            return false;
        }
    }
    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.toLowerCase().contains("win");
    }

    public List<String> getImageMagickCommandList(int width, int height, String inputPath, String outputPath) {

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

        command.add("-density");

        if(height > MAGICK_LIMIT_HEIGHT || width > MAGICK_LIMIT_WIDTH) {
            command.add(Integer.toString(MAGICK_DENSITY_MINIMUM));
        } else {
            command.add(Integer.toString(MAGICK_DENSITY));
        }

        command.add("-quality");
        command.add(Integer.toString(MAGICK_QUALITY));
        command.add("-interlace");
        command.add("JPEG");
        command.add("-define");
        command.add("jpeg:fancy-upsampling=off");
        command.add("-sampling-factor");
        command.add("4:2:0");
        command.add(inputPath);
        command.add("-background");
        command.add("white");
        command.add("-alpha");
        command.add("remove");

        if(height > MAGICK_LIMIT_HEIGHT || width > MAGICK_LIMIT_WIDTH) {
            command.add("-resize");
            command.add(Math.round((double)MAGICK_LIMIT_HEIGHT/(double)height * 100.0f) + "%");
        }

        command.add(outputPath);

        return command;
    }
    /*public boolean split(String inputPath, String outputPath) {

        // ImageMagick 명령어 구성
        List<String> switches = new ArrayList<>();
        switches.add("convert");
        switches.add("-density " + Integer.toString(density));
        switches.add("-background white -alpha remove -alpha off");
        switches.add(inputPath);
        switches.add(outputPath);

        StringBuilder outTxt = new StringBuilder();
        StringBuilder errTxt = new StringBuilder();

        // 프로세스 실행
        boolean result = MagickNetProcLib.executeCommand(magickPath, switches, outTxt, errTxt);

        // 프로세스 실행 결과 확인
        if(result) {
            logger.info("PDF TO PNG 분리 성공");
            return true;
        } else {
            logger.error("PDF TO PNG 분리 실패");
            return false;
        }
    }*/
}
