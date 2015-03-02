package no.hin.student.timeregistrering.applikasjon;

/**
 * Created by Aleksander on 02.03.2015.
 */
public class SystemTid implements Tid
{
    @Override
    public long getCurrentTime()
    {
        return System.currentTimeMillis();
    }

    @Override
    public void sleepMilliseconds(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
