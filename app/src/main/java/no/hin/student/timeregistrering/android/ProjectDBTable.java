package no.hin.student.timeregistrering.android;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ProjectDBTable {

    public static final String PROJECT_TABLE = "ProjectTable";
    public static final String PROJECT_COL_ID = "_id";
    public static final String PROJECT_COL_NAME = "name";
    public static final String PROJECT_COL_CODE = "code";
    public static final String PROJECT_COL_LEADER = "leader";
    public static final String PROJECT_COL_STATUS = "status";

    public static final String TIMEREG_TABLE = "TimeregistreringTable";
    public static final String TIMEREG_COL_ID = "_id";
    public static final String TIMEREG_COL_FROM = "from";
    public static final String TIMEREG_COL_TO = "to";
    public static final String TIMEREG_COL_DESCRIPTION = "description";
    public static final String TIMEREG_COL_PROJECT_ID = "projectId";


    private static final String PROJECT_TABLE_CREATE = "create table "
            + PROJECT_TABLE
            + " (" + PROJECT_COL_ID + " integer primary key autoincrement, "
            + PROJECT_COL_NAME + " text not null, "
            + PROJECT_COL_CODE + " text not null, "
            + PROJECT_COL_LEADER + " text not null, "
            + PROJECT_COL_STATUS + " integer not null" + ");";

    private static final String TIMEREG_TABLE_CREATE = "create table "
            + TIMEREG_TABLE
            + " (" + TIMEREG_COL_ID + " integer primary key autoincrement, "
            + TIMEREG_COL_FROM + " text not null, "
            + TIMEREG_COL_TO + " text not null, "
            + TIMEREG_COL_DESCRIPTION + " text, "
            + TIMEREG_COL_PROJECT_ID + " integer not null, "
            + " foreign key (" + TIMEREG_COL_PROJECT_ID + ") references "
            + PROJECT_TABLE + " (" + PROJECT_COL_ID + "));";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(PROJECT_TABLE_CREATE);
        database.execSQL(TIMEREG_TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ProjectDBTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + TIMEREG_TABLE);
        onCreate(database);
    }
}
