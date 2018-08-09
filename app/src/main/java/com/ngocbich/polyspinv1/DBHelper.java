package com.ngocbich.polyspinv1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ngoc Bich on 7/24/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private String name;

    public static final int VERSION = 1;
    public static final String TABLE_NAME = "CHALLENGE_POLYSPIN";
    public static final String TABLE_COL_ID = "id";
    public static final String TABLE_COL_NAME = "name";
    public static final String TABLE_COL_DESCRIPTION = "description";
    public static final String TABLE_COL_REQUITMENT = "requitment";
    public static final String TABLE_COL_PASS = "pass";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + TABLE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_COL_NAME + " INTEGER, "
            + TABLE_COL_DESCRIPTION + " TEXT, "
            + TABLE_COL_REQUITMENT + " INTEGER, "
            + TABLE_COL_PASS + " INTEGER)";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
