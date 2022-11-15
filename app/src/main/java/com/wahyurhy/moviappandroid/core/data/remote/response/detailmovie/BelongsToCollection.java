package com.wahyurhy.moviappandroid.core.data.remote.response.detailmovie;

import com.google.gson.annotations.SerializedName;

public class BelongsToCollection{

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("poster_path")
	private String posterPath;

	public String getBackdropPath(){
		return backdropPath;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getPosterPath(){
		return posterPath;
	}
}