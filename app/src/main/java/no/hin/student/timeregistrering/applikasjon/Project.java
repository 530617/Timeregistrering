package no.hin.student.timeregistrering.applikasjon;

public class Project
{
    public enum Status {
        NOT_STARTED(0),
        STARTED(1),
        FINISHED(2);

        private int type;

        Status(int i) {
            this.type = i;
        }

        public int getValue() {
            return type;
        }
    };

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

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Timeregistrering getTimeregistrering()
    {
        return timeregistrering;
    }
}