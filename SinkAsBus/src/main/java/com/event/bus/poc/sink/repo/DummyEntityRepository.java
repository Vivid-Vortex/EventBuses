package com.event.bus.poc.sink.repo;

import com.event.bus.poc.sink.entity.MyEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DummyEntityRepository {

    private final ConcurrentHashMap<Long, MyEntity> database = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public Mono<MyEntity> save(MyEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.incrementAndGet());
        }
        database.put(entity.getId(), entity);
        return Mono.just(entity);
    }
}
