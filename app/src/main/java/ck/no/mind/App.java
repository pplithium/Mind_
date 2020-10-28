package ck.no.mind;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App instance;

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
