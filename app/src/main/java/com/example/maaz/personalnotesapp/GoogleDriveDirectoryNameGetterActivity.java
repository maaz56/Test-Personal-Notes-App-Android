package com.example.maaz.personalnotesapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;

/**
 * Created by maaz on 8/14/16.
 */
public class GoogleDriveDirectoryNameGetterActivity extends GoogleDriveBaseActivity {

    private final ResultCallback<DriveResource.MetadataResult> metadataCallback = new
            ResultCallback<DriveResource.MetadataResult>() {
                @Override
                public void onResult(DriveResource.MetadataResult metadataResult) {
                    if (!metadataResult.getStatus().isSuccess()) {
                        showMessage("Problem trying to fetch metadata");
                        return;
                    }
                    Metadata metadata = metadataResult.getMetadata();
                    AppSharedPreferences.storeGoogleDriveUploadFileName(getApplicationContext(), metadata.getTitle());
                    startActivity(new Intent(GoogleDriveDirectoryNameGetterActivity.this, NotesActivity.class));
                    finish();
                }
            };

    private final ResultCallback<DriveApi.DriveIdResult> mIdCallBack = new
            ResultCallback<DriveApi.DriveIdResult>() {
                @Override
                public void onResult(DriveApi.DriveIdResult driveIdResult) {
                    if (!driveIdResult.getStatus().isSuccess()) {
                        showMessage("cannot find drive ID , Are you authorized to view this file");
                        return;
                    }
                    DriveFile file = Drive.DriveApi.getFile(getmGoogleApiClient(), driveIdResult.getDriveId());
                    file.getDriveId().encodeToString();
                    file.getMetadata(getmGoogleApiClient()).setResultCallback(metadataCallback);
                }
            };

    @Override
    public void onConnected(Bundle bundle) {
        try {
            Drive.DriveApi.fetchDriveId(getmGoogleApiClient(),
                    AppSharedPreferences.getGoogleDriveResourceId(getApplicationContext())).setResultCallback(mIdCallBack);
            AppSharedPreferences.setPersonalNotesPreferences(getApplicationContext(), AppConstant.GOOGLE_DRIVE_SELECTION);
            AppSharedPreferences.isGoogleDriveAuthenticated(getApplicationContext(), true);
            showMessage("Image Location set in Google Drive");
        } catch (IllegalStateException e) {
            showMessage("An error occured while selected the folder, Sync issue? Please try again");
            startActivity(new Intent(GoogleDriveDirectoryNameGetterActivity.this, GoogleDriveSelectionActivity.class));
            finish();
        }catch (IllegalArgumentException e){
            showMessage("An error occured while selected the folder, Sync issue? Please try again");
            startActivity(new Intent(GoogleDriveDirectoryNameGetterActivity.this, GoogleDriveSelectionActivity.class));
            finish();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


}
