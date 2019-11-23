package com.example.weatherwardrobe;

public class ClothingItem {

    // create object attributes
    private long id;
    private Byte[] byteArr;
    private String type;

    // constructor sets attributes
    public ClothingItem(long id, Byte[] byteArr, String type) {
        this.id = id;
        this.byteArr = byteArr;
        this.type = type;
    }

    // get function for id
    public long getId() {
        return this.id;
    }

    // get function for byte array
    public Byte[] getByteArr() {
        return this.byteArr;
    }

    // get function for type
    public String getType() {
        return this.type;
    }

    // set function for type
    public void setByteArr(Byte[] byteArr) {
        this.byteArr = byteArr;
    }

    // set function for type
    public void setType(String type) {
        this.type = type;
    }

}
