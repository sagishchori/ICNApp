package com.sagishchori.icnapp.api;

import com.sagishchori.icnapp.api.interfaces.Get;
import com.sagishchori.icnapp.api.responses.JokeResponse;
import com.sagishchori.icnapp.models.UserDetails;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sagishchori.icnapp.api.ApiConst.BASE_URL;

public class ApiClient {

    private static ApiClient apiClient;
    private static Retrofit retrofit = null;
    private String url;

    public static ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }

        return apiClient;
    }

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    /**
     * Use this to get a random joke.
     *
     * @param userDetails       This class holds all user data needed to pass
     * @param callback
     *
     * @return                  This {@link ApiClient}
     */
    public ApiClient getRandomJoke(UserDetails userDetails, Callback<JokeResponse> callback) {
        Get.JokesInterface jokesInterface = ApiClient.getApiClient().create(Get.JokesInterface.class);
        Call<JokeResponse> call = jokesInterface.getJoke(userDetails.getFirstName(), userDetails.getLastName());
        call.enqueue(callback);

        return this;
    }
}
