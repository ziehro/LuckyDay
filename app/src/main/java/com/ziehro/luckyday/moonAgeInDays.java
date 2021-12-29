package com.ziehro.luckyday;

import android.widget.Toast;

import java.text.DecimalFormat;

public class moonAgeInDays {
    Double moneyIn = 0.0;
    Double moneyOut = 0.0;
    Double percent = 0.0;

    moonAgeInDays(Double moneyIn,Double moneyOut) {
        this.moneyIn += moneyIn;
        this.moneyOut += moneyOut;

    }
    Integer getPercent() {
        percent = this.moneyOut/this.moneyIn*100;
        Integer place = (int)Math.round(percent);
        return place;
    }
}



