/* *********************************************************************************************
 *  ListFragment.java
 *  ---------------------------------------------------------------------------------------------
 *  Dette er klassefilen listefragmentet
 *  ********************************************************************************************/

package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import no.hin.student.timeregistrering.R;


public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object> {
    private final int LOADER_ID = 0;
    private ListView lvProjects;
    private OnProjectClickListener projectClickListener;


    interface OnProjectClickListener
    {
        public void onProjectClick(int index);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        projectClickListener = (OnProjectClickListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        return view;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        lvProjects = (ListView)getActivity().findViewById(R.id.lvProjects);
        lvProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                projectClickListener.onProjectClick(position);
            }
        });
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}