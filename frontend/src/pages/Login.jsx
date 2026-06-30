import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ScanFace, Lock, User, ArrowRight } from 'lucide-react';
import { cn } from '../utils/cn';

export default function Login() {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = (e) => {
    e.preventDefault();
    setIsLoading(true);
    // Mock login delay
    setTimeout(() => {
      localStorage.setItem('token', 'mock-token-xyz');
      navigate('/dashboard');
    }, 1000);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-900 relative overflow-hidden">
      {/* Background Micro-animations & Gradients */}
      <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-indigo-600 rounded-full blur-[120px] opacity-30 animate-pulse" />
      <div className="absolute bottom-[-10%] right-[-10%] w-[40%] h-[40%] bg-blue-500 rounded-full blur-[120px] opacity-30 animate-pulse" style={{ animationDelay: '2s' }} />

      <div className="relative z-10 bg-slate-800/60 backdrop-blur-xl border border-slate-700 p-10 rounded-3xl shadow-2xl w-full max-w-md">
        <div className="flex flex-col items-center mb-10">
          <div className="w-16 h-16 bg-gradient-to-br from-indigo-500 to-blue-500 rounded-2xl flex items-center justify-center shadow-lg mb-4">
            <ScanFace className="text-white w-8 h-8" />
          </div>
          <h1 className="text-3xl font-extrabold text-white tracking-tight">OCR Gateway</h1>
          <p className="text-slate-400 mt-2 text-sm">대용량 비동기 이미지 처리 솔루션</p>
        </div>

        <form onSubmit={handleLogin} className="space-y-6">
          <div className="space-y-4">
            <div className="group relative">
              <User className="absolute left-4 top-3.5 w-5 h-5 text-slate-400 group-focus-within:text-indigo-400 transition-colors" />
              <input
                type="text"
                placeholder="Manager ID"
                required
                className="w-full bg-slate-900/50 border border-slate-700 text-white rounded-xl py-3 pl-12 pr-4 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all placeholder:text-slate-500"
              />
            </div>
            <div className="group relative">
              <Lock className="absolute left-4 top-3.5 w-5 h-5 text-slate-400 group-focus-within:text-indigo-400 transition-colors" />
              <input
                type="password"
                placeholder="Password"
                required
                className="w-full bg-slate-900/50 border border-slate-700 text-white rounded-xl py-3 pl-12 pr-4 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-transparent transition-all placeholder:text-slate-500"
              />
            </div>
          </div>

          <button
            type="submit"
            disabled={isLoading}
            className={cn(
              "w-full flex items-center justify-center gap-2 bg-gradient-to-r from-indigo-500 to-blue-600 text-white font-semibold py-3.5 rounded-xl transition-all hover:scale-[1.02] hover:shadow-lg hover:shadow-indigo-500/25 active:scale-95",
              isLoading && "opacity-80 cursor-not-allowed transform-none"
            )}
          >
            {isLoading ? (
              <div className="w-5 h-5 border-2 border-white/30 border-t-white rounded-full animate-spin" />
            ) : (
              <>
                로그인 <ArrowRight className="w-5 h-5" />
              </>
            )}
          </button>
        </form>
      </div>
    </div>
  );
}
