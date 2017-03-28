package com.hubspot.partner;

import java.util.Date;
import java.util.List;

/**
 * Created by navdeep.a.sharma on 27/03/2017.
 */
public class Countries {
    private int attendeeCount;
    private String name;
    private Date startDate;
    private List<String> attendees;

    public Countries(int attendeeCount, String name, Date startDate, List<String> attendees) {
        this.attendeeCount = attendeeCount;
        this.name = name;
        this.startDate = startDate;
        this.attendees = attendees;
    }

    public Countries() {

    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }

}
