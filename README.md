# Java Learning Project: A Beginner's Guide

Welcome! This project is designed to be your first step into modern Java development with Spring Boot. We'll start with the absolute basics and build up to more complex features, giving you a solid foundation for a career in software development.

## Getting Started: Running the Application

Follow these steps to get the project running on your local machine.

#### Prerequisites

*   **Java 17** (or later): Make sure you have a JDK installed.
*   **Maven**: This project uses Maven for dependency management. It's usually bundled with modern IDEs like IntelliJ IDEA or VS Code.
*   **An IDE**: An Integrated Development Environment like IntelliJ IDEA, VS Code, or Eclipse is highly recommended.

#### Configuration

This project is configured to use a temporary, in-memory H2 database. **You do not need to install or configure any external database.** The database will be created automatically when you start the application and will be cleared every time you stop it.

1.  **Clone the Repository:**
    ```bash
    git clone <repository_url>
    cd java_learning
    ```

2.  **Set Up the Database:**
    *   Create a new database in PostgreSQL. For example, you can name it `java_learning_db`.
    *   Open the `src/main/resources/application.properties` file.
    *   Update the following lines with your database connection details:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
        spring.datasource.username=your_postgres_username
        spring.datasource.password=your_postgres_password
        spring.jpa.hibernate.ddl-auto=update
        ```
        *   `ddl-auto=update` will automatically create and update the database tables based on your `Entity` classes.

#### Running the App

You can run the application in two main ways:

1.  **From your IDE:**
    *   Open the project in your IDE (e.g., IntelliJ IDEA).
    *   Locate the `JavaLearningApplication.java` file in `src/main/java/com/learning/java_learning`.
    *   Right-click on the file and select "Run 'JavaLearningApplication.main()'".

2.  **From the command line using Maven:**
    *   Open a terminal in the root directory of the project.
    *   Run the following command:
        ```bash
        ./mvnw spring-boot:run
        ```
        (On Windows, you might need to use `mvnw.cmd spring-boot:run`)

The application will start, and you should see log output ending with a line like `Started JavaLearningApplication in ... seconds`.

#### Accessing the API & Database

*   **API (Swagger UI):** Open your web browser and go to `http://localhost:8080/swagger-ui.html`
*   **Database (H2 Console):** Open your web browser and go to `http://localhost:8080/h2-console`
    *   **JDBC URL:** Make sure the JDBC URL matches the one in `application.properties`. It should be `jdbc:h2:mem:java_learning_db`.
    *   **Username:** `sa`
    *   **Password:** Leave this blank.
    *   Click "Connect". You can now see the tables (`USER`, `BLOG`, etc.) and even run SQL queries!

## Productivity Tips & Code Generation

### Using Cursor's AI Features

Cursor provides powerful AI-assisted code generation. Here are some useful shortcuts:

1. **Entity Generation:**
   - Type `@Entity` and press Tab
   - Or use the command: `Ctrl+K` (Cmd+K on Mac) and type "create JPA entity with fields: [your fields]"
   - Example: "create JPA entity with fields: id (Long), name (String), email (String), createdAt (LocalDateTime)"

2. **Repository Generation:**
   - Type `@Repository` and press Tab
   - Or use `Ctrl+K` and type "create Spring Data JPA repository for [entity name]"

3. **Service Layer:**
   - Type `@Service` and press Tab
   - Or use `Ctrl+K` and type "create service class with CRUD methods for [entity name]"

4. **Controller Generation:**
   - Type `@RestController` and press Tab
   - Or use `Ctrl+K` and type "create REST controller with CRUD endpoints for [entity name]"

### Common Code Templates

Here are some useful code templates you can type followed by Tab:

