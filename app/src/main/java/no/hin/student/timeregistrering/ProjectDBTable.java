package no.hin.student.timeregistrering;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ProjectDBTable extends SQLiteOpenHelper {
    //Databasespesifikt:
    private static final String DATABASE_NAME = "projects.db";
    private static final int DATABASE_VERSION = 1;
    public static final String PROJECTS_TABLE = "Projects";
    //Tabellspesifikt:
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DETAILS = "details";
    public static final String KEY_TIME = "time";
    // SQL statement for å opprette en ny database:
    private static final String DATABASE_CREATE = "create table "
            + PROJECTS_TABLE + " (" + KEY_ID
            + " integer primary key autoincrement, " + KEY_NAME
            + " text not null, " + KEY_DETAILS + " text, " + KEY_TIME
            + " text" + ");";
    //Konstruktør;
    public ProjectDBTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Kalles når databasen ikke eksisterer og må opprettes
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    // Kalles ved behov for oppgradering, dvs. mismatch mellom ny og
    // gammel versjon
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // The simplest case is to drop the old table and create a new one.
        db.execSQL("DROP TABLE IF IT EXISTS " + PROJECTS_TABLE);
        // Create a new one.
        onCreate(db);
    }
}