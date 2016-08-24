package com.example.maaz.personalnotesapp;

import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by maaz on 8/7/16.
 */
public class HelpFeedActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_feedback_layout);
        mToolbar = activateToolbar();
        setUpNavigationDrawer();
    }
}
