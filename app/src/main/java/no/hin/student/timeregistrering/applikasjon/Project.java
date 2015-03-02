package no.hin.student.timeregistrering.applikasjon;

public class Project
{
    public enum Status {NOT_STARTED, STARTED, FINISHED};

    private Status status = Status.NOT_STARTED;
    private String name;
    private String code;
    private String leader;

    private TimerListener timerListener;
    private Tid tid;
    private Timeregistrering timeregistrering;

    public Project(String name, String code, String leader, Status status,
                   TimerListener timerListener, Tid tid)
    {
        this.name = name;
        this.code = code;
        this.leader = leader;
        this.status = status;
        this.timerListener = timerListener;
        this.tid = tid;
    }

    public void startTimeregistrering()
    {
        timeregistrering = new Timeregistrering(timerListener, tid);
        timeregistrering.start();
    }

    public void stopTimeregistrering()
    {
        timeregistrering.stop();
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public String getLeader()
    {
        return leader;
    }

    public String getStatus()
    {
        if (status == Status.NOT_STARTED)
            return "Ikke startet";
        else if (status == Status.STARTED)
            return "Startet";
        else
            return "Ferdig";
    }
}