```java
// Entity templates
@entity         -> Creates basic entity class structure
@table          -> Adds table configuration
@column         -> Adds column configuration
@manytoone      -> Adds Many-to-One relationship
@onetomany      -> Adds One-to-Many relationship

// Repository templates
@repo           -> Creates JPA repository interface
@query          -> Adds custom query method

// Service templates
@service        -> Creates service class structure
@transactional  -> Adds transaction configuration

// Controller templates
@restcontroller -> Creates REST controller structure
@getmapping     -> Adds GET endpoint
@postmapping    -> Adds POST endpoint
@putmapping     -> Adds PUT endpoint
@deletemapping  -> Adds DELETE endpoint
```

### Using AI for Complex Tasks

For more complex code generation, you can use Cursor's AI features:

1. **Press `Ctrl+K` (Cmd+K on Mac) and describe what you want:**
   - "Create a complete entity class for User with validation annotations"
   - "Generate CRUD repository methods with custom queries"
   - "Create a service method that implements pagination"

2. **Inline Code Generation (Press Tab after typing):**
   - Type `getter` -> Generates getter methods
   - Type `setter` -> Generates setter methods
   - Type `constructor` -> Generates constructor
   - Type `builder` -> Generates builder pattern code

3. **Documentation Generation:**
   - Type `javadoc` and press Tab -> Generates documentation template
   - Or use `Ctrl+K` and type "add javadoc to this class/method"

Remember to review and adjust the generated code as needed. The AI is a helpful tool but might need fine-tuning to match your specific requirements.

## Learning Path

We'll take a structured approach, starting with a simple, self-contained feature and gradually adding layers of complexity.

1.  **Core Concepts (Our First Feature):** We've built a complete `Blog` feature for you. We'll walk through how it works, from the database entity to the API controller. This is a classic CRUD (Create, Read, Update, Delete) implementation, which is the cornerstone of most web applications.
2.  **Your First Exercise:** You will create your own feature—a `Comment` system for the blogs. This will solidify your understanding of the concepts from the `Blog` example.
3.  **User Authentication:** Next, we'll explore the user authentication system that's already in this project. You'll see how to secure your application and ensure that only the right people can perform certain actions.
4.  **Advanced Topics:** Once you've mastered the fundamentals, you'll be ready to tackle more advanced topics like connecting to external services (OAuth) and building real-time features (Kafka and WebSockets).

## Our First Feature: A Simple Blog

We have implemented a complete feature that allows you to create, read, update, and delete blog posts. This is a great way to understand the key components of a Spring Boot application.

### How It Works: The Layers of the Application

*   **`BlogController.java` (`controller` package):** This is the front door to our feature. It defines the API endpoints (URLs) that the outside world can interact with (e.g., `/api/blogs`). When a request comes in, the controller calls the appropriate method in the `BlogService`.
*   **`BlogService.java` (`service` package):** This is the brain of our feature. It contains the business logic. For example, the `createBlog` method takes the data from the controller, creates a new `Blog` object, and tells the `BlogRepository` to save it.
*   **`BlogRepository.java` (`repository` package):** This is the component that talks to the database. It extends `JpaRepository`, which gives us a set of ready-to-use methods for database operations (`save()`, `findById()`, `findAll()`, etc.).
*   **`Blog.java` (`entity` package):** This is the blueprint for our `blogs` table in the database. Each field in this class (e.g., `title`, `content`) corresponds to a column in the table.
*   **`BlogRequest.java` (`dto.request` package):** This is a Data Transfer Object (DTO). We use it to shape the data that we expect to receive in an API request. It ensures that users can't pass in fields we don't want them to (like the `createdAt` timestamp).

### A Note on Best Practices: The Order of Creation

When you build your own features, following a "bottom-up" approach is highly recommended. The order above explains how the pieces connect, but here is the best order to **create** the files for a new feature:

1.  **Entity:** Define your data structure first.
2.  **DTOs:** Define how your data will look for API requests and responses.
3.  **Repository:** Create the interface to access the data in the database.
4.  **Service:** Write the business logic that uses the repository.
5.  **Controller:** Expose your service's logic to the world via API endpoints.

