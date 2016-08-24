package com.example.maaz.personalnotesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by maaz on 8/8/16.
 */
public class AppAuthenticationActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_layout);
        activeToolbarWithHomeEnabled();
        ImageView dropBoxImageView = (ImageView) findViewById(R.id.drop_box_set);

        dropBoxImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppAuthenticationActivity.this, DropboxPickerActivity.class));
                finish();
            }
        });

        ImageView googleDriveImageView = (ImageView) findViewById(R.id.google_drive_set);
        googleDriveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppAuthenticationActivity.this, GoogleDriveSelectionActivity.class));
                finish();
            }
        });

        setLabel();
    }

    private void setLabel() {
        TextView dropLabel = (TextView) findViewById(R.id.label_drop_box);
        TextView googleLabel = (TextView) findViewById(R.id.label_google_drive);

        if (AppSharedPreferences.isGoogleDriveAuthenticated(getApplicationContext()))
            googleLabel.setText(AppConstant.STORING_AT + AppSharedPreferences.getGoogleDriveUploadPath(getApplicationContext()));
        else
            googleLabel.setText(AppConstant.AUTH_MESSAGE);
        if (AppSharedPreferences.isDropboxAuthenticated(getApplicationContext()))
            dropLabel.setText(AppConstant.STORING_AT + getDirNameFromFullPath());
        else
            dropLabel.setText(AppConstant.AUTH_MESSAGE);
        LinearLayout dropTick = (LinearLayout) findViewById(R.id.tick_drop_box);
        LinearLayout googleTick = (LinearLayout) findViewById(R.id.tick_google_drive);
        if (AppSharedPreferences.getUploadPreference(getApplicationContext()) == AppConstant.DROP_BOX_SELECTION)
            googleTick.setVisibility(View.GONE);
        else if (AppSharedPreferences.getUploadPreference(getApplicationContext()) == AppConstant.GOOGLE_DRIVE_SELECTION)
            dropTick.setVisibility(View.GONE);
        else {
            googleTick.setVisibility(View.GONE);
            dropTick.setVisibility(View.GONE);
        }

    }

    private String getDirNameFromFullPath() {
        String fullPath = AppSharedPreferences.getDropboxUploadPath(getApplicationContext());
        String tokes[] = fullPath.split("/");
        return tokes[tokes.length - 1];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            actAsNote();
            startActivity(new Intent(AppAuthenticationActivity.this, NotesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
