package com.test.model;

public class RoadMap {
	String firstCity;
	String LastCity;
		
	public RoadMap() {
	}
	
	public RoadMap(String soucreCity, String destinationCity ) {
		this.firstCity= soucreCity;
		this.LastCity=destinationCity;
		}
	
	public String getFirstCity() {
		return firstCity;
	}
	public void setFirstCity(String firstCity) {
		this.firstCity = firstCity;
	}
	public String getLastCity() {
		return LastCity;
	}
	public void setLastCity(String lastCity) {
		LastCity = lastCity;
	}

	@Override
	public String toString() {
		return "RoadMap [firstCity=" + firstCity + ", LastCity=" + LastCity + "]";
	}
	

}