---

## Your First Exercises

It's time to get your hands dirty! We'll start with some simple exercises to get you comfortable with the codebase.

### Exercise 1: Create a `Comment` Entity

Your first task is to create an entity for blog comments. This will teach you how to define a data model and establish relationships between different entities.

**Tasks:**

1.  **Create the `Comment.java` file** in the `entity` package.
2.  **Add the following fields:**
    *   `id` (Long, Primary Key, GeneratedValue)
    *   `content` (String, should not be null)
    *   `author` (String)
    *   `createdAt`, `updatedAt` (just like in the `Blog` entity)
3.  **Link it to the `Blog` entity:** A comment belongs to a blog post. You need to add a field in `Comment.java` to represent this relationship.
    *   **Hint:** Use the `@ManyToOne` annotation. You'll also want to add `@JoinColumn(name = "blog_id")` to specify the foreign key column in the `comments` table.

### Exercise 2: Build the `Comment` CRUD Logic

Now that you have your `Comment` entity, let's build the logic to manage comments.

**Tasks:**

1.  **Create `CommentRepository.java`** in the `repository` package. It should extend `JpaRepository<Comment, Long>`.
2.  **Create `CommentService.java`** in the `service` package. Implement methods to:
    *   `getCommentsForBlog(Long blogId)`
    *   `addCommentToBlog(Long blogId, CommentRequest commentRequest)`
3.  **Create `CommentController.java`** in the `controller` package, mapped to `/api/blogs/{blogId}/comments`. Create endpoints that use your service methods.
4.  **Create a `CommentRequest` DTO** for adding new comments.

### Exercise 3: Introduction to Security

Let's dip our toes into security. We're going to make it so that only users with an "ADMIN" role can delete a blog post.

**Tasks:**

1.  Go to `BlogController.java`.
2.  Find the `deleteBlog` method.
3.  Add the following annotation above the method: `@PreAuthorize("hasRole('ADMIN')")`
4.  You'll also need to enable method-level security. In `SecurityConfig.java`, ensure the `@EnableMethodSecurity` annotation is present above the class definition. (It's already there for you!)

Now, if you try to delete a blog post with a regular user's token, you'll get a "Forbidden" error.

---

Good luck, and remember that building things one step at a time is the key to mastering any new skill!

## JPA Entity Guide

### Field Validations

Here's a comprehensive guide to field validations in JPA entities:

| Annotation | Use Case | Example | Description |
|------------|----------|---------|-------------|
| `@NotNull` | Basic null check | `@NotNull(message = "Field cannot be null")` | Ensures value is not null |
| `@NotBlank` | String content check | `@NotBlank(message = "Name is required")` | Ensures string has content (not null or empty) |
| `@NotEmpty` | Collection/Array check | `@NotEmpty(message = "List cannot be empty")` | Ensures collection/array is not empty |
| `@Size` | Length/size constraints | `@Size(min = 2, max = 50)` | Validates length of strings or size of collections |
| `@Min` | Minimum number value | `@Min(value = 0)` | Validates minimum number value |
| `@Max` | Maximum number value | `@Max(value = 100)` | Validates maximum number value |
| `@Email` | Email format | `@Email(message = "Invalid email")` | Validates email format |
| `@Pattern` | Regex pattern | `@Pattern(regexp = "^[A-Za-z0-9]*$")` | Validates string against regex pattern |
| `@DecimalMin` | Minimum decimal | `@DecimalMin("0.01")` | Validates minimum decimal value |
| `@DecimalMax` | Maximum decimal | `@DecimalMax("999.99")` | Validates maximum decimal value |
| `@Digits` | Number format | `@Digits(integer = 6, fraction = 2)` | Validates number of digits |

### JPA Relationships and Fetch Types

Understanding when to use different fetch types is crucial for application performance:

