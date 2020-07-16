package com.example.tubbrcodetest.new_model;

public class RequestModel {

    private TrackResponse tracks;
    private RequestStates state;

    public RequestModel(TrackResponse tracks, RequestStates state) {
        this.tracks = tracks;
        this.state = state;
    }

    public TrackResponse getTracks() {
        return tracks;
    }

    public void setTracks(TrackResponse tracks) {
        this.tracks = tracks;
    }

    public RequestStates getState() {
        return state;
    }

    public void setState(RequestStates state) {
        this.state = state;
    }

    public enum RequestStates{
        IDLE,
        LOADING,
        ERROR,
        SUCCESS
    }
}


