package com.cjh.common.shared.utils;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilePathSorter {

    public static void sortPathsByNumberOrder(List<String> filePaths) {
        Collections.sort(filePaths, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return extractNumber(o1) - extractNumber(o2);
            }

            private int extractNumber(String filePath) {
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
                String numberPart = fileName.replaceAll("\\D", "");
                return Integer.parseInt(numberPart);
            }
        });
    }

    // public static void sortFilesByNumberOrder(File[] files) {
    //     Arrays.sort(files, new Comparator<File>() {
    //         @Override
    //         public int compare(File o1, File o2) {
    //             return extractNumber(o1.getName()) - extractNumber(o2.getName());
    //         }

    //         private int extractNumber(String fileName) {
    //             String numberPart = fileName.replaceAll("\\D", "");
    //             return numberPart.isEmpty() ? 0 : Integer.parseInt(numberPart);
    //         }
    //     });
    // }

    public static void sortFilesByNumberOrder(File[] files) {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                BigInteger n1 = extractNumber(o1.getName());
                BigInteger n2 = extractNumber(o2.getName());
                return n1.compareTo(n2);
            }

            private BigInteger extractNumber(String fileName) {
                String numberPart = fileName.replaceAll("\\D", "");
                return numberPart.isEmpty() ? BigInteger.valueOf(0) : new BigInteger(numberPart);
            }
        });
    }

    //2024-08-07 소팅 메소드 파라미터 배열대신 리스트로 변경
    public static void sortFilesByNumberOrderV2(List<File> files) {

        if(files == null || files.isEmpty())
            return;
            
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                BigInteger n1 = extractNumber(o1.getName());
                BigInteger n2 = extractNumber(o2.getName());
                return n1.compareTo(n2);
            }

            private BigInteger extractNumber(String fileName) {
                String numberPart = fileName.replaceAll("\\D", "");
                return numberPart.isEmpty() ? BigInteger.valueOf(0) : new BigInteger(numberPart);
            }
        });
    }
}
