package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import no.hin.student.timeregistrering.R;
import no.hin.student.timeregistrering.applikasjon.Project;
import no.hin.student.timeregistrering.applikasjon.Projects;
import no.hin.student.timeregistrering.applikasjon.SystemTid;
import no.hin.student.timeregistrering.applikasjon.TimerListener;


public class MainActivity extends Activity implements ListFragment.OnProjectClickListener, TimerListener
{
    //private static final int NEW_PROJECT_ACTIVITY = 0;

    private ProjectFragment projectFragment;
    private SecondsUpdateReceiver secondsUpdateReceiver;
    private IntentFilter secondsUpdateFilter;
    private Intent updateSecondsIntent;
    private SQLiteDatabase database;
    private Projects projects;
    private ArrayAdapter<Project> myAdapterInstance;
    private ListView lvProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getResources().getString(R.string.project_notstarted);
        projects = new Projects(this);
        initializeListView();
        initializeReceiver();

        database = new MyDatabaseHelper(this).getWritableDatabase();
        insertDefaultProjectsIntoDatabase();
    }

    private void initializeListView()
    {
        lvProjects = (ListView)findViewById(R.id.lvProjects);
        ArrayList<Project> myProjectArray = new ArrayList<Project>();

        int layoutID = android.R.layout.simple_list_item_1;
        myAdapterInstance = new ArrayAdapter<Project>(this, layoutID, myProjectArray);
        for (Project project : projects.getAllProjects() ) {
            myAdapterInstance.add(project);
        }
        lvProjects.setAdapter(myAdapterInstance);
    }

    private void initializeReceiver()
    {
        secondsUpdateReceiver = new SecondsUpdateReceiver();
        secondsUpdateFilter = new IntentFilter(SecondsUpdateReceiver.UPDATE_SECONDS);
        updateSecondsIntent = new Intent(SecondsUpdateReceiver.UPDATE_SECONDS);
    }

    private void insertDefaultProjectsIntoDatabase()
    {
        long numberOfRows = DatabaseUtils.queryNumEntries(database, ProjectDBTable.PROJECT_TABLE);
        Log.d("-------------------------------------------------------", "rows: " + numberOfRows);
        if (numberOfRows <= 4) // If default projects already exist in db, don't add them
        {
            createDefaultProjects();
            ArrayList<Project> projectList = projects.getAllProjects();
            ContentValues newProjectEntry;

            for (Project project: projectList)
            {
                newProjectEntry = new ContentValues();
                newProjectEntry.put(ProjectDBTable.PROJECT_COL_NAME, project.getName());
                newProjectEntry.put(ProjectDBTable.PROJECT_COL_CODE, project.getCode());
                newProjectEntry.put(ProjectDBTable.PROJECT_COL_LEADER, project.getLeader());
                newProjectEntry.put(ProjectDBTable.PROJECT_COL_STATUS, project.getStatusId());

                database.insert(ProjectDBTable.PROJECT_TABLE, null, newProjectEntry);
            }
            Log.d("-------------------------------------------------------", "inserting default projects");
        }

        Log.d("-------------------------------------------------------", "rows: " + DatabaseUtils.queryNumEntries(database, ProjectDBTable.PROJECT_TABLE));
        database.close();
    }

    private void createDefaultProjects()
    {
        projects.addProject(new Project("Implementasjon av ny HP StoreOnce lagringshylle", "P1001", "Olav", Project.Status.NOT_STARTED, this, new SystemTid()));
        projects.addProject(new Project("Ny ITV løsning", "P1002", "Leif", Project.Status.STARTED, this, new SystemTid()));
        projects.addProject(new Project("Utvidelse av Blade C7000 hylle", "P1003", "Hjørdiss", Project.Status.NOT_STARTED, this, new SystemTid()));
        projects.addProject(new Project("Oppgradering til Citrix Xenapp 7.6 Enterprise", "P1004", "Leif", Project.Status.FINISHED, this, new SystemTid()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_newproject) {
            startActivityForResult(new Intent(MainActivity.this, NewprojectActivity.class), RESULT_OK);
        }
        if (id == R.id.action_projecthours) {
            if (projects.countProjects() != 0){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int storedPreference = preferences.getInt("LastSelectedProject", 0);
                Intent myIntent = new Intent(getBaseContext(), ProjecthoursActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String projectName = projects.getProject(storedPreference).getName();
                startActivity(myIntent);
                //TextView tvProjectName = (TextView)findViewById(R.id.tvProjectName);
                //tvProjectName.setText(projectName);
            }
        }

        if (id == R.id.action_exit) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_OK)
        {
            String name = data.getStringExtra("projectName");
            String code = data.getStringExtra("projectCode");
            String leader = data.getStringExtra("projectLeader");
            int status = data.getIntExtra("projectStatus", 0);

            projects.addProject(new Project(name, code, leader, Project.Status.NOT_STARTED, this, new SystemTid()));
            initializeListView();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (projects.countProjects() != 0)
        try {
            getFragmentReferences();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int storedPreference = preferences.getInt("LastSelectedProject", 0);
            projectFragment.displayProject(projects.getAllProjects().get(storedPreference)); // Sett default prosjekt i project-fragment
        } finally {

        }
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
        projectFragment.displayProject(projects.getAllProjects().get(index));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("LastSelectedProject", index);
        editor.commit();
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
