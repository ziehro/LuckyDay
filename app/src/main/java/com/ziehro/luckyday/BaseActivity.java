package com.ziehro.luckyday;

import android.app.Activity;

public class BaseActivity extends Activity {


    protected static String sheetURL = "https://script.google.com/macros/s/AKfycbxNzPwWEIDEe1hRrit26Lqq2qoY_QwC52bqExkLIoFWwSaH7AO2KJnbGJjFelX_ZS_05w/exec";
    Object moonAge1 = new moonAgeInDays(0.0,0.0);
    protected moonAgeInDays moonDay23;

    {
        moonDay23 = new moonAgeInDays(0.0, 0.0);
    }
}