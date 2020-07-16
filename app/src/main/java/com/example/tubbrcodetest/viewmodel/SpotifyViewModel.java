package com.example.tubbrcodetest.viewmodel;

import android.util.Log;

import com.example.tubbrcodetest.new_model.RequestModel;
import com.example.tubbrcodetest.new_model.TrackResponse;
import com.example.tubbrcodetest.repo.SpotifyRepo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpotifyViewModel extends ViewModel {

    private MutableLiveData<RequestModel> songData = new MutableLiveData<RequestModel>();
    private SpotifyRepo repo;

    private static final String TAG = "SpotifyViewModel";

    public MutableLiveData<RequestModel> getSongData() {
        return songData;
    }

    public SpotifyViewModel() {
        songData.setValue(new RequestModel( new TrackResponse(), RequestModel.RequestStates.IDLE));
        repo = new SpotifyRepo();
    }

    public void noQueries(){
        songData.setValue(new RequestModel( new TrackResponse(), RequestModel.RequestStates.IDLE));
    }

    public void loadSongs(String query, String accessToken){
        Log.d(TAG, "loadSongs: Loading Songs");
        songData.setValue(new RequestModel(null, RequestModel.RequestStates.LOADING));
        repo.getSongs(query, accessToken).enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                Log.d(TAG, "loadSongs: Loading Songs" + response.code());
                songData.setValue(new RequestModel(response.body(), RequestModel.RequestStates.SUCCESS));
                Log.e(TAG, "onResponse: Gotten Tracks: " + response.body() );
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getLocalizedMessage() + ", " + t.getMessage());
                songData.setValue(new RequestModel(null, RequestModel.RequestStates.ERROR));
            }
        });
    }

    public void load20sSongs(String query, String accessToken){
        Log.d(TAG, "loadSongs: Loading 20s Songs");
        songData.setValue(new RequestModel(null, RequestModel.RequestStates.LOADING));
        repo.get20sSongs(query, accessToken).enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                Log.d(TAG, "loadSongs: Loading 20s Songs" + response.code() + call.request().url());
                songData.setValue(new RequestModel(response.body(), RequestModel.RequestStates.SUCCESS));
                Log.e(TAG, "onResponse: Gotten 20s Tracks: " + response.body().getTracks().getItems().size() );
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: 20s "+ t.getLocalizedMessage() + ", " + t.getMessage());
                songData.setValue(new RequestModel(null, RequestModel.RequestStates.ERROR));
            }
        });
    }


}
