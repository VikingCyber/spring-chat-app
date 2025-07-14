import { Link, useNavigate} from 'react-router-dom';

function Navbar() {
    const navigate = useNavigate();

    const logout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <nav>
            <Link to="/">Домашняя страница</Link> |
            <Link to="/register">Регистрация</Link> |
            <Link to="/login">Вход</Link> |
            <Link to="/secure">Защищенная</Link> |
            <Link to="/chat">Чат</Link> |
            <button onClick={logout}>Выход</button>
        </nav>
    );
}

export default Navbar;
