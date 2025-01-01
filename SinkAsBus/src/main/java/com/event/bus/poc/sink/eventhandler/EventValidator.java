package com.event.bus.poc.sink.eventhandler;

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


