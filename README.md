# Cab-O-Meter

Employee cab booking for office commutes. This revision modernizes the original Java 7 / Spring 3.2 / MySQL / Ext JS 4 app to:

- **Java 25** and **Spring Boot 4.1**
- **PostgreSQL 16** with Flyway migrations
- **React 19** UI (Vite) that covers the old Sencha screens

## What you can do

| Role | Demo login | Screens |
| --- | --- | --- |
| Employee | `PC0014` / `password` | Cab request and cancellation |
| Manager | `PC0001` / `password` | Approve / reject team requests |
| Trip manager | `PC0011` / `password` | Search approved requests and assign cabs |
| Admin | `PC0013` / `password` | All of the above plus employee search |

## Run it

### 1. PostgreSQL

```bash
docker compose up -d postgres
```

Or a local Postgres 16 instance with database `cab_o_meter`, user `cabwala`, password `secret`.

### 2. Backend

Requires JDK 25 and Maven 3.9+.

```bash
export JAVA_HOME=/path/to/jdk-25
./mvnw spring-boot:run
```

Flyway creates tables and loads the original employee seed plus demo cabs, drivers, addresses, and sample requests.

API: `http://localhost:8080/api`

### 3. React UI (development)

```bash
cd frontend
npm install
npm run dev
```

Vite proxies `/api` to the Spring Boot server. Open `http://localhost:5173`.

### Production-style single jar

```bash
cd frontend && npm install && npm run build
cd .. && ./mvnw -DskipTests package
java -jar target/cab-o-meter.jar
```

The Vite build writes into `src/main/resources/static`, so the jar serves the UI and API together.

## Tests

```bash
createdb cab_o_meter_test   # if it does not already exist
./mvnw test
```

Tests use PostgreSQL at `jdbc:postgresql://localhost:5432/cab_o_meter_test`.

## API sketch

- `POST /api/login` `{ employeeId, password }`
- `GET /api/user`
- `GET /api/employees?name=`
- `POST /api/cab-requests`
- `GET /api/cab-requests`
- `POST /api/cab-requests/{id}/cancel`
- `GET /api/manager/requests?status=pending|approved|declined`
- `POST /api/manager/requests/{id}/approve`
- `POST /api/manager/requests/{id}/reject`
- `GET /api/trip-requests`
- `GET /api/cabs`
- `POST /api/trips`

## Layout

```
src/main/java/com/sangeeth/cab   Spring Boot API
src/main/resources/db/migration  PostgreSQL Flyway scripts
frontend                         React UI
docker-compose.yml               Postgres
```
