import React, { useState, useEffect } from 'react';
import { Upload, RefreshCw, FileText, CheckCircle2, Loader2, AlertCircle } from 'lucide-react';
import UploadModal from '../components/UploadModal';
import ResultOverlay from '../components/ResultOverlay';
import { getBatchStatus, getBatchItemStatus } from '../api/client';
import { cn } from '../utils/cn';

export default function Dashboard() {
  const [isUploadOpen, setIsUploadOpen] = useState(false);
  const [selectedImage, setSelectedImage] = useState(null);
  const [selectedBatchId, setSelectedBatchId] = useState(null);
  
  const [stats, setStats] = useState({ total: 0, processing: 0, completed: 0 });
  const [batches, setBatches] = useState([]);
  const [items, setItems] = useState([]);
  const [isPolling, setIsPolling] = useState(false);

  // 대시보드 진입 시 배치 목록 가져오기
  useEffect(() => {
    fetchBatches();
  }, []);

  // 특정 배치를 클릭하면 해당 아이템 목록 가져오기
  useEffect(() => {
    if (selectedBatchId) {
      fetchBatchItems(selectedBatchId);
    }
  }, [selectedBatchId]);

  const fetchBatches = async () => {
    try {
      const res = await getBatchStatus({ page: 0, size: 10, status: 'ALL' });
      if (res.success) {
        const content = res.data.content;
        // Transform backend keys to match frontend if necessary, or just use as is
        const mappedBatches = content.map(b => ({
          id: b.batchId,
          name: b.fileName,
          status: b.status,
          total: b.totalCount,
          done: b.completedCount,
          date: b.createdAt
        }));
        setBatches(mappedBatches);

        // Update overall stats based on batches
        let t = 0, p = 0, c = 0;
        mappedBatches.forEach(b => {
          t += b.total;
          if (b.status === 'PROCESSING') p += (b.total - b.done);
          c += b.done;
        });
        setStats({ total: t, processing: p, completed: c });
      }
    } catch (e) {
      console.error("Failed to fetch batches", e);
    }
  };

  const fetchBatchItems = async (batchId) => {
    setIsPolling(true);
    try {
      const res = await getBatchItemStatus(batchId, { page: 0, size: 50 });
      if (res.success && res.data.items) {
        setItems(res.data.items);
      }
    } catch (e) {
      console.error("Failed to fetch batch items", e);
    } finally {
      setIsPolling(false);
    }
  };

  const StatusBadge = ({ status }) => {
    const styles = {
      COMPLETED: 'bg-emerald-100 text-emerald-700 border-emerald-200',
      PROCESSING: 'bg-blue-100 text-blue-700 border-blue-200',
      PENDING: 'bg-slate-100 text-slate-700 border-slate-200'
    };
    const labels = {
      COMPLETED: '완료됨',
      PROCESSING: '처리중',
      PENDING: '대기중'
    };
    return (
      <span className={cn("px-3 py-1 text-xs font-semibold rounded-full border", styles[status])}>
        {labels[status]}
      </span>
    );
  };

  return (
    <div className="space-y-8 animate-in fade-in slide-in-from-bottom-4 duration-500">
      
      {/* Header Area */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-extrabold text-slate-900 tracking-tight">Processing Dashboard</h1>
          <p className="text-slate-500 mt-1">대용량 배치 작업의 실시간 현황을 확인하세요.</p>
        </div>
        <button 
          onClick={() => setIsUploadOpen(true)}
          className="bg-indigo-600 hover:bg-indigo-700 text-white px-5 py-2.5 rounded-xl font-medium flex items-center gap-2 transition-all shadow-lg shadow-indigo-200"
        >
          <Upload className="w-5 h-5" />
          <span>새 배치 업로드</span>
        </button>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 relative overflow-hidden group">
          <div className="absolute -right-4 -bottom-4 w-24 h-24 bg-indigo-50 rounded-full group-hover:scale-150 transition-transform duration-500" />
          <div className="relative">
            <div className="flex items-center gap-3 text-indigo-600 mb-2">
              <FileText className="w-5 h-5" />
              <h3 className="font-semibold">전체 누적 건수</h3>
            </div>
            <p className="text-3xl font-bold text-slate-900">{stats.total.toLocaleString()}</p>
          </div>
        </div>
        
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 relative overflow-hidden group">
          <div className="absolute -right-4 -bottom-4 w-24 h-24 bg-blue-50 rounded-full group-hover:scale-150 transition-transform duration-500" />
          <div className="relative">
            <div className="flex items-center gap-3 text-blue-600 mb-2">
              <Loader2 className="w-5 h-5 animate-spin-slow" />
              <h3 className="font-semibold">현재 처리 중</h3>
            </div>
            <p className="text-3xl font-bold text-slate-900">{stats.processing.toLocaleString()}</p>
          </div>
        </div>

        <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 relative overflow-hidden group">
          <div className="absolute -right-4 -bottom-4 w-24 h-24 bg-emerald-50 rounded-full group-hover:scale-150 transition-transform duration-500" />
          <div className="relative">
            <div className="flex items-center gap-3 text-emerald-600 mb-2">
              <CheckCircle2 className="w-5 h-5" />
              <h3 className="font-semibold">처리 완료</h3>
            </div>
            <p className="text-3xl font-bold text-slate-900">{stats.completed.toLocaleString()}</p>
          </div>
        </div>
      </div>

      {/* Main Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Batch List (Left side, takes 2 columns) */}
        <div className="lg:col-span-2 bg-white rounded-3xl shadow-sm border border-slate-200 overflow-hidden">
          <div className="p-6 border-b border-slate-100 flex items-center justify-between bg-slate-50/50">
            <h2 className="text-xl font-bold text-slate-800">최근 배치 내역</h2>
            <button onClick={fetchBatches} className="text-indigo-600 hover:text-indigo-800 text-sm font-medium flex items-center gap-1 group">
              <RefreshCw className="w-4 h-4 group-hover:rotate-180 transition-transform duration-500" />
              <span>새로고침</span>
            </button>
          </div>
          
          <div className="p-6 space-y-4">
            {batches.length === 0 ? (
              <p className="text-slate-500 text-center py-10">등록된 배치 내역이 없습니다.</p>
            ) : batches.map(batch => (
              <div 
                key={batch.id} 
                onClick={() => setSelectedBatchId(batch.id)}
                className={cn(
                  "p-5 border rounded-2xl hover:border-indigo-300 hover:shadow-md transition-all group cursor-pointer",
                  selectedBatchId === batch.id ? "bg-indigo-50/30 border-indigo-400" : "bg-white border-slate-100"
                )}
              >
                <div className="flex items-center justify-between mb-4">
                  <div className="flex items-center gap-3">
                    <div className="w-10 h-10 rounded-full bg-slate-100 flex items-center justify-center">
                      <FileText className="w-5 h-5 text-slate-500" />
                    </div>
                    <div>
                      <h4 className="font-bold text-slate-800 group-hover:text-indigo-600 transition-colors">{batch.name}</h4>
                      <p className="text-xs text-slate-400 mt-1">{batch.date} • ID: {batch.id}</p>
                    </div>
                  </div>
                  <StatusBadge status={batch.status} />
                </div>
                
                {/* Progress Bar */}
                <div className="space-y-2">
                  <div className="flex justify-between text-sm">
                    <span className="text-slate-500 font-medium">진행률</span>
                    <span className="font-bold text-slate-700">{Math.min(100, Math.round((batch.done / batch.total) * 100))}% ({batch.done}/{batch.total})</span>
                  </div>
                  <div className="h-2.5 w-full bg-slate-100 rounded-full overflow-hidden">
                    <div 
                      className={cn(
                        "h-full rounded-full transition-all duration-1000 bg-gradient-to-r",
                        batch.status === 'COMPLETED' ? "from-emerald-400 to-emerald-500" : "from-indigo-400 to-blue-500"
                      )}
                      style={{ width: `${Math.min(100, (batch.done / batch.total) * 100)}%` }}
                    />
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Detailed Item List (Right side, takes 1 column) */}
        <div className="bg-white rounded-3xl shadow-sm border border-slate-200 overflow-hidden flex flex-col h-[600px]">
          <div className="p-6 border-b border-slate-100 bg-slate-50/50 flex justify-between items-center">
            <div>
              <h2 className="text-lg font-bold text-slate-800">개별 이미지 상태</h2>
              <p className="text-sm text-slate-500 mt-1">{selectedBatchId ? `배치: ${selectedBatchId}` : '배치를 선택해주세요.'}</p>
            </div>
            {isPolling && <Loader2 className="w-4 h-4 animate-spin text-indigo-500" />}
          </div>
          
          <div className="flex-1 overflow-y-auto p-4 space-y-3 bg-slate-50/30">
            {items.length === 0 && !selectedBatchId ? (
              <p className="text-center text-slate-400 py-10 text-sm">왼쪽 목록에서 배치를 선택하세요.</p>
            ) : items.length === 0 ? (
               <p className="text-center text-slate-400 py-10 text-sm">데이터를 가져오는 중이거나 없습니다.</p>
            ) : items.map((item, idx) => (
              <div 
                key={idx} 
                onClick={() => {
                  if(item.status === 'COMPLETED') setSelectedImage(item.imageKey);
                }}
                className={cn(
                  "p-4 rounded-xl border flex items-center justify-between transition-all",
                  item.status === 'COMPLETED' ? "bg-white border-slate-200 hover:border-indigo-300 hover:shadow-sm cursor-pointer" : "bg-slate-50 border-slate-100 opacity-70"
                )}
              >
                <div className="flex items-center gap-3">
                  {item.status === 'COMPLETED' ? <CheckCircle2 className="w-5 h-5 text-emerald-500" /> : <Loader2 className="w-5 h-5 text-blue-500 animate-spin" />}
                  <span className={cn("font-medium text-sm", item.status === 'COMPLETED' ? "text-slate-800" : "text-slate-500")}>
                    {item.imageKey}
                  </span>
                </div>
                {item.status === 'COMPLETED' && (
                  <span className="text-xs font-semibold text-indigo-600 bg-indigo-50 px-2 py-1 rounded-lg">결과보기</span>
                )}
              </div>
            ))}
          </div>
        </div>

      </div>

      <UploadModal 
        isOpen={isUploadOpen} 
        onClose={() => setIsUploadOpen(false)} 
        onSuccess={() => {
          fetchBatches(); // Upload 완료 시 목록 갱신
        }}
      />

      <ResultOverlay
        isOpen={!!selectedImage}
        onClose={() => setSelectedImage(null)}
        imageKey={selectedImage}
      />
    </div>
  );
}
