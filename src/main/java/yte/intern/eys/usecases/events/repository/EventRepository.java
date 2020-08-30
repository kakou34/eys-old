package yte.intern.eys.usecases.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yte.intern.eys.usecases.events.entity.Event;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    Optional<Event> findById(Long id);

    boolean existsById(Long id);

    @Transactional
    void deleteById(Long id);
}
