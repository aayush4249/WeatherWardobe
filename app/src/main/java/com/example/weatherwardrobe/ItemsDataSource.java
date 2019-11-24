package com.example.weatherwardrobe;

import java.util.ArrayList;

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

    public ClothingItem createItem(ClothingItem item) {
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
        ClothingItem newItem = cursorToItem(cursor);

        cursor.close();
        return newItem;
    }

    public void deleteItem(ClothingItem item) {
        long id = item.getId();
        Log.d(TAG, "delete item = " + id);
        System.out.println("Item deleted with id: " + id);

        database.delete(SQLiteHelper.TABLE_ITEMS, SQLiteHelper.COLUMN_ID
                + " = " + id, null);

    }

    public void deleteAllItems() {
        database.delete(SQLiteHelper.TABLE_ITEMS, null, null);
    }

    /*public List<ClothingItem> getAllItem() {
        List<ClothingItem> items = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_ITEMS,
                allItems, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ClothingItem item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return items;
    }

    public List<ClothingItem> getDirtyItems(){
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE isClean = 0";
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ClothingItem item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return items;
    }*/

    public ArrayList<ClothingItem> getItems(String sql){
        ArrayList<ClothingItem> items = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ClothingItem item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }

        // Make sure to close the cursor
        cursor.close();
        return items;
    }

    public void clear_basket(){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_ISCLEAN, 1);
        String sql = "SELECT * FROM items WHERE isClean = 0";
        database.update(SQLiteHelper.TABLE_ITEMS, values, sql, null);
    }

    public void clear_item(long id){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_ISCLEAN, 1);
        String sql = "SELECT * FROM items WHERE _id = " + id;
        database.update(SQLiteHelper.TABLE_ITEMS, values, sql, null);
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


    private ClothingItem cursorToItem(Cursor cursor) {
        ClothingItem item = new ClothingItem();


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
