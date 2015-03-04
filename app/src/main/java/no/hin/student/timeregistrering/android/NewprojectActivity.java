package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import no.hin.student.timeregistrering.R;
import no.hin.student.timeregistrering.applikasjon.Project;
import no.hin.student.timeregistrering.applikasjon.Projects;
import no.hin.student.timeregistrering.applikasjon.SystemTid;


public class NewprojectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);
    }


    public void btnCreateProject(View view) {
        try {
            String projectName = ((EditText) findViewById(R.id.etProjectName)).getText().toString();
            String projectLeader = ((EditText) findViewById(R.id.etProjectLeader)).getText().toString();
            String projectStatus = ((Spinner) findViewById(R.id.spProjectStatus)).getSelectedItem().toString();


            Log.d("Project status:", projectStatus);

            if (projectName.equals("") || projectLeader.equals("")) {
                //Projects.addProject(new Project("Implementasjon av ny HP StoreOnce lagringshylle", "P1001", "Olav", Project.Status.NOT_STARTED, new SystemTid()));
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Vennligst fyll ut alle felter før du forsøker å opprette prosjektet.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        } finally {


        }
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
}
