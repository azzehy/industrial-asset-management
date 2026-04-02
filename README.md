# Spring Boot Microservices – Industrial Asset Management

This repository contains the latest source code for a **Spring Boot microservices-based Industrial Asset Management system**.

You can watch the tutorial on YouTube here.

## Services Overview

* **Machine Service**
  Manages industrial machines, their configurations, specifications, and lifecycle.

* **WorkOrder Service**
  Handles creation and processing of work orders related to machine operations, maintenance, and tasks.

* **MachineAvailability Service**
  Tracks machine availability, status (active, down, under maintenance), and capacity across the system.

* **Notification Service**
  Sends alerts and notifications for important events such as machine downtime, maintenance schedules, or work order updates.

* **API Gateway**
  Entry point for all client requests using Spring Cloud Gateway MVC.

* **Management Frontend (Angular 18)**
  User interface for monitoring machines, managing work orders, and tracking availability.

## Tech Stack

The technologies used in this project are:

* Spring Boot
* Angular
* MongoDB
* MySQL
* Kafka
* Keycloak
* Testcontainers with WireMock
* Grafana Stack (Prometheus, Grafana, Loki, and Tempo)
* API Gateway using Spring Cloud Gateway MVC
* Kubernetes

## Application Architecture

image

## How to Run the Frontend Application

Make sure you have the following installed on your machine:

* Node.js
* NPM
* Angular CLI

Run the following commands to start the frontend application:

```bash
cd frontend
npm install
npm run start
```

---

If you want, I can also **adapt the backend naming (packages, topics, DB schemas, Kafka events)** to fully match this industrial domain—this makes your project look much more professional during your presentation.
