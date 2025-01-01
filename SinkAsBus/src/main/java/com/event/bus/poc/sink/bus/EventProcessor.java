package com.event.bus.poc.sink.bus;

import com.event.bus.poc.sink.eventhandler.EventValidator;
import com.event.bus.poc.sink.mapper.EventMapper;
import com.event.bus.poc.sink.persistance.EventPersistence;
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
                .flatMap(persistence::persist)    // Persist to dummy DB
                .subscribe(
                        entity -> System.out.println("Processed entity: " + entity),
                        error -> System.err.println("Error processing event: " + error.getMessage())
                );
    }
}
