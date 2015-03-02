package no.hin.student.timeregistrering;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity implements ListFragment.OnProjectClickListener
{
    private ArrayList<Project> projects = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projects.add(new Project("Implementasjon av ny HP StoreOnce lagringshylle",""));
        projects.add(new Project("Ny ITV løsning",""));
        projects.add(new Project("Utvidelse av Blade C7000 hylle",""));
        projects.add(new Project("Oppgradering til Citrix Xenapp 7.6 Enterprise",""));

        ListView lvProjects= (ListView)findViewById(R.id.lvProjects);
        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> myAdapterInstance;
        int layoutID = android.R.layout.simple_list_item_1;
        myAdapterInstance = new ArrayAdapter<String>(this, layoutID, myStringArray);
        for (Project project : projects ) {
            myAdapterInstance.add(project.getName());
        }
        lvProjects.setAdapter(myAdapterInstance);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProjectClick(int index)
    {
        ProjectFragment projectFragment = (ProjectFragment)getFragmentManager().findFragmentById(R.id.fragment_project);
        projectFragment.displayProject(projects.get(index));
    }
}
