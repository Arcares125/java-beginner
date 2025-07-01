## Add this to the .vscode folder

{
  "JPA Entity": {
    "prefix": "@entity",
    "scope": "java",
    "body": [
      "package ${1:com.learning.java_learning.entity};",
      "",
      "import lombok.Data;",
      "import lombok.Builder;",
      "import lombok.NoArgsConstructor;",
      "import lombok.AllArgsConstructor;",
      "import jakarta.persistence.*;",
      "import jakarta.validation.constraints.*",
      "import java.time.LocalDateTime;",
      "",
      "@Data",
      "@Builder",
      "@NoArgsConstructor",
      "@AllArgsConstructor",
      "@Entity",
      "@Table(name = \"${2:table_name}\")",
      "public class ${3:EntityName} {",
      "",
      "    @Id",
      "    @GeneratedValue(strategy = GenerationType.IDENTITY)",
      "    private Long id;",
      "",
      "    @NotBlank",
      "    @Size(min = 3, max = 50)",
      "    @Column(nullable = false)",
      "    private String ${4:fieldName};",
      "",
      "    @Column(name = \"created_at\", nullable = false, updatable = false)",
      "    private LocalDateTime createdAt;",
      "",
      "    @Column(name = \"updated_at\")",
      "    private LocalDateTime updatedAt;",
      "",
      "    @PrePersist",
      "    protected void onCreate() {",
      "        createdAt = LocalDateTime.now();",
      "    }",
      "",
      "    @PreUpdate",
      "    protected void onUpdate() {",
      "        updatedAt = LocalDateTime.now();",
      "    }",
      "}"
    ],
    "description": "Creates a complete JPA entity with Lombok, builder pattern, timestamps, and basic configuration"
  },
  "JPA Repository": {
    "prefix": "@repository",
    "scope": "java",
    "body": [
      "package ${1:com.learning.java_learning.repository};",
      "",
      "import org.springframework.data.jpa.repository.JpaRepository;",
      "import org.springframework.data.jpa.repository.Query;",
      "import org.springframework.data.repository.query.Param;",
      "import org.springframework.stereotype.Repository;",
      "import ${2:com.learning.java_learning.entity}.${3:EntityName};",
      "import java.util.List;",
      "import java.util.Optional;",
      "",
      "@Repository",
      "public interface ${3:EntityName}Repository extends JpaRepository<${3:EntityName}, Long> {",
      "    // Basic query methods",
      "    Optional<${3:EntityName}> findByFieldName(String fieldName);",
      "    List<${3:EntityName}> findAllByOrderByCreatedAtDesc();",
      "",
      "    // Custom JPQL query",
      "    @Query(\"SELECT e FROM ${3:EntityName} e WHERE e.fieldName LIKE %:keyword%\")",
      "    List<${3:EntityName}> searchByKeyword(@Param(\"keyword\") String keyword);",
      "",
      "    // Native SQL query",
      "    @Query(value = \"SELECT * FROM ${4:table_name} WHERE created_at > :date\", nativeQuery = true)",
      "    List<${3:EntityName}> findRecentEntries(@Param(\"date\") LocalDateTime date);",
      "}"
    ],
    "description": "Creates a JPA repository interface with custom query examples"
  },
  "Service Class": {
    "prefix": "@service",
    "scope": "java",
    "body": [
      "package ${1:com.learning.java_learning.service};",
      "",
      "import lombok.RequiredArgsConstructor;",
      "import lombok.extern.slf4j.Slf4j;",
      "import org.springframework.stereotype.Service;",
      "import org.springframework.transaction.annotation.Transactional;",
      "import ${2:com.learning.java_learning.repository}.${3:EntityName}Repository;",
      "import ${4:com.learning.java_learning.entity}.${3:EntityName};",
      "import java.util.List;",
      "",
      "@Slf4j",
      "@Service",
      "@RequiredArgsConstructor",
      "@Transactional(readOnly = true)",
      "public class ${3:EntityName}Service {",
      "",
      "    private final ${3:EntityName}Repository ${5:entityName}Repository;",
      "",
      "    public ${3:EntityName} findById(Long id) {",
      "        log.debug(\"Finding ${3:EntityName} by id: {}\", id);",
      "        return ${5:entityName}Repository.findById(id)",
      "            .orElseThrow(() -> {",
      "                log.error(\"${3:EntityName} not found with id: {}\", id);",
      "                return new RuntimeException(\"${3:EntityName} not found\");",
      "            });",
      "    }",
      "",
      "    public List<${3:EntityName}> findAll() {",
      "        log.debug(\"Finding all ${3:EntityName}s\");",
      "        return ${5:entityName}Repository.findAll();",
      "    }",
      "",
      "    @Transactional",
      "    public ${3:EntityName} save(${3:EntityName} ${5:entityName}) {",
      "        log.info(\"Saving ${3:EntityName}: {}\", ${5:entityName});",
      "        return ${5:entityName}Repository.save(${5:entityName});",
      "    }",
      "",
      "    @Transactional",
      "    public void deleteById(Long id) {",
      "        log.info(\"Deleting ${3:EntityName} with id: {}\", id);",
      "        ${5:entityName}Repository.deleteById(id);",
      "    }",
      "}"
    ],
    "description": "Creates a service class with basic CRUD operations, logging, and transaction management"
  },
  "REST Controller": {
    "prefix": "@restcontroller",
    "scope": "java",
    "body": [
      "package ${1:com.learning.java_learning.controller};",
      "",
      "import lombok.RequiredArgsConstructor;",
      "import lombok.extern.slf4j.Slf4j;",
      "import org.springframework.http.ResponseEntity;",
      "import org.springframework.web.bind.annotation.*;",
      "import ${2:com.learning.java_learning.service}.${3:EntityName}Service;",
      "import ${4:com.learning.java_learning.entity}.${3:EntityName};",
      "import java.util.List;",
      "",
      "@Slf4j",
      "@RestController",
      "@RequestMapping(\"/api/${5:endpoint}\")",
      "@RequiredArgsConstructor",
      "public class ${3:EntityName}Controller {",
      "",
      "    private final ${3:EntityName}Service ${6:entityName}Service;",
      "",
      "    @GetMapping",
      "    public ResponseEntity<List<${3:EntityName}>> getAll() {",
      "        log.debug(\"REST request to get all ${3:EntityName}s\");",
      "        return ResponseEntity.ok(${6:entityName}Service.findAll());",
      "    }",
      "",
      "    @GetMapping(\"/{id}\")",
      "    public ResponseEntity<${3:EntityName}> getById(@PathVariable Long id) {",
      "        log.debug(\"REST request to get ${3:EntityName} : {}\", id);",
      "        return ResponseEntity.ok(${6:entityName}Service.findById(id));",
      "    }",
      "",
      "    @PostMapping",
      "    public ResponseEntity<${3:EntityName}> create(@RequestBody ${3:EntityName} ${6:entityName}) {",
      "        log.info(\"REST request to save ${3:EntityName} : {}\", ${6:entityName});",
      "        return ResponseEntity.ok(${6:entityName}Service.save(${6:entityName}));",
      "    }",
      "",
      "    @PutMapping(\"/{id}\")",
      "    public ResponseEntity<${3:EntityName}> update(@PathVariable Long id, @RequestBody ${3:EntityName} ${6:entityName}) {",
      "        log.info(\"REST request to update ${3:EntityName} : {}\", ${6:entityName});",
      "        ${6:entityName}.setId(id);",
      "        return ResponseEntity.ok(${6:entityName}Service.save(${6:entityName}));",
      "    }",
      "",
      "    @DeleteMapping(\"/{id}\")",
      "    public ResponseEntity<Void> delete(@PathVariable Long id) {",
      "        log.info(\"REST request to delete ${3:EntityName} : {}\", id);",
      "        ${6:entityName}Service.deleteById(id);",
      "        return ResponseEntity.ok().build();",
      "    }",
      "}"
    ],
    "description": "Creates a REST controller with CRUD endpoints and logging"
  },
  "OneToMany Relationship": {
    "prefix": "@onetomany",
    "scope": "java",
    "body": [
      "@OneToMany(mappedBy = \"${1:parentReference}\", cascade = CascadeType.ALL, orphanRemoval = true)",
      "private Set<${2:ChildEntity}> ${3:childCollection} = new HashSet<>();"
    ],
    "description": "Adds a OneToMany relationship field"
  },
  "ManyToOne Relationship": {
    "prefix": "@manytoone",
    "scope": "java",
    "body": [
      "@ManyToOne(fetch = FetchType.LAZY)",
      "@JoinColumn(name = \"${1:parent}_id\", nullable = false)",
      "private ${2:ParentEntity} ${3:parentReference};"
    ],
    "description": "Adds a ManyToOne relationship field"
  },
  "OneToOne Relationship": {
    "prefix": "@onetoone",
    "scope": "java",
    "body": [
      "@OneToOne(cascade = CascadeType.ALL)",
      "@JoinColumn(name = \"${1:related}_id\", referencedColumnName = \"id\")",
      "private ${2:RelatedEntity} ${3:reference};"
    ],
    "description": "Adds a OneToOne relationship field"
  },
  "ManyToMany Relationship": {
    "prefix": "@manytomany",
    "scope": "java",
    "body": [
      "@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})",
      "@JoinTable(",
      "    name = \"${1:join_table_name}\",",
      "    joinColumns = @JoinColumn(name = \"${2:this}_id\"),",
      "    inverseJoinColumns = @JoinColumn(name = \"${3:other}_id\")",
      ")",
      "private Set<${4:OtherEntity}> ${5:collection} = new HashSet<>();"
    ],
    "description": "Adds a ManyToMany relationship field"
  },
  "String Validation": {
    "prefix": "@stringval",
    "scope": "java",
    "body": [
      "@NotBlank(message = \"${1:Field} cannot be blank\")",
      "@Size(min = ${2:3}, max = ${3:50}, message = \"${1:Field} must be between ${2:3} and ${3:50} characters\")",
      "@Column(length = ${3:50}, nullable = false)"
    ],
    "description": "Adds string validation with @NotBlank and @Size"
  },
  "Number Validation": {
    "prefix": "@numval",
    "scope": "java",
    "body": [
      "@NotNull(message = \"${1:Field} cannot be null\")",
      "@Min(value = ${2:0}, message = \"${1:Field} must be at least ${2:0}\")",
      "@Max(value = ${3:100}, message = \"${1:Field} must be at most ${3:100}\")",
      "@Column(nullable = false)"
    ],
    "description": "Adds number validation with @Min and @Max"
  },
  "Email Validation": {
    "prefix": "@emailval",
    "scope": "java",
    "body": [
      "@NotBlank(message = \"Email cannot be blank\")",
      "@Email(message = \"Please provide a valid email address\")",
      "@Column(length = 100, nullable = false, unique = true)"
    ],
    "description": "Adds email validation"
  },
  "Decimal Validation": {
    "prefix": "@decimalval",
    "scope": "java",
    "body": [
      "@NotNull(message = \"${1:Field} cannot be null\")",
      "@DecimalMin(value = \"${2:0.0}\", message = \"${1:Field} must be at least ${2:0.0}\")",
      "@DecimalMax(value = \"${3:100.0}\", message = \"${1:Field} must be at most ${3:100.0}\")",
      "@Digits(integer = ${4:6}, fraction = ${5:2}, message = \"${1:Field} must have at most ${4:6} integer digits and ${5:2} fraction digits\")",
      "@Column(nullable = false)"
    ],
    "description": "Adds decimal number validation"
  },
  "Pattern Validation": {
    "prefix": "@patternval",
    "scope": "java",
    "body": [
      "@NotBlank(message = \"${1:Field} cannot be blank\")",
      "@Pattern(regexp = \"${2:^[A-Za-z0-9]*$}\", message = \"${3:Field must contain only letters and numbers}\")",
      "@Column(nullable = false)"
    ],
    "description": "Adds pattern validation using regular expression"
  },
  "DTO Class": {
    "prefix": "@dto",
    "scope": "java",
    "body": [
      "package ${1:com.learning.java_learning.dto};",
      "",
      "import lombok.Builder;",
      "import lombok.Data;",
      "import jakarta.validation.constraints.*;",
      "",
      "@Data",
      "@Builder",
      "public class ${2:Name}DTO {",
      "    ${3:// Add your DTO fields here}",
      "}"
    ],
    "description": "Creates a DTO class with Lombok builder pattern"
  }
} 