package com.example.weatherwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WardrobeItemInfo extends AppCompatActivity {
    public long id;
    private ItemsDataSource dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe_item_info);
        dh = new ItemsDataSource(this);
        dh.open();
        id = getIntent().getExtras().getLong("ID");
        byte[] img = getIntent().getExtras().getByteArray("Image");
        Bitmap bitmap = null;
        if (img != null){
            bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        }
        String colour = getIntent().getExtras().getString("Colour");
        String type = getIntent().getExtras().getString("Type");
        String desc = getIntent().getExtras().getString("Description");
        TextView id_text = (TextView)findViewById(R.id.item_id);
        TextView colour_text = (TextView)findViewById(R.id.colour);
        TextView type_text = (TextView)findViewById(R.id.type);
        TextView desc_text = (TextView)findViewById(R.id.desc);
        ImageView img_picture = (ImageView)findViewById(R.id.image);
        id_text.setText("Item ID: " + id);
        colour_text.setText("Colour: " + colour);
        type_text.setText("Type: " + type);
        desc_text.setText("Description: " + desc);
        if (bitmap != null) {
            img_picture.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 30, 30, false));
        }
    }

    public void onClick(View v){
        dh.deleteItem(id);
        finish();
    }

    public void onDestroy(){
        super.onDestroy();
        dh.close();
    }
}
