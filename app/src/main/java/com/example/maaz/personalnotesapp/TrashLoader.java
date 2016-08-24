package com.example.maaz.personalnotesapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maaz on 7/10/16.
 */
public class TrashLoader extends AsyncTaskLoader<List<Trash>> {
    private List<Trash> mTrash;
    private ContentResolver mContentResolver;
    private Cursor mCursor;

    public TrashLoader(Context context, ContentResolver contentResolver) {
        super(context);
        this.mContentResolver = contentResolver;
    }


    @Override
    public List<Trash> loadInBackground() {
        List<Trash> entries = new ArrayList<>();
        String[] projection = {BaseColumns._ID,
                TrashContract.TrashColumns.TRASH_TITLE,
                TrashContract.TrashColumns.TRASH_DESCRIPTION,
                TrashContract.TrashColumns.TRASH_DATE_TIME
        };

        Uri uri = TrashContract.URI_TABLE;

        mCursor = mContentResolver.query(uri, projection, null, null, BaseColumns._ID + " DESC");
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String title = mCursor.getString(mCursor.getColumnIndex(TrashContract.TrashColumns.TRASH_TITLE));
                    String description = mCursor.getString(mCursor.getColumnIndex(TrashContract.TrashColumns.TRASH_DESCRIPTION));
                    String dateTime = mCursor.getString(mCursor.getColumnIndex(TrashContract.TrashColumns.TRASH_DATE_TIME));
                    int _id = mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));

                    Trash trash = new Trash(_id, title, description, dateTime);

                    entries.add(trash);
                } while (mCursor.moveToNext());
            }
        }

        return entries;
    }

    @Override
    public void deliverResult(List<Trash> trash) {
        if (isReset()) {
            if (trash != null) {
                releaseResources();
                return;
            }
        }
        List<Trash> oldTrash = mTrash;
        mTrash = trash;
        if (isStarted()) {
            super.deliverResult(trash);
        }
        if (oldTrash != null && oldTrash != trash) {
            releaseResources();
        }
    }


    @Override
    protected void onStartLoading() {
        if (mTrash != null) {
            deliverResult(mTrash);
        }
        if (takeContentChanged()) {
            forceLoad();
        } else if (mTrash == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (mTrash != null) {
            releaseResources();
            mTrash = null;
        }
    }

    @Override
    public void onCanceled(List<Trash> trash) {
        super.onCanceled(trash);
        releaseResources();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    private void releaseResources() {
        mCursor.close();
    }


}
