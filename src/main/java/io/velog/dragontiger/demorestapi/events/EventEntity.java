package io.velog.dragontiger.demorestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

public class EventEntity extends RepresentationModel<EntityModel<Event>> {

    @JsonUnwrapped
    private final Event event;

    public EventEntity(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
