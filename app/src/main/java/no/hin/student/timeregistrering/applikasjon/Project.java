package no.hin.student.timeregistrering.applikasjon;


public class Project
{
    public enum Status {
        NOT_STARTED(0, "Ikke startet"),
        STARTED(1, "Startet"),
        FINISHED(2, "Ferdig");

        private int type;
        private String text;

        Status(int i, String text) {
            this.type = i;
            this.text = text;
        }

        public int getId() {
            return type;
        }

        public String getText() {
            return text;
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
        return status.getText();
    }

    public int getStatusId()
    {
        return status.getId();
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public Timeregistrering getTimeregistrering()
    {
        return timeregistrering;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}