/* *********************************************************************************************
 *  ListFragment.java
 *  ---------------------------------------------------------------------------------------------
 *  Dette er klassefilen listefragmentet
 *  ********************************************************************************************/

package no.hin.student.timeregistrering;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListFragment extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }


}