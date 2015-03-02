package no.hin.student.timeregistrering.applikasjon;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Aleksander on 02.03.2015.
 */
public class Timeregistrering
{
    private String timestampAtStart;
    private String timestampAtStop;
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

        timestampAtStart = getTimeStamp();

        Thread timeThread = new Thread(new RunnableTimeregistrering());
        timeThread.start();
    }

    public void stop()
    {
        isRunning = false;

        timestampAtStop = getTimeStamp();
    }

    private String getTimeStamp()
    {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        Date date = new Date();

        return dateFormat.format(date);
    }

    private class RunnableTimeregistrering implements Runnable
    {
        @Override
        public void run()
        {
            int elapsedSeconds = 0;
            while (isRunning)
            {
                tid.sleepMilliseconds(1000);
                elapsedSeconds++;
                timerListener.onSecondsUpdate(elapsedSeconds);
            }
        }
    }
}
