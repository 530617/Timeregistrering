package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import no.hin.student.timeregistrering.R;
import no.hin.student.timeregistrering.applikasjon.Project;
import no.hin.student.timeregistrering.applikasjon.SystemTid;
import no.hin.student.timeregistrering.applikasjon.TimerListener;


public class MainActivity extends Activity implements ListFragment.OnProjectClickListener, TimerListener
{
    private ArrayList<Project> projects = new ArrayList<Project>();
    ProjectFragment projectFragment;
    private SecondsUpdateReceiver secondsUpdateReceiver;
    private IntentFilter secondsUpdateFilter;
    private Intent updateSecondsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projects.add(new Project("Implementasjon av ny HP StoreOnce lagringshylle","P1001", "Olav", Project.Status.NOT_STARTED, this, new SystemTid()));
        projects.add(new Project("Ny ITV løsning","P1002", "Leif", Project.Status.STARTED, this, new SystemTid()));
        projects.add(new Project("Utvidelse av Blade C7000 hylle","P1003", "Hjørdiss", Project.Status.NOT_STARTED, this, new SystemTid()));
        projects.add(new Project("Oppgradering til Citrix Xenapp 7.6 Enterprise","P1004", "Leif", Project.Status.FINISHED, this, new SystemTid()));

        ListView lvProjects= (ListView)findViewById(R.id.lvProjects);
        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> myAdapterInstance;
        int layoutID = android.R.layout.simple_list_item_1;
        myAdapterInstance = new ArrayAdapter<String>(this, layoutID, myStringArray);
        for (Project project : projects ) {
            myAdapterInstance.add(project.getName());
        }
        lvProjects.setAdapter(myAdapterInstance);


        secondsUpdateReceiver = new SecondsUpdateReceiver();
        secondsUpdateFilter = new IntentFilter(SecondsUpdateReceiver.UPDATE_SECONDS);
        updateSecondsIntent = new Intent(SecondsUpdateReceiver.UPDATE_SECONDS);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_newproject) {
            startActivity(new Intent(MainActivity.this, NewprojectActivity.class));
        }
        if (id == R.id.action_report) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        getFragmentReferences();
        projectFragment.displayProject(projects.get(0)); // Sett default prosjekt i project-fragment
    }

    @Override
    public void onResume()
    {
        super.onResume();

        registerReceiver(secondsUpdateReceiver, secondsUpdateFilter);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        unregisterReceiver(secondsUpdateReceiver);
    }

    private void getFragmentReferences()
    {
        FragmentManager manager = getFragmentManager();

        projectFragment = (ProjectFragment)manager.findFragmentById(R.id.fragment_project);
    }

    @Override
    public void onProjectClick(int index)
    {
        projectFragment.displayProject(projects.get(index));
    }

    @Override
    public void onSecondsUpdate(int elapsedSeconds)
    {
        updateSecondsIntent.putExtra("elapsedSeconds", elapsedSeconds);
        this.sendBroadcast(updateSecondsIntent);
    }


    public void showMenuMain(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
    }

}
