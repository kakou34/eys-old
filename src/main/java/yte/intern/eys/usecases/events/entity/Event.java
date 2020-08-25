package yte.intern.eys.usecases.events.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Event Name must be given")
    private String eventName;
    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    @NotNull(message = "A maximum number of participants must be specified")
    private Integer quota;
    @NotNull(message = "Please give the altitude of the geographical location of the event")
    private Double altitude;
    @NotNull(message = "Please give the longitude of the geographical location of the event")
    private Double longitude;


    @AssertTrue
    public boolean isEndDateValid() {
        return (endDate.isAfter(startDate) || endDate.equals(startDate) );
    }





}
