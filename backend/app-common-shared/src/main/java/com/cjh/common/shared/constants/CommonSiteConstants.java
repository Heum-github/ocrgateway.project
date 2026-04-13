package com.cjh.common.shared.constants;

import java.util.Arrays;
import java.util.List;

public class CommonSiteConstants {

    public static class ENV {

        public static String OS = "";
        public static String IP = "";
        public static int PORT = 0;
        public static String IMAGE_MAGICK_PATH = "";
        public static String SERVER_ACTIVE = "";
        public static String HOST_NAME = "";

        public static boolean isWindows() {
            return OS != null && OS.toLowerCase().contains("win");
        }

        public static boolean isLocal() {
            return "local".equals(SERVER_ACTIVE);
        }

        public static boolean isDev() {
            return "dev".equals(SERVER_ACTIVE);
        }

        public static boolean isQa() {
            return "qa".equals(SERVER_ACTIVE);
        }

        public static boolean isProd() {
            return "prod".equals(SERVER_ACTIVE);
        }
    }

    public static class EXTENSIONS {
        public static final String PDF = "pdf";
        public static final List<String> TIF = Arrays.asList("tiff", "tif");
    }
}