package com.example.tubbrcodetest.repo;

import com.example.tubbrcodetest.Constants;
import com.example.tubbrcodetest.api.RetrofitClient;
import com.example.tubbrcodetest.api.SpotifyService;
import com.example.tubbrcodetest.new_model.TrackResponse;

import retrofit2.Call;

public class SpotifyRepo {

    private SpotifyService client = new RetrofitClient().getApiSevice();

    public Call<TrackResponse> getSongs(String query, String accessToken){
        return client.getSongsByQuery(query, "track", "Bearer " + accessToken);
    }

    public Call<TrackResponse> get20sSongs(String query, String accessToken){
        return client.get20sSongsByQuery(query+ Constants.CENTURY_LIMITER, "track", "Bearer " + accessToken);
    }

}
