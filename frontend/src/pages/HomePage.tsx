import { Link } from "react-router-dom";

export default function HomePage() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-b from-blue-50 via-slate-100 to-gray-200">
      <div className="text-center bg-white p-8 rounded-2xl shadow-lg w-full max-w-md animate-fade-in">
        <h1 className="text-4xl font-bold mb-4 text-gray-800">⚙️ Viking Chat</h1>
        <p className="text-lg text-gray-600 mb-6">Простой и быстрый чат для общения.</p>

        <div className="space-y-4">
          <Link to="/login">
            <button className="w-full mb-2 px-4 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition cursor-pointer">
              Войти
            </button>
          </Link>
          <Link to="/register">
            <button className="w-full px-4 py-3 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition cursor-pointer">
              Регистрация
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
}
