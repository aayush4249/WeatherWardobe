package com.example.weatherwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyWardrobe extends AppCompatActivity {
    public Button wardrobeButton;
    public ArrayList<ClothingItem> clothingItems;
    public ListView wardrobeList;
    public WardrobeListAdapter wardrobeAdapter;
    private ItemsDataSource dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wardrobe);
        wardrobeButton = findViewById(R.id.add_clothing);
        wardrobeList = findViewById(R.id.wardrobe_list);
        dh = new ItemsDataSource(this);
        clothingItems = new ArrayList<ClothingItem>();
        wardrobeAdapter = new WardrobeListAdapter(this);
        wardrobeList.setAdapter(wardrobeAdapter);
        dh.open();
        String sql = "SELECT * FROM items WHERE isClean = 1";
        clothingItems = dh.getItems(sql);
        wardrobeAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {



    }

    public void clear_basket(View v){
        if (clothingItems.size() > 0) {
            dh.clear_basket();
            clothingItems.clear();
            wardrobeAdapter.notifyDataSetChanged();
        }
    }

    private class WardrobeListAdapter extends ArrayAdapter<ClothingItem> {
        public WardrobeListAdapter(Context ctx) {
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
            LayoutInflater inflater = MyWardrobe.this.getLayoutInflater();
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
