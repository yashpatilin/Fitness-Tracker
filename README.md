# 🏋️ AI-Powered Fitness Tracking Platform

A scalable microservices-based fitness tracking platform that monitors user activities and generates personalized fitness recommendations using **Google Gemini AI**. The system follows an **event-driven architecture** powered by **Apache Kafka** and is designed using modern backend engineering practices.

---

## 🚀 Features

* User Registration & Management
* Fitness Activity Tracking
* AI-Powered Personalized Recommendations
* Event-Driven Communication using Kafka
* Service Discovery with Eureka
* Centralized Configuration Management
* API Gateway Routing
* PostgreSQL Data Persistence
* RESTful API Architecture
* Scalable Microservices Design

---

## 🏗️ System Architecture

```text
                    ┌─────────────────┐
                    │     Client      │
                    └────────┬────────┘
                             │
                             ▼
                  ┌────────────────────┐
                  │    API Gateway     │
                  └────────┬───────────┘
                           │
        ┌──────────────────┼──────────────────┐
        ▼                  ▼                  ▼

 ┌─────────────┐   ┌─────────────┐   ┌─────────────┐
 │ User Service│   │Activity Svc │   │ AI Service │
 └─────────────┘   └──────┬──────┘   └──────┬──────┘
                           │                │
                           ▼                │
                     ┌──────────┐           │
                     │  Kafka   │───────────┘
                     └──────────┘
                                             ▼
                                    ┌────────────────┐
                                    │ Google Gemini │
                                    └────────────────┘
```

---

## 🛠️ Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Spring WebClient

### Microservices

* Spring Cloud Gateway
* Netflix Eureka
* Spring Cloud Config Server

### Messaging

* Apache Kafka

### Database

* PostgreSQL

### AI Integration

* Google Gemini API

### Build & Tools

* Maven
* Git
* GitHub
* Postman

---

## 📦 Services

### 1. User Service

Manages user registration and user-related operations.

### 2. Activity Service

Tracks user fitness activities and publishes events to Kafka.

### 3. AI Service

Consumes Kafka events and generates AI-powered recommendations using Google Gemini.

### 4. Gateway Service

Single entry point for all incoming client requests.

### 5. Eureka Server

Handles service registration and discovery.

### 6. Config Server

Provides centralized configuration management.

---

## 🔄 Event Flow

1. User performs a fitness activity.
2. Activity Service stores activity data.
3. Activity event is published to Kafka.
4. AI Service consumes the event.
5. Activity data is sent to Google Gemini AI.
6. AI-generated recommendation is stored.
7. Recommendation is retrieved through API endpoints.

---

## 📡 API Endpoints

### User Service

#### Register User

```http
POST /api/users/register
```

#### Get User

```http
GET /api/users/{userId}
```

---

### Activity Service

#### Track Activity

```http
POST /api/activities/track
```

#### Get Activity

```http
GET /api/activities/{activityId}
```

---

### AI Service

#### Get Recommendation by User

```http
GET /api/recommendations/user/{userId}
```

#### Get Recommendation by Activity

```http
GET /api/recommendations/activity/{activityId}
```

---

## 🗄️ Database

### PostgreSQL

Stores:

* User Information
* Activity Records
* AI Recommendations

---

## 🔥 Kafka Integration

Topic:

```text
activity-events
```

Producer:

* Activity Service

Consumer:

* AI Service

---

## ⚙️ Running the Project

### Start Services in Order

1. Config Server
2. Eureka Server
3. Kafka Server
4. User Service
5. Activity Service
6. AI Service
7. API Gateway

---

## 🎯 Key Highlights

* Microservices Architecture
* Event-Driven Design
* AI Integration with Gemini
* Scalable Backend System
* Service Discovery & Centralized Configurations
* Production-Oriented Design Patterns

---

## 🚀 Future Enhancements

* JWT Authentication & Authorization
* Redis Caching
* Docker Containerization
* Kubernetes Deployment
* Fitness Analytics Dashboard
* Wearable Device Integration
* Real-Time Notifications
* AI-Based Fitness Progress Prediction

---

## 👨‍💻 Author

**Yash Patil**

Software Engineering | Java Backend Development | Microservices | Kafka | Spring Boot | AI Integration

---

### ⭐ If you found this project interesting, consider giving it a star!
