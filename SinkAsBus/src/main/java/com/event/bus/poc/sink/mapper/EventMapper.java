package com.event.bus.poc.sink.mapper;

import com.event.bus.poc.sink.entity.MyEntity;
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

