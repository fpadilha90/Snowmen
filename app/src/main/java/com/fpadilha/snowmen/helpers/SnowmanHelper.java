package com.fpadilha.snowmen.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fpadilha.snowmen.application.MyApplication;
import com.fpadilha.snowmen.models.Snowman;

import java.util.ArrayList;
import java.util.List;

public class SnowmanHelper {

    private static String SQL_SELECT_ALL = "SELECT * FROM " + Snowman.TABLE_NAME;
    private static String SQL_WHERE_ID = " WHERE " + Snowman.ID + " = ?";
    private static String SQL_WHERE_FAVORITE = " WHERE " + Snowman.FAVORITE + " = ?";
    private static String FAVORITE = "1";

    public static void setFavoriteSnowman(Context c, Snowman snowman) {
        String where = Snowman.ID + "=" + snowman.getName();
        getDb(c).update(Snowman.TABLE_NAME, snowmanToContentValues(snowman), where, null);
    }

    public static void insertOrUpdate(Context c, Snowman snowman) {

        Cursor cursor = getDb(c).rawQuery(SQL_SELECT_ALL + SQL_WHERE_ID, new String[]{String.valueOf(snowman.getName())});

        if (cursor.moveToNext()) {

            Snowman snowmanBO = cursorToSnowman(cursor);

            snowman.setFavorite(snowmanBO.isFavorite());

            String where = Snowman.ID + "=" + snowman.getName();
            getDb(c).update(Snowman.TABLE_NAME, snowmanToContentValues(snowman), where, null);
        } else {
            getDb(c).insert(Snowman.TABLE_NAME, null, snowmanToContentValues(snowman));
        }

    }

    public static Snowman getById(Context c, Long id) {

        Cursor cursor = getDb(c).rawQuery(SQL_SELECT_ALL + SQL_WHERE_ID, new String[]{String.valueOf(id)});

        if (cursor.moveToNext()) {
            return cursorToSnowman(cursor);
        }

        return null;
    }

    public static List<Snowman> getFavorites(Context c) {
        ArrayList<Snowman> snowmen = new ArrayList<>();

        Cursor cursor = getDb(c).rawQuery(SQL_SELECT_ALL + SQL_WHERE_FAVORITE, new String[]{String.valueOf(FAVORITE)});
        while (cursor.moveToNext()) {
            snowmen.add(cursorToSnowman(cursor));
        }

        return snowmen;

    }
    public static List<Snowman> getAll(Context c) {
        ArrayList<Snowman> snowmen = new ArrayList<>();

        Cursor cursor = getDb(c).rawQuery(SQL_SELECT_ALL, null);
        while (cursor.moveToNext()) {
            snowmen.add(cursorToSnowman(cursor));
        }

        return snowmen;

    }

    public static void clear(Context c) {
        getDb(c).delete(Snowman.TABLE_NAME, null, null);
    }

    private static Snowman cursorToSnowman(Cursor cursor) {
        Snowman snowman = new Snowman();
        snowman.setName(cursor.getString(cursor.getColumnIndex(Snowman.ID)));
        snowman.setPhoto(cursor.getString(cursor.getColumnIndex(Snowman.PHOTO)));
        snowman.setLatitude(cursor.getDouble(cursor.getColumnIndex(Snowman.LATITUDE)));
        snowman.setLongitude(cursor.getDouble(cursor.getColumnIndex(Snowman.LONGITUDE)));
        snowman.setFavorite(cursor.getInt(cursor.getColumnIndex(Snowman.ID)) != 0);

        return snowman;
    }

    private static ContentValues snowmanToContentValues(Snowman snowman) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Snowman.ID, snowman.getName());
        contentValues.put(Snowman.PHOTO, snowman.getPhoto());
        contentValues.put(Snowman.LATITUDE, snowman.getLatitude());
        contentValues.put(Snowman.LONGITUDE, snowman.getLongitude());
        contentValues.put(Snowman.FAVORITE, snowman.isFavorite());

        return contentValues;
    }

    private static SQLiteDatabase getDb(Context c) {
        return ((MyApplication) c.getApplicationContext()).getDb();

    }
}
