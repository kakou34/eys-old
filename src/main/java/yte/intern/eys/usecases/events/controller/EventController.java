package yte.intern.eys.usecases.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yte.intern.eys.usecases.common.dto.MessageResponse;
import yte.intern.eys.usecases.events.dto.EventDTO;
import yte.intern.eys.usecases.events.dto.FormQuestionDTO;
import yte.intern.eys.usecases.events.entity.Event;
import yte.intern.eys.usecases.events.entity.FormQuestion;
import yte.intern.eys.usecases.events.mapper.EventMapper;
import yte.intern.eys.usecases.events.mapper.FormQuestionMapper;
import yte.intern.eys.usecases.events.service.EventService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final FormQuestionMapper formQuestionMapper;

    @GetMapping
    public List<EventDTO> listAllEvents() {
        List<Event> events = eventService.listAllEvents();
        return eventMapper.mapToDto(events);
    }

    @GetMapping("/{eventID}")
    public EventDTO getEventById(@PathVariable(value="eventID") Long id) {
        Event event = eventService.getEventByID(id);
        return eventMapper.mapToDto(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public MessageResponse addEvent(@Valid @RequestBody EventDTO eventDTO){
        Event event = eventMapper.mapToEntity(eventDTO);
        return eventService.addEvent(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{eventID}")
    public MessageResponse updateEvent(@PathVariable(value="eventID") Long id, @Valid @RequestBody EventDTO eventDTO) {
        Event event = eventMapper.mapToEntity(eventDTO);
        return eventService.updateEvent(id, event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{eventID}")
    public MessageResponse deleteEvent(@PathVariable(value="eventID") Long id) {
        return eventService.deleteEvent(id);
    }

    //Handle making custom application forms for events


    @GetMapping("/{eventID}/questions")
    public List<FormQuestionDTO> getEventsFormQuestions(@PathVariable(value = "eventID") Long id) {
        Set<FormQuestion> formQuestions = eventService.getEventsFormQuestions(id);
        return formQuestionMapper.mapToDto(new ArrayList<>(formQuestions));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{eventID}/questions")
    public MessageResponse addQuestionToEvent(@PathVariable(value = "eventID") Long id, @RequestBody @Valid FormQuestionDTO formQuestionDTO) {
        return eventService.addFormQuestionToEvent(id, formQuestionMapper.mapToEntity(formQuestionDTO));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{eventID}/questions/{questionID}")
    public MessageResponse deleteFormQuestionFromEvent(@PathVariable(value = "eventID") Long eventID, @PathVariable(value = "questionID") Long questionID) {
        return eventService.deleteQuestion(eventID, questionID);
    }

}
