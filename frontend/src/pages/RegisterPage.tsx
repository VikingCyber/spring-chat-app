import { useState } from 'react';
import api from '../api/api';
import { useNavigate } from 'react-router-dom';

type FormState = {
  email: string;
  username: string;
  password: string;
  confirmPassword: string;
};

type FormErrors = Partial<Record<keyof FormState, string>>;

export default function RegisterPage() {
  
  const navigate = useNavigate();
  const [form, setForm] = useState<FormState>({
    email: '',
    username: '',
    password: '',
    confirmPassword: ''
  });

  const [errors, setErrors] = useState<FormErrors>({});
  const [success, setSuccess] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors(prev => ({ ...prev, [e.target.name]: '' }));
    setSuccess('');
  };

  const validate = (): boolean => {
    const newErrors: FormErrors = {};

    if (!form.email) newErrors.email = 'Email обязателен';
    else if (!/\S+@\S+\.\S+/.test(form.email)) newErrors.email = 'Неверный email';

    if (!form.username) newErrors.username = 'Имя обязательно';
    else if (form.username.length < 3) newErrors.username = 'Минимум 3 символа';

    if (!form.password) newErrors.password = 'Пароль обязателен';
    else if (form.password.length < 8) newErrors.password = 'Минимум 8 символов';

    if (!form.confirmPassword) newErrors.confirmPassword = 'Подтвердите пароль';
    else if (form.confirmPassword !== form.password) newErrors.confirmPassword = 'Пароли не совпадают';

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validate()) return;

    setIsSubmitting(true);
    try {
      await api.post('auth/register', {
        email: form.email,
        username: form.username,
        password: form.password,
      });
      setSuccess('Регистрация прошла успешно!');
      setTimeout(() => {
        navigate('/login');
      }, 1500);
      
      setForm({ email: '', username: '', password: '', confirmPassword: '' });
      setErrors({});
    } catch (err: any) {
      setErrors({ email: err.response?.data?.message || 'Ошибка регистрации' });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className='flex items-center justify-center min-h-screen bg-gradient-to-b from-blue-50 via-slate-100 to-gray-200'>
      <div className="text-center bg-white p-8 rounded-2xl shadow-lg w-full max-w-md animate-fade-in">
        <h2 className="text-2xl font-bold mb-6 text-center">Регистрация</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            name="username"
            value={form.username}
            onChange={handleChange}
            placeholder="Имя пользователя"
            error={errors.username}
          />
          <Input
            type="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            placeholder="Email"
            error={errors.email}
          />
          <Input
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            placeholder="Пароль"
            error={errors.password}
          />
          <Input
            type="password"
            name="confirmPassword"
            value={form.confirmPassword}
            onChange={handleChange}
            placeholder="Подтверждение пароля"
            error={errors.confirmPassword}
          />
          {form.confirmPassword.length > 0 && form.password.length > 0 && (
            form.password === form.confirmPassword ? (
              <p className="text-green-600 text-sm">Пароли совпадают ✅</p>
            ) : (
              <p className="text-red-600 text-sm">Пароли не совпадают ❌</p>
            )
          )}
          {success && <p className="text-green-600 text-sm">{success}</p>}
          <button
            type="submit"
            disabled={isSubmitting}
            className={`w-full py-2 text-white rounded cursor-pointer ${isSubmitting ? 'bg-gray-500' : 'bg-blue-600 hover:bg-blue-700'}`}
          >
            {isSubmitting ? 'Регистрация...' : 'Зарегистрироваться'}
          </button>
        </form>
      </div>
    </div>
  );
}

type InputProps = {
  type?: string;
  name: string;
  value: string;
  placeholder: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
};

function Input({ type = "text", name, value, placeholder, onChange, error }: InputProps) {
  return (
    <div>
      <input
        type={type}
        name={name}
        value={value}
        placeholder={placeholder}
        onChange={onChange}
        className={`w-full px-3 py-2 border ${error ? 'border-red-500' : 'border-gray-300'} rounded`}
      />
      {error && <p className="text-red-500 text-sm mt-1">{error}</p>}
    </div>
  );
}
