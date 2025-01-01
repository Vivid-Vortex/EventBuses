package com.event.bus.poc.sink.persistance;

import com.event.bus.poc.sink.entity.MyEntity;
import com.event.bus.poc.sink.repo.DummyEntityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventPersistence {

    private final DummyEntityRepository repository;

    public EventPersistence(DummyEntityRepository repository) {
        this.repository = repository;
    }

    public Mono<MyEntity> persist(MyEntity entity) {
        return repository.save(entity);
    }
}
