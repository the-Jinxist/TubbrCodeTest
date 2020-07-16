package com.example.tubbrcodetest.api;

import com.example.tubbrcodetest.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Retrofit retrofit;

    private Retrofit getClientSongsInstance(){
        synchronized (this){
            if (retrofit == null){
                retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_SONG_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                return retrofit;
            }else{
                return retrofit;
            }
        }
    }

    public SpotifyService getApiSevice(){
        return getClientSongsInstance().create(SpotifyService.class);
    }

}
