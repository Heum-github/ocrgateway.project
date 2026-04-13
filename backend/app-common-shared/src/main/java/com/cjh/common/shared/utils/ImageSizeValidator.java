
package com.cjh.common.shared.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageSizeValidator {

    private static final String[] checkExtensions = {"png", "jpg", "jpeg", "bmp", "ico", "gif", "svg"};
    private static final int minWidth = 300;
    private static final int minHeight = 400;
    
    private static final Logger logger = LoggerFactory.getLogger(ImageSizeValidator.class);

    public static void main(String[] args) {

        String filePath = "d:\\sample2.gif";
        File file = new File(filePath);

        if(!file.exists()){
            logger.info("파일이 존재하지 않습니다");
            return;
        }

        // 확장자 확인
        if (!isCheckExtension(file)) {
            logger.info("체크 대상 확장자가 아님");
            return;
        }
        
        logger.info("체크 대상 확장자임");

        if (isValidImage(file)) {
            logger.info("유효한 이미지 파일입니다.");
        } else {
            logger.info("유효하지 않은 이미지 파일입니다.");
        }
    }

    public static boolean isValidImage(String filePath) {
        logger.info("대상 이미지 파일 경로:" + filePath);
        
        File file = new File(filePath);
        
        return isValidImage(file);
    }

    public static boolean isValidImage(File file) {
        try{

            // 이미지 크기 확인
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return false;
            }

            int width = image.getWidth();
            int height = image.getHeight();

            logger.info("width: "+width+" height:"+height);

            return width < minWidth && height < minHeight;

        }catch(Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }

    public static boolean isCheckExtension(File file) {

        String fileName = file.getName().toLowerCase();

        return isCheckExtension(fileName);
    }

    public static boolean isCheckExtension(String fileName) {

        for (String ext : checkExtensions) {
            if (fileName.endsWith("." + ext)) {
                return true;
            }
        }

        return false;
    }
}