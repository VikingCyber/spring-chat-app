import React, { useState } from "react";
import api from "../api/api";
import type { ApiResponse, JwtResponse } from "../types/api";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function LoginPage() {
  const [form, setForm] = useState({ email: "", password: "" });
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await api.post<ApiResponse<JwtResponse>>("auth/login", form);
      const username = res.data.data.username;
      const token = res.data.data.token;
      login(token, username);
      alert("Успешный вход");
      navigate("/chat");
    } catch (err: any) {
      alert(err.response?.data?.message || "Ошибка авторизации");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-b from-blue-50 via-slate-100 to-gray-200">
      <div className="text-center bg-white p-8 rounded-2xl shadow-lg w-full max-w-md animate-fade-in">
        <h2 className="text-2xl font-bold mb-6">Вход</h2>
        <form onSubmit={handleSubmit} className="space-y-4 text-left">
          <div>
            <input
              type="email"
              placeholder="Email"
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 rounded"
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="Пароль"
              value={form.password}
              onChange={(e) => setForm({ ...form, password: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 rounded"
            />
          </div>
          <button
            type="submit"
            className="w-full py-2 text-white bg-blue-600 hover:bg-blue-700 rounded cursor-pointer"
          >
            Войти
          </button>
        </form>
      </div>
    </div>
  );
}

export default LoginPage;
