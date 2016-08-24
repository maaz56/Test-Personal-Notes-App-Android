package com.example.maaz.personalnotesapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maaz on 7/1/16.
 */
public class AppSharedPreferences {

    //setter method for Nevigation Drawer state,
    public static void setUserLearned(Context context, String prefName, String prefValue) {
        android.content.SharedPreferences sharedPreferences = context
                .getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }

    //getter method for Nevigation Drawer state,
    public static String hasUserLearned(Context context, String prefName, String defaultValue) {
        android.content.SharedPreferences sharedPreferences = context
                .getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefName, defaultValue);
    }

    //return preferences for image location ,Google drive, Droup Box, Local.
    public static int getUploadPreference(Context context) {
        SharedPreferences preferences = context
                .getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getInt(AppConstant.IMAGE_SELECTION_STORAGE, AppConstant.NONE_SELECTION);
    }

    //Set perameters for Image Location
    public static void setPersonalNotesPreferences(Context context, int value) {
        SharedPreferences preferences = context.getSharedPreferences(
                AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AppConstant.IMAGE_SELECTION_STORAGE, value);
        editor.apply();
    }

    //this method will store the ResourceID of the google drive directory to which we will save the image
    public static void storeGoogleDriveResourceId(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(
                AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstant.GOOGLE_DRIVE_ID, value);
        editor.apply();
    }


    //get ResourceID of the Google drive path (where save images)
    public static String getGoogleDriveResourceId(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstant.GOOGLE_DRIVE_ID, "");
    }

    //this method will store the name of the google drive directory to which we will save the image
    public static void storeGoogleDriveUploadFileName(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(
                AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstant.GOOGLE_DRIVE_UPLOADER_BOOL, value);
        editor.apply();
    }

    //this will get the path of the google drive directory to which we will save the image
    public static String getGoogleDriveUploadPath(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstant.GOOGLE_DRIVE_UPLOADER_BOOL, "");
    }

    //this will store the image upload path in dropbox
    public static void storeDropBoxUploadPath(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(
                AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstant.DROP_BOX_UPLOAD_PATH, value);
        editor.apply();
    }

    //setDropboxploadPath (where save images)
    public static void setDropboxploadPath(Context context, String value) {
        SharedPreferences preferences = context.getSharedPreferences(
                AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AppConstant.DROP_BOX_UPLOAD_PATH, value);
        editor.apply();
    }

    //getDropboxUploadPath (where save images)
    public static String getDropboxUploadPath(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstant.DROP_BOX_UPLOAD_PATH, "");
    }


    public static boolean isDropboxAuthenticated(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(AppConstant.DROP_BOX_AUTH_BOOL, false);
    }

    public static void isDropboxAuthenticated(Context context, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(
                AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AppConstant.DROP_BOX_AUTH_BOOL, value);
        editor.apply();
    }

    public static boolean isGoogleDriveAuthenticated(Context context) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(AppConstant.GOOGLE_DRIVE_AUTH_BOOL, false);
    }

    public static void isGoogleDriveAuthenticated(Context context, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(
                AppConstant.PERSONAL_NOTES_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AppConstant.GOOGLE_DRIVE_AUTH_BOOL, value);
        editor.apply();
    }
}
