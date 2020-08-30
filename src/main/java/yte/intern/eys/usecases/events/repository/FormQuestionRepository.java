package yte.intern.eys.usecases.events.repository;

import org.checkerframework.checker.nullness.Opt;
import org.springframework.stereotype.Repository;
import yte.intern.eys.usecases.events.entity.Event;
import yte.intern.eys.usecases.events.entity.FormQuestion;

import java.util.Optional;

@Repository
public interface FormQuestionRepository {
    Optional<FormQuestion> findByQuestion (String question);
    boolean existsByQuestion(String question);
}
