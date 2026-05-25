# Spring Boot Authentication System

A simple authentication system built with Spring Boot and Thymeleaf.

## Features

- User registration and login
- Authentication using Spring Security Form Login
- Password encryption with Bcrypt
- Email verification after registration
- Role-based authentication support
- Server-side rendering with Thymeleaf
- Database integration using Spring Data JPA
- In-memory H2 database for development
- Lombok for reducing boilerplate code

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- Lombok
- H2 Database
- Maven

---

## Project Structure

```
src/main/java
├── config
├── controller
├── dto
├── entity
├── repository
├── security
├── service
└── util
```
---

## Authentication Flow

1. User registers an account
2. Password is encrypted using Bcrypt
3. Verification email is sent to the user
4. User clicks verification link
5. Account is activated
6. User can log in using Form Login

---

## Getting Started

### Prerequisites
- Java 17+
- Maven

### Clone the repository
```
git clone https://github.com/dzung-min/mvc-auth-demo.git
cd mvc-auth-demo
```

### Run the application
```
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

### Email Configuration

Configure SMTP settings in application.properties:

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Default Routes

| Route       | Description                         |
|-------------|-------------------------------------|
| `/register` | User registration page              |
| `/login`    | User login page                     |
| `/verify`   | Email verification endpoint         |
| `/reissue`  | Reissue verification email endpoint |
| `/`         | Protected home page                 |

---

## Future Improvements
- JWT authentication
- OAuth2 login
- Password reset via email
- Remember me functionality
- Docker support
- User profile management