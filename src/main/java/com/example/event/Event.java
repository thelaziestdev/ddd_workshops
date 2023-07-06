package com.example.event;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public abstract class Event {

    private String id = UUID.randomUUID().toString();

    protected final String objectId;
    @Getter
    private final String eventName;
    private EventType type;
    private HashMap<String, String> metadata = new HashMap<>();

    public Event(String objectId, String eventName, EventType type) {
        this.objectId = objectId;
        this.eventName = eventName;
        this.type = type;
    }
}
