# 01 – Java SE Fundamentals

This branch contains the first stage of the Library Management System project.
The implementation covers Java SE fundamentals and fulfills the requirements of Laboratory 01.

The application is a console-based, in-memory system that models a simple domain with
a one-to-many relationship and demonstrates core Java concepts.

---

## Implemented requirements

### 1. Domain model
- Implemented two business entities connected with a **1:N relationship**:
    - `Author` → `Book`
- Each entity contains multiple fields of different types.
- Implemented:
    - natural ordering (`Comparable`)
    - equality (`equals` / `hashCode`)
    - readable text representation (`toString`)
- Object creation implemented using the **Builder pattern** (Lombok).
- Implemented `BookDto` with a reduced representation of the related entity.

> **Note:**  
> The `Author`–`Book` relationship is a conceptual choice made for the purpose of this laboratory.
> It is assumed that each book is written by a single author.
> The model is intentionally simplified to focus on Java SE concepts rather than real-world
> publishing edge cases.


### 2. Initial data creation
- Domain objects are created at application startup.
- Two-way relationships between entities are preserved.
- All entities and related elements are printed using nested `forEach` operations.

### 3. Stream API – collection processing
- All books are collected into a `Set` using a single Stream API pipeline.
- The resulting collection is printed using a separate stream.

### 4. Stream API – filtering and sorting
- Books are filtered by selected property (`Genre`).
- Filtered results are sorted by a different property and printed.

### 5. Stream API – DTO transformation
- Books are transformed into `BookDto` objects using a Stream API pipeline.
- DTOs are sorted using natural ordering and collected into a `List`.

### 6. Serialization
- Collection of authors is serialized to a binary file.
- Data is deserialized and printed to verify correctness.

### 7. Parallel processing
- Tasks are executed using **parallel Stream API** with a custom `ForkJoinPool`.
- Different thread pool sizes are tested.
- Workload is simulated using `Thread.sleep()`.

---

## Technologies
- Java SE
- Lombok
- Streams API
- Serialization
- ForkJoinPool

---

## Running the application
Run the `Main` class.
All tasks are executed sequentially during application startup and printed to the console.
