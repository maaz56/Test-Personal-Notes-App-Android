package com.example.maaz.personalnotesapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by maaz on 7/8/16.
 */
public class ArchivesContract {

    interface ArchivesColumns {
        String ARCHIVES_TITLE = "archives_title";
        String ARCHIVES_DESCRIPTION = "archives_description";
        String ARCHIVES_DATE_TIME = "archives_datetime";
        String ARCHIVES_CATEGORY = "archives_category";
        String ARCHIVES_TYPE = "archives_type";
    }

    public static final String CONTENT_AUTHORITY = "com.example.maaz.personalnotesapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ARCHIVES = "archives";
    public static final Uri URI_TABLE = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_ARCHIVES).build();

    public static class Archives implements ArchivesColumns, BaseColumns {
        public static final String CONTANT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "notes";
        public static final String CONTANT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "notes";

        public static Uri buildArchiveUri(String archiveId) {
            return URI_TABLE.buildUpon().appendEncodedPath(archiveId).build();
        }

        public static String getArchiveId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
