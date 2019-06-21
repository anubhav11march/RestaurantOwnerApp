package com.anubhav11march.foodorder;

public class orde {
    String username, itemname;
    public orde(){

    }

    public orde(String username, String itemname){
        this.username = username;
        this.itemname = itemname;
    }

    public String getItemname() {
        return itemname;
    }

    public String getUsername() {
        return username;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

