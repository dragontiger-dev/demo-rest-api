package io.velog.dragontiger.demorestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

public class EventResource extends RepresentationModel<EntityModel<Event>> {

    @JsonUnwrapped
    private final Event event;

    public EventResource(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
