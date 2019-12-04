package com.example.weatherwardrobe;

import android.content.Context;
import java.util.ArrayList;
import java.util.Random;

public class OutfitGenerator {

    private String topType;
    private String topColour;
    private String bottomType;
    private String bottomColour;
    private Context context;
    public Outfit selectedOutfit;


    // use empty constructor when selecting random outfit
    // context is needed for database
    public OutfitGenerator(Context context, float currentTemp){
        this.context = context;
        this.selectedOutfit = getRandomOutfit(context, currentTemp);
    }

    public OutfitGenerator(Context context, String topType, String topColour, String bottomType, String bottomColour){
        this.context = context;
        this.selectedOutfit = getSelectedOutfit(context, topType, topColour, bottomType, bottomColour);
    }

    public Outfit getOutfit(){
        return this.selectedOutfit;
    }

    public Outfit getSelectedOutfit(Context context, String topType, String topColour, String bottomType, String bottomColour){
        Outfit out = new Outfit();
        ItemsDataSource dh = new ItemsDataSource(context);
        dh.open();

        String topSQL = "SELECT * FROM items WHERE isClean = 1 AND typeof = '" + topType + "' AND colour = '" + topColour + "'";
        String bottomSQL = "SELECT * FROM items WHERE isClean = 1 AND typeof = '" + bottomType + "' AND colour = '" + bottomColour + "'";

        ArrayList<ClothingItem> topItems = dh.getItems(topSQL);
        ArrayList<ClothingItem> bottomItems = dh.getItems(bottomSQL);

        Random rand = new Random();
        if (topItems.size() > 0) {
            int topRand = rand.nextInt(topItems.size());
            out.top = topItems.get(topRand);
        }
        if (bottomItems.size() > 0) {
            int botRand = rand.nextInt(bottomItems.size());
            out.bottom = bottomItems.get(botRand);
        }

        dh.close();
        return out;
    }

    private Outfit getRandomOutfit(Context context, float currentTemp){
        Outfit out = new Outfit();
        String outerwearType = null;
        String topType = context.getResources().getString(R.string.tshirt);
        String bottomType = context.getResources().getString(R.string.pants);

        if(currentTemp > 18){
            bottomType = context.getResources().getString(R.string.shorts);
        }

        if(currentTemp < 5){
            outerwearType = context.getResources().getString(R.string.coat);
            topType = context.getResources().getString(R.string.longsleeve);
        }

        ItemsDataSource dh = new ItemsDataSource(context);
        dh.open();

        String outerwearSQL = "SELECT * FROM items WHERE isClean = 1 AND typeof = '" + outerwearType + "'";
        String topSQL = "SELECT * FROM items WHERE isClean = 1 AND typeof = '" + topType + "'";
        String bottomSQL = "SELECT * FROM items WHERE isClean = 1 AND typeof = '" + bottomType + "'";

        ArrayList<ClothingItem> outerwearItems = dh.getItems(outerwearSQL);
        ArrayList<ClothingItem> topItems = dh.getItems(topSQL);
        ArrayList<ClothingItem> bottomItems = dh.getItems(bottomSQL);

        Random rand = new Random();
        if (!outerwearItems.isEmpty()) {
            int outRand = rand.nextInt(outerwearItems.size());
            out.outerwear = outerwearItems.get(outRand);
        }
        else{
            out.outerwear = null;
        }

        if (!topItems.isEmpty()) {
            int topRand = rand.nextInt(topItems.size());
            out.top = topItems.get(topRand);
        }
        else{
            out.top = null;
        }

        if (!bottomItems.isEmpty()) {
            int botRand = rand.nextInt(bottomItems.size());
            out.bottom = bottomItems.get(botRand);
        }
        else{
            out.bottom = null;
        }


        dh.close();
        return out;
    }

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
            this.outerwear = null;
        }
    }

}
