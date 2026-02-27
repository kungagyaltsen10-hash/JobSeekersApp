# AI Job Auto Apply Backend (Spring Boot 3)

Production-ready backend scaffold for an AI Job Auto Apply platform focused on the Indian job market.

## Stack
- Java 17 + Spring Boot 3
- PostgreSQL
- Redis cache
- Spring Security + JWT
- Quartz Scheduler
- Docker + Docker Compose

## Clean Architecture Layout

```text
com.jobseekers
├── controller          # HTTP layer
├── service             # use-case contracts
├── service.impl        # business logic
├── domain              # entities + enums + scheduler job
├── repository          # persistence interfaces
├── dto                 # request/response contracts
├── mapper              # domain ↔ dto mapping layer
├── security/jwt        # auth infra
├── infra               # storage/email adapters
├── config              # security, quartz, bootstrap
└── common              # global api + exception handling
```

## Core Modules Implemented
1. Authentication (register/login, JWT, role-based)
2. User profile (get/update)
3. Location preferences
4. Resume upload + metadata storage
5. Job + Job location entities (mock seeded)
6. Application tracking (duplicate-safe)
7. Matching engine framework (skill/experience/location scoring)
8. Auto apply daily Quartz job (threshold + duplicate guard)
9. Notification module (entity + SMTP email trigger)

## Run Locally
```bash
cd backend
mvn spring-boot:run
```

## Run via Docker Compose
From repo root:
```bash
cp .env.example .env
docker compose up --build
```

## Key APIs
- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`
- `GET /api/v1/users/me`
- `PUT /api/v1/users/me`
- `POST /api/v1/location-preferences`
- `GET /api/v1/location-preferences/me`
- `POST /api/v1/resumes/upload`
- `GET /api/v1/jobs`
- `POST /api/v1/applications/{jobId}`
- `GET /api/v1/applications/me`
- `GET /api/v1/notifications/me`

## Notes
- Quartz runs daily at `02:00` by default.
- DB schema reference is provided in `src/main/resources/schema.sql`.
- Role seeding (`USER`, `ADMIN`) and one mock job are auto-initialized at startup.
