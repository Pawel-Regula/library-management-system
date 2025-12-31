# 02 – Spring Backend (JPA + REST API)

This branch contains the second stage of the Library Management System project.
The implementation focuses on building a backend application using Spring Boot,
with persistence and a RESTful API.

The application exposes HTTP endpoints for managing authors and books and
demonstrates a layered backend architecture with clear separation of responsibilities.

---

## Key features

### Backend architecture (Spring MVC)
- Spring Boot application with a layered structure:
    - Controller layer (REST API)
    - Service layer (business logic)
    - Repository layer (data access)
- Clear separation of concerns between layers.

### Persistence
- JPA entities with a bidirectional **1:N relationship**:
    - `Author` --> `Book`
- Spring Data JPA repositories.
- In-memory database configuration (H2).
- Data initializer for sample data.

### REST API
- RESTful endpoints for managing authors and books.
- Hierarchical resource structure (e.g. authors and their books).
- Standard CRUD operations exposed via HTTP methods.
- Proper HTTP response codes (`200`, `201`, `204`, `404`).

### DTO layer
- Dedicated DTOs for:
    - create / update operations,
    - read (single resource),
    - list views.
- DTO mapping is performed in the controller layer.
- DTOs used to decouple API contracts from persistence models.

### API testing
- HTTP request files included for testing endpoints
  (IntelliJ HTTP Client / REST Client).
- Example requests for common operations.

---

## Technologies

- Java 21
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- H2 Database
- Lombok
- Maven

---

## Running the application
Run the `LibraryApplication` class.

The application starts an embedded web server and exposes REST endpoints.
Example HTTP requests can be found in the request files included in the project.
