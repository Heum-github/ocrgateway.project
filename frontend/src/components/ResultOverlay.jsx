import React, { useEffect, useState } from 'react';
import { X } from 'lucide-react';
import { getOcrResult } from '../api/client';

/**
 * 절대 좌표를 이용한 바운딩 박스 표시 컴포넌트
 */
export default function ResultOverlay({ isOpen, onClose, imageKey }) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [hoveredBox, setHoveredBox] = useState(null);

  useEffect(() => {
    if (isOpen && imageKey) {
      fetchResult();
    }
  }, [isOpen, imageKey]);

  const fetchResult = async () => {
    setLoading(true);
    try {
      // Mock data replacement for API: await getOcrResult(imageKey);
      await new Promise(resolve => setTimeout(resolve, 600));
      setData({
        imageUrl: "https://images.unsplash.com/photo-1559811814-e2c56a5c192b?q=80&w=800&auto=format&fit=crop", // placeholder document structure
        imageWidth: 800,
        imageHeight: 1000,
        layoutData: [
          { id: '1', text: 'INVOICE NUMBER: 00123', confidence: 0.99, boundingBox: [50, 100, 350, 150] },
          { id: '2', text: 'TOTAL AMOUNT: $4,500.00', confidence: 0.88, boundingBox: [50, 200, 400, 250] },
          { id: '3', text: 'DATE: 2026-06-01', confidence: 0.95, boundingBox: [500, 100, 750, 150] }
        ]
      });
    } catch (e) {
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/60 backdrop-blur-sm">
      <div className="bg-white rounded-2xl shadow-2xl w-full max-w-5xl h-[85vh] flex flex-col overflow-hidden animate-in fade-in zoom-in-95 duration-200">
        
        {/* Header */}
        <div className="flex items-center justify-between px-6 py-4 border-b border-slate-100 bg-slate-50/50">
          <div>
            <h3 className="text-lg font-bold text-slate-800">OCR 검증 (절대 픽셀 좌표)</h3>
            <p className="text-sm text-slate-500">Image Key: {imageKey}</p>
          </div>
          <button onClick={onClose} className="p-2 bg-white rounded-full text-slate-500 hover:text-slate-800 shadow-sm border border-slate-200 hover:bg-slate-50 transition-all">
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Content Area */}
        <div className="flex-1 flex overflow-hidden bg-slate-100/50">
          
          {/* Main Image viewer */}
          <div className="flex-1 p-6 overflow-auto flex items-center justify-center relative">
            {loading ? (
              <div className="flex flex-col items-center">
                 <div className="w-10 h-10 border-4 border-indigo-200 border-t-indigo-600 rounded-full animate-spin"></div>
                 <p className="mt-4 text-slate-500 font-medium animate-pulse">이미지 및 OCR 결과를 불러오는 중...</p>
              </div>
            ) : data ? (
              <div className="relative shadow-lg border border-slate-200 bg-white" style={{ width: data.imageWidth, height: data.imageHeight, minWidth: data.imageWidth, minHeight: data.imageHeight, transform: 'scale(0.8)', transformOrigin: 'center center' }}>
                <img 
                  src={data.imageUrl} 
                  alt="Original Document" 
                  className="absolute inset-0 w-full h-full object-cover opacity-80"
                />
                
                {/* SVG Overlay for drawing boxes using absolute pixel coordinates [x_min, y_min, x_max, y_max] */}
                <svg className="absolute inset-0 pointer-events-none" width={data.imageWidth} height={data.imageHeight}>
                  {data.layoutData.map((box) => {
                    const [x_min, y_min, x_max, y_max] = box.boundingBox;
                    const width = x_max - x_min;
                    const height = y_max - y_min;
                    const isHovered = hoveredBox === box.id;

                    return (
                      <g key={box.id} className="pointer-events-auto cursor-pointer" onMouseEnter={() => setHoveredBox(box.id)} onMouseLeave={() => setHoveredBox(null)}>
                        {/* Box outline */}
                        <rect
                          x={x_min}
                          y={y_min}
                          width={width}
                          height={height}
                          fill={isHovered ? "rgba(99, 102, 241, 0.2)" : "rgba(59, 130, 246, 0.1)"}
                          stroke={isHovered ? "#4f46e5" : "#3b82f6"}
                          strokeWidth={isHovered ? "3" : "2"}
                          className="transition-all duration-200"
                        />
                        
                        {/* Tooltip on hover */}
                        {isHovered && (
                          <foreignObject x={x_min} y={Math.max(0, y_min - 40)} width="300" height="40">
                            <div className="bg-slate-900 text-white text-xs px-2 py-1.5 rounded shadow-lg truncate inline-block">
                              {box.text} <span className="text-indigo-300 ml-1">({Math.round(box.confidence * 100)}%)</span>
                            </div>
                          </foreignObject>
                        )}
                      </g>
                    );
                  })}
                </svg>
              </div>
            ) : (
               <p className="text-slate-400">데이터를 찾을 수 없습니다.</p>
            )}
          </div>

          {/* Sidebar for text data */}
          <div className="w-80 bg-white border-l border-slate-200 flex flex-col h-full shadow-[-4px_0_15px_-5px_rgba(0,0,0,0.05)]">
            <div className="p-4 border-b border-slate-100 bg-slate-50/80">
              <h4 className="font-semibold text-slate-700">추출된 텍스트 항목</h4>
              <p className="text-xs text-slate-500 mt-1">상자를 클릭하거나 호버하여 확인하세요.</p>
            </div>
            <div className="flex-1 overflow-y-auto p-4 space-y-3">
              {data?.layoutData.map(box => (
                <div 
                  key={box.id} 
                  className={`p-3 rounded-xl border transition-all cursor-pointer ${hoveredBox === box.id ? 'border-indigo-400 bg-indigo-50 shadow-sm' : 'border-slate-100 hover:border-indigo-200 hover:bg-slate-50'}`}
                  onMouseEnter={() => setHoveredBox(box.id)}
                  onMouseLeave={() => setHoveredBox(null)}
                >
                  <p className="text-sm font-medium text-slate-800">{box.text}</p>
                  <p className="text-xs text-slate-500 mt-1">Confidence: {(box.confidence * 100).toFixed(1)}%</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
