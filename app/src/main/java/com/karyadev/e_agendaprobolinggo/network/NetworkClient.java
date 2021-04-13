package com.karyadev.e_agendaprobolinggo.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class NetworkClient {

    public static final String BASE_URL = "https://protokol.probolinggokab.go.id/e-agenda/api/";
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();

                        Request request = original.newBuilder().header("x-sm-key", "35d3d08c3d7b7f445ceb8e726a87b26c").build();

                        return chain.proceed(request);
                    })
                    .addInterceptor(new BasicAuthInterceptor("sm4rts0ft", "?zwMAxBnS9jj"))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

}
