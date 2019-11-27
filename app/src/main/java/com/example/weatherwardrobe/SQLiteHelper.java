package com.example.weatherwardrobe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_COLOUR = "colour";
    public static final String COLUMN_TYPEOF = "typeof";
    public static final String COLUMN_ISCLEAN = "isClean";
    public static final String COLUMN_DESC = "description";

    private static final String DATABASE_NAME = "items.db";
    private static final int DATABASE_VERSION = 3;


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ITEMS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_ITEM
            + " text not null, " + COLUMN_IMG + " blob, "
            +  COLUMN_COLOUR + " text not null, "
            + COLUMN_TYPEOF + " text not null, "
            + COLUMN_ISCLEAN + " integer, "
            + COLUMN_DESC + " text not null);";

    // super constructor call
    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }


    public long  count(SQLiteDatabase database) {
        long count = 0 ;
        database.execSQL( "select count(*) " + " from " +"  TABLE_ITEMS" );
        return count ;
    }
}

