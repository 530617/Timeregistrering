package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import no.hin.student.timeregistrering.R;


public class NewprojectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);
    }



    public void btnCreateProject(View view) {
        String projectName = ((EditText) findViewById(R.id.etProjectName)).getText().toString();
        String projectCode = ((EditText) findViewById(R.id.etProjectCode)).getText().toString();
        String projectLeader = ((EditText) findViewById(R.id.etProjectLeader)).getText().toString();
        int projectStatus = ((Spinner) findViewById(R.id.spProjectStatus)).getSelectedItemPosition();


        // If all fields are filled out then return values to MainActivity for creating new project
        if (projectName.equals("") || projectCode.equals("") || projectLeader.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Vennligst fyll ut alle felter før du forsøker å opprette prosjektet.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent projectIntent = new Intent();
            projectIntent.putExtra("projectName", projectName);
            projectIntent.putExtra("projectCode", projectCode);
            projectIntent.putExtra("projectLeader", projectLeader);
            projectIntent.putExtra("projectStatus", projectStatus);

            setResult(Activity.RESULT_OK, projectIntent);
            finish();
        }
    }
}
