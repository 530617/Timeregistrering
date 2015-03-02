package no.hin.student.timeregistrering;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Aleksander on 02.03.2015.
 */
public class ProjectFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_project, container, false);
        return view;

    }
}
