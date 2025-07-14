import React, { useState } from "react";
import api from "../api/api";
import type { ApiResponse, JwtResponse } from "../types/api";
import { useNavigate } from "react-router-dom";

function LoginPage() {
    const [form, setForm] = useState({ email: '', password: ''});
    const navigate = useNavigate();

    const handleSumbit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const res = await api.post<ApiResponse<JwtResponse>>('auth/login', form);
            localStorage.setItem('token', res.data.data.token);
            alert("Успешный вход");
            navigate("/chat");
        } catch (err: any) {
            alert(err.response?.data?.message || 'Ошибка авторизации');
        }
    };

    return (
        <form onSubmit={handleSumbit}>
            <input placeholder="Email" onChange={e => setForm({...form, email: e.target.value})} />
            <input type="password" placeholder="Password" onChange={e => setForm({...form, password: e.target.value})} />
            <button type="submit">Войти</button>
        </form>
    )
}

export default LoginPage;