# Home Services Booking API

A Spring Boot REST API where a user can browse home services (cleaning, plumbing, electrician, etc.) and book one.

## Tech stack

- Java 17
- Spring Boot 3.3 (Web, Data JPA, Validation)
- MySQL 8
- springdoc-openapi (Swagger UI)
- JUnit 5 + Mockito for unit tests

## Project structure

```
src/main/java/com/adityax/homeservices
├── controller   REST endpoints
├── service      Business logic
├── repository   Spring Data JPA repositories
├── entity       JPA entities (HomeService, Booking, BookingStatus)
├── dto          Request/response objects (entities are never returned directly)
├── exception    Custom exceptions + global exception handler
└── config       DataLoader that preloads services on startup
```

## Setup

### 1. Prerequisites

- JDK 17 or newer
- MySQL 8 running on `localhost:3306`
- Maven 3.9+ (or an IDE with built-in Maven, such as Eclipse / Spring Tools or IntelliJ IDEA)

### 2. Database configuration

The app connects to a database called `home_services_db` and creates it automatically if it does not exist. Tables are created from the entities (`spring.jpa.hibernate.ddl-auto=update`).

The default credentials are `root` / `root`. To use different ones, set environment variables before starting the app:

```
set DB_USERNAME=root
set DB_PASSWORD=your_password
```

Or edit `spring.datasource.username` / `spring.datasource.password` in `src/main/resources/application.properties`.

### 3. Run

```
mvn spring-boot:run
```

Or import it into Eclipse / Spring Tools as an existing Maven project, then Run As → Spring Boot App.

The app starts on `http://localhost:8080`. On the first start, 10 services are inserted into the `home_services` table.

### 4. Run the tests

```
mvn test
```

## API documentation

- Swagger UI: http://localhost:8080/swagger-ui.html
- Postman collection: `postman/HomeServicesBooking.postman_collection.json`

## Endpoints

| Method | URL | Description |
|---|---|---|
| GET | `/api/services` | List all services |
| GET | `/api/services?search=clean` | Search services by name |
| GET | `/api/services/{id}` | Get one service (404 if not found) |
| POST | `/api/bookings` | Create a booking (status `PENDING`) |
| GET | `/api/bookings` | List all bookings |
| PATCH | `/api/bookings/{id}/cancel` | Cancel a booking |

### Create booking request

```json
{
  "serviceId": 1,
  "name": "Ravi Kumar",
  "phone": "9876543210",
  "address": "12 MG Road, Pune",
  "date": "2026-12-20",
  "timeSlot": "10:00 AM - 12:00 PM"
}
```

Validation rules:

- `name` and `address` cannot be empty
- `phone` must be exactly 10 digits
- `date` (format `yyyy-MM-dd`) cannot be in the past
- `serviceId` must refer to an existing service

### Validation error response (400)

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Name cannot be empty",
    "phone": "Phone must be exactly 10 digits",
    "address": "Address cannot be empty",
    "date": "Date cannot be in the past"
  },
  "timestamp": "2026-10-16T10:15:30.123"
}
```

### Not found response (404)

```json
{
  "status": 404,
  "message": "Service not found with id 999",
  "errors": {},
  "timestamp": "2026-10-16T10:15:30.123"
}
```

## Assumptions

- There is no login, so `GET /api/bookings` returns all bookings.
- Time slot is free text (e.g. `10:00 AM - 12:00 PM`).
- Only a `PENDING` booking can be cancelled. Cancelling an already cancelled booking returns 400.
