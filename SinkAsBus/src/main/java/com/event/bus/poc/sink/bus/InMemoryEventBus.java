package com.event.bus.poc.sink.bus;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class InMemoryEventBus {

    private final Sinks.Many<String> sink;

    public InMemoryEventBus() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer(); // Multicast to multiple subscribers
    }

    public void publish(String event) {
        sink.tryEmitNext(event); // Publish the event
    }

    public Flux<String> subscribe() {
        return sink.asFlux(); // Subscribe to the event stream
    }
}
