package yte.intern.eys.usecases.events.service;
import org.springframework.stereotype.Service;
import yte.intern.eys.usecases.events.dto.AddEventDTO;
import yte.intern.eys.usecases.events.entity.Event;
import yte.intern.eys.usecases.events.repository.EventRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service

public class EventService {
    private final EventRepository eventRepository;
    public EventService(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    public String addEvent(AddEventDTO addEventDTO) {
        try {
            Event event = new Event(
                    null, addEventDTO.getEventName(), LocalDate.parse(addEventDTO.getStartDate()), LocalDate.parse(addEventDTO.getEndDate()),
                    addEventDTO.getQuota(), addEventDTO.getAltitude(), addEventDTO.getLongitude());

            eventRepository.save(event);

            return "Event Added Successfully!";
        } catch (DateTimeParseException e) {
            return "Please give the dates in this format \"yyyy-mm-dd\"";
        }

    }
}
