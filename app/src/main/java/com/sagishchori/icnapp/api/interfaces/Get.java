package com.sagishchori.icnapp.api.interfaces;

import com.sagishchori.icnapp.api.responses.JokeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.sagishchori.icnapp.api.ApiConst.JOKES;
import static com.sagishchori.icnapp.api.ApiConst.URL_JOKES_LIMITATION;


public class Get {

    public interface JokesInterface {

//        @Headers({
//
//        })
        @GET(JOKES + URL_JOKES_LIMITATION)
        Call<JokeResponse> getJoke(@Query("firstName") String firstName, @Query("lastName") String lastName);

    }
}
