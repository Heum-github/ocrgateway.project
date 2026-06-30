package com.cjh.base.service;

import com.cjh.domain.ocr.shared.entity.OcrBatchTrgtImag;
import com.cjh.base.repository.OcrBatchTrgtImagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchOcrService {

    private final OcrBatchTrgtImagRepository imageRepository;
    private final StringRedisTemplate redisTemplate;
    private final org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate;

    @Transactional
    public void saveImageKeys(List<String> imageKeys) {
        List<OcrBatchTrgtImag> entities = imageKeys.stream()
                .map(key -> OcrBatchTrgtImag.builder()
                        .ocrWorkTrgtImagId(key)
                        .sysDt(LocalDateTime.now())
                        .status("PENDING")
                        .build())
                .collect(Collectors.toList());
        imageRepository.saveAll(entities);

        // Redis에 초기 상태 등록 및 RabbitMQ 발송
        // 키 구조: batch:status:item:{imageKey}
        imageKeys.forEach(key -> {
            redisTemplate.opsForValue().set("batch:status:item:" + key, "PENDING");
            
            // 큐에 발송
            rabbitTemplate.convertAndSend(com.cjh.base.common.config.RabbitConfig.EXCHANGE_NAME, 
                                          com.cjh.base.common.config.RabbitConfig.ROUTING_KEY, 
                                          key);
        });
    }
}
