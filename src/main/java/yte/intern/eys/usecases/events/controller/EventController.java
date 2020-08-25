package yte.intern.eys.usecases.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.eys.usecases.events.dto.AddEventDTO;
import yte.intern.eys.usecases.events.service.EventService;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/addEvent")
    @PreAuthorize("permitAll()")
    public String addEvent(@RequestBody AddEventDTO addEventDTO) {
        return eventService.addEvent(addEventDTO);
    }
}
