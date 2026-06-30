import asyncio
import json
import aio_pika
from app.core.config import settings
from app.models.schemas import OcrTaskRequest
from app.services.ai_pipeline import ai_pipeline

# Spring Boot와 약속한 큐 이름
QUEUE_NAME = "ocr.task.queue"

async def process_message(message: aio_pika.IncomingMessage):
    """
    메시지가 큐에 들어올 때마다 실행되는 콜백 함수
    """
    # async with 구문을 사용하면 작업 성공 시 자동으로 ACK(완료 확인)를 보냅니다.
    async with message.process():
        try:
            # 1. 메시지 해독 (Spring Boot가 보낸 JSON 파싱)
            body = message.body.decode()
            task_req = OcrTaskRequest.parse_raw(body)
            print(f"[작업 수신] Task ID: {task_req.task_id} | File: {task_req.file_path}")

            # 2. MinIO에서 이미지 다운로드 (현재는 가상 코드로 대체)
            # image_bytes = await minio_service.download_image(task_req.file_path)
            print(f"MinIO에서 이미지 다운로드 완료 (가상): {task_req.file_path}")
            
            # (테스트용 임시 바이트 데이터 생성)
            image_bytes = b"dummy_image_data"

            # 3. 무거운 AI 처리 로직 실행 (EasyOCR + LayoutLM)
            loop = asyncio.get_event_loop()
            result = await loop.run_in_executor(
                None, ai_pipeline.process, task_req.task_id, image_bytes
            )

            # 4. 처리 완료 및 결과 출력 (추후 DB 저장 로직 추가)
            print(f"[작업 완료] Task ID: {task_req.task_id}")
            print(f"결과 데이터:\n{result.json(indent=2, ensure_ascii=False)}")

        except Exception as e:
            # 에러 발생 시 처리 (재시도 로직이나 Dead Letter Queue로 보낼 수 있음)
            print(f"[작업 실패] Task ID 알 수 없음 | 에러: {e}")


async def start_rabbitmq_consumer():
    """
    RabbitMQ 서버에 연결하고 큐를 구독(Subscribe)하는 함수
    """
    print(f"RabbitMQ 연결 시도 중... ({settings.RABBITMQ_URL})")
    
    # 1. RabbitMQ 서버 연결 (connect_robust - 끊어지면 자동 재연결)
    connection = await aio_pika.connect_robust(settings.RABBITMQ_URL)
    
    # 2. 통신을 위한 채널 생성
    channel = await connection.channel()
    
    # 3. 한 번에 가져올 메시지 수(Prefetch) 설정 (메모리 폭주 방지)
    await channel.set_qos(prefetch_count=1)
    
    # 4. 큐 선언 (없으면 만들고, 있으면 연결)
    queue = await channel.declare_queue(QUEUE_NAME, durable=True)
    
    # 5. 메시지 소비 시작
    await queue.consume(process_message)
    print(f"'{QUEUE_NAME}' 큐 구독 시작. 메시지 대기 중...")
    
    # main.py에서 연결을 안전하게 닫을 수 있도록 connection 객체 반환
    return connection