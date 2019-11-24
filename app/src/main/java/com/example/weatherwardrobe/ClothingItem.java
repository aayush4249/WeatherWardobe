package com.example.weatherwardrobe;

import android.os.Parcel;
import android.os.Parcelable;

public class ClothingItem{
    private long id;
    private String item ;
    private byte[] img ; // DELETE THIS AFTER
    private String colour;
    private String type;
    private int isClean;
    private String description;

    public long getId() {
        return id;
    }

    public ClothingItem () {}
    public  ClothingItem (String name, byte[] img, String colour, String type, int isClean, String desc) {
        this.item = name ;
        this.img = img;
        this.colour = colour;
        this.type = type;
        this.isClean = isClean;
        this.description = desc;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setImg(byte[] img) {
        this.img = img;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setIsClean(int isClean) {
        this.isClean = isClean;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }


    public String getItem() {
        return item;
    }

    public byte[] getImg() {
        return img;
    }

    public String getColour() {
        return colour;
    }

    public String getType() {
        return type;
    }

    public int getIsClean() {
        return isClean;
    }

    public String getDescription(){
        return description;
    }

    public void setItem(String item) {
        this.item = item;
    } // DELETE THIS AFTER
    public String toString () {
        return item + "  " + id;
    }
}

