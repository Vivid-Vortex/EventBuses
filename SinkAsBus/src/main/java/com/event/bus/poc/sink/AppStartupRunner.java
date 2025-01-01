package com.event.bus.poc.sink;

import com.event.bus.poc.sink.bus.EventProcessor;
import com.event.bus.poc.sink.bus.InMemoryEventBus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    private final EventProcessor eventProcessor;
    private final InMemoryEventBus eventBus;

    public AppStartupRunner(EventProcessor eventProcessor, InMemoryEventBus eventBus) {
        this.eventProcessor = eventProcessor;
        this.eventBus = eventBus;
    }

    @Override
    public void run(String... args) {
        // Start the event processing pipeline
        eventProcessor.startProcessing();

        // Simulate publishing events
        System.out.println("Publishing events...");
        eventBus.publish("Event 1");
        eventBus.publish("Event 2");
        eventBus.publish("Event 3");

        System.out.println("Events published.");
    }
}

