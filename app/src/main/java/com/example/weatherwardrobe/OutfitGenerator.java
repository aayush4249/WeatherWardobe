package com.example.weatherwardrobe;

import android.content.SharedPreferences;

public class OutfitGenerator {

    private String topType;
    private String topColour;
    private String bottomType;
    private String bottomColour;
//    private int currentTemp = getCurrentTemp();
    public Outfit selectedOutfit;


    // use empty constructor when selecting random outfit
    public OutfitGenerator(){
        this.selectedOutfit = getRandomOutfit();
    }

    public OutfitGenerator(String topType, String topColour, String bottomType, String bottomColour){
        this.topType = topType;
        this.topColour = topColour;
        this.bottomType = bottomType;
        this.bottomColour = bottomColour;
    }

    public Outfit getOutfit(){
        return this.selectedOutfit;
    }

    private Outfit getRandomOutfit(){
        Outfit out = new Outfit();
        return out;
    }

//    private int getCurrentTemp(){
//        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
//        String temp = bb.getString("currentTemp", "");
//        int currentTemp = Integer.parseInt(temp);
//        return currentTemp;
//    }

    public class Outfit{

        ClothingItem outerwear;
        ClothingItem top;
        ClothingItem bottom;

        public Outfit() { }

        public Outfit(ClothingItem outerwear, ClothingItem top, ClothingItem bottom){
            this.outerwear = outerwear;
            this.top = top;
            this.bottom = bottom;
        }

        public Outfit(ClothingItem top, ClothingItem bottom){
            this.top = top;
            this.bottom = bottom;
        }
    }

}
