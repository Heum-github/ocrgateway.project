import React, { useState, useRef } from 'react';
import { UploadCloud, X, File, CheckCircle } from 'lucide-react';
import { cn } from '../utils/cn';
import { uploadBatchKeys } from '../api/client';

export default function UploadModal({ isOpen, onClose, onSuccess }) {
  const [file, setFile] = useState(null);
  const [isUploading, setIsUploading] = useState(false);
  const [dragActive, setDragActive] = useState(false);
  const inputRef = useRef(null);

  if (!isOpen) return null;

  const handleDrag = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (e.type === "dragenter" || e.type === "dragover") {
      setDragActive(true);
    } else if (e.type === "dragleave") {
      setDragActive(false);
    }
  };

  const handleDrop = (e) => {
    e.preventDefault();
    e.stopPropagation();
    setDragActive(false);
    if (e.dataTransfer.files && e.dataTransfer.files[0]) {
      setFile(e.dataTransfer.files[0]);
    }
  };

  const handleChange = (e) => {
    e.preventDefault();
    if (e.target.files && e.target.files[0]) {
      setFile(e.target.files[0]);
    }
  };

  const handleUpload = async () => {
    if (!file) return;
    setIsUploading(true);
    try {
      // API call using multipart/form-data
      // const res = await uploadBatchKeys(file, "Batch Upload");
      
      // Mocking for frontend demo
      await new Promise(resolve => setTimeout(resolve, 1500));
      onSuccess?.();
      onClose();
      setFile(null);
    } catch (e) {
      console.error(e);
      // handle error
    } finally {
      setIsUploading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
      {/* Backdrop */}
      <div 
        className="absolute inset-0 bg-slate-900/40 backdrop-blur-sm transition-opacity"
        onClick={onClose}
      />
      
      {/* Modal */}
      <div className="relative bg-white rounded-2xl shadow-xl w-full max-w-lg p-6 animate-in fade-in zoom-in-95 duration-200">
        <button 
          onClick={onClose}
          className="absolute top-4 right-4 p-2 text-slate-400 hover:text-slate-600 hover:bg-slate-100 rounded-full transition-colors"
        >
          <X className="w-5 h-5" />
        </button>

        <h3 className="text-xl font-bold text-slate-800 mb-2">Upload Batch File</h3>
        <p className="text-sm text-slate-500 mb-6">Excel, CSV, TXT 파일을 업로드하여 대량 OCR 작업을 시작합니다.</p>

        <div 
          className={cn(
            "border-2 border-dashed rounded-xl p-8 text-center transition-all",
            dragActive ? "border-indigo-500 bg-indigo-50" : "border-slate-300 hover:border-indigo-400 bg-slate-50",
            file && "border-green-500 bg-green-50/30"
          )}
          onDragEnter={handleDrag}
          onDragLeave={handleDrag}
          onDragOver={handleDrag}
          onDrop={handleDrop}
          onClick={() => inputRef.current?.click()}
        >
          <input 
            ref={inputRef}
            type="file" 
            className="hidden" 
            accept=".csv, .xlsx, .txt"
            onChange={handleChange}
          />
          
          {file ? (
            <div className="flex flex-col items-center gap-3">
              <div className="w-12 h-12 bg-green-100 text-green-600 rounded-full flex items-center justify-center">
                <CheckCircle className="w-6 h-6" />
              </div>
              <div>
                <p className="font-semibold text-slate-700">{file.name}</p>
                <p className="text-sm text-slate-500">{(file.size / 1024).toFixed(2)} KB</p>
              </div>
            </div>
          ) : (
            <div className="flex flex-col items-center gap-3">
              <div className="w-12 h-12 bg-indigo-100 text-indigo-600 rounded-full flex items-center justify-center">
                <UploadCloud className="w-6 h-6" />
              </div>
              <div>
                <p className="font-semibold text-slate-700">여기로 파일을 드래그 하거나 클릭하여 탐색</p>
                <p className="text-sm text-slate-500 mt-1">지원 포맷: CSV, Excel, TXT (Max 50MB)</p>
              </div>
            </div>
          )}
        </div>

        <div className="mt-8 flex justify-end gap-3">
          <button 
            onClick={onClose}
            className="px-5 py-2.5 text-sm font-medium text-slate-600 hover:bg-slate-100 rounded-lg transition-colors"
          >
            취소
          </button>
          <button 
            onClick={handleUpload}
            disabled={!file || isUploading}
            className="px-5 py-2.5 text-sm font-medium bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-2 shadow-sm shadow-indigo-200"
          >
            {isUploading && <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />}
            업로드 처리
          </button>
        </div>
      </div>
    </div>
  );
}
