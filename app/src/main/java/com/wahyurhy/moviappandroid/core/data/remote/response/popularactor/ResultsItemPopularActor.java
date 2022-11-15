package com.wahyurhy.moviappandroid.core.data.remote.response.popularactor;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItemPopularActor {

	@SerializedName("gender")
	private int gender;

	@SerializedName("known_for_department")
	private String knownForDepartment;

	@SerializedName("known_for")
	private List<Object> knownFor;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("name")
	private String name;

	@SerializedName("profile_path")
	private String profilePath;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	public int getGender(){
		return gender;
	}

	public String getKnownForDepartment(){
		return knownForDepartment;
	}

	public List<Object> getKnownFor(){
		return knownFor;
	}

	public double getPopularity(){
		return popularity;
	}

	public String getName(){
		return name;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public int getId(){
		return id;
	}

	public boolean isAdult(){
		return adult;
	}
}