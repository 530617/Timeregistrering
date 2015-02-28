package no.hin.student.timeregistrering;

public class TimeTable {
    private long id;
    private String name;
    private String details;

    public TimeTable(String name, String details) {
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