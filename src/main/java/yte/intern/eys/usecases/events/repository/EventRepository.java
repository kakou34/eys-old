package yte.intern.eys.usecases.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.eys.usecases.events.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
