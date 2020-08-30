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

    public Event getEventByID(Long id) {
        return eventRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public MessageResponse addEvent(Event event) {
       eventRepository.save(event);
        return new MessageResponse("Event has been added successfully!", SUCCESS);
    }

    @Transactional
    public MessageResponse updateEvent(Long id, Event event) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event eventFromDB = eventOptional.get();
            if(eventFromDB.getStartDate().isBefore(LocalDate.now())) {
                return new MessageResponse("You cannot update an event after its start date", ERROR);
            } else {
            updateEventFromDB(event, eventFromDB);
            eventRepository.save(eventFromDB);
            return new MessageResponse(String.format("Event with ID %s has been updated successfully!", id), SUCCESS);
            }
        } else {
            return new MessageResponse(String.format("Event with ID %s can't be found!", id), ERROR);
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

    public MessageResponse deleteEvent(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event eventFromDB = eventOptional.get();
            if(eventFromDB.getStartDate().isBefore(LocalDate.now())) {
                return new MessageResponse("You cannot delete an event after its start date", ERROR);
            } else {
                eventRepository.deleteById(id);
                return new MessageResponse("Event has been deleted successfully!", SUCCESS);
            }
        } else {
            return new MessageResponse(String.format("Event with ID %s can't be found!", id), ERROR);
        }
    }

    // Managing Event Application form's questions:

    public Set<FormQuestion> getEventsFormQuestions(Long id) {
        return eventRepository.findById(id).map(Event::getFormQuestions)
                .orElseThrow(EntityNotFoundException::new);
    }

    public MessageResponse addFormQuestionToEvent(Long id, FormQuestion formQuestion) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Set<FormQuestion> formQuestions = event.getFormQuestions();

            formQuestions.add(formQuestion);
            return new MessageResponse("The question has successfully been added!", SUCCESS);

        } else {
            return new MessageResponse(String.format("Event with id %s can't be found!", id), ERROR);
        }
    }


    public MessageResponse deleteQuestion(Long eventID, Long questionID) {

        Optional<Event> eventOptional = eventRepository.findById(eventID);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if(!event.hasFormQuestion(questionID)) {
                return new MessageResponse(String.format("Event with ID %s doesn't have question with ID %s!", eventID, questionID),ERROR);
            }
            removeQuestionFromEvent(questionID, event);
            eventRepository.save(event);
            return new MessageResponse(String.format("The question has been deleted successfylly!"), SUCCESS);
        }
        return new MessageResponse(String.format("Event with ID %s can't be found!", eventID), ERROR);
    }

    private void removeQuestionFromEvent(Long questionID, Event event) {
        Set<FormQuestion> filteredFormQuestions = event.getFormQuestions()
                .stream()
                .filter(it -> !it.getId().equals(questionID))
                .collect(toSet());

        event.getFormQuestions().clear();
        event.getFormQuestions().addAll(filteredFormQuestions);
    }
}