| Relationship | Fetch Type | Use Case | Example |
|-------------|------------|-----------|---------|
| `@OneToOne` | EAGER | When child entity is essential and small | User -> UserProfile |
| `@OneToOne` | LAZY | When child entity is large or rarely needed | User -> UserSettings |
| `@OneToMany` | LAZY (Always!) | Collection of child entities | Blog -> Comments |
| `@ManyToOne` | EAGER | When parent entity is essential | Comment -> Author |
| `@ManyToOne` | LAZY | When parent entity is large | Comment -> Blog |
| `@ManyToMany` | LAZY (Always!) | Many-to-many relationships | User -> Roles |

### Real-World Examples

#### 1. Blog with Comments (One-to-Many)
```java
@Entity
public class Blog {
    @Id
    private Long id;
    
    @NotBlank
    @Size(min = 5, max = 200)
    private String title;
    
    // EAGER: Author is small and frequently needed
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;
    
    // LAZY: Comments could be numerous
    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private List<Comment> comments;
}

@Entity
public class Comment {
    @Id
    private Long id;
    
    @NotBlank
    @Size(min = 10, max = 1000)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Blog blog;
}
```

#### 2. User with Profile (One-to-One)
```java
@Entity
public class User {
    @Id
    private Long id;
    
    @Email
    @NotBlank
    private String email;
    
    // EAGER: Profile is essential and small
    @OneToOne(fetch = FetchType.EAGER)
    private UserProfile profile;
}

@Entity
public class UserProfile {
    @Id
    private Long id;
    
    @NotBlank
    @Size(min = 2, max = 50)
    private String fullName;
    
    @Pattern(regexp = "^\\+[0-9]{10,15}$")
    private String phoneNumber;
}
```

### Best Practices

1. **Fetch Type Selection:**
   - Default to LAZY fetching
   - Use EAGER only when:
     - The related entity is small
     - The data is almost always needed
     - The relationship is essential for the object

2. **Validation Guidelines:**
   - Always validate user input
   - Provide clear error messages
   - Use appropriate validation constraints
   - Combine multiple constraints when needed

3. **Common Combinations:**

| Field Type | Recommended Validations | Example |
|------------|------------------------|---------|
| Name | `@NotBlank` + `@Size` | `@NotBlank @Size(min = 2, max = 50)` |
| Email | `@NotBlank` + `@Email` | `@NotBlank @Email` |
| Phone | `@Pattern` | `@Pattern(regexp = "^\\+[0-9]{10,15}$")` |
| Price | `@NotNull` + `@DecimalMin` + `@Digits` | `@NotNull @DecimalMin("0.0") @Digits(integer=6, fraction=2)` |
| Age | `@NotNull` + `@Min` + `@Max` | `@NotNull @Min(0) @Max(150)` |
| URL | `@URL` + `@NotBlank` | `@URL @NotBlank` |

### Performance Considerations

1. **LAZY Loading:**
   ```java
   @Service
   @Transactional(readOnly = true)
   public class BlogService {
       public BlogDTO getBlog(Long id) {
           Blog blog = blogRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException());
           
           // Explicitly load what you need
           int commentCount = blog.getComments().size();
           
           return new BlogDTO(
               blog.getId(),
               blog.getTitle(),
               blog.getAuthor().getName(),  // EAGER: loads immediately
               commentCount                 // LAZY: loads when accessed
           );
       }
   }
   ```

2. **EAGER Loading:**
   - Use sparingly
   - Consider the impact on query performance
   - Better to use specific JPQL queries when needed

Remember:
- LAZY loading requires an active transaction
- Use DTOs to control data loading
- Consider using specific queries for better performance 

## Builder Pattern & Object Creation

### Using Lombok Builder

