package com.qilu.ecampus;

import android.app.Application;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.qilu.qilu.app.Qilu;
import com.qilu.qilu.util.storage.QiluPreference;


public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Qilu.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://123.207.13.112:8080/qilu/")
                .withLoaderDelayed(100)
                .configure();
        QiluPreference.addCustomAppProfile("version","ECampus v1.0.0 (Qilu)");

    }
}

