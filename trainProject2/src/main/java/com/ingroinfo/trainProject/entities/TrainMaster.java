package com.ingroinfo.trainProject.entities;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "TrainMasters")
public class TrainMaster extends EntityClass {


	private String numOfStops;

	public String getNumOfStops() {
		return numOfStops;
	}

	public void setNumOfStops(String numOfStops) {
		this.numOfStops = numOfStops;
	}
	
	
	
}
