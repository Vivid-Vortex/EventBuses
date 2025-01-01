package com.event.bus.poc.sink.controller;

import com.event.bus.poc.sink.bus.InMemoryEventBus;
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

