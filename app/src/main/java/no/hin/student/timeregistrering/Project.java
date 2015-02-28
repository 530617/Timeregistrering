package no.hin.student.timeregistrering;

public class Project {

    private String navn;

    public Project(String navn) {
        super();
        this.navn = navn;
    }

    // Get metoder for å hente informasjon
    public String getNavn() {
        return this.navn;
    }



    @Override
    public String toString() {
        return navn;
    }
}