# Dish Dex API

## Overview
As someone who loves to cook, I wanted to dedicate this ongoing project as a useful and easy-to-use tool to those who like to stay organized with their recipes. The DishDex API is a Spring Boot-based RESTful API that allows users to manage recipes. Users can create, retrieve, update, and delete recipes, as well as manage associated ingredients.

## Features
- CRUD operations for Recipes
- CRUD operations for Ingredients
- Sorting recipes by cook time
- Supports pagination for retrieving recipes
- Filters recipes by ingredients
- Implements efficient sorting algorithms for optimized data retrieval

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Lombok
- Jackson

## Project Structure
```
recipeapi/
├── controller/               # REST Controllers
│   ├── IngredientController.java
│   ├── RecipeController.java
│
├── model/                    # Entity models
│   ├── Ingredient.java
│   ├── Recipe.java
│
├── repository/               # Data repositories
│   ├── IngredientRepository.java
│   ├── RecipeRepository.java
│
├── service/                  # Business logic services
│   ├── IngredientService.java
│   ├── RecipeService.java
│   ├── IngredientPriorityComparator.java
│
├── RecipeapiApplication.java # Main application entry point
```

## Setup and Installation
### Prerequisites
Ensure you have the following installed:
- Java 17 or later
- Maven (Install manually if needed)
- PostgreSQL (or any preferred database)

### Steps to Run Locally
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/dishdex-api.git
   cd dishdex-api
   ```
2. Ensure Maven is installed manually if you do not use the Maven wrapper.
3. Configure the database in `src/main/resources/application.yml`:
   ```yaml
   spring:
       application:
           name: dishdex-api
       datasource:
           url: jdbc:postgresql://localhost:5432/recipes
           username: your_db_user
           password: your_db_password
       jpa:
           database-platform: org.hibernate.dialect.PostgreSQLDialect
           generate-ddl: true
           show-sql: true
           hibernate:
               ddl-auto: update
           properties:
               hibernate:
                   globally_quoted_identifiers: true
                   dialect: org.hibernate.dialect.PostgreSQLDialect
                   format_sql: true
   server:
       port: 8080
       error:
           path: /user/error
           whitelabel:
               enabled: false
   ```
4. Build the application using Maven:
   ```sh
   mvn install
   ```
5. Run the application:
   ```sh
   java -jar target/*.jar
   ```
   or, if using Spring Boot Run:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### Recipe Endpoints
| Method | Endpoint               | Description |
|--------|------------------------|-------------|
| GET    | `/api/recipes`         | Get paginated list of recipes |
| GET    | `/api/recipes/{id}`    | Get a recipe by ID |
| GET    | `/api/recipes/filter`  | Filter recipes based on ingredients|
| POST   | `/api/recipes`         | Create a new recipe |
| PUT    | `/api/recipes/{id}`    | Update an existing recipe |
| DELETE | `/api/recipes/{id}`    | Delete a recipe |

### Ingredient Endpoints
| Method | Endpoint                           | Description |
|--------|------------------------------------|-------------|
| GET    | `/api/ingredients/recipe/{id}`    | Get ingredients by recipe ID |
| POST   | `/api/ingredients/{recipeId}`     | Add an ingredient to a recipe |
| PUT    | `/api/ingredients/{ingredientId}` | Update an ingredient |
| DELETE | `/api/ingredients/{ingredientId}` | Delete an ingredient |

