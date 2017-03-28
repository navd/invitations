package com.hubspot.partner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by navdeep.a.sharma on 27/03/2017.
 */
public class Partner {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private List<Date> availableDates;;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Partner(String firstName, String lastName, String email, String country, String availableDates) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        List<String> temp=Arrays.asList(availableDates.replace("[","").replace("]","").replace("\"", "").split(","));
        this.availableDates = new ArrayList<>();
        for (String dateString : temp) {
            try {
                this.availableDates.add(simpleDateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Date> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<Date> availableDates) {
        this.availableDates = availableDates;
    }

}
