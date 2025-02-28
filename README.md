# MarketOrderlye

MarketOrderlye – это Spring Boot приложение для управления заказами и продуктами. Оно поддерживает регистрацию и аутентификацию пользователей, управление клиентами, заказами и товарами.

## 📌 Функциональность
- **Аутентификация и регистрация** (JWT-токены)
- **Управление клиентами** (CRUD-операции)
- **Создание заказов**
- **Работа с товарами** (добавление, поиск, просмотр)
- **Защищенные API-маршруты**

## 🏗️ Структура проекта

- **MarketOrdersApplication** – главный класс запуска приложения.
- **Controller Layer** – обработка HTTP-запросов:
  - `AuthenticationController`
  - `RegistrationController`
  - `CustomerController`
  - `OrderController`
  - `ProductController`
- **Service Layer** – бизнес-логика:
  - `AuthenticationService`
  - `RegistrationService`
  - `CustomerService`
  - `OrderService`
  - `ProductService`
- **Repository Layer** – работа с базой данных:
  - `CustomerRepository`
  - `OrderRepository`
  - `ProductRepository`
- **Entity Layer** – модели данных:
  - `Customer`, `Order`, `Product`, `OrderItem`
- **Security Layer** – безопасность:
  - `JwtUtil`, `JwtFilter`, `SecurityConfig`
- **DTO Layer** – передачи данных:
  - `LoginRequest`, `RegistrationDTO`, `CustomerDTO`, `CreateOrderRequest`, `ProductDTO`
- **Validator Layer** – валидация входных данных
- **Exception Handling** – обработка ошибок

## 🚀 Установка и запуск
### 1. Клонирование репозитория
```sh
git clone <репозиторий>
cd MarketOrderlye
```

### 2. Настройка базы данных
- Убедитесь, что у вас настроена база данных (например, PostgreSQL, MySQL или H2)
- В файле `application.properties` укажите параметры подключения:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/market_orderlye
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Сборка и запуск
#### Через Gradle
```sh
./gradlew build
java -jar build/libs/MarketOrderlye.jar
```

#### Через IDE (IntelliJ IDEA, Eclipse)
- Открыть проект
- Запустить `MarketOrdersApplication.java`

## 🛠 API Маршруты
| Маршрут               | Метод | Описание |
|----------------------|------|----------|
| `/auth/login`       | POST | Авторизация пользователя |
| `/auth/register`    | POST | Регистрация пользователя |
| `/customers`        | GET  | Получение списка клиентов |
| `/customers/{id}`   | GET  | Получение клиента по ID |
| `/orders`          | POST | Создание заказа |
| `/products`        | POST | Добавление нового товара |
| `/products`        | GET  | Получение всех товаров |
| `/products/{id}`   | GET  | Получение товара по ID |
| `/products/search` | GET  | Поиск товара по имени |

## 🔒 Безопасность
Приложение использует **JWT-аутентификацию**. Для доступа к защищенным маршрутам:
1. Авторизоваться через `/auth/login` и получить JWT-токен.
2. Передавать этот токен в заголовке `Authorization` при запросах:
   ```
   Authorization: Bearer <your_jwt_token>
   ```

## 📌 Дополнительно
- Логирование запросов хранится в папке `logs/`
- Конфигурация безопасности (`SecurityConfig`) управляет доступом к маршрутам.

📌 **Разработано с использованием Spring Boot, Spring Security, Hibernate, и JWT.**

