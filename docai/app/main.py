from fastapi import FastAPI
from app.worker.rabbitmq import start_rabbitmq_consumer
import asyncio

app = FastAPI(title="AI OCR Engine")
rabbitmq_connection = None

@app.on_event("startup")
async def startup_event():
    global rabbitmq_connection
    # FastAPI가 켜질 때 RabbitMQ 워커를 백그라운드 태스크로 던짐
    rabbitmq_connection = await start_rabbitmq_consumer()

@app.on_event("shutdown")
async def shutdown_event():
    if rabbitmq_connection:
        await rabbitmq_connection.close()

@app.get("/health")
async def health_check():
    return {"status": "healthy", "message": "AI OCR Server with EasyOCR & RabbitMQ is running."}