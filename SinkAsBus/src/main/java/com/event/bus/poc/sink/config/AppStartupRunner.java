package com.event.bus.poc.sink.config;

import com.event.bus.poc.sink.bus.EventProcessor;
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

