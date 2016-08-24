package com.example.maaz.personalnotesapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by maaz on 7/19/16.
 */
final public class GDUT {

    public GDUT() {}

    private static final String L_TAG = "_";

    static final String MYROOT = "PersonalNotes";
    static final String TEMP_FILENM = "temp";
    static final String JPEG_EXT = AppConstant.JPG;
    static final String MIME_JPEG = "image/jpeg";
    static final String MIME_FOLDER = "application/vnd.google-apps.folder";

    static final String TITLE_FMT = "yyMMdd-HHmmss";

    static final int BUF_SZ = 2048;

    private static GDUT mInst;
    static SharedPreferences pfs;
    static Context acx;

    static GDUT init(Context ctx) {
        if (mInst == null) {
            acx = ctx.getApplicationContext();
            pfs = PreferenceManager.getDefaultSharedPreferences(acx);
            mInst = new GDUT();
        }
        return mInst;
    }

    final static class AM {
        public AM() {
        }

        private static final String ACC_NAME = "account_name";
        static final int FAIL = -1;
        static final int UNCHANGED = 0;
        static final int CHANGED = +1;

        private static String mCurrentEmail = null;

        static Account[] getAllAccounts() {
            return AccountManager.get(GDUT.acx).getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        }

        static Account getPrimaryAccount(boolean bOneOnly) {
            Account[] accts = getAllAccounts();
            if (bOneOnly) {
                return accts == null || accts.length != 1 ? null : accts[0];

            }
            return accts == null || accts.length == 0 ? null : accts[0];
        }

        static Account getActiveAccnt() {
            return email2Account(getActiveEmail());
        }

        static String getActiveEmail() {
            if (mCurrentEmail != null) {
                return mCurrentEmail;
            }
            return GDUT.pfs.getString(ACC_NAME, null);
        }


        static Account email2Account(String email) {
            if (email != null) {
                Account[] accounts = AccountManager.get(GDUT.acx).getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                for (Account account : accounts) {
                    if (email.equalsIgnoreCase(account.name)) {
                        return account;
                    }
                }
            }
            return null;
        }

        static int setEmail(String newEmail) {
            int result = FAIL;

            String prevEmail = getActiveEmail();
            if ((prevEmail == null) && (newEmail != null)) {
                result = CHANGED;
            } else if ((prevEmail != null) && (newEmail == null)) {
                result = UNCHANGED;
            } else if (prevEmail != null) {
                result = prevEmail.equalsIgnoreCase(newEmail) ? UNCHANGED : CHANGED;
            }
            if (result == CHANGED) {
                mCurrentEmail = newEmail;
                GDUT.pfs.edit().putString(ACC_NAME, newEmail).apply();

            }
            return result;

        }

        static void removeActiveAccount() {
            mCurrentEmail = null;
            GDUT.pfs.edit().remove(ACC_NAME).apply();
        }
    }

    static File bytes2File(byte[] buf, File fl) {
        if (buf == null || fl == null)
            return null;
        BufferedOutputStream bs = null;

        try {
            bs = new BufferedOutputStream(new FileOutputStream(fl));
            bs.write(buf);
        } catch (Exception e) {
            le(e);
        } finally {
            if (bs != null)
                try {
                    bs.close();
                } catch (Exception e) {
                    le(e);
                }
        }
        return fl;
    }

    static byte[] file2Bytes(File file) {
        if (file != null)
            try {
                return is2Bytes(new FileInputStream(file));
            } catch (Exception e) {
                le(e);
            }
        return null;
    }

    static byte[] is2Bytes(InputStream is) {
        byte[] buf = null;
        BufferedInputStream budIS = null;
        if (is != null)
            try {
                ByteArrayOutputStream bytesBuffer = new ByteArrayOutputStream();
                budIS = new BufferedInputStream(is);
                buf = new byte[BUF_SZ];
                int cnt;
                while ((cnt = budIS.read(buf)) >= 0) {
                    bytesBuffer.write(buf, 0, cnt);
                }
                buf = bytesBuffer.size() > 0 ? bytesBuffer.toByteArray() : null;
            } catch (Exception e) {
                le(e);
            } finally {
                try {
                    if (budIS != null)
                        budIS.close();
                } catch (Exception e) {
                    le(e);
                }
            }
        return buf;
    }

    static String time2Title(Long milis) {
        Date dt = (milis == null) ? new Date() : (milis >= 0) ? new Date(milis) : null;
        return (dt == null) ? null : new SimpleDateFormat(TITLE_FMT, Locale.US).format(dt);
    }

    static String title2Month(String title) {
        return title == null ? null : ("20" + title.substring(0, 2) + "-" + title.substring(2, 4));
    }

    private static String stack2String(Throwable ex) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        try {
            ex.printStackTrace(printWriter);
            return result.toString();
        } finally {
            printWriter.close();
        }
    }


    static void le(Throwable ex) {
        String msg = (ex == null || ex.getMessage() == null) ? "" : ex.getMessage() + "\n";
        try {
            Log.e(L_TAG, msg + stack2String(ex));
        } catch (Exception e) {

        }
    }
}

























