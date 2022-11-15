package com.wahyurhy.moviappandroid.core.data.remote.response.videos;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseVideos{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<ResultsItemVideos> results;

	public int getId(){
		return id;
	}

	public List<ResultsItemVideos> getResults(){
		return results;
	}
}