package com.wahyurhy.moviappandroid.core.data.remote.response.toprated;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseTopRatedMovie{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsItemTopRatedMovie> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<ResultsItemTopRatedMovie> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}