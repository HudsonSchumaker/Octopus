## Force - Framework Object-Relational Resource Injection Control (Inversion of Control) Entities
A brief description for your project:

---

**Project Description:**

The framework, named Force, provides a lightweight and modular structure for developing rest services. It includes custom annotations for defining application components such as controllers and services, and an IoC (Inversion of Control) container for managing dependencies.

**Key Features:**
- **Annotation-Based Configuration:** Use custom annotations like `@ForceApp`, `@Controller`, and `@Service` to define application components.
- **IoC Container:** A built-in IoC container for managing service and controller dependencies.
- **ORM Support:** Easily connect to a MySQL, Postgres database and perform CRUD operations using the `@Entity` annotation.
- **Web Server Integration:** Easily start and configure a web server with customizable server port and context.
- **Class Scanning:** Automatically scan and register classes with specific annotations within the defined root package.

**Technologies Used:**
- Java
- Gradle
- Jackson
- MySQL
- Postgres
- JUnit
- Docker

**Project Structure:**
- `src/main/java`: Contains the main application code, including the framework core and annotations.
- `src/test/java`: Contains unit tests for the framework components.

**Getting Started:**
1. Clone the repository.
2. Build the project using Gradle.
3. Run a docker mySQL
```bash
docker run -d -ti --name local-mysql-8 -p 3306:3306 -p 33060:33060 -v mysql-data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=tester mysql:8.0
```
```sql 
mysql -u root -p

CREATE DATABASE octopus;
USE octopus;

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DOUBLE NOT NULL
);
```
4. Run the `Main` class to start the application.
**Sample Data:**
```json
{
  "name": "Sample Product",
  "description": "This is a sample product description.",
  "price": 19.99
}
```
---
## Guide

### 1. `@PathVariable`
- **Purpose**: Used to extract values from the URI and bind them to method parameters.
- **Attributes**:
    - `value`: The name of the path variable.
- **Example**:
  ```java
  @Get("/user/{id}")
  public User getUser(@PathVariable("id") Long userId) {
      // Method implementation
  }
  ```

### 2. `@Patch`
- **Purpose**: Marks a method to handle HTTP PATCH requests.
- **Attributes**:
    - `value`: The URL pattern to which the method should respond.
    - `type`: The content type that the method consumes.
    - `httpCode`: The HTTP status code to return.
- **Example**:
  ```java
  @Patch(value = "/update/{id}", type = "application/json")
  public ResponseView<Entity> updateEntity(@PathVariable Long id, @Payload Map<String, Object> patchMessage) {
      // Method implementation
  }
  ```

### 3. `@Get`
- **Purpose**: Maps HTTP GET requests onto specific handler methods.
- **Attributes**:
    - `value`: The path for the HTTP GET request.
    - `type`: The content type of the response.
    - `httpCode`: The HTTP status code to return.
- **Example**:
  ```java
  @Get(value = "/hello", type = "text/plain")
  public String sayHello() {
      return "Hello, World!";
  }
  ```

### 4. `@Delete`
- **Purpose**: Specifies the HTTP DELETE method.
- **Attributes**:
    - `value`: The path to which the method should respond.
    - `type`: The content type of the request.
    - `httpCode`: The HTTP status code to return.
- **Example**:
  ```java
  @Delete("/my-resource")
  public void deleteResource() {
      // Method implementation
  }
  ```

### 5. `@Column`
- **Purpose**: Specifies the column name in a database that corresponds to the annotated field in an entity class.
- **Attributes**:
    - `value`: The name of the column.
- **Example**:
  ```java
  @Table("my_table")
  public class MyEntity {
      @Pk
      private Long id;

      @Column("name_column")
      private String name;
  }
  ```

### 6. `@Pk`
- **Purpose**: Specifies the primary key column in a database that corresponds to the annotated field in an entity class.
- **Attributes**:
    - `value`: The name of the primary key column.
- **Example**:
  ```java
  @Table("my_table")
  public class MyEntity {
      @Pk("id_column")
      private Long id;

      @Column("name_column")
      private String name;
  }
  ```

### 7. `@Repository`
- **Purpose**: Indicates that an interface is a repository.
- **Attributes**: None.
- **Example**:
  ```java
  @Repository
  public interface MyRepository extends DbCrud<Long, MyEntity> {
      // Repository implementation
  }
  ```

### 8. `@Table`
- **Purpose**: Specifies the table name in a database that corresponds to the annotated entity class.
- **Attributes**:
    - `value`: The name of the table.
- **Example**:
  ```java
  @Table("my_table")
  public class MyEntity {
      @Pk
      private Long id;

      @Column
      private String name;
  }
  ```

### 9. `@ExceptionHandler`
- **Purpose**: Marks a method as an exception handler.
- **Attributes**:
    - `value`: The type of exception that the method handles.
- **Example**:
  ```java
  @GlobalExceptionHandler
  public class MyGlobalExceptionHandler {
      @ExceptionHandler(SomeException.class)
      public void handleSomeException(SomeException ex) {
          // Handle the exception
      }
  }
  ```

### 10. `@GlobalExceptionHandler`
- **Purpose**: Marks a class as a global exception handler.
- **Attributes**: None.
- **Example**:
  ```java
  @GlobalExceptionHandler
  public class MyGlobalExceptionHandler {
      @ExceptionHandler(SomeException.class)
      public void handleSomeException(SomeException ex) {
          // Handle the exception
      }
  }
  ```

### 11. `@NotNull`
- **Purpose**: Marks a field as a not null value.
- **Attributes**:
    - `value`: The validation message.
- **Example**:
  ```java
  public class User {
      @NotNull
      private String name;
  }
  ```

### 12. `@NotEmpty`
- **Purpose**: Marks a field as a not empty value.
- **Attributes**:
    - `value`: The validation message.
- **Example**:
  ```java
  public class User {
      @NotEmpty
      private String name;
  }
  ```

### 13. `@NotBlank`
- **Purpose**: Marks a field as a not blank value.
- **Attributes**:
    - `value`: The validation message.
- **Example**:
  ```java
  public class User {
      @NotBlank
      private String name;
  }
  ```

### 14. `@Min`
- **Purpose**: Marks a field as a minimum value.
- **Attributes**:
    - `value`: The minimum value.
    - `message`: The validation message.
- **Example**:
  ```java
  public class User {
      @Min(value = 18)
      private int age;
  }
  ```

### 15. `@Max`
- **Purpose**: Marks a field as a maximum value.
- **Attributes**:
    - `value`: The maximum value.
    - `message`: The validation message.
- **Example**:
  ```java
  public class User {
      @Max(100)
      private int age;
  }
  ```

### 16. `@Future`
- **Purpose**: Marks a field as a future date.
- **Attributes**:
    - `value`: The validation message.
    - `allowedTypes`: The allowed types for the field.
- **Example**:
  ```java
  public class Invoice {
      @Future
      private Date dueDate;
  }
  ```

### 17. `@Email`
- **Purpose**: Marks a field as an email address.
- **Attributes**:
    - `value`: The validation message.
- **Example**:
  ```java
  public class User {
      @Email
      private String email;
  }
  ```

### 18. `@Past`
- **Purpose**: Marks a field as a past date.
- **Attributes**:
    - `value`: The validation message.
    - `allowedTypes`: The allowed types for the field.
- **Example**:
  ```java
  public class User {
      @Past
      private Date birthDate;
  }

