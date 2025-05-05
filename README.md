

# Folder Structure

```PlainText
alice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── app/udala/alice/
│   │   │       ├── application/          # Use Cases / Interactors
│   │   │       │   └── port/             # Input and Output Ports (Interfaces)
│   │   │       │   └── usercase/         # Input and Output Ports (Interfaces)
│   │   │       ├── domain/               # Entities and Business Logic
│   │   │       │   └── service/          # Domain Services (if any)
│   │   │       │   └── entity/           # Domain Services (if any)
│   │   │       ├── infrastructure/       # Frameworks and Drivers
│   │   │       │   ├── config/           # Application Configuration
│   │   │       │   ├── delivery/         # Presentation Layer (Controllers, REST APIs)
│   │   │       │   │   ├── dto/          # Data Transfer Objects
│   │   │       │   │   └── mapper/       # Mappers for Delivery DTOs
│   │   │       │   ├── persistence/      # Data Access Layer (Repositories, ORM)
│   │   │       │   │   ├── mapper/       # Mappers for Persistence Entities
│   │   │       │   │   └── entity/       # Database Entities
│   │   │       │   └── external/         # Integrations with external systems
│   │   │       └── shared/               # Frameworks and Drivers
│   │   └── resources/
│   │       └── ...
│   └── test/
│       ├── java/
│       │   └── com/yourcompany/yourproject/
│       │       ├── application/
│       │       ├── domain/
│       │       ├── infrastructure/
│       │       └── ...
│       └── resources/
│           └── ...
├── pom.xml
└── ...
```

## Explanation of the Layers:

- `domain`: This layer contains your core business logic and entities. It is independent of any specific framework or technology.

    - `entity`: Represents the business objects and their associated data. These should be plain Java objects (POJOs) with minimal or no dependencies on external frameworks.
    - `service` (optional): Contains domain-specific business logic that doesn't naturally fit within a single entity. These services operate on the domain entities.
- `application`: This layer contains the use cases or interactors of your application. It orchestrates the domain logic to fulfill specific business requirements.

    - `port`: Defines interfaces that act as boundaries between the application layer and the outer layers (infrastructure).
        - Input Ports (Interfaces): Define the methods that the delivery layer (e.g., controllers) can call to trigger use cases.
        - Output Ports (Interfaces): Define the interfaces that the application layer uses to interact with the infrastructure layer (e.g., repositories, external services).

- `infrastructure`: This layer is where you implement the details of how your application interacts with the outside world. It contains framework-specific code.
    - `config`: Contains configuration classes for Spring Boot and other libraries.
    - `delivery`: Handles the presentation layer, such as REST controllers, GraphQL endpoints, or web interfaces.
        - `dto`: Data Transfer Objects used for communication between the delivery layer and the application layer. They help decouple the internal domain model from the external API.
    - `persistence`: Deals with data access and storage.
        - `repository`: Implementations of the output ports defined in the application layer for data persistence (e.g., using Spring Data JPA).
        - `entity`: Database-specific entities (which might be different from the domain entities, requiring mapping).
    - `external`: Handles integrations with external services, APIs, or other systems.
