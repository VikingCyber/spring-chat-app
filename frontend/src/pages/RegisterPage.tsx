import { useState } from 'react';
import api from '../api/api';

type FormState = {
  email: string;
  username: string;
  password: string;
  confirmPassword: string;
};

type FormErrors = Partial<Record<keyof FormState, string>>;

export default function RegisterPage() {
  const [form, setForm] = useState<FormState>({
    email: '',
    username: '',
    password: '',
    confirmPassword: ''
  });

  const [errors, setErrors] = useState<FormErrors>({});
  const [success, setSuccess] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Универсальный обработчик изменения полей
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors(prev => ({ ...prev, [e.target.name]: '' })); // Сброс ошибки на поле
    setSuccess('');
  };

  // Простая валидация формы
  const validate = (): boolean => {
    const newErrors: FormErrors = {};

    if (!form.email) {
      newErrors.email = 'Email обязателен';
    } else if (!/\S+@\S+\.\S+/.test(form.email)) {
      newErrors.email = 'Неверный формат email';
    }

    if (!form.username) {
      newErrors.username = 'Имя пользователя обязательно';
    } else if (form.username.length < 3) {
      newErrors.username = 'Минимум 3 символа';
    }

    if (!form.password) {
      newErrors.password = 'Пароль обязателен';
    } else if (form.password.length < 6) {
      newErrors.password = 'Минимум 6 символов';
    }

    if (!form.confirmPassword) {
      newErrors.confirmPassword = 'Подтвердите пароль';
    } else if (form.confirmPassword !== form.password) {
      newErrors.confirmPassword = 'Пароли не совпадают';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSuccess('');
    if (!validate()) return;

    setIsSubmitting(true);
    try {
      await api.post('auth/register', {
        email: form.email,
        username: form.username,
        password: form.password,
      });
      setSuccess('Регистрация прошла успешно!');
      setForm({ email: '', username: '', password: '', confirmPassword: '' });
      setErrors({});
    } catch (err: any) {
      setErrors({ email: err.response?.data?.message || 'Ошибка регистрации' });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: '0 auto' }}>
      <h2>Регистрация</h2>
      <form onSubmit={handleSubmit} noValidate>
        <input
          name="username"
          placeholder="Имя пользователя"
          value={form.username}
          onChange={handleChange}
          style={{ display: 'block', marginBottom: 5, width: '100%' }}
        />
        {errors.username && <p style={{ color: 'red', marginTop: 0 }}>{errors.username}</p>}

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          style={{ display: 'block', marginBottom: 5, width: '100%' }}
        />
        {errors.email && <p style={{ color: 'red', marginTop: 0 }}>{errors.email}</p>}

        <input
          type="password"
          name="password"
          placeholder="Пароль"
          value={form.password}
          onChange={handleChange}
          style={{ display: 'block', marginBottom: 5, width: '100%' }}
        />
        {errors.password && <p style={{ color: 'red', marginTop: 0 }}>{errors.password}</p>}

        <input
          type="password"
          name="confirmPassword"
          placeholder="Подтверждение пароля"
          value={form.confirmPassword}
          onChange={handleChange}
          style={{ display: 'block', marginBottom: 5, width: '100%' }}
        />
        {errors.confirmPassword && <p style={{ color: 'red', marginTop: 0 }}>{errors.confirmPassword}</p>}

        {/* Индикатор совпадения паролей */}
        {form.confirmPassword && !errors.confirmPassword && (
          <p style={{ color: 'green', marginTop: 0, marginBottom: 10 }}>Совпадает ✅</p>
        )}

        {success && <p style={{ color: 'green' }}>{success}</p>}

        <button
          type="submit"
          disabled={isSubmitting}
          style={{
            backgroundColor: '#333',
            color: '#fff',
            padding: '10px 15px',
            border: 'none',
            cursor: isSubmitting ? 'not-allowed' : 'pointer',
            width: '100%',
            marginTop: 10
          }}
        >
          {isSubmitting ? 'Регистрация...' : 'Зарегистрироваться'}
        </button>
      </form>
    </div>
  );
}
