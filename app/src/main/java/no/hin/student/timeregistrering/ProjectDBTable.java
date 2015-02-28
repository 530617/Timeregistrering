package no.hin.student.timeregistrering;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class ProjectDBTable {

    public static final String PROJECT_TABLE = "ProjectTable";
    public static final String PROJECT_COL_ID = "_id";
    public static final String PROJECT_COL_NAME = "name";
    public static final String PROJECT_COL_DETAILS = "details";
    public static final String PROJECT_COL_RATE = "";


    private static final String PROJECT_TABLE_CREATE = "create table "
            + PROJECT_TABLE
            + " (" + PROJECT_COL_ID + " integer primary key autoincrement, "
            + PROJECT_COL_NAME + " text not null, "
            + PROJECT_COL_DETAILS + " text, "
            + PROJECT_COL_RATE + " text" + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(PROJECT_TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ProjectDBTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        database.execSQL("DROP TABLE IF EXISTS " + PROJECT_TABLE);
        onCreate(database);
    }
}
