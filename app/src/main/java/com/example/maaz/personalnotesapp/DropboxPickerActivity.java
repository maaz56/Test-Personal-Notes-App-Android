package com.example.maaz.personalnotesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Created by maaz on 8/13/16.
 */
public class DropboxPickerActivity extends BaseActivity
        implements DropboxDirectoryListenerAsync.OnLoadFinished,
        DropboxDirectoryCreatorAsync.OnDirectoryCreateFinished

{
    private ProgressDialog mDialog;
    private DropboxAPI<AndroidAuthSession> mApi;
    private boolean mAfterAuth = false;
    private DropBoxAdapter mDropBoxAdapter;
    private Stack<String> mDirectoryStack = new Stack<>();
    private boolean mIsfirstClick = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbx_picker_layout);
        mDirectoryStack.push("/");
        setUpList();
        setUpBar();
        setUpDirectoryCreator();
        if (!AppSharedPreferences.isDropboxAuthenticated(getApplicationContext())) {
            authenticate();
        } else {
            AndroidAuthSession session = DropBoxActions.buildSession(getApplicationContext());
            mApi = new DropboxAPI<AndroidAuthSession>(session);
            initProgressDialog();
            new DropboxDirectoryListenerAsync(getApplicationContext(), mApi, getCurrentPath(), DropboxPickerActivity.this).execute();

        }
    }

    private void setUpDirectoryCreator() {
        final EditText newDirectory = (EditText) findViewById(R.id.new_directory_edit_text);
        final ImageView createDir = (ImageView) findViewById(R.id.new_directory);
        createDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsfirstClick) {
                    newDirectory.setVisibility(View.VISIBLE);
                    createDir.setImageResource(R.drawable.ic_action_done);
                    newDirectory.requestFocus();
                    mIsfirstClick = false;
                } else {
                    String directoryName = newDirectory.getText().toString();
                    createDir.setImageResource(R.drawable.ic_add_folder);
                    newDirectory.setVisibility(View.GONE);
                    if (directoryName.length() > 0) {
                        initProgressDialog();
                        new DropboxDirectoryCreatorAsync(getApplicationContext(), mApi,
                                directoryName, getCurrentPath() + "/" + directoryName, DropboxPickerActivity.this).execute();
                    }
                }
            }
        });
    }

    private void setUpBar() {
        TextView logoutTV = (TextView) findViewById(R.id.log_out_drop_box_label);
        logoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                AppSharedPreferences.isDropboxAuthenticated(getApplicationContext(), false);
                startActivity(new Intent(DropboxPickerActivity.this, AppAuthenticationActivity.class));
            }
        });

        ImageView save = (ImageView) findViewById(R.id.selection_directory);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreferences.storeDropBoxUploadPath(getApplicationContext(), getCurrentPath());
                AppSharedPreferences.setPersonalNotesPreferences(getApplicationContext(), AppConstant.DROP_BOX_SELECTION);
                showToast(AppConstant.IMAGE_LOCATION_SAVED_DROPBOX);
                actAsNote();
                startActivity(new Intent(DropboxPickerActivity.this, NotesActivity.class));
            }
        });

        ImageView back = (ImageView) findViewById(R.id.back_navigation);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDialog();
                try {
                    mDirectoryStack.pop();

                } catch (EmptyStackException e) {
                    startActivity(new Intent(DropboxPickerActivity.this, NotesActivity.class));
                }

                new DropboxDirectoryListenerAsync(getApplicationContext(), mApi, getCurrentPath(), DropboxPickerActivity.this).execute();
            }
        });
    }

    private void setUpList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_drop_box_directories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mDropBoxAdapter = new DropBoxAdapter(getApplicationContext(), new ArrayList<String>());
        recyclerView.setAdapter(mDropBoxAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.drop_box_directory_name);
                String currentDirectory = textView.getText().toString();
                mDirectoryStack.push(currentDirectory);
                new DropboxDirectoryListenerAsync(getApplicationContext(), mApi, getCurrentPath(), DropboxPickerActivity.this).execute();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    private String getCurrentPath() {
        String path = "";
        for (String p : mDirectoryStack) {
            if (!p.equals("/")) {
                path = path + "/" + p;
            }
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void authenticate() {
        AndroidAuthSession session = DropBoxActions.buildSession(getApplicationContext());
        mApi = new DropboxAPI<AndroidAuthSession>(session);
        mApi.getSession().startOAuth2Authentication(DropboxPickerActivity.this);
        mAfterAuth = true;
        AppSharedPreferences.setPersonalNotesPreferences(getApplicationContext(), AppConstant.DROP_BOX_SELECTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAfterAuth) {
            AndroidAuthSession session = mApi.getSession();
            if (session.authenticationSuccessful()) {
                try {
                    session.finishAuthentication();
                    DropBoxActions.storeAuth(session, getApplicationContext());
                    AppSharedPreferences.isDropboxAuthenticated(getApplicationContext(), true);
                    initProgressDialog();
                    new DropboxDirectoryListenerAsync(getApplicationContext(), mApi, getCurrentPath(), DropboxPickerActivity.this).execute();

                } catch (IllegalStateException e) {
                    showToast("Could not authenticate with dropbox " + e.getLocalizedMessage());
                }
            }
        }
    }

    private void logOut() {
        mApi.getSession().unlink();
        DropBoxActions.clearKeys(getApplicationContext());
    }

    @Override
    public void onDirectoryCreateFinished(String dirName) {
        mDropBoxAdapter.add(dirName);
        mDropBoxAdapter.notifyDataSetChanged();
        TextView path = (TextView) findViewById(R.id.path_display);
        path.setText(getCurrentPath());
        mDialog.dismiss();
    }

    @Override
    public void onLoadFinished(List<String> values) {
        mDropBoxAdapter.setData(values);
        mDropBoxAdapter.notifyDataSetChanged();
        TextView path = (TextView) findViewById(R.id.path_display);
        path.setText(getCurrentPath());
        mDialog.dismiss();
    }

    private void initProgressDialog() {
        mDialog = new ProgressDialog(DropboxPickerActivity.this);
        mDialog.setTitle("Dropbox");
        mDialog.setMessage("Retrieving directories");
        mDialog.show();
    }
}
