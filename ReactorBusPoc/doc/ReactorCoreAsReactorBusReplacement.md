Project Reactor with **Flux** and **Mono** serves as a modern alternative to Reactor Bus by addressing similar event-driven and reactive programming use cases. Reactor Bus was initially designed to manage events with routing capabilities, but with the evolution of Reactor Core (Flux/Mono), these capabilities have been integrated and improved with a more robust and flexible API.

Here's how **Flux** and **Mono** provide a modern and powerful alternative to Reactor Bus:

---

### **1. Event Routing with Reactor's Functional API**
Reactor Bus provided routing through selectors (e.g., `$("key")`) for publishing and subscribing to events. In Reactor Core, similar routing and filtering can be achieved using **Flux** or **Mono** with operators like `filter`, `map`, and `groupBy`.

#### Example: Routing Events
**Reactor Bus (Old Way)**:
```java
eventBus.on($("topic1"), event -> System.out.println("Handled event: " + event.getData()));
eventBus.notify("topic1", Event.wrap("Hello, Reactor Bus!"));
```

**Reactor Core (Modern Way)**:
```java
Flux<String> eventStream = Flux.just("Event1", "Event2", "Event3")
    .filter(event -> event.startsWith("Event1")); // Routing by condition

eventStream.subscribe(event -> System.out.println("Handled event: " + event));
```

- **Advantage**: The functional API in Reactor Core is more expressive and allows combining routing logic with stream processing in a single pipeline.

---

### **2. Asynchronous Event Processing**
Reactor Bus provided basic asynchronous event handling. Flux and Mono, being the backbone of Reactor Core, are designed to handle **non-blocking asynchronous streams** efficiently.

#### Example: Async Event Handling
```java
Flux.just("Event1", "Event2", "Event3")
    .delayElements(Duration.ofMillis(500)) // Simulate async processing
    .subscribe(event -> System.out.println("Handled event: " + event));
```

- **Advantage**: Reactor Core provides fine-grained control over backpressure, concurrency, and thread scheduling using operators like `publishOn` and `subscribeOn`.

---

### **3. Backpressure Handling**
Reactor Bus did not inherently handle backpressure, which could lead to memory issues in high-throughput systems. Reactor Core, with its Reactive Streams compliance, provides **native backpressure handling**, making it more suitable for modern reactive systems.

#### Example: Backpressure with Flux
```java
Flux.range(1, 100)
    .onBackpressureDrop() // Drop events if downstream is slow
    .subscribe(System.out::println);
```

- **Advantage**: Ensures system stability under heavy load without manual intervention.

---

### **4. Event Broadcasting**
Reactor Bus allowed broadcasting an event to multiple subscribers. With Flux, you can achieve the same using **hot publishers** like `Sinks` or `ConnectableFlux`.

#### Example: Broadcasting with Reactor
```java
Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
Flux<String> eventStream = sink.asFlux();

eventStream.subscribe(event -> System.out.println("Subscriber 1: " + event));
eventStream.subscribe(event -> System.out.println("Subscriber 2: " + event));

sink.tryEmitNext("Broadcast Event");
```

- **Advantage**: The `Sinks` API is more flexible and configurable compared to Reactor Bus for broadcasting events.

---

### **5. Flexible Composition and Transformation**
Reactor Core allows chaining multiple operators for transforming and composing event streams in ways Reactor Bus could not support.

#### Example: Composing Event Streams
```java
Flux<String> eventStream = Flux.just("Event1", "Event2", "Event3")
    .map(event -> event.toUpperCase()) // Transform
    .flatMap(event -> Flux.just(event + " - Processed"));

eventStream.subscribe(System.out::println);
```

- **Advantage**: Enables complex event pipelines without the need for custom listeners or handlers.

---

### **6. Integration with Modern Ecosystems**
Reactor Core integrates seamlessly with modern reactive systems like WebFlux, Kafka (via `reactor-kafka`), RSocket, and databases using R2DBC, making it a more versatile alternative for event-driven architectures.

#### Example: Reactor Kafka
```java
KafkaReceiver.create(receiverOptions)
    .receive()
    .doOnNext(record -> System.out.println("Received: " + record.value()))
    .subscribe();
```

- **Advantage**: Native support for distributed messaging systems.

