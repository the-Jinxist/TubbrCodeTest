package com.example.tubbrcodetest.api;

import com.example.tubbrcodetest.new_model.TrackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpotifyService {

    @GET("v1/search")
    Call<TrackResponse> getSongsByQuery(
            @Query("q") String query,
            @Query("type") String type,
            @Header("Authorization") String accessToken
    );

    @GET("v1/search")
    Call<TrackResponse> get20sSongsByQuery(
            //Don't forget to add %20year:1980-2020 to the back of the query
            @Query("q") String query,
            @Query("type") String type,
            //type = tracks
            @Header("Authorization") String accessToken
    );
}
