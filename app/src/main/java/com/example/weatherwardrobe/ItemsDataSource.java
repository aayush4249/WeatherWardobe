package com.example.weatherwardrobe;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.example.weatherwardrobe.SQLiteHelper.COLUMN_DESC;
import static com.example.weatherwardrobe.SQLiteHelper.COLUMN_TYPE;

public class ItemsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allItems = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_ITEM, SQLiteHelper.COLUMN_IMG, SQLiteHelper.COLUMN_COLOUR,
            COLUMN_TYPE, SQLiteHelper.COLUMN_ISCLEAN, COLUMN_DESC   };

    private static final String TAG = "myItemDB";

    // call to database constructor
    ItemsDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public Item createItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_ITEM, item.getItem());
        values.put(SQLiteHelper.COLUMN_COLOUR, item.getColour());
        values.put(COLUMN_TYPE, item.getType());
        values.put(SQLiteHelper.COLUMN_ISCLEAN, item.getIsClean());
        values.put(COLUMN_DESC, item.getDescription());
        long insertId = database.insert(SQLiteHelper.TABLE_ITEMS, null,
                values);


        Cursor cursor = database.query(SQLiteHelper.TABLE_ITEMS,
                allItems, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Item newItem = cursorToItem(cursor);

        // Log the item stored
        Log.d(TAG, "item = " + cursorToItem(cursor).toString()
                + " insert ID = " + insertId);

        cursor.close();
        return newItem;
    }

    public void deleteItem(Item item) {
        long id = item.getId();
        Log.d(TAG, "delete item = " + id);
        System.out.println("Item deleted with id: " + id);

        database.delete(SQLiteHelper.TABLE_ITEMS, SQLiteHelper.COLUMN_ID
                + " = " + id, null);

    }

    public void deleteAllItems() {
        System.out.println("Item deleted all");
        Log.d(TAG, "delete all = ");

        database.delete(SQLiteHelper.TABLE_ITEMS, null, null);
    }

    public List<Item> getAllItem() {
        List<Item> items = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_ITEMS,
                allItems, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Item item = cursorToItem(cursor);
            Log.d(TAG, "get item = " + cursorToItem(cursor).toString());
            items.add(item);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return items;
    }


    public long count () {


        long count = DatabaseUtils.queryNumEntries(database, SQLiteHelper.TABLE_ITEMS);
        return count;
    }

//
//    public Item updateItem(String item, int id) {
//
//        ContentValues values = new ContentValues();
//        values.put(SQLiteHelper.COLUMN_ID, id+"");
//        values.put(SQLiteHelper.COLUMN_ITEM, item);
//
//        database.update(SQLiteHelper.TABLE_ITEMS, values, SQLiteHelper.COLUMN_ID
//                + " = " + id, null);
//        Item it  = new Item() ;
//        it.setItem(item);
//        it.setId(id);
//        return it;
//    }


    private Item cursorToItem(Cursor cursor) {
        Item item = new Item();


//        Log.d(TAG, "item.getImg = " + imgIndex);
//        Log.d(TAG, "item.getColour = " + colourIndex);
//        Log.d(TAG, "item.getType = " + typeIndex);
//        Log.d(TAG, "item.getIsClean = " + cleanIndex);
//        Log.d(TAG, "item.getDescription = " + descIndex);

        item.setId(cursor.getLong(0));
        item.setItem(cursor.getString(1));
        item.setImg(cursor.getBlob(2));
        item.setColour(cursor.getString(3));
        item.setType(cursor.getString(4));
        item.setIsClean(cursor.getInt(5));
        item.setDescription(cursor.getString(6));

        return item;
    }
}
