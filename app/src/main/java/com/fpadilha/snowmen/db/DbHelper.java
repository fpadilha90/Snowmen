package com.fpadilha.snowmen.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fpadilha.snowmen.models.Snowman;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "snowmenapp.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String LONG_TYPE = " LONG";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BOOLEAN_TYPE = " BOOLEAN";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_SNOWMEN =
            "CREATE TABLE " + Snowman.TABLE_NAME + " (" +
                    Snowman.ID + TEXT_TYPE + " PRIMARY KEY," +
                    Snowman.PHOTO + TEXT_TYPE + COMMA_SEP +
                    Snowman.FAVORITE + BOOLEAN_TYPE + COMMA_SEP +
                    Snowman.LATITUDE + REAL_TYPE + COMMA_SEP +
                    Snowman.LONGITUDE + REAL_TYPE +
            " )";

    private static final String SQL_DELETE_SNOWMEN =
            "DROP TABLE IF EXISTS " + Snowman.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SNOWMEN);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SNOWMEN);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
