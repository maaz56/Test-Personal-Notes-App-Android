package com.example.maaz.personalnotesapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by maaz on 7/8/16.
 */
public class NotesContract {
    interface NotesColumns {
        String NOTES_ID = "_ID";
        String NOTES_TITLE = "notes_title";
        String NOTES_DESCRIPTION = "notes_description";
        String NOTES_DATE = "notes_date";
        String NOTES_TIME = "notes_time";
        String NOTES_IMAGE = "notes_image";
        String NOTES_TYPE = "notes_type";
        String NOTES_IMAGE_STORAGE_SELECTION = "notes_image_storage_selection";
    }

    public static final String CONTENT_AUTHORITY = "com.example.maaz.personalnotesapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NOTES = "notes";
    public static final Uri URI_TABLE = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_NOTES).build();

    public static class Notes implements NotesColumns, BaseColumns {
        public static final String CONTANT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "notes";
        public static final String CONTANT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "notes";

        public static Uri buildNoteUri(String noteId) {
            return URI_TABLE.buildUpon().appendEncodedPath(noteId).build();
        }

        public static String getNoteId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}