import axios from 'axios';

// Create an Axios instance with base URL (adjust in production/env)
export const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api/v1',
  timeout: 10000,
});

// Optionally add interceptors here for JWT tokens
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

/**
 * 1. 파일 업로드 API
 * POST /batch/upload-keys
 * 대용량 파일 (Excel, CSV, TXT)을 multipart/form-data로 업로드
 */
export const uploadBatchKeys = async (file, description) => {
  const formData = new FormData();
  formData.append('file', file);
  if (description) formData.append('description', description);

  const response = await apiClient.post('/batch/upload-keys', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};

/**
 * 2. 처리 현황 리스트 조회 API
 * GET /batch/status
 * 페이지네이션 및 필터링 기능 포함
 */
export const getBatchStatus = async (params) => {
  // params: { page, size, status }
  const response = await apiClient.get('/batch/status', { params });
  return response.data;
};

/**
 * 3. 개별 이미지 처리 상태 조회 API
 * GET /batch/{batchId}/items/status
 * Redis 캐시 기반 고속 조회용 (Polling에 사용)
 */
export const getBatchItemStatus = async (batchId, params) => {
  const response = await apiClient.get(`/batch/${batchId}/items/status`, { params });
  return response.data;
};

/**
 * 4. OCR 결과 상세 조회 API
 * GET /ocr/result/{imageKey}
 * 이미지 경로 및 LayoutLM JSON 반환 (바운딩 박스 절대 픽셀 좌표)
 */
export const getOcrResult = async (imageKey) => {
  const response = await apiClient.get(`/ocr/result/${imageKey}`);
  return response.data;
};
