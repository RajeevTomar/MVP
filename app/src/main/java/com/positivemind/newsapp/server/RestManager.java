package com.positivemind.newsapp.server;


import com.positivemind.newsapp.data.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    // ---------------------------------------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------------------------------------
    private static final int TIMEOUT_SECONDS = 60;

    // ---------------------------------------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------------------------------------
    private static Retrofit mRetrofitClient;

    public static Retrofit getRetrofitClient() {
        if (mRetrofitClient == null) {
            mRetrofitClient = createRetrofit();
        }
        return mRetrofitClient;

    }


    private static Retrofit createRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add authrization key in header
        httpClient.addInterceptor( chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", Constants.API_KEY)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });
        return new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // <- add this
                .client(httpClient.build())
                .build();
    }

}
