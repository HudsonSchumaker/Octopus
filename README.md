# Octopus
Sure, here's a brief description for your project:

---

**Project Description:**

The framework, named Octopus, provides a lightweight and modular structure for developing rest services. It includes custom annotations for defining application components such as controllers and services, and an IoC (Inversion of Control) container for managing dependencies.

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
**Sample Data:**
```json
{
"name": "Sample Product",
"description": "This is a sample product description.",
"price": 19.99
}
```
---

