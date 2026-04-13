package com.cjh.domain.ocr.shared.constants;

public class OcrConstants {
    
    public static final String SPLIT_FILE_EXTENSION = "png";

    public static class MAPPING {
        public static String OCR_ITEM_KEY_MAPPING_FILE_NAME = "data/ocr.inference.item.key.mappings.yml";

        public static String OCR_DOCUMENT_MODEL_MAPPING_FILE_NAME = "data/ocr.document.model.mappings.yml";
        public static String OCR_POSTPROCESS_KEY_MAPPING_FILE_NAME = "data/ocr.postprocess.key.mappings.yml";
    }

    public static class LEGACY {
        public static class IMAGE {
            public static String FILE_EXTENSION = "tif";
        }
    }

    public static class LOCAL_FILE_STORAGE {
        public static String BASE_PATH = "";
    }

    public static class IMAGE_INFO {
        public static String[] VALID_EXTENSIONS = {"jpg", "jpeg", "png", "tif", "tiff", "heic", "pdf"};
    }
}
