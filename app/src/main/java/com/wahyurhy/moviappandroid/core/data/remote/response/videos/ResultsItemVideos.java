package com.wahyurhy.moviappandroid.core.data.remote.response.videos;

import com.google.gson.annotations.SerializedName;

public class ResultsItemVideos {

	@SerializedName("site")
	private String site;

	@SerializedName("size")
	private int size;

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;

	@SerializedName("official")
	private boolean official;

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	@SerializedName("published_at")
	private String publishedAt;

	@SerializedName("iso_639_1")
	private String iso6391;

	@SerializedName("key")
	private String key;

	public String getSite(){
		return site;
	}

	public int getSize(){
		return size;
	}

	public String getIso31661(){
		return iso31661;
	}

	public String getName(){
		return name;
	}

	public boolean isOfficial(){
		return official;
	}

	public String getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public String getPublishedAt(){
		return publishedAt;
	}

	public String getIso6391(){
		return iso6391;
	}

	public String getKey(){
		return key;
	}
}