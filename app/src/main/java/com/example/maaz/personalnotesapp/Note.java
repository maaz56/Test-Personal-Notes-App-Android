package com.example.maaz.personalnotesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by maaz on 7/6/16.
 */
public class Note {

    private String mTitle, mDescription, mDate, mTime, mImagePath, mType;
    private int mId;
    private boolean mHasNoImage = false;
    private int mStorageSelection;

    private Bitmap mBitmap;

    public Note(String mTitle, String mDescription, String mDate, String mTime, int mId, int mStorageSelection, String mType) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mId = mId;
        this.mStorageSelection = mStorageSelection;
        this.mType = mType;
    }

    public Note(String reminderString) {

        String[] fields = reminderString.split("\\$");
        Log.d("fields length", fields.length + "");

        Log.d("remider", reminderString);
        this.mType = fields[0];
        this.mId = Integer.parseInt(fields[1]);
        this.mTitle = fields[2];
        this.mDate = fields[5];
        this.mTime = fields[3];
        this.mImagePath = fields[4];
        this.mStorageSelection = Integer.parseInt(fields[6]);
        if (mType.equals(AppConstant.NORMAL)) {
            this.mDescription = fields[7];
            Note aNote = new Note(this.mTitle, this.mDescription, this.mDate, this.mTime, this.mId, this.mStorageSelection, this.mType);
            aNote.setImagePath(this.mImagePath);
        } else {
            String list = "";
            for (int i = 7; i < fields.length; i++){
                list = list + fields[i];
                this.mDescription = list;
            }

        }

    }

    public String convertToString() {
        return mType + "$"
                + mId + "$"
                + mTitle + "$"
                + mTime + "$"
                + mImagePath + "$"
                + mDate + "$"
                + mStorageSelection + "$"
                + mDescription;
        }

    public void setBitmap(String path){
        setImagePath(path);
        this.mBitmap = BitmapFactory.decodeFile(path);
    }
    public void setBitmap(Bitmap bitmap){
        this.mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public boolean isHasNoImage() {
        return mHasNoImage;
    }

    public void setHasNoImage(boolean hasNoImage) {
        mHasNoImage = hasNoImage;
    }

    public int getStrogeSelection() {
        return mStorageSelection;
    }

    public void setStrogeSelection(int strogeSelection) {
        mStorageSelection = strogeSelection;
    }
}
