package com.cjh.base.common.config;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image_tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String imageKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProcessStatus status;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public ImageTask(String imageKey, ProcessStatus status) {
        this.imageKey = imageKey;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}