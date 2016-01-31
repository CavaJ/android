package com.example.animemania.model;

public class AnimeSeries {
	
	private String mSeriesName;
	private String mSeasonName;
	
	public AnimeSeries(String seriesName, String seasonName) {
		mSeriesName = seriesName;
		mSeasonName = seasonName;
	} // AnimeSeries

	public AnimeSeries() {
		
	} // AnimeSeries
	
	public String getSeriesName() {
		return mSeriesName;
	}

	public void setSeriesName(String seriesName) {
		mSeriesName = seriesName;
	}

	public String getSeasonName() {
		return mSeasonName;
	}

	public void setSeasonName(String seasonName) {
		mSeasonName = seasonName;
	}
	
	
} // class AnimeSeries
