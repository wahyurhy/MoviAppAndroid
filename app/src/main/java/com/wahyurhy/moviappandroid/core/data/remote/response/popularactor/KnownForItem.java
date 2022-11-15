package com.wahyurhy.moviappandroid.core.data.remote.response.popularactor;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KnownForItem{

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("original_title")
	private String originalTitle;

	@SerializedName("video")
	private boolean video;

	@SerializedName("title")
	private String title;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("release_date")
	private String releaseDate;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	@SerializedName("vote_count")
	private int voteCount;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("name")
	private String name;

	public String getOverview(){
		return overview;
	}

	public String getOriginalLanguage(){
		return originalLanguage;
	}

	public String getOriginalTitle(){
		return originalTitle;
	}

	public boolean isVideo(){
		return video;
	}

	public String getTitle(){
		return title;
	}

	public List<Integer> getGenreIds(){
		return genreIds;
	}

	public String getPosterPath(){
		return posterPath;
	}

	public String getMediaType(){
		return mediaType;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public double getVoteAverage(){
		return voteAverage;
	}

	public int getId(){
		return id;
	}

	public boolean isAdult(){
		return adult;
	}

	public int getVoteCount(){
		return voteCount;
	}

	public String getBackdropPath(){
		return backdropPath;
	}

	public String getFirstAirDate(){
		return firstAirDate;
	}

	public List<String> getOriginCountry(){
		return originCountry;
	}

	public String getOriginalName(){
		return originalName;
	}

	public String getName(){
		return name;
	}
}