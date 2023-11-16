package com.ingroinfo.trainProject.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
public class AddTrains extends EntityClass {
	private String trainName;
	private String schedule;
	private String numberOfStops;
	private String trainFrom;
	private String trainTo;
	private String departureDate;
	private String trainFromTiming;
	private String trainToTime;
	private String ticketPrice;
	
	private String message;


	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getNumberOfStops() {
		return numberOfStops;
	}

	public void setNumberOfStops(String numberOfStops) {
		this.numberOfStops = numberOfStops;
	}

	public String getTrainFrom() {
		return trainFrom;
	}

	public void setTrainFrom(String trainFrom) {
		this.trainFrom = trainFrom;
	}

	public String getTrainTo() {
		return trainTo;
	}

	public void setTrainTo(String trainTo) {
		this.trainTo = trainTo;
	}

	public String getTrainFromTiming() {
		return trainFromTiming;
	}

	public void setTrainFromTiming(String trainFromTiming) {
		this.trainFromTiming = trainFromTiming;
	}

	public String getTrainToTime() {
		return trainToTime;
	}

	public void setTrainToTime(String trainToTime) {
		this.trainToTime = trainToTime;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}



	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
