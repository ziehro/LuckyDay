package com.ziehro.luckyday;

public class moonAgeInDays {
    Double moneyIn = 0.0;
    Double moneyOut = 0.0;
    Double percent = 0.0;

    moonAgeInDays(Double moneyIn,Double moneyOut) {
        this.moneyIn += moneyIn;
        this.moneyOut += moneyOut;

    }
    Double getPercent() {
        this.percent = this.moneyOut/this.moneyIn;
        return this.percent;
    }
}



