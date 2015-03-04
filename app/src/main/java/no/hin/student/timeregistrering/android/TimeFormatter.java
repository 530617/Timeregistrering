package no.hin.student.timeregistrering.android;

public class TimeFormatter
{
    public String formatTime(int elapsedSeconds)
    {
        int hours = elapsedSeconds / 3600;
        int minutes = (elapsedSeconds % 3600) / 60;
        int seconds = (elapsedSeconds % 60);

        String time = formatHours(hours) + formatMinutes(minutes) + formatSeconds(seconds);

        return time;
    }

    private String formatHours(int hours)
    {
        String hourString = "";

        if (hours < 10)
            hourString += "0" + hours + ":";
        else
            hourString += hours + ":";

        return hourString;
    }

    private String formatMinutes(int minutes)
    {
        String minuteString = "";

        if (minutes < 10)
            minuteString += "0" + minutes + ":";
        else
            minuteString += minutes + ":";

        return minuteString;
    }

    private String formatSeconds(int seconds)
    {
        String secondString = "";

        if (seconds < 10)
            secondString += "0" + seconds;
        else
            secondString += seconds;

        return secondString;
    }
}
