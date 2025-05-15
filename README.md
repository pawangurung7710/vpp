
---

#  Virtual Power Plant - Battery Stats System

This Spring Boot application is built to manage and analyze battery information for a **Virtual Power Plant (VPP)**. It provides APIs to store battery data and retrieve statistical insights based on filters like postcode and watt capacity.

---

##  Tech Stack

- **Java**: Java 21
- **Framework**: Spring Boot
- **Database**: MySQL 8.x
- **ORM**: Spring Data JPA
- **Libraries**:
    - Lombok – For reducing boilerplate code
    - AspectJ (Spring AOP) – For logging
- **Build Tool**: Maven
- **Testing**:
    - JUnit 5
    - Mockito
    - Testcontainers (for integration tests)
    - Docker (for local DB in integration tests)

---

##  Features

### 1. Add Batteries

Allows adding multiple batteries with their name, postcode, and watt capacity.

**Endpoint:** `POST /api/batteries`

**Request Body Example:**
```json
[
  {
    "name": "Cannington",
    "postcode": "6107",
    "capacity": 13500
  },
  {
    "name": "Midland",
    "postcode": "6057",
    "capacity": 50500
  },
  {
    "name": "Hay Street",
    "postcode": "6000",
    "capacity": 23500
  }
]
```

**Response:**
```json
{
  "result": "Approved",
  "code": "VPP_S00",
  "message": "Batteries added successfully",
  "status": true,
  "developerMsg": "Success",
  "count": 3
}
```

---

### 2. Get Battery Statistics

Fetches aggregated stats (total & average watt capacity) filtered by postcode range and optional watt capacity range.

**Endpoint:** `GET /api/batteries`

**Query Parameters:**

| Parameter       | Description                          |
|----------------|--------------------------------------|
| `postcodeStart` | Start of postcode range              |
| `postcodeEnd`   | End of postcode range                |
| `minCapacity`  | Minimum watt capacity (optional)     |
| `maxCapacity`  | Maximum watt capacity (optional)     |

**Example Request:**
```
GET /api/batteries?postcodeStart=6000&postcodeEnd=6200&minCapacity=10000&maxCapacity=60000
```

**Response:**
```json
{
  "result": "Approved",
  "code": "VPP_S00",
  "message": "Successfully retrieved battery stats",
  "status": true,
  "developerMsg": "Success",
  "batteryNames": [
    "Cannington",
    "Midland"
  ],
  "totalWattCapacity": 180004.0,
  "averageWattCapacity": 90002.0
}
```

---

##  How to Run Locally

### Prerequisites

- Java 21
- Maven
- MySQL 8.x installed and running (or Docker for integration tests)
- IDE (optional): IntelliJ IDEA, Eclipse, or VS Code

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/pawangurung7710/vpp
   cd vpp
   ```

2. **Configure database connection:**

   Update `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/vpp
   spring.datasource.username=root
   spring.datasource.password=root
   spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
   ```

3. **Create Database Schema Manually**

   A SQL script is provided to create the database and table:

    - **SQL File**: `sql/batteries.sql`
    - **Action**: Run this file to initialize the schema.
    - **Update URL**: Ensure `spring.datasource.url` matches your local DB settings.

4. **Build and Run Application**

   ```bash
   ./mvnw spring-boot:run 
   ```

5. **Access API at:**
   ```
   http://localhost:9000
   ```

---

##  Testing Strategy

| Type | Description |
|------|-------------|
| ✅ **JUnit 5** | Core testing framework |
| ✅ **Mockito** | Mocking dependencies in service layer |
| ✅ **Test Coverage** |
| - Service Layer | Fully tested |
| - Controller Layer | Partially tested with mocks |
| - Repository Layer | Covered by integration tests or assumed covered by Spring Data JPA |
| ✅ **Integration Tests** | Written using **Testcontainers** and real MySQL container |
| ✅ **Validation & Error Cases** | Unit and integration tests include validation failures and edge cases |

---

##  Integration Testing with Docker

This project uses **Testcontainers**, which requires **Docker ** to be running locally during test execution.

### Why Docker?

- Ensures tests run against a real MySQL database
- Avoids conflicts with local databases
- Makes tests portable and repeatable

### Running Integration Tests

Make sure Docker is running, then execute:

```bash
./mvnw test
```

Or run specific test classes via IDE or command line.

---

##  Architectural Decisions

| Component | Decision | Rationale |
|----------|----------|-----------|
| **Connection Pooling & Web Server Tuning** | Used `spring.datasource.hikari.pool-name=VppHikariPool`, configured Tomcat and HikariCP for high concurrency | Ensures system can handle massive battery registration load; improves throughput and traceability in logs |
| **Transactional Save** | `@Transactional` used in service layer | Ensures atomic save operation; rollback on failure |
| **AOP-based Logging** | Implemented via Spring AOP | Logs method entry/exit, execution time, and exceptions without polluting business logic |
| **Custom Exception Handling** | Used `VppException` class | Ensures consistent error responses across APIs |
| **Layered Architecture** | Controller → Service → Repository | Separation of concerns, testability |
| **DTO Mapping** | Static mapping methods used | Clean separation between domain models and API response |
| **Unit Testing** | Mockito + JUnit 5 | Covers core logic, isolates dependencies |
| **Integration Testing** | Testcontainers used | Tests full flow including DB interaction |
---



