package com.example.maaz.personalnotesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.google.api.services.drive.model.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maaz on 8/13/16.
 */
public class DropboxDirectoryCreatorAsync extends AsyncTask<Voice, Long, Boolean> {

    private DropboxAPI<?> mApi;
    private String mPath;
    private Context mContext;
    private OnDirectoryCreateFinished mListener;
    private String mName;
    private String mMessage;

    public DropboxDirectoryCreatorAsync(Context mContext, DropboxAPI<?> mApi, String mName, String mPath, OnDirectoryCreateFinished mListener) {
        this.mContext = mContext;
        this.mApi = mApi;
        this.mName = mName;
        this.mPath = mPath;
        this.mListener = mListener;
    }

    @Override
    protected Boolean doInBackground(Voice... params) {
        try {
            mApi.createFolder(mPath);
            mMessage = AppConstant.FOLDER_CREATED;
        }catch (DropboxException e){
                mMessage = AppConstant.FOLDER_CREATE_ERROR;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result){
            mListener.onDirectoryCreateFinished(mName);
            Toast.makeText(mContext.getApplicationContext(), mMessage, Toast.LENGTH_LONG).show();
        }

    }

    public interface OnDirectoryCreateFinished{
        void onDirectoryCreateFinished(String dirName);
    }
}
