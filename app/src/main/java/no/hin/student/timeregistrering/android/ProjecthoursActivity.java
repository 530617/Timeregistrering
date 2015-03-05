package no.hin.student.timeregistrering.android;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        TextView projectName = (TextView)findViewById(R.id.tvProjectName);
        projectName.setText(ProjectFragment.currentProject.getName());
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


    // Fetch time entries by reading records from database and show them in projecthour listview
    private void fetchTimeregistreringerFromDatabase()
    {
        timeregistreringer.clear();
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
}
