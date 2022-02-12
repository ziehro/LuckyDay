package com.ziehro.luckyday;

import android.app.Activity;

public class BaseActivity extends Activity {


    protected static String sheetURL = "https://script.google.com/macros/s/AKfycbxNzPwWEIDEe1hRrit26Lqq2qoY_QwC52bqExkLIoFWwSaH7AO2KJnbGJjFelX_ZS_05w/exec";
    //String uid = "bob";
    String uid = "bob";

    public String getUidVar() {
        return uid;
    }

}