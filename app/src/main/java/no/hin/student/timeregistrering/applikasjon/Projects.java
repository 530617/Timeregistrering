package no.hin.student.timeregistrering.applikasjon;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import no.hin.student.timeregistrering.android.MainActivity;
import no.hin.student.timeregistrering.android.MyDatabaseHelper;
import no.hin.student.timeregistrering.android.ProjectDBTable;


/**
 * Created by kurt-erik on 03.03.2015.
 */
public class Projects
{
    private ArrayList<Project> projects = new ArrayList<Project>();
    private SQLiteDatabase database;
    private MainActivity mainActivity;

    public Projects(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    public void addProject(Project project)
    {
        projects.add(project);

        database = new MyDatabaseHelper(mainActivity).getWritableDatabase();

        ContentValues newProjectEntry;
        newProjectEntry = new ContentValues();
        newProjectEntry.put(ProjectDBTable.PROJECT_COL_NAME, project.getName());
        newProjectEntry.put(ProjectDBTable.PROJECT_COL_CODE, project.getCode());
        newProjectEntry.put(ProjectDBTable.PROJECT_COL_LEADER, project.getLeader());
        newProjectEntry.put(ProjectDBTable.PROJECT_COL_STATUS, project.getStatusId());

        database.insert(ProjectDBTable.PROJECT_TABLE, null, newProjectEntry);
        Log.d("-------------------------------------------------------", "rows: " + DatabaseUtils.queryNumEntries(database, ProjectDBTable.PROJECT_TABLE));
        database.close();
    }

    public Project getProject(int id)
    {
        if (projects.isEmpty())
            fetchProjectsFromDatabase();

        return projects.get(id);
    }

    public ArrayList<Project> getAllProjects()
    {
        if (projects.isEmpty())
            fetchProjectsFromDatabase();

        return projects;
    }

    private void fetchProjectsFromDatabase()
    {
        projects.clear();
        database = new MyDatabaseHelper(mainActivity).getWritableDatabase();

        String[] result_columns = new String[] {ProjectDBTable.PROJECT_COL_ID, ProjectDBTable.PROJECT_COL_NAME,
            ProjectDBTable.PROJECT_COL_CODE, ProjectDBTable.PROJECT_COL_LEADER, ProjectDBTable.PROJECT_COL_STATUS};

        Cursor cursor = database.query(ProjectDBTable.PROJECT_TABLE, result_columns, null, null, null, null, null);

        while (cursor.moveToNext())
        {
            int idIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.PROJECT_COL_ID);
            int id = cursor.getInt(idIndex);

            int nameIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.PROJECT_COL_NAME);
            String name = cursor.getString(nameIndex);

            int codeIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.PROJECT_COL_CODE);
            String code = cursor.getString(codeIndex);

            int leaderIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.PROJECT_COL_LEADER);
            String leader = cursor.getString(leaderIndex);

            int statusIndex = cursor.getColumnIndexOrThrow(ProjectDBTable.PROJECT_COL_STATUS);
            int statusInt = cursor.getInt(statusIndex);
            Project.Status status = convertToStatus(statusInt); // We should really do this another way...

            Project project = new Project(name, code, leader, status, mainActivity, new SystemTid());
            project.setId(id);
            projects.add(project);
        }

        cursor.close();
        database.close();
    }

    private Project.Status convertToStatus(int status)
    {
        if (status == 0)
            return Project.Status.NOT_STARTED;
        else if (status == 1)
            return Project.Status.STARTED;
        else
            return Project.Status.FINISHED;
    }
}
