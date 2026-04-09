# Industrial Asset Management Platform

<div align="center">

<img src="./Frontend/screenshots/Industrial%20asset%20management%20platform%20logo.png" width="300" height="300">

</div>

The **Industrial Asset Management Platform** is a microservices-based system for tracking **industrial machines**, **availability**, and **maintenance work orders**. It is designed for teams that need a central catalog of assets, scheduling of inspections or tasks, and **event-driven notifications** when work orders are created. The web experience combines secure sign-in with dashboards to register machines, view the fleet, and place work orders tied to the signed-in technician.

---

## 📁 Table of Contents

- [🗺️ Software Architecture](#software-architecture)
- [🐳 Docker Configuration](#docker-configuration)
- [💻 Frontend](#frontend)
- [🔙 Backend](#backend)
- [🚀 Getting Started](#getting-started)
- [✨ Features](#features)
- [🤝 Contributing](#contributing)
- [👩‍💻 Contributors](#contributors)
- [🌟 Future Enhancements](#future-enhancements)

---

<a id="software-architecture"></a>

## 🗺️ Software Architecture

**Industrial Asset Management Platform — Architecture**  
*(Add your architecture diagram image here.)*

The platform consists of the following components:

| Layer | Description |
|--------|-------------|
| **Frontend** | **Angular 18** with **Tailwind CSS** for a responsive SPA; authenticates via **Keycloak** (OpenID Connect) and calls APIs through the gateway. |
| **Backend** | Multiple **Spring Boot 4** microservices: **machine**, **work order**, **machine availability**, **notification**, plus **API Gateway** and **Eureka** discovery. |
| **Databases** | **MongoDB** for machine documents; **MySQL** for work orders and availability data (separate services/schemas). |
| **Messaging** | **Apache Kafka** for domain events (e.g. `workorder-placed`) consumed by the notification service. |
| **Identity** | **Keycloak** for users, realms, and **JWT** issuance validated by the API Gateway. |
| **APIs** | **REST** over HTTP; gateway routes `/api/machine`, `/api/workorder`, and `/api/machine-availability/**`. |
| **Observability** | Optional stack via Compose under the gateway module: **Prometheus**, **Grafana**, **Loki**, **Tempo** (for metrics, dashboards, logs, and traces in local demos). |

---

<a id="docker-configuration"></a>

## 🐳 Docker Configuration

The project uses **Docker Compose** in **per-service folders** (databases, Kafka, Keycloak, observability) so you can start only what you need. There is **no single root `docker-compose.yml`** in the repository yet; below is an **illustrative** `docker-compose.yml` that shows how the pieces fit together for a full local stack (adjust image names, build contexts, and passwords to match your setup).

```yaml
version: "3.8"

services:
  mongodb:
    image: mongo:7
    container_name: iam_mongodb
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: password
      MONGO_INITDB_DATABASE: machine-service
    volumes:
      - mongodb_data:/data/db
    networks:
      - iam-network
    healthcheck:
      test: ["CMD", "mongosh", "--eval", "db.adminCommand('ping')"]
      interval: 10s
      timeout: 5s
      retries: 5

  mysql-workorder:
    image: mysql:8.3
    container_name: iam_mysql_workorder
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: workorder_service
    networks:
      - iam-network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  mysql-availability:
    image: mysql:8.3
    container_name: iam_mysql_availability
    ports:
      - "3307:3306"
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: machineavailability_service
    networks:
      - iam-network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    networks:
      - iam-network

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    networks:
      - iam-network

  # Keycloak + observability: see Backend/api-gateway/docker-compose.yml for a ready-made example

networks:
  iam-network:
    driver: bridge

volumes:
  mongodb_data:
```

**Existing Compose files in the repo (reference):**

- `Backend/machine-service/docker-compose.yml` — MongoDB  
- `Backend/workorder-service/docker-compose.yml` — MySQL, Zookeeper, Kafka, Schema Registry, Kafka UI  
- `Backend/machineavailability-service/docker-compose.yml` — MySQL  
- `Backend/api-gateway/docker-compose.yml` — Keycloak, MySQL for Keycloak, Prometheus, Grafana, Loki, Tempo  

---

<a id="frontend"></a>

## 💻 Frontend

### 🌐 Technologies Used

- **Angular 18** — SPA framework for components, routing, and HTTP clients.  
- **Tailwind CSS** — Utility-first styling (see `Frontend/package.json`).  
- **TypeScript** — Typed components and services.  
- **angular-auth-oidc-client** — OpenID Connect / OAuth2 login flow with **Keycloak**.  
- **RxJS** — Async streams for auth state and API calls.

### 🔑 Highlights

- **Secure login** via Keycloak before loading machine data.  
- **Machine list** and navigation to register new machines.  
- **Work order scheduling** from the home view (e.g. inspection type) using the authenticated user’s profile.  
- **Gateway-backed API** calls to `http://localhost:9000` for machines and work orders.

---

<a id="backend"></a>

## 🔙 Backend

### 🛠 Technologies Used

- **Spring Boot 4.0.x** (Java **21**) — All microservices and the gateway.  
- **Spring Cloud** (Eureka client, Gateway MVC, Resilience4j) — Discovery, routing, circuit breaking.  
- **Spring Data** — MongoDB (machines), JPA/MySQL (work orders, availability).  
- **Spring Kafka** — Producing and consuming `workorder-placed` events.  
- **Spring Security OAuth2 Resource Server** — JWT validation on the gateway.  
- **SpringDoc / OpenAPI** — API documentation (aggregated for the machine service through the gateway).  
- **Testcontainers** — Integration tests with real MongoDB, MySQL, and Kafka containers.

### 🔒 Key Features

**Authentication and security**

- **JWT** validated at the **API Gateway** using the Keycloak **issuer URI**.  
- Public paths for Swagger UI, OpenAPI docs, and Actuator (as configured); other routes require authentication.

**API endpoints (representative)**

- **Machine service** — `POST/GET /api/machine`, `DELETE /api/machine/{id}`.  
- **Work order service** — `POST/GET /api/workorder`, `GET /api/workorder/{id}`.  
- **Machine availability service** — availability checks and listings under `/api/machine-availability/...`.

**Resilience and integration**

- **Circuit breakers** on gateway routes and on the work-order → availability client.  
- **Kafka** decouples work-order creation from **email notifications** (Mailtrap or SMTP configurable).

---

<a id="getting-started"></a>

## 🚀 Getting Started

### ✅ Prerequisites

- **Docker** and **Docker Compose** (for databases, Kafka, Keycloak, observability).  
- **Node.js** and **npm** (LTS recommended; compatible with Angular 18).  
- **Java JDK 21** and **Maven 3.8+**.  
- **Git**.

### 🛠 Steps

**1. Clone the repository**

```bash
git clone https://github.com/azzehy/industrial-asset-management.git
cd "industrial-asset-management"
```

**2. Start infrastructure** (from each folder as needed; fix any typos in env vars in existing compose files, e.g. `MYSQL_ROOT_PASSWORD`)

```bash
cd Backend/machine-service && docker compose up -d
cd ../workorder-service && docker compose up -d
cd ../machineavailability-service && docker compose up -d
cd ../api-gateway && docker compose up -d
```

Align **JDBC and MongoDB URIs** in each service’s `application.yaml` with your actual host ports and passwords.

**3. Run the backend** (Eureka first, then machine, machine availability, work order, notification, then API Gateway)

```bash
cd Backend
mvn clean install
cd discovery-server && mvn spring-boot:run
# In separate terminals for each: machine-service, machineavailability-service,
# workorder-service, notification-service, api-gateway
```

Typical ports: **8761** (Eureka), **8080** (machine), **8081** (work order), **8082** (availability), **8083** (notification), **9000** (gateway), **8181** (Keycloak mapped from container).

**4. Start the frontend**

```bash
cd Frontend
npm install
npm start
```

Open **http://localhost:4200/** and sign in through Keycloak using a realm and client configured to match `Frontend/src/app/config/auth.config.ts`.

---

<a id="features"></a>

## ✨ Features

- **Machine registry** — Register, list, and remove industrial machines (MongoDB-backed).  
- **Machine availability** — Query availability by serial number; list available or certified machines.  
- **Work orders** — Create maintenance or inspection work orders linked to machines and technicians.  
- **Event-driven notifications** — Kafka event on work order placement triggers email notification workflow.  
- **Service discovery** — Eureka for registering microservices.  
- **Unified API entry** — Spring Cloud Gateway with JWT security and fallback responses when downstream services fail.  
- **Observability-ready** — Prometheus metrics on several services; sample Grafana/Prometheus/Loki/Tempo via gateway Docker Compose.

---

<a id="contributing"></a>

## 🤝 Contributing

Contributions are welcome. Please **fork** the repository, create a **feature branch**, and open a **pull request** with a clear description of your changes.

---



<a id="future-enhancements"></a>

## 🌟 Future Enhancements

- **Native mobile app** — Companion app for technicians in the field.  
- **Single root Docker Compose** — One command to spin up the full stack with corrected Keycloak DB hostnames and secrets via `.env`.  
- **CI/CD** — Automated build, test, and image publish (Jenkins, GitHub Actions, or GitLab CI).  
- **Kubernetes manifests** — Declarative deployment for test and production.  
- **Operational dashboards** — Pre-built Grafana boards for SLOs and business KPIs.  
- **Multi-tenant or multi-site** — Support for several plants or organizations in one deployment.

