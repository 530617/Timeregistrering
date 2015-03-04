package no.hin.student.timeregistrering.applikasjon;

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
