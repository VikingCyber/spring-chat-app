import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {
  const { isAuthenticated, logout } = useAuth();

  return (
    <nav className="sticky top-0 z-50 bg-white pb-5 flex items-center justify-between px-6">
      <Link to="/" className="hover:text-blue-600 transition">Главная</Link>
      {!isAuthenticated && (
        <>
          <Link to="/register" className="hover:text-blue-600 transition">Регистрация</Link>
          <Link to="/login" className="hover:text-blue-600 transition">Вход</Link>
        </>
      )}
      {isAuthenticated && (
        <>
          <Link to="/chat" className="hover:text-blue-600 transition">Чат</Link>
          <button
            onClick={logout}
            className="text-red-600 hover:text-red-800 transition cursor-pointer bg-transparent border-none"
          >
            Выход
          </button>
        </>
      )}
    </nav>
  );
}

export default Navbar;