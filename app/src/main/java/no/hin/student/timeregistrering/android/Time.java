package no.hin.student.timeregistrering.android;

public class Time {
    private long id;
    private String name;
    private String details;

    public Time(String name, String details) {
        super();
        this.name = name;
        this.details = details;
    }

    // Get metoder for å hente informasjon
    public String getName() {
        return this.name;
    }



    @Override
    public String toString() {
        return name;
    }
}