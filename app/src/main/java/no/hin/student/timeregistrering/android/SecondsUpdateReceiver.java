package no.hin.student.timeregistrering.android;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import no.hin.student.timeregistrering.R;

/**
 * Created by Aleksander on 02.03.2015.
 */
public class SecondsUpdateReceiver extends BroadcastReceiver
{
    public final static String UPDATE_SECONDS = "no.hin.student.timeregistrering.UPDATE_SECONDS";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        MainActivity activity = (MainActivity)context;
        FragmentManager manager = activity.getFragmentManager();
        ProjectFragment projectFragment = (ProjectFragment)manager.findFragmentById(R.id.fragment_project);
        projectFragment.onSecondsUpdate();
    }
}
