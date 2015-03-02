package no.hin.student.timeregistrering;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Aleksander on 02.03.2015.
 */
public class ProjectFragment extends Fragment
{
    private TextView projectName;
    private TextView projectCode;
    private TextView projectStatus;
    private TextView projectLeader;

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
    }

    public void displayProject(Project project)
    {
        currentProject = project;
        projectName.setText(currentProject.getName());
    }
}
