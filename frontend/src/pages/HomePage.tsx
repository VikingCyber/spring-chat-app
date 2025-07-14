import { Link } from 'react-router-dom';

export default function HomePage() {
  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>Добро пожаловать в Viking Chat!</h1>
      <p>Простой и быстрый чат для общения.</p>
      <div style={{ marginTop: '20px' }}>
        <Link to="/login">
          <button style={{ marginRight: '10px' }}>Войти</button>
        </Link>
        <Link to="/register">
          <button>Регистрация</button>
        </Link>
      </div>
    </div>
  );
}