---

### **7. Declarative Error Handling**
Reactor Core provides rich error-handling operators such as `onErrorResume` and `onErrorContinue`.

#### Example: Error Handling
```java
Flux.just("Event1", "Error")
    .map(event -> {
        if (event.equals("Error")) throw new RuntimeException("An error occurred!");
        return event;
    })
    .onErrorResume(e -> Flux.just("Fallback Event"))
    .subscribe(System.out::println);
```

- **Advantage**: Simplifies error management compared to Reactor Bus.

---

### **Summary of Key Differences**

| Feature                  | Reactor Bus                  | Reactor Core (Flux/Mono)                     |
|--------------------------|------------------------------|----------------------------------------------|
| **Event Handling**       | Selector-based              | Stream-based (filter, map, groupBy)          |
| **Asynchronous**         | Basic async support         | Full non-blocking reactive streams support   |
| **Backpressure**         | No built-in backpressure    | Built-in backpressure management             |
| **Broadcasting**         | Direct event notification   | Hot publishers (Sinks, ConnectableFlux)      |
| **Error Handling**       | Custom handling required    | Declarative operators (`onErrorResume`, etc.)|
| **Ecosystem**            | Reactor-specific            | Integrated with WebFlux, Kafka, R2DBC, etc.  |
| **Maintainability**      | Deprecated                  | Actively maintained and Reactive Streams-compliant |

---

### **Conclusion**
Reactor Core with **Flux** and **Mono** effectively replaces Reactor Bus by offering:
- Greater flexibility.
- Robust backpressure management.
- A modern, functional, and compositional API.
- Seamless integration with Spring Boot's reactive stack and external messaging systems.

If you're starting a new project or modernizing an old one, **Flux** and **Mono** are the recommended choices for event-driven programming. Let me know if you’d like specific examples for your use case!

---

### **Question:**
I have a microservice that consumes events from a Kafka consumer and then performs validation, mapping, and persistence. I want to separate these layers using Reactor Core (`Mono` and `Flux`) instead of Reactor Bus. How can I do so? Please give a step-by-step detailed explanation along with the Spring Boot Gradle libraries involved.

---

### **Answer:**

To achieve a layered design for your microservice using Reactor Core (`Mono` and `Flux`), you can follow these steps:

---

### **Step 1: Add Required Dependencies**
For a Kafka-based reactive application using Reactor Core and Spring Boot, add the following dependencies to your `build.gradle`:

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webflux' // Reactive programming with Spring WebFlux
    implementation 'org.springframework.kafka:spring-kafka'               // Kafka integration
    implementation 'io.projectreactor.kafka:reactor-kafka:1.3.17'         // Reactor Kafka
    implementation 'org.springframework.boot:spring-boot-starter-data-r2dbc' // For reactive database access
    implementation 'io.r2dbc:r2dbc-postgresql' // Reactive PostgreSQL Driver (replace based on DB)
    implementation 'org.springframework.boot:spring-boot-starter-validation' // For validation
}
```

---

### **Step 2: Define the Kafka Consumer**
Set up a Kafka consumer to receive events and expose it as a **Reactive Flux**. Use `Reactor Kafka` for integrating with Kafka reactively.

#### Kafka Configuration (application.yml):
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: microservice-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
```

#### Kafka Receiver Setup:
```java
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Component
public class KafkaEventConsumer {

    private final KafkaReceiver<String, String> kafkaReceiver;

    public KafkaEventConsumer() {
        ReceiverOptions<String, String> options = ReceiverOptions.<String, String>create(
                Collections.singletonMap("bootstrap.servers", "localhost:9092")
        ).subscription(Collections.singleton("your-topic"));

        this.kafkaReceiver = KafkaReceiver.create(options);
    }

    public Flux<String> consumeEvents() {
        return kafkaReceiver.receive()
                .map(record -> record.value());
    }
}
```

---

### **Step 3: Define the Service Layers**
Each step in the pipeline (validation, mapping, persistence) can be encapsulated in separate service layers. Use `Mono` and `Flux` for chaining these operations.

#### Validation Layer:
```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventValidator {

    public Mono<String> validate(String event) {
        // Perform validation logic
        if (event == null || event.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Invalid Event"));
        }
        return Mono.just(event);
    }
}
```

