package android.fastrack.udacity.easybaking.services;

import android.fastrack.udacity.easybaking.model.Baking;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by winzaldi on 6/27/17.
 */


public interface BakingServices {


    @GET("/android-baking-app-json")
    Call<List<Baking>> getBakingFrom();

}
