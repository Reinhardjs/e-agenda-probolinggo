package com.karyadev.e_agendaprobolinggo;

import android.app.Application;
import android.widget.Toast;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RxJavaPlugins.setErrorHandler(throwable -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
