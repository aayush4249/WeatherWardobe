package com.example.weatherwardrobe;

public class ClothingItem {

    // create object attributes
    private long id;
    private Byte[] byteArr;
    private String type;
    private boolean isClean;

    // constructor sets attributes
    public ClothingItem(long id, Byte[] byteArr, String type, boolean isClean) {
        this.id = id;
        this.byteArr = byteArr;
        this.type = type;
        this.isClean = isClean;
    }

    // get function for id
    public long getId() {
        return this.id;
    }

    // ==========
    // GETTERS
    // ==========

    // get function for byte array
    public Byte[] getByteArr() {
        return this.byteArr;
    }

    // get function for type
    public String getType() {
        return this.type;
    }

    // get function for is clean
    public boolean getIsClean() {
        return this.isClean;
    }

    // ==========
    // SETTERS
    // ==========

    // set function for type
    public void setByteArr(Byte[] byteArr) {
        this.byteArr = byteArr;
    }

    // set function for type
    public void setType(String type) {
        this.type = type;
    }

    // set function for is clean
    public void setIsClean(boolean isClean) {
        this.isClean = isClean;
    }

}
