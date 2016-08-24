package com.example.maaz.personalnotesapp;

import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by maaz on 8/14/16.
 */
public class DropboxImageUploadAsync extends AsyncTask<Void, Long, Boolean> {

    private DropboxAPI<?> mApi;
    private String mPath;
    private File mFile;
    private String mFileName;

    public DropboxImageUploadAsync(Context context, DropboxAPI<?> mApi, File mFile, String mFileName) {

        this.mApi = mApi;
        this.mFile = mFile;
        this.mFileName = mFileName;
        this.mPath = AppSharedPreferences.getDropboxUploadPath(context);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String errorMessage;
        try {
            FileInputStream fis = new FileInputStream(mFile);
            String path = mPath + "/" + mFileName;
            DropboxAPI.UploadRequest request = mApi.putFileOverwriteRequest(path, fis, mFile.length(),
                    new ProgressListener() {

                        @Override
                        public long progressInterval() {
                            return 500;
                        }

                        @Override
                        public void onProgress(long bytes, long total) {
                            publishProgress(bytes);
                        }
                    });

            if (request != null) {
                request.upload();
                return true;
            }
        } catch (DropboxException e) {
            errorMessage = "Dropbox Exception";
        } catch (FileNotFoundException e) {
            errorMessage = "FileNotFoundException";
        }

        return false;
    }
}
