package yte.intern.eys.usecases.events.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yte.intern.eys.usecases.common.dto.MessageResponse;
import yte.intern.eys.usecases.events.entity.Event;
import yte.intern.eys.usecases.events.entity.FormQuestion;
import yte.intern.eys.usecases.events.repository.EventRepository;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import static yte.intern.eys.usecases.common.enums.MessageType.ERROR;
import static yte.intern.eys.usecases.common.enums.MessageType.SUCCESS;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    public List<Event> listAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventByName(String name) {
        return eventRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    public MessageResponse addEvent(Event event) {
        if(eventRepository.existsByName(event.getName())) {
            return new MessageResponse("An event with this name already exists!", ERROR);
        }
        eventRepository.save(event);
        return new MessageResponse("Event has been added successfully!", SUCCESS);
    }

    @Transactional
    public MessageResponse updateEvent(String name, Event event) {
        Optional<Event> eventOptional = eventRepository.findByName(name);
        if (eventOptional.isPresent()) {
            Event eventFromDB = eventOptional.get();
            if(eventFromDB.getStartDate().isBefore(LocalDate.now())) {
                return new MessageResponse("You cannot update an event after its start date", ERROR);
            } else {
            updateEventFromDB(event, eventFromDB);
            eventRepository.save(eventFromDB);
            return new MessageResponse(String.format("Event with name %s has been updated successfully!", name), SUCCESS);
            }
        } else {
            return new MessageResponse(String.format("Event with name %s can't be found!", name), ERROR);
        }
    }

    private void updateEventFromDB(Event event, Event eventFromDB) {
        eventFromDB.setName(event.getName());
        eventFromDB.setStartDate(event.getStartDate());
        eventFromDB.setEndDate(event.getEndDate());
        eventFromDB.setQuota(event.getQuota());
        eventFromDB.setAltitude(event.getAltitude());
        eventFromDB.setLongitude(event.getLongitude());
    }

    public MessageResponse deleteEvent(String name) {
        Optional<Event> eventOptional = eventRepository.findByName(name);
        if (eventOptional.isPresent()) {
            Event eventFromDB = eventOptional.get();
            if(eventFromDB.getStartDate().isBefore(LocalDate.now())) {
                return new MessageResponse("You cannot delete an event after its start date", ERROR);
            } else {
                eventRepository.deleteByName(name);
                return new MessageResponse("Event has been deleted successfully!", SUCCESS);
            }
        } else {
            return new MessageResponse(String.format("Event with name %s can't be found!", name), ERROR);
        }
    }

    // Managing Event Application form's questions:

    public Set<FormQuestion> getEventsFormQuestions(String name) {
        return eventRepository.findByName(name).map(Event::getFormQuestions)
                .orElseThrow(EntityNotFoundException::new);
    }

    public MessageResponse addFormQuestionToEvent(String name, FormQuestion formQuestion) {
        Optional<Event> eventOptional = eventRepository.findByName( name);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Set<FormQuestion> formQuestions = event.getFormQuestions();

            formQuestions.add(formQuestion);
            return new MessageResponse("The question has successfully been added!", SUCCESS);

        } else {
            return new MessageResponse(String.format("Event with name %s can't be found!", name), ERROR);
        }
    }


    public MessageResponse deleteQuestion(String eventName, String question) {

        Optional<Event> eventOptional = eventRepository.findByName(eventName);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if(!event.hasFormQuestion(question)) {
                return new MessageResponse(String.format("Event with Name %s doesn't have question: %s!", eventName, question),ERROR);
            }
            removeQuestionFromEvent(question, event);
            eventRepository.save(event);
            return new MessageResponse("The question has been deleted successfylly!", SUCCESS);
        }
        return new MessageResponse(String.format("Event %s can't be found!", eventName), ERROR);
    }

    private void removeQuestionFromEvent(String question, Event event) {
        Set<FormQuestion> filteredFormQuestions = event.getFormQuestions()
                .stream()
                .filter(it -> !it.getQuestion().equals(question))
                .collect(toSet());

        event.getFormQuestions().clear();
        event.getFormQuestions().addAll(filteredFormQuestions);
    }
}
