package android.fastrack.udacity.easybaking.services;


import android.fastrack.udacity.easybaking.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by winzaldi on 6/28/17.
 */

public class BakingServiceFactory {

    HttpLoggingInterceptor interceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


    final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.URL_BAKING_API)
            .build();
    public BakingServices create(){
        return  retrofit.create(BakingServices.class);
    }

}
