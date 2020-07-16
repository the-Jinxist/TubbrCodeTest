package com.example.tubbrcodetest.new_model;

import com.google.gson.annotations.SerializedName;

public class TrackResponse{

	@SerializedName("tracks")
	private Tracks tracks;

	public Tracks getTracks(){
		return tracks;
	}
}