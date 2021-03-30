package com.example.e_agendaprobolinggo.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.e_agendaprobolinggo.model.response.User;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceUtils {

    public static void removeUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("myData", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = prefs.edit();
        mEditor.remove("user_json");
        mEditor.apply();
    }

    public static void saveUser(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences("myData", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = prefs.edit();
        Gson gson = new Gson();
        String user_json = gson.toJson(user);
        mEditor.putString("user_json", user_json);
        mEditor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("myData", Context.MODE_PRIVATE);
        String user_json = prefs.getString("user_json", null);

        Gson gson = new Gson();
        return gson.fromJson(user_json, User.class);
    }

}
