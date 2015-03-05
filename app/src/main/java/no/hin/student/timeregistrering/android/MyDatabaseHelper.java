package no.hin.student.timeregistrering.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Projects.db";
    public static final int DATABASE_VERSION = 2;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        ProjectDBTable.onCreate(database);
    }

    // Method that is called when database version is updated
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        ProjectDBTable.onUpgrade(database, oldVersion, newVersion);
    }
}