package ck.no.mind;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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

    public static void notImplementedCodeToast() {
        Toast.makeText(instance, "This code is not yet implemented.", Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String msg) {
        Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
    }

}
