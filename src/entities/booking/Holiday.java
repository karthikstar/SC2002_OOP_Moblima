package entities.booking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that stores the dates of the respective holiday dates.
 */
public class Holiday implements Serializable,PriceChanger, Comparable<Holiday> {
    /**
     * Holiday date.
     */
    private LocalDate holidayDate;

    /**
     * Constructor for the holiday object.
     * @param holidayDate Date of the Holiday
     */
    public Holiday (LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    /**
     * Getter for the Holiday Date in the object.
     * @return Holiday Date of the holiday object
     */
    public LocalDate getHolidayDate() {
        return holidayDate;
    }
    /**
     * Setter for the Holiday Date in the object.
     * @param holidayDate Holiday Date to be set
     */
    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }

    /**
     * Returns the string of the holiday date formatted for viewing.
     * @return String of the holiday date formatted for viewing
     */
    public String getHolidayDateToString(){
        return holidayDate.format(DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy"));
    }

    /**
     * Returns the statement that tells the user which day the holiday is on.
     * @return String of holiday date formatted for viewing
     */
    @Override
    public String toString() {
        return "This holiday is on " + this.getHolidayDateToString();
    }

    /**
     * Comparable method that allows the Holiday Dates to be compared with one another and thus sorted later in terms of chronological order.
     * @param o the object to be compared.
     * @return Integer that tells us the results of the comparison of the chronological order
     */
    @Override
    public int compareTo(Holiday o) {
        return getHolidayDate().compareTo(o.getHolidayDate());
    }
}
