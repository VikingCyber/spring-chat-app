💬 Spring Chat App
Чат-приложение с WebSocket, JWT-аутентификацией, REST API, React SPA и PostgreSQL.

🚀 Технологии
Backend: Spring Boot, Spring Security, WebSocket (STOMP), JWT, PostgreSQL, Hibernate

Frontend: React + TypeScript + Vite

Документация: OpenAPI 3 (Swagger UI) + AsyncAPI (WebSocket)

База данных: PostgreSQL (локально или через Docker)

⚙️ Как запустить
1. Клонирование проекта
git clone https://github.com/your-username/spring-chat-app.git
cd spring-chat-app

2. База данных
Вариант A: локально
CREATE DATABASE chat_app;

Настрой подключение в application.yml заменив плейсхоледры на реальные значения:
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/chat_app
    username: postgres
    password: postgres

Вариант B: через Docker

docker run --name chat-postgres -e POSTGRES_DB=chat_app \
  -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 -d postgres:15

3. Backend

cd backend
./mvnw clean install
./mvnw spring-boot:run
Swagger UI будет доступен по адресу:
📄 http://localhost:8080/swagger-ui/index.html

4. Frontend

cd frontend
npm install
npm run dev

Приложение будет работать на:
🌐 http://localhost:5173

🔐 Аутентификация
POST /api/auth/register – регистрация

POST /api/auth/login – логин, возвращает JWT

Добавляй заголовок Authorization: Bearer <токен> для защищённых запросов.

📚 Документация
REST API: Swagger UI (/swagger-ui/index.html)

WebSocket: AsyncAPI спецификация (/asyncapi.yaml)

Описание моделей: аннотации @Schema, @Operation, @ApiResponse в DTO и контроллерах

