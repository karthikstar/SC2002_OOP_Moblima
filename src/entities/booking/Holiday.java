package entities.booking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Holiday implements Serializable,PriceChanger {

    private LocalDate holidayDate;
    public Holiday (LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayDateToString(){
        return holidayDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return "This holiday is on " + this.getHolidayDateToString();
    }
}
