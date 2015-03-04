package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
        try {
            String projectName = ((EditText) findViewById(R.id.etProjectName)).getText().toString();
            String projectLeader = ((EditText) findViewById(R.id.etProjectLeader)).getText().toString();
            int projectStatus = ((Spinner) findViewById(R.id.spProjectStatus)).getSelectedItemPosition();


            Log.d("Project status:", "" + projectStatus);

            if (projectName.equals("") || projectLeader.equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), "Vennligst fyll ut alle felter før du forsøker å opprette prosjektet.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent projectIntent = new Intent();
                projectIntent.putExtra("projectName", projectName);
                projectIntent.putExtra("projectCode", "code");
                projectIntent.putExtra("projectLeader", projectLeader);
                projectIntent.putExtra("projectStatus", projectStatus);

                setResult(Activity.RESULT_OK, projectIntent);
                finish();
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
