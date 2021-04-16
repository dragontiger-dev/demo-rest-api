package io.velog.dragontiger.demorestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventEntity extends RepresentationModel<EntityModel<Event>> {

    @JsonUnwrapped
    private final Event event;

    public EventEntity(Event event) {
        this.event = event;
        this.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    public Event getEvent() {
        return event;
    }
}
