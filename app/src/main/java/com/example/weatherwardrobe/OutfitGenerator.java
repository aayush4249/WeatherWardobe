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
        this.topType = topType;
        this.topColour = topColour;
        this.bottomType = bottomType;
        this.bottomColour = bottomColour;
    }

    public Outfit getOutfit(){
        return this.selectedOutfit;
    }

    private Outfit getRandomOutfit(Context context, float currentTemp){
        Outfit out = new Outfit();
        String outerwearType = "coat";
        String topType = "tshirt";
        String bottomType = "pants";

        if(currentTemp > 18){
            bottomType = "shorts";
        }

        if(currentTemp < 5){
            outerwearType = "coat";
            topType = "longsleeve";
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
        int outRand = rand.nextInt(outerwearItems.size());
        int topRand = rand.nextInt(topItems.size());
        int botRand = rand.nextInt(bottomItems.size());

        out.outerwear = outerwearItems.get(outRand);
        out.top = topItems.get(topRand);
        out.bottom = bottomItems.get(botRand);

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
        }
    }

}
