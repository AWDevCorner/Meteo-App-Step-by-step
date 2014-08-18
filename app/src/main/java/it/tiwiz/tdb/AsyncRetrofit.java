package it.tiwiz.tdb;

import android.os.AsyncTask;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;


public class AsyncRetrofit extends AsyncTask<String, Void, List<AsyncRetrofit.Result>>{

    private static final String API_URL = "https://api.metwit.com";

    @Override
    protected List<Result> doInBackground (String... params) {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();

        Metwit metwit = restAdapter.create(Metwit.class);
        List<Result> weathers = metwit.weather(params[0],params[1]);
        return weathers;
    }



    static class Result {
        String weather;
    }

    interface Metwit {
        @GET("/v2/weather/")
        List<Result> weather (
                @Query("location_lat") String latitude,
                @Query("location_lng") String longitude
        );

    };
}
