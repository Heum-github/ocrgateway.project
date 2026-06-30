from pydantic import BaseModel
from typing import List

# 1. RabbitMQ에서 수신할 메시지 구조 (Spring Boot가 보내는 데이터)
class OcrTaskRequest(BaseModel):
    task_id: str
    file_path: str   # MinIO에 저장된 이미지 경로

# 2. AI 모델이 추출한 개별 데이터 (Bounding Box + Text + Label)
class ExtractedEntity(BaseModel):
    label: str       # 예: "상호명", "총금액" (LayoutLM이 분류할 태그)
    text: str        # EasyOCR이 읽은 글자
    confidence: float
    box: List[List[int]] # [[x1, y1], [x2, y1], [x2, y2], [x1, y2]]

# 3. 최종 반환할 JSON 구조
class OcrResult(BaseModel):
    task_id: str
    status: str = "COMPLETED"
    entities: List[ExtractedEntity]