import easyocr
import cv2
import numpy as np
import torch
from transformers import LayoutLMTokenizer, LayoutLMForTokenClassification
from app.models.schemas import ExtractedEntity, OcrResult

class AiOcrPipeline:
    def __init__(self):
        print("🤖 AI 모델 로딩 중...")
        
        # 1. EasyOCR 로드 (텍스트 및 좌표 추출용)
        self.reader = easyocr.Reader(['ko', 'en'], gpu=False)
        
        # 2. LayoutLM 모델 & 토크나이저 로드
        # (현재는 HuggingFace의 기본 모델을 부르지만, 나중에 파인튜닝한 본인 모델 경로로 변경하게 됩니다)
        self.model_name = "microsoft/layoutlm-base-uncased"
        self.tokenizer = LayoutLMTokenizer.from_pretrained(self.model_name)
        
        # 라벨 개수(num_labels)는 향후 추출할 데이터(상호명, 금액 등)의 종류에 따라 달라집니다.
        self.layoutlm_model = LayoutLMForTokenClassification.from_pretrained(self.model_name, num_labels=5)
        
        # 임시 라벨 맵 (추후 파인튜닝 시 본인의 라벨로 교체)
        self.label_map = {0: "O", 1: "B-STORE", 2: "I-STORE", 3: "B-TOTAL", 4: "I-TOTAL"}
        
        print("✅ AI 모델 로딩 완료!")

    def preprocess_image(self, image_bytes: bytes):
        np_arr = np.frombuffer(image_bytes, np.uint8)
        img = cv2.imdecode(np_arr, cv2.IMREAD_COLOR)
        return img, cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    def normalize_bbox(self, bbox, width, height):
        """EasyOCR의 좌표를 LayoutLM이 인식할 수 있게 0~1000 스케일로 변환"""
        x_min, y_min = bbox[0]
        x_max, y_max = bbox[2]
        
        return [
            int(1000 * (x_min / width)),
            int(1000 * (y_min / height)),
            int(1000 * (x_max / width)),
            int(1000 * (y_max / height))
        ]

    def process(self, task_id: str, image_bytes: bytes) -> OcrResult:
        # 1. 이미지 전처리 및 해상도 확보
        original_img, gray_img = self.preprocess_image(image_bytes)
        height, width = original_img.shape[:2]
        
        # 2. EasyOCR로 텍스트와 원본 좌표 추출
        ocr_results = self.reader.readtext(gray_img)
        
        words = []
        boxes = []
        entities = []
        
        # 3. LayoutLM 입력을 위한 데이터 가공
        for bbox, text, conf in ocr_results:
            words.append(text)
            boxes.append(self.normalize_bbox(bbox, width, height))
            
        # 추출된 글자가 없다면 조기 종료
        if not words:
            return OcrResult(task_id=task_id, entities=[])

        # 4. LayoutLM 추론 (Inference)
        # 텍스트와 0~1000으로 변환된 좌표를 모델에 전달
        encoding = self.tokenizer(words, boxes=boxes, return_tensors="pt", truncation=True)
        
        with torch.no_grad():
            outputs = self.layoutlm_model(**encoding)
            
        # 가장 확률이 높은 라벨(클래스) 예측
        predictions = outputs.logits.argmax(-1).squeeze().tolist()
        
        # 만약 단어가 하나뿐이라 리스트가 아닌 int로 반환될 경우를 대비
        if isinstance(predictions, int):
            predictions = [predictions]

        # 5. 최종 Entity 조립
        for i, (bbox, text, conf) in enumerate(ocr_results):
            # 토크나이저 특성상 word piece로 쪼개질 수 있으나, 여기서는 간략화하여 첫 번째 토큰의 예측값 사용
            pred_label_id = predictions[i] if i < len(predictions) else 0
            predicted_label = self.label_map.get(pred_label_id, "미분류")
            
            entities.append(ExtractedEntity(
                label=predicted_label,
                text=text,
                confidence=round(conf, 4),
                box=bbox
            ))
            
        return OcrResult(task_id=task_id, entities=entities)