The Builder pattern is a clean and fluent way to construct objects. With Lombok, it's easy to implement:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
```

### Why Use Builder?

1. **Readable Object Creation**:
   ```java
   // Without builder (constructor)
   Blog blog = new Blog(null, "Title", author, LocalDateTime.now());
   
   // With builder (more readable!)
   Blog blog = Blog.builder()
       .title("My First Blog")
       .author(currentUser)
       .build();
   ```

2. **Flexible Construction**:
   ```java
   // Build with some fields
   BlogDTO basicDto = BlogDTO.builder()
       .id(1L)
       .title("Title")
       .build();

   // Build with all fields
   BlogDTO fullDto = BlogDTO.builder()
       .id(1L)
       .title("Title")
       .content("Content")
       .authorName("John")
       .createdAt(LocalDateTime.now())
       .build();
   ```

3. **Default Values**:
   ```java
   @Builder
   public class Comment {
       @Builder.Default
       private LocalDateTime createdAt = LocalDateTime.now();
       
       @Builder.Default
       private boolean active = true;
   }
   ```

### Best Practices with Builder

| Scenario | Solution | Example |
|----------|----------|---------|
| Required Fields | Use `@NonNull` | `@NonNull private String title;` |
| Default Values | Use `@Builder.Default` | `@Builder.Default private boolean active = true;` |
| Custom Validation | Use `@Builder.Default` with validation | See example below |
| Complex Objects | Use nested builders | See example below |

### Advanced Builder Examples

1. **Custom Validation in Builder**:
```java
@Data
@Builder
public class UserDTO {
    @Email
    private final String email;
    
    @Builder.Default
    private final String role = "USER";
    
    public static class UserDTOBuilder {
        // Custom validation in builder
        public UserDTO build() {
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Invalid email");
            }
            return new UserDTO(email, role);
        }
    }
}
```

2. **Entity to DTO Conversion**:
```java
@Data
@Builder
public class BlogDTO {
    private Long id;
    private String title;
    private String authorName;
    private int commentCount;
    
    public static BlogDTO fromEntity(Blog blog) {
        return BlogDTO.builder()
            .id(blog.getId())
            .title(blog.getTitle())
            .authorName(blog.getAuthor().getName())
            .commentCount(blog.getComments().size())
            .build();
    }
}
```

3. **Nested Builders**:
```java
@Data
@Builder
public class OrderDTO {
    private final CustomerDTO customer;
    private final List<ItemDTO> items;
    
    @Data
    @Builder
    public static class CustomerDTO {
        private String name;
        private String address;
    }
    
    @Data
    @Builder
    public static class ItemDTO {
        private String name;
        private BigDecimal price;
    }
    
    // Usage:
    OrderDTO order = OrderDTO.builder()
        .customer(CustomerDTO.builder()
            .name("John")
            .address("123 Street")
            .build())
        .items(List.of(
            ItemDTO.builder()
                .name("Item 1")
                .price(new BigDecimal("19.99"))
                .build()
        ))
        .build();
}
```

### Common Builder Patterns

1. **Request DTOs**:
```java
@Data
@Builder
public class CreateBlogRequest {
    @NotBlank
    private String title;
    
    @NotBlank
    private String content;
    
    @Builder.Default
    private List<String> tags = new ArrayList<>();
}
```

2. **Response DTOs**:
```java
@Data
@Builder
public class BlogResponse {
    private Long id;
    private String title;
    private String authorName;
    
    @Builder.Default
    private List<CommentDTO> comments = new ArrayList<>();
    
    public static BlogResponse fromEntity(Blog blog) {
        return BlogResponse.builder()
            .id(blog.getId())
            .title(blog.getTitle())
            .authorName(blog.getAuthor().getName())
            .comments(blog.getComments().stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList()))
            .build();
    }
}
```

Remember:
- Always include `@NoArgsConstructor` and `@AllArgsConstructor` with `@Builder` in entities
- Use `@Builder.Default` for default values
- Consider creating static factory methods for common conversions
- Use nested builders for complex objects
- Validate in the builder when necessary 