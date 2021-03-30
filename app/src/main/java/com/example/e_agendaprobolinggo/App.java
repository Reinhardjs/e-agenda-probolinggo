package com.example.e_agendaprobolinggo;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class App extends Application {

    private static Context context = null;

    @Override
    public void onCreate(){
        super.onCreate();

        context = getApplicationContext();
        RxJavaPlugins.setErrorHandler(throwable -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public static Context getAppContext(){
        return context;
    }
}
