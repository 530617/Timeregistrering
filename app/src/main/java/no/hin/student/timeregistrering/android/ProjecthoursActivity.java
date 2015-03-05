package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import no.hin.student.timeregistrering.R;
import no.hin.student.timeregistrering.applikasjon.Timeregistrering;


public class ProjecthoursActivity extends Activity {

    private ArrayList<Timeregistrering> timeregistreringer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projecthours);

        initializeListView();
    }

    private void initializeListView()
    {
        fetchTimeregistreringerFromDatabase();
        ListView lvProjectsHour = (ListView)findViewById(R.id.lvProjectsHour);
        ArrayList<Timeregistrering> myProjectArray = new ArrayList<>();
        ArrayAdapter<Timeregistrering> myAdapterInstance;
        int layoutID = android.R.layout.simple_list_item_1;
        myAdapterInstance = new ArrayAdapter<Timeregistrering>(this, layoutID, myProjectArray);
        for (Timeregistrering timereg : timeregistreringer) {
            myAdapterInstance.add(timereg);
        }
        lvProjectsHour.setAdapter(myAdapterInstance);
    }

    private void fetchTimeregistreringerFromDatabase()
    {

        SQLiteDatabase database = new MyDatabaseHelper(this).getWritableDatabase();

        String[] result_columns = new String[] {ProjectDBTable.TIMEREG_COL_STARTED, ProjectDBTable.TIMEREG_COL_ENDED,
                                                    ProjectDBTable.TIMEREG_COL_DESCRIPTION};

        String where = ProjectDBTable.TIMEREG_COL_PROJECT_ID + " = " + ProjectFragment.currentProject.getId();

        Cursor cursor = database.query(ProjectDBTable.TIMEREG_TABLE, result_columns, where, null, null, null, null);

        while (cursor.moveToNext())
        {
            int startedIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.TIMEREG_COL_STARTED);
            String started = cursor.getString(startedIndex);

            int endedIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.TIMEREG_COL_ENDED);
            String ended = cursor.getString(endedIndex);

            int descriptionIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.TIMEREG_COL_DESCRIPTION);
            String description = cursor.getString(descriptionIndex);

            timeregistreringer.add(new Timeregistrering(started, ended, description));
        }

        cursor.close();
        database.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_projecthours, menu);
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
}
