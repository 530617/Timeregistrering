package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import no.hin.student.timeregistrering.R;
import no.hin.student.timeregistrering.applikasjon.Project;

/**
 * Created by Aleksander on 02.03.2015.
 */
public class ProjectFragment extends Fragment
{
    private TextView projectName;
    private TextView projectCode;
    private TextView projectStatus;
    private TextView projectLeader;
    private TextView clock;
    private Button startStopButton;
    private int elapsedSeconds;

    private Project currentProject;


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

        startStopButton = (Button)activity.findViewById(R.id.buttonStartStop);
        startStopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentProject.startTimeregistrering();
                elapsedSeconds = 0;
            }
        });
    }

    public void displayProject(Project project)
    {
        currentProject = project;
        projectName.setText(currentProject.getName());
        projectCode.setText(currentProject.getCode());
        projectLeader.setText(currentProject.getLeader());
        projectStatus.setText(currentProject.getStatus());
    }

    public void onSecondsUpdate()
    {
        elapsedSeconds++;
        clock.setText(String.valueOf(elapsedSeconds)); // må formateres riktig (gjøres ikke nå)
    }
}
