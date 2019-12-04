package com.example.weatherwardrobe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static android.graphics.BitmapFactory.*;

public class FragmentResult extends Fragment{

    String ACTIVITY_NAME = "FragmentResult";

    private ImageView outerwearImageView;
    private TextView outerwearTitle, outerwearTypeText, outerwearColourText, outerwearDescText;
    private ImageView topImageView;
    private TextView topTitle, topTypeText, topColourText, topDescText;
    private ImageView bottomImageView;
    private TextView bottomTitle, bottomTypeText, bottomColourText, bottomDescText;
    private Button refreshButton, confirmButton;

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

    private ItemsDataSource dh;

    public View view;
    public ClothingItem top, bottom, outerwear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_result, container, false);

        confirmButton = view.findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dh = new ItemsDataSource(view.getContext());
                dh.open();
                if (top != null) {
                    dh.add_to_laundry(top.getId());
                }
                if (bottom != null) {
                    dh.add_to_laundry(bottom.getId());
                }
                if(outerwear != null){
                    dh.add_to_laundry(outerwear.getId());
                }
                dh.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        refreshButton = view.findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerwearTitle = view.findViewById(R.id.outerwearTitle);
                outerwearImageView = view.findViewById(R.id.result_outerwear);
                outerwearTypeText = view.findViewById(R.id.outerwearType);
                outerwearColourText = view.findViewById(R.id.outerwearColour);
                outerwearDescText = view.findViewById(R.id.outerwearDesc);

                topTitle = view.findViewById(R.id.top_title);
                topImageView = view.findViewById(R.id.result_top);
                topTypeText = view.findViewById(R.id.topType);
                topColourText = view.findViewById(R.id.topColour);
                topDescText = view.findViewById(R.id.topDesc);

                bottomTitle = view.findViewById(R.id.bottom_title);
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

                    outerwearTitle.setVisibility(View.GONE);
                    outerwearImageView.setVisibility(View.GONE);
                    outerwearTypeText.setVisibility(View.GONE);
                    outerwearColourText.setVisibility(View.GONE);
                    outerwearDescText.setVisibility(View.GONE);

                    if (top != null) {
                        topByteArr = top.getImg();
                        if (topByteArr != null) {
                            topImg = BitmapFactory.decodeByteArray(topByteArr, 0, topByteArr.length);
                        }
                        topImageView.setImageBitmap(topImg);
                        topTypeText.setText(top.getType());
                        topColourText.setText(top.getColour());
                        topDescText.setText(top.getDescription());
                    }
                    else{
                        topTitle.setVisibility(View.GONE);
                        topImageView.setVisibility(View.GONE);
                        topTypeText.setVisibility(View.GONE);
                        topColourText.setVisibility(View.GONE);
                        topDescText.setVisibility(View.GONE);
                    }
                    if (bottom != null) {
                        bottomByteArr = bottom.getImg();
                        if (bottomByteArr != null) {
                            bottomImg = BitmapFactory.decodeByteArray(bottomByteArr, 0, bottomByteArr.length);
                        }
                        bottomImageView.setImageBitmap(bottomImg);
                        bottomTypeText.setText(bottom.getType());
                        bottomColourText.setText(bottom.getColour());
                        bottomDescText.setText(bottom.getDescription());
                    }
                    else{
                        bottomTitle.setVisibility(View.GONE);
                        bottomImageView.setVisibility(View.GONE);
                        bottomTypeText.setVisibility(View.GONE);
                        bottomColourText.setVisibility(View.GONE);
                        bottomDescText.setVisibility(View.GONE);
                    }
                } else {
                    Log.i(ACTIVITY_NAME, "bundle empty");
                    OutfitGenerator generator = new OutfitGenerator(getContext(), currentTemp);
                    // clothing items from generator
                    ClothingItem outerwear = generator.selectedOutfit.outerwear;
                    ClothingItem top = generator.selectedOutfit.top;
                    ClothingItem bottom = generator.selectedOutfit.bottom;
                    if (outerwear != null) {
                        outerwearImg = BitmapFactory.decodeByteArray(outerwear.getImg(), 0, outerwear.getImg().length);
                        outerwearImageView.setImageBitmap(outerwearImg);
                        outerwearTypeText.setText(outerwear.getType());
                        outerwearColourText.setText(outerwear.getColour());
                        outerwearDescText.setText(outerwear.getDescription());
                    } else {
                        outerwearTitle.setVisibility(View.GONE);
                        outerwearImageView.setVisibility(View.GONE);
                        outerwearTypeText.setVisibility(View.GONE);
                        outerwearColourText.setVisibility(View.GONE);
                        outerwearDescText.setVisibility(View.GONE);
                    }
                    if (top != null) {
                        topByteArr = top.getImg();
                        if (topByteArr != null) {
                            topImg = BitmapFactory.decodeByteArray(topByteArr, 0, topByteArr.length);
                        }
                        topImageView.setImageBitmap(topImg);
                        topTypeText.setText(top.getType());
                        topColourText.setText(top.getColour());
                        topDescText.setText(top.getDescription());
                    }
                    else{
                        topTitle.setVisibility(View.GONE);
                        topImageView.setVisibility(View.GONE);
                        topTypeText.setVisibility(View.GONE);
                        topColourText.setVisibility(View.GONE);
                        topDescText.setVisibility(View.GONE);
                    }
                    if (bottom != null) {
                        bottomByteArr = bottom.getImg();
                        if (bottomByteArr != null) {
                            bottomImg = BitmapFactory.decodeByteArray(bottomByteArr, 0, bottomByteArr.length);
                        }
                        bottomImageView.setImageBitmap(bottomImg);
                        bottomTypeText.setText(bottom.getType());
                        bottomColourText.setText(bottom.getColour());
                        bottomDescText.setText(bottom.getDescription());
                    }
                    else{
                        bottomTitle.setVisibility(View.GONE);
                        bottomImageView.setVisibility(View.GONE);
                        bottomTypeText.setVisibility(View.GONE);
                        bottomColourText.setVisibility(View.GONE);
                        bottomDescText.setVisibility(View.GONE);
                    }
                }
            }
        });

        outerwearTitle = view.findViewById(R.id.outerwearTitle);
        outerwearImageView = view.findViewById(R.id.result_outerwear);
        outerwearTypeText = view.findViewById(R.id.outerwearType);
        outerwearColourText = view.findViewById(R.id.outerwearColour);
        outerwearDescText = view.findViewById(R.id.outerwearDesc);
        topTitle = view.findViewById(R.id.top_title);
        topImageView = view.findViewById(R.id.result_top);
        topTypeText = view.findViewById(R.id.topType);
        topColourText = view.findViewById(R.id.topColour);
        topDescText = view.findViewById(R.id.topDesc);
        bottomTitle = view.findViewById(R.id.bottom_title);
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

            top = generator.selectedOutfit.top;
            bottom = generator.selectedOutfit.bottom;

            outerwearTitle.setVisibility(View.GONE);
            outerwearImageView.setVisibility(View.GONE);
            outerwearTypeText.setVisibility(View.GONE);
            outerwearColourText.setVisibility(View.GONE);
            outerwearDescText.setVisibility(View.GONE);

            if (top != null) {
                topByteArr = top.getImg();
                if (topByteArr != null) {
                    topImg = BitmapFactory.decodeByteArray(topByteArr, 0, topByteArr.length);
                }
                topImageView.setImageBitmap(topImg);
                topTypeText.setText(top.getType());
                topColourText.setText(top.getColour());
                topDescText.setText(top.getDescription());
            }
            else{
                topTitle.setVisibility(View.GONE);
                topImageView.setVisibility(View.GONE);
                topTypeText.setVisibility(View.GONE);
                topColourText.setVisibility(View.GONE);
                topDescText.setVisibility(View.GONE);
            }
            if (bottom != null) {
                bottomByteArr = bottom.getImg();
                if (bottomByteArr != null) {
                    bottomImg = BitmapFactory.decodeByteArray(bottomByteArr, 0, bottomByteArr.length);
                }
                bottomImageView.setImageBitmap(bottomImg);
                bottomTypeText.setText(bottom.getType());
                bottomColourText.setText(bottom.getColour());
                bottomDescText.setText(bottom.getDescription());
            }
            else{
                bottomTitle.setVisibility(View.GONE);
                bottomImageView.setVisibility(View.GONE);
                bottomTypeText.setVisibility(View.GONE);
                bottomColourText.setVisibility(View.GONE);
                bottomDescText.setVisibility(View.GONE);
            }

        } else {
            Log.i(ACTIVITY_NAME, "bundle empty");
            OutfitGenerator generator = new OutfitGenerator(getContext(), currentTemp);
            // clothing items from generator
            outerwear = generator.selectedOutfit.outerwear;
            top = generator.selectedOutfit.top;
            bottom = generator.selectedOutfit.bottom;
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
            if (top != null) {
                topByteArr = top.getImg();
                if (topByteArr != null) {
                    topImg = BitmapFactory.decodeByteArray(topByteArr, 0, topByteArr.length);
                }
                topImageView.setImageBitmap(topImg);
                topTypeText.setText(top.getType());
                topColourText.setText(top.getColour());
                topDescText.setText(top.getDescription());
            }
            else{
                topTitle.setVisibility(View.GONE);
                topImageView.setVisibility(View.GONE);
                topTypeText.setVisibility(View.GONE);
                topColourText.setVisibility(View.GONE);
                topDescText.setVisibility(View.GONE);
            }
            if (bottom != null) {
                bottomByteArr = bottom.getImg();
                if (bottomByteArr != null) {
                    bottomImg = BitmapFactory.decodeByteArray(bottomByteArr, 0, bottomByteArr.length);
                }
                bottomImageView.setImageBitmap(bottomImg);
                bottomTypeText.setText(bottom.getType());
                bottomColourText.setText(bottom.getColour());
                bottomDescText.setText(bottom.getDescription());
            }
            else{
                bottomTitle.setVisibility(View.GONE);
                bottomImageView.setVisibility(View.GONE);
                bottomTypeText.setVisibility(View.GONE);
                bottomColourText.setVisibility(View.GONE);
                bottomDescText.setVisibility(View.GONE);
            }
        }

        return view;
    }
}
