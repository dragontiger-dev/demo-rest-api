package io.velog.dragontiger.demorestapi.events;

import io.velog.dragontiger.demorestapi.common.Constants;
import io.velog.dragontiger.demorestapi.index.IndexController;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;
    private final EventValidator eventValidator;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger("EventController");

    public EventController(EventRepository eventRepository, EventValidator eventValidator, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.eventValidator = eventValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<?> createEntity(@RequestBody @Valid EventDto eventDto, Errors errors) {

        // 빈 값 유효성 검사
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        // 데이터 유효성 검사
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();

        Event newEvent = eventRepository.save(event);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();
        event.setId(10);

        EventEntity eventEntity = new EventEntity(event);
        eventEntity.add(linkTo(EventController.class).withRel("query-events"));
        eventEntity.add(selfLinkBuilder.withSelfRel());
        eventEntity.add(selfLinkBuilder.withRel("update-event"));

        return ResponseEntity.created(createdUri).contentType(Constants.HAL_JSON_UTF8).body(eventEntity);
    }

}
