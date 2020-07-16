package com.example.tubbrcodetest.new_model;

import com.google.gson.annotations.SerializedName;

public class ExternalUrls{

	@SerializedName("spotify")
	private String spotify;

	public String getSpotify(){
		return spotify;
	}
}