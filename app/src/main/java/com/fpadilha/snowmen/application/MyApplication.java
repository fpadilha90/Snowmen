package com.fpadilha.snowmen.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.fpadilha.snowmen.db.DbHelper;

public class MyApplication extends Application {

    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    private void setupDatabase() {
        DbHelper helper = new DbHelper(this);
        db = helper.getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