#### Mapping Layer:
```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventMapper {

    public Mono<MyEntity> mapToEntity(String event) {
        // Convert the event string to an entity
        MyEntity entity = new MyEntity();
        entity.setData(event);
        return Mono.just(entity);
    }
}
```

#### Persistence Layer:
```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EventPersistence {

    @Autowired
    private MyEntityRepository repository; // Reactive repository

    public Mono<MyEntity> persist(MyEntity entity) {
        return repository.save(entity);
    }
}
```

---

### **Step 4: Combine the Layers in a Reactive Pipeline**
Create a coordinator class that consumes events from Kafka, processes them through the layers, and handles the results.

```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EventProcessor {

    private final KafkaEventConsumer kafkaConsumer;
    private final EventValidator validator;
    private final EventMapper mapper;
    private final EventPersistence persistence;

    public EventProcessor(KafkaEventConsumer kafkaConsumer, EventValidator validator, EventMapper mapper, EventPersistence persistence) {
        this.kafkaConsumer = kafkaConsumer;
        this.validator = validator;
        this.mapper = mapper;
        this.persistence = persistence;
    }

    public Flux<MyEntity> processEvents() {
        return kafkaConsumer.consumeEvents()
                .flatMap(validator::validate) // Validate the event
                .flatMap(mapper::mapToEntity) // Map to entity
                .flatMap(persistence::persist) // Persist to database
                .onErrorContinue((e, o) -> System.err.println("Error processing event: " + o + ", Error: " + e));
    }
}
```

---

### **Step 5: Reactive Database Setup**
Set up a reactive database repository using Spring Data R2DBC. Ensure your database schema matches the entity.

#### Entity Class:
```java
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("my_entity")
public class MyEntity {
    @Id
    private Long id;
    private String data;

    // Getters and setters
}
```

#### Repository:
```java
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MyEntityRepository extends ReactiveCrudRepository<MyEntity, Long> {}
```

---

### **Step 6: Launch the Reactive Pipeline**
In your `@SpringBootApplication` class or a separate service, start the pipeline:

```java
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final EventProcessor processor;

    public AppStartupRunner(EventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run(String... args) {
        processor.processEvents()
                 .subscribe(entity -> System.out.println("Processed and saved entity: " + entity));
    }
}
```

---

### **Advantages of This Approach**
1. **Separation of Concerns**:
    - Each layer (validation, mapping, persistence) is encapsulated in its own service.
2. **Reactive Design**:
    - End-to-end reactive pipeline from Kafka consumption to database persistence.
3. **Error Handling**:
    - The `onErrorContinue` operator ensures the pipeline doesn't stop for individual errors.
4. **Scalability**:
    - Flux-based design ensures non-blocking processing, even under high throughput.

---

### **Summary**
By replacing Reactor Bus with **Project Reactor's Flux and Mono**, you can create a flexible, non-blocking event processing pipeline with clean separation of concerns. This approach leverages modern reactive programming paradigms and integrates well with the Spring ecosystem.

Let me know if you'd like a GitHub-ready project or help implementing this!

---
### **Question:**
I prefer to avoid using external topics systems such as kafka etc. I understand that Reactor Bus allowed me to do this. Can I achieve the same functionality within the Project Reactor framework using a similar approach?

---

### **Answer:**

Yes, you can achieve similar behavior to Reactor Bus (which allows event routing without external topics) by using **Project Reactor** with its **hot publishers**, such as `Sinks`. This approach keeps all events and routing in-memory within your application, avoiding the need for external systems like Kafka.

Here’s how you can adapt the above method to use **Project Reactor** for in-memory event handling instead of relying on external topics:

---

