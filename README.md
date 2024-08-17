# Octopus
Sure, here's a brief description for your project:

---

**Project Description:**

The framework, named Octopus, provides a lightweight and modular structure for developing web applications. It includes custom annotations for defining application components such as controllers and services, and an IoC (Inversion of Control) container for managing dependencies.

**Key Features:**
- **Annotation-Based Configuration:** Use custom annotations like `@OctopusApp`, `@Controller`, and `@Service` to define application components.
- **IoC Container:** A built-in IoC container for managing service and controller dependencies.
- **Web Server Integration:** Easily start and configure a web server with customizable server port and context.
- **Class Scanning:** Automatically scan and register classes with specific annotations within the defined root package.

**Technologies Used:**
- Java
- Gradle
- Jackson
- MySQL

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

{
"name": "Sample Product",
"description": "This is a sample product description.",
"price": 19.99
}
---

### Developer Manual

#### Overview
This manual provides step-by-step instructions to create an application similar to the one in the `br.com.schumaker.octopus.app` package using the `br.com.schumaker.octopus.framework` package.

#### Prerequisites
- Java Development Kit (JDK) 17 or higher
- IntelliJ IDEA or any other Java IDE
- Gradle build tool
- MySQL database

#### Project Structure
```
src/main/java
├── br/com/schumaker/octopus/app
│   ├── model
│   ├── service
│   ├── controller
│   ├── dto
│   ├── view
│   └── exception
└── br/com/schumaker/octopus/framework
    ├── annotations
    ├── reflection
    ├── web
    └── jdbc
```

#### Step-by-Step Guide

1. **Setup Project with Gradle**
    - Create a new Gradle project.
    - Add necessary dependencies in `build.gradle`.

   ```groovy
   plugins {
       id 'java'
   }

   repositories {
       mavenCentral()
   }

   dependencies {
       implementation 'org.springframework.boot:spring-boot-starter'
       implementation 'mysql:mysql-connector-java'
       testImplementation 'org.springframework.boot:spring-boot-starter-test'
   }
   ```

2. **Define Models**
    - Create model classes in the `br.com.schumaker.octopus.app.model` package.

   ```java
   package br.com.schumaker.octopus.app.model;

   @Table
   public class Product {
       @Pk("id")
       private BigInteger id;

       @Column("name")
       private String name;

       @Column
       private String description;

       @Column("price")
       private double price;

       // Getters and Setters
   }
   ```

3. **Create Repositories**
    - Extend the `DbCrud` class in the `br.com.schumaker.octopus.app.model.db` package.

   ```java

   @Repository
   public class ProductRepository extends DbCrud<BigInteger, Product> {}
   ```

4. **Implement Services**
    - Create service classes in the `br.com.schumaker.octopus.app.service` package.

   ```java
   package br.com.schumaker.octopus.app.service;

   @Service
   public class ProductService {
       private final ProductRepository productRepository;

       public ProductService(ProductRepository productRepository) {
           this.productRepository = productRepository;
       }

       public List<Product> list() {
           return productRepository.findAll();
       }

       public BigInteger save(Product product) {
           return productRepository.save(product);
       }
   }
   ```

5. **Create Controllers**
    - Define controller classes in the `br.com.schumaker.octopus.app.controller` package.

   ```java
   package br.com.schumaker.octopus.app.controller;

   @Controller("/product")
   public class ProductController {
       private final ProductService service;

       public ProductController(ProductService service) {
           this.service = service;
       }

       @Get
       public ResponseView<List<Product>> list() {
           var list = service.list();
           return ResponseView.of(list, 200);
       }
       
       @Post
       public ResponseView<ProductView> create(@Payload ProductDTO dto) {
           var id = service.save(new Product(BigInteger.ONE, dto.name(), dto.description(), dto.price()));
           return ResponseView.of(new ProductView(id, dto.name(), dto.description(), dto.price()), 201);
       }
   }
   ```

6. **Handle Exceptions**
    - Create exception handler classes in the `br.com.schumaker.octopus.app.exception` package.

   ```java
   package br.com.schumaker.octopus.app.exception;

   import br.com.schumaker.octopus.framework.annotations.ExceptionHandler;
   import br.com.schumaker.octopus.framework.annotations.GlobalExceptionHandler;
   import br.com.schumaker.octopus.framework.exception.ErrorView;
   import br.com.schumaker.octopus.framework.web.view.ResponseView;

   import java.io.IOException;

   @GlobalExceptionHandler
   public class AppExceptionHandler {
       @ExceptionHandler
       public ResponseView<String> handle(Exception e) {
           return ResponseView.of("Exception", 300);
       }

       @ExceptionHandler(IOException.class)
       public ResponseView<ErrorView> handle2(IOException e) {
           return ResponseView.of(new ErrorView(e.getMessage(), 400), 400);
       }
   }
   ```

7. **Define DTOs and Views**
    - Create DTO and view classes in the `br.com.schumaker.octopus.app.dto` and `br.com.schumaker.octopus.app.view` packages.

   ```java
   package br.com.schumaker.octopus.app.dto;

   public record ProductDTO(String name, String description, double price) {}
   ```

   ```java
   package br.com.schumaker.octopus.app.view;

   import java.math.BigInteger;

   public record ProductView(BigInteger id, String name, String description, double price) {}
   ```

#### Running the Application
- Use the following command to run the application:
  ```bash
  ./gradlew bootRun
  ```

#### Conclusion
By following these steps, you can create a similar application using the `br.com.schumaker.octopus.framework`. This manual covers the essential components and structure needed to build the application.

This description provides an overview of the project's purpose, features, technologies, and structure.
