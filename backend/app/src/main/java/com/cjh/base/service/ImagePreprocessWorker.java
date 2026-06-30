package com.cjh.base.service;

import com.cjh.base.common.config.RabbitConfig;
import com.cjh.base.repository.OcrBatchTrgtImagRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagePreprocessWorker {

    private static final Logger logger = LoggerFactory.getLogger(ImagePreprocessWorker.class);

    private final MinioClient minioClient;
    private final StringRedisTemplate redisTemplate;
    private final OcrBatchTrgtImagRepository imageRepository;

    @Value("${minio.bucket.name:ocr-images}")
    private String bucketName;

    // 처리 가능한 확장자 목록
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif");

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    @Transactional
    public void processImage(String imageKey) {
        logger.info("[Worker] 수신된 이미지 키: {}", imageKey);

        try {
            // 1. Redis 상태 변경 (PROCESSING)
            updateStatus(imageKey, "PROCESSING");

            // 2. MinIO에서 파일 다운로드
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(imageKey)
                            .build()
            );

            // 3. 확장자 검증
            String extension = getExtension(imageKey);
            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                logger.warn("[Worker] 지원하지 않는 확장자입니다: {}", extension);
                updateStatus(imageKey, "ERROR");
                return;
            }

            // 4. 이미지 PNG 변환 (전처리)
            BufferedImage originalImage = ImageIO.read(stream);
            if (originalImage == null) {
                logger.error("[Worker] 이미지를 읽을 수 없거나 파일이 손상되었습니다: {}", imageKey);
                updateStatus(imageKey, "ERROR");
                return;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 포맷을 PNG로 변환하여 기록
            ImageIO.write(originalImage, "png", baos);
            byte[] pngBytes = baos.toByteArray();

            // 5. MinIO에 덮어쓰기 (기존 키 그대로 사용, 내용은 PNG 바이너리)
            try (ByteArrayInputStream uploadStream = new ByteArrayInputStream(pngBytes)) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(imageKey)
                                .stream(uploadStream, pngBytes.length, -1)
                                .contentType("image/png")
                                .build()
                );
            }

            logger.info("[Worker] {} 이미지 PNG 변환 및 MinIO 덮어쓰기 완료.", imageKey);

            // 완료 처리는 AI 모델(FastAPI)이 수행할 예정이므로 워커는 여기까지 수행합니다.
            // 필요하다면 여기서 COMPLETED 처리 혹은 다음 단계 Queue(AI용) 로 넘길 수 있습니다.

        } catch (Exception e) {
            logger.error("[Worker] 이미지 처리 중 오류 발생: {}", imageKey, e);
            updateStatus(imageKey, "ERROR");
        }
    }

    private void updateStatus(String imageKey, String status) {
        // Redis 업데이트
        redisTemplate.opsForValue().set("batch:status:item:" + imageKey, status);

        // MariaDB 업데이트
        imageRepository.findById(imageKey).ifPresent(entity -> {
            entity.updateStatus(status);
        });
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}
