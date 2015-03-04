package no.hin.student.timeregistrering.applikasjon;

import java.text.DateFormat;
import java.util.Date;

public class Timeregistrering
{
    private String timestampAtStart;
    private String timestampAtStop;
    private int elapsedSeconds = 0;
    private boolean isRunning = false;

    private TimerListener timerListener;
    private Tid tid;

    public Timeregistrering(TimerListener timerListener, Tid tid)
    {
        this.timerListener = timerListener;
        this.tid = tid;
    }

    public void start()
    {
        isRunning = true;

        timestampAtStart = getTimestamp();

        Thread timeThread = new Thread(new RunnableTimeregistrering());
        timeThread.start();
    }

    public void stop()
    {
        isRunning = false;

        timestampAtStop = getTimestamp();
    }

    private String getTimestamp()
    {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Date date = new Date();

        return dateFormat.format(date);
    }

    public String getTimestampAtStart()
    {
        return timestampAtStart;
    }

    public String getTimestampAtStop()
    {
        return timestampAtStop;
    }

    public int getElapsedSeconds()
    {
        return elapsedSeconds;
    }

    private class RunnableTimeregistrering implements Runnable
    {
        @Override
        public void run()
        {
            while (isRunning)
            {
                timerListener.onSecondsUpdate(elapsedSeconds);
                elapsedSeconds++;
                tid.sleepMilliseconds(1000);
            }
        }
    }
}
