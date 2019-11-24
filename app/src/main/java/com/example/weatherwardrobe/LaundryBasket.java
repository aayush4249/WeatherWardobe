package com.example.weatherwardrobe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class LaundryBasket extends AppCompatActivity {
    public Button b;
    public ArrayList<ClothingItem> clothingItems;
    public ListView l;
    public LaundryListAdapter laundryAdapter;
    public long positionInArray;
    private ItemsDataSource dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_basket);
        b = findViewById(R.id.clean);
        l = findViewById(R.id.laundry_list);
        ClothingItem temp;
        dh = new ItemsDataSource(this);
        clothingItems = new ArrayList<ClothingItem>();
        laundryAdapter = new LaundryBasket.LaundryListAdapter(this);
        l.setAdapter(laundryAdapter);
        dh.open();
        String sql = "SELECT * FROM items WHERE isClean = 0";
        clothingItems = dh.getItems(sql);
        laundryAdapter.notifyDataSetChanged();

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                positionInArray = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(LaundryBasket.this);
                builder.setMessage("Do you want to delete this item");
                builder.setTitle("Delete Item");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        long itemId = clothingItems.get((int)positionInArray).getId();
                        dh.clear_item(itemId);
                        clothingItems.remove(positionInArray);
                        laundryAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    public void clear_basket(View v){
        if (clothingItems.size() > 0) {
            dh.clear_basket();
            clothingItems.clear();
            laundryAdapter.notifyDataSetChanged();
        }
    }

    private class LaundryListAdapter extends ArrayAdapter<ClothingItem> {
        public LaundryListAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount(){
            return clothingItems.size();
        }
        public ClothingItem getItem(int position){
            return clothingItems.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            //get clothing item and attributes
            ClothingItem item = getItem(position);
            String type = item.getType();
            //convert byte array to bitmap
            byte[] byteArr = item.getImg();
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length);
            //inflate view
            LayoutInflater inflater = LaundryBasket.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.clothing_item_layout, null);
            //get views
            ImageView image = (ImageView)result.findViewById(R.id.image);
            TextView type_text = (TextView)result.findViewById(R.id.type);
            //set views
            image.setImageBitmap(bitmap);
            type_text.setText(type);

            return result;
        }
    }
}
