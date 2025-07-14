import { useEffect, useState } from "react";
import api from "../api/api";
import {type ApiResponse } from "../types/api";

function SecurePage() {
    const [text, setText] = useState('');

    useEffect(() => {
        api.get<ApiResponse<string>>('chat/secure-test')
        .then(res => setText(res.data.data))
        .catch(err => setText(err.response?.data?.message || 'Ошибка доступа'));
    }, []);

    return <h2>{text}</h2>
}

export default SecurePage;