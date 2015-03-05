package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import no.hin.student.timeregistrering.R;
import no.hin.student.timeregistrering.applikasjon.Project;
import no.hin.student.timeregistrering.applikasjon.Timeregistrering;

public class ProjectFragment extends Fragment
{
    private TextView projectName;
    private TextView projectCode;
    private TextView projectStatus;
    private TextView projectLeader;
    private TextView clock;
    private Button startStopButton;

    public static Project currentProject;
    private TimeFormatter timeFormatter = new TimeFormatter();
    private MainActivity mainActivity;

    private boolean timeregistreringInProgress = false;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        mainActivity = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_project, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        attachViews();
    }

    private void attachViews()
    {
        Activity activity = getActivity();
        projectName = (TextView)activity.findViewById(R.id.textViewProsjektnavn);
        projectCode = (TextView)activity.findViewById(R.id.textViewProsjektkode);
        projectStatus = (TextView)activity.findViewById(R.id.textViewProsjektstatus);
        projectLeader = (TextView)activity.findViewById(R.id.textViewProsjektleder);
        clock = (TextView)activity.findViewById(R.id.textViewTimeregKlokke);

        // Set click listener on Start/Stop timerbutton for project
        startStopButton = (Button)activity.findViewById(R.id.buttonStartStop);
        startStopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!timeregistreringInProgress)
                {
                    currentProject.startTimeregistrering();
                    currentProject.setStatus(Project.Status.STARTED);
                    projectStatus.setText(currentProject.getStatus());
                    timeregistreringInProgress = true;
                    startStopButton.setText("Stopp");
                }
                else
                {
                    currentProject.stopTimeregistrering();
                    timeregistreringInProgress = false;
                    startStopButton.setText("Start");
                    saveTimeregistrering();
                }
            }
        });
    }

    // Method to record time entries and save them to the database
    private void saveTimeregistrering()
    {
        SQLiteDatabase database = new MyDatabaseHelper(mainActivity).getWritableDatabase();
        Timeregistrering timereg = currentProject.getTimeregistrering();

        ContentValues newProjectEntry;
        newProjectEntry = new ContentValues();
        newProjectEntry.put(ProjectDBTable.TIMEREG_COL_STARTED, timereg.getTimestampAtStart());
        newProjectEntry.put(ProjectDBTable.TIMEREG_COL_ENDED, timereg.getTimestampAtStop());
        newProjectEntry.put(ProjectDBTable.TIMEREG_COL_DESCRIPTION, "placeholder text");
        newProjectEntry.put(ProjectDBTable.TIMEREG_COL_PROJECT_ID, currentProject.getId());

        database.insert(ProjectDBTable.TIMEREG_TABLE, null, newProjectEntry);
        Log.d("-------------------------------------------------------", "rows: " + DatabaseUtils.queryNumEntries(database, ProjectDBTable.TIMEREG_TABLE));
        database.close();
    }

    // Method to show project details
    public void displayProject(Project project)
    {
        if (!timeregistreringInProgress)
        {
            currentProject = project;
            projectName.setText(currentProject.getName());
            projectCode.setText(currentProject.getCode());
            projectLeader.setText(currentProject.getLeader());
            projectStatus.setText(currentProject.getStatus());

            if (currentProject.getStatus().equals("Ferdig"))
                startStopButton.setClickable(false);
            else
                startStopButton.setClickable(true);
        }

        // Reset timer view
        clock.setText("00:00:00");
    }

    public void onSecondsUpdate(int elapsedSeconds)
    {
        String tid = timeFormatter.formatTime(elapsedSeconds);
        clock.setText(tid);
    }
}
