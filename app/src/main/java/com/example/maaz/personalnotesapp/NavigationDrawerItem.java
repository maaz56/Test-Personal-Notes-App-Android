package com.example.maaz.personalnotesapp;

/**
 * Created by maaz on 7/5/16.
 */
public class NavigationDrawerItem {

    private int iconId;
    private String title;


    public NavigationDrawerItem(int iconId, String title) {
        this.iconId = iconId;
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }



    public String getTitle() {
        return title;
    }


}