### **1. Add the Required Dependencies**
If you’re using only in-memory event handling, you don’t need Kafka dependencies. Your `build.gradle` will be simpler:

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webflux' // Reactive programming
    implementation 'org.springframework.boot:spring-boot-starter-data-r2dbc' // Reactive database access
    implementation 'io.r2dbc:r2dbc-postgresql' // Reactive PostgreSQL Driver (or your DB)
    implementation 'org.springframework.boot:spring-boot-starter-validation' // For validation
}
```

---

### **2. Use Reactor `Sinks` for In-Memory Event Routing**
Reactor's **`Sinks` API** is a flexible, thread-safe utility for in-memory event broadcasting. It allows publishing events and provides a `Flux` stream for subscribers.

#### Define a Sink for Event Broadcasting:
```java
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class InMemoryEventBus {

    private final Sinks.Many<String> sink;

    public InMemoryEventBus() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer(); // Supports multiple subscribers
    }

    // Publish events
    public void publish(String event) {
        sink.tryEmitNext(event);
    }

    // Subscribe to events
    public Flux<String> subscribe() {
        return sink.asFlux();
    }
}
```

---

### **3. Separate the Service Layers**
You can now use this in-memory event bus to route events through different layers in the application, maintaining the separation of concerns.

#### Validation Layer:
```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventValidator {

    public Mono<String> validate(String event) {
        if (event == null || event.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Invalid Event"));
        }
        return Mono.just(event);
    }
}
```

#### Mapping Layer:
```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventMapper {

    public Mono<MyEntity> mapToEntity(String event) {
        MyEntity entity = new MyEntity();
        entity.setData(event);
        return Mono.just(entity);
    }
}
```

#### Persistence Layer:
```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventPersistence {

    private final MyEntityRepository repository;

    public EventPersistence(MyEntityRepository repository) {
        this.repository = repository;
    }

    public Mono<MyEntity> persist(MyEntity entity) {
        return repository.save(entity);
    }
}
```

---

### **4. Combine the Layers Using the Event Bus**
Create a processor that listens to the event bus and routes the events through validation, mapping, and persistence.

```java
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EventProcessor {

    private final InMemoryEventBus eventBus;
    private final EventValidator validator;
    private final EventMapper mapper;
    private final EventPersistence persistence;

    public EventProcessor(InMemoryEventBus eventBus, EventValidator validator, EventMapper mapper, EventPersistence persistence) {
        this.eventBus = eventBus;
        this.validator = validator;
        this.mapper = mapper;
        this.persistence = persistence;
    }

    public void startProcessing() {
        eventBus.subscribe()
                .flatMap(validator::validate)      // Validate the event
                .flatMap(mapper::mapToEntity)      // Map to entity
                .flatMap(persistence::persist)    // Persist to database
                .subscribe(
                        entity -> System.out.println("Processed entity: " + entity),
                        error -> System.err.println("Error processing event: " + error.getMessage())
                );
    }
}
```

---

### **5. Publish Events**
You can publish events directly to the in-memory event bus. For example, in a controller or a scheduler:

```java
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private final InMemoryEventBus eventBus;

    public EventController(InMemoryEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostMapping("/publish")
    public void publishEvent(@RequestBody String event) {
        eventBus.publish(event);
        System.out.println("Event published: " + event);
    }
}
```

---

### **6. Initialize the Event Processor**
Start the event processing pipeline when the application starts:

```java
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final EventProcessor processor;

    public AppStartupRunner(EventProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void run(String... args) {
        processor.startProcessing();
    }
}
```

---

### **Advantages of This Approach**
1. **No External Dependencies**: Unlike Kafka or RabbitMQ, this approach keeps all event routing in memory.
2. **Separation of Concerns**: Each step (validation, mapping, persistence) is clearly defined and encapsulated.
3. **Reactive and Non-Blocking**: Fully utilizes Reactor's non-blocking and backpressure-aware APIs.
4. **Scalable within the Application**: Handles high throughput as long as memory is sufficient for buffering events.
5. **Error Handling**: Reactor's operators (`onErrorContinue`, `onErrorResume`) make handling failures simple.

---

### **When to Use This Approach**
- **In-Memory Event Handling**: When events do not need to persist beyond the application's lifecycle or be shared across distributed systems.
- **High Performance**: If you need fast, lightweight event routing without the latency of external messaging systems.
- **Local Microservice Communication**: Ideal for communication between layers within a single microservice.

---

### **Limitations**
- **No Durability**: Events are not persisted. If the application crashes, all events are lost.
- **Scalability Across Services**: This approach is limited to a single application instance. For distributed systems, use Kafka, RabbitMQ, or similar systems.

Let me know if you need a more tailored example or further clarification!

---
