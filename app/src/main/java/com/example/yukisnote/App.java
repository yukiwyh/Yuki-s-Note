package com.example.yukisnote;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orm.SugarApp;

import im.fir.sdk.FIR;

/**
 * Created by 钰辉 on 2015/8/24.
 */
public class App extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        FIR.init(this);
        FIR.addCustomizeValue("yes", "ok");
    }
}
