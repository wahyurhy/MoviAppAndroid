package com.wahyurhy.moviappandroid.core.data.remote.response.popularmovie;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsePopularMovie{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsItemPopularMovie> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<ResultsItemPopularMovie> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}