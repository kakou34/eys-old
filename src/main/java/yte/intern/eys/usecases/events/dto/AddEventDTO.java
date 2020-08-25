package yte.intern.eys.usecases.events.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class AddEventDTO {

    //@NotBlank(message = "Event Name must be given")
    private String eventName;
    //@NotBlank(message = "A start date must be given in the format yyyy-mm-dd")
    private String startDate;
    //@NotBlank(message = "An end date must be given in the format yyyy-mm-dd and cannot be before the start date")
    private String endDate;
    //@NotNull(message = "A maximum number of participants must be specified")
    private Integer quota;
    //@NotNull(message = "Please give the altitude of the geographical location of the event")
    private Double altitude;
    //@NotNull(message = "Please give the longitude of the geographical location of the event")
    private Double longitude;


//    @AssertTrue
//    public boolean isEndDateValid() {
//        LocalDate endDate = LocalDate.parse(this.endDate);
//        LocalDate startDate = LocalDate.parse(this.startDate);
//        boolean cond1 = this.endDate.matches("\\d{4}-\\d{2}-\\d{2}");
//        return (endDate.isAfter(startDate) || endDate.equals(startDate) );
//    }
//
//    @AssertTrue
//    public boolean isStartDateValid() {
//        return this.startDate.matches("\\d{4}-\\d{2}-\\d{2}");
//    }
}
