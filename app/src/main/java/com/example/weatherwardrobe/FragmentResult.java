package com.example.weatherwardrobe;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.BitmapFactory.*;

public class FragmentResult extends Fragment {

    String ACTIVITY_NAME = "FragmentResult";

    private ImageView outerwearImageView;
    private TextView outerwearTitle, outerwearTypeText, outerwearColourText, outerwearDescText;
    private ImageView topImageView;
    private TextView topTypeText, topColourText, topDescText;
    private ImageView bottomImageView;
    private TextView bottomTypeText, bottomColourText, bottomDescText;

    private String outerwearType;
    private String outerwearColour;
    private String outerwearDesc;
    private String topType;
    private String topColour;
    private String topDesc;
    private String bottomType;
    private String bottomColour;
    private String bottomDesc;

    private Bitmap outerwearImg = null;
    private Bitmap topImg = null;
    private Bitmap bottomImg = null;

    private byte[] outerwearByteArr = null;
    private byte[] topByteArr = null;
    private byte[] bottomByteArr = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        outerwearTitle = view.findViewById(R.id.outerwearTitle);
        outerwearImageView = view.findViewById(R.id.result_outerwear);
        outerwearTypeText = view.findViewById(R.id.outerwearType);
        outerwearColourText = view.findViewById(R.id.outerwearColour);
        outerwearDescText = view.findViewById(R.id.outerwearDesc);
        topImageView = view.findViewById(R.id.result_top);
        topTypeText = view.findViewById(R.id.topType);
        topColourText = view.findViewById(R.id.topColour);
        topDescText = view.findViewById(R.id.topDesc);
        bottomImageView = view.findViewById(R.id.result_bottom);
        bottomTypeText = view.findViewById(R.id.bottomType);
        bottomColourText = view.findViewById(R.id.bottomColour);
        bottomDescText = view.findViewById(R.id.bottomDesc);

        // get current temperature
        SharedPreferences preferences = getActivity().getSharedPreferences("my_prefs", MODE_PRIVATE);
        float currentTemp = Float.parseFloat(preferences.getString("currentTemp",""));

        Log.i(ACTIVITY_NAME, "temp: " + currentTemp);

        Bundle bundle = getArguments();
        if(bundle != null) {
            topType = bundle.getString("topType");
            topColour = bundle.getString("topColour");
            bottomType = bundle.getString("bottomType");
            bottomColour = bundle.getString("bottomColour");

            OutfitGenerator generator = new OutfitGenerator(getContext(), topType, topColour, bottomType, bottomColour);

            ClothingItem top = generator.selectedOutfit.top;
            ClothingItem bottom = generator.selectedOutfit.bottom;

            topByteArr = top.getImg();
            if (topByteArr != null){
                topImg = BitmapFactory.decodeByteArray(topByteArr, 0, topByteArr.length);
            }
            topColour = top.getColour();
            topType = top.getType();
            topDesc = top.getDescription();
            bottomByteArr = bottom.getImg();
            if (bottomByteArr != null){
                bottomImg = BitmapFactory.decodeByteArray(bottomByteArr, 0, bottomByteArr.length);
            }
            bottomColour = bottom.getColour();
            bottomType = bottom.getType();
            bottomDesc = bottom.getDescription();

            outerwearTitle.setVisibility(View.GONE);
            outerwearImageView.setVisibility(View.GONE);
            outerwearTypeText.setVisibility(View.GONE);
            outerwearColourText.setVisibility(View.GONE);
            outerwearDescText.setVisibility(View.GONE);

            topTypeText.setText(top.getType());
            topColourText.setText(top.getColour());
            topDescText.setText(top.getDescription());
            topImageView.setImageBitmap(topImg);
            bottomTypeText.setText(bottom.getType());
            bottomColourText.setText(bottom.getColour());
            bottomDescText.setText(bottom.getDescription());
            bottomImageView.setImageBitmap(bottomImg);

        } else {
            Log.i(ACTIVITY_NAME, "bundle empty");
            OutfitGenerator generator = new OutfitGenerator(getContext(), currentTemp);
            // clothing items from generator
            ClothingItem outerwear = generator.selectedOutfit.outerwear;
            ClothingItem top = generator.selectedOutfit.top;
            ClothingItem bottom = generator.selectedOutfit.bottom;
            if (outerwear != null){
                outerwearImg = BitmapFactory.decodeByteArray(outerwear.getImg(), 0, outerwear.getImg().length);
                outerwearImageView.setImageBitmap(outerwearImg);
                outerwearTypeText.setText(outerwear.getType());
                outerwearColourText.setText(outerwear.getColour());
                outerwearDescText.setText(outerwear.getDescription());
            }
            else{
                outerwearTitle.setVisibility(View.GONE);
                outerwearImageView.setVisibility(View.GONE);
                outerwearTypeText.setVisibility(View.GONE);
                outerwearColourText.setVisibility(View.GONE);
                outerwearDescText.setVisibility(View.GONE);
            }
            topByteArr = top.getImg();
            if (topByteArr != null){
                topImg = BitmapFactory.decodeByteArray(topByteArr, 0, topByteArr.length);
            }
            topImageView.setImageBitmap(topImg);
            topTypeText.setText(top.getType());
            topColourText.setText(top.getColour());
            topDescText.setText(top.getDescription());
            bottomByteArr = bottom.getImg();
            if (bottomByteArr != null){
                bottomImg = BitmapFactory.decodeByteArray(bottomByteArr, 0, bottomByteArr.length);
            }
            bottomImageView.setImageBitmap(bottomImg);
            bottomTypeText.setText(bottom.getType());
            bottomColourText.setText(bottom.getColour());
            bottomDescText.setText(bottom.getDescription());

        }

        return view;
    }
